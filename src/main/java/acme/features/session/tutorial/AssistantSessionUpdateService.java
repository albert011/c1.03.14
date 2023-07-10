
package acme.features.session.tutorial;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.course.LectureType;
import acme.entities.session.SessionTutorial;
import acme.entities.tutorial.Tutorial;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.controllers.HttpMethod;
import acme.framework.helpers.MomentHelper;
import acme.framework.helpers.PrincipalHelper;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantSessionUpdateService extends AbstractService<Assistant, SessionTutorial> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AssistantSessionRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Assistant.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		SessionTutorial session;
		int id;
		id = super.getRequest().getData("id", int.class);
		session = this.repository.findSessionById(id);

		super.getBuffer().setData(session);
	}

	@Override
	public void bind(final SessionTutorial session) {
		assert session != null;

		int tutorialId;
		Tutorial tutorial;

		tutorialId = super.getRequest().getData("tutorial", int.class);
		tutorial = this.repository.findTutorialById(tutorialId);

		super.bind(session, "title", "abstractMessage", "timeStart", "timeEnd", "link");
		session.setTutorial(tutorial);
	}

	@Override
	public void validate(final SessionTutorial session) {
		assert session != null;

		if (!super.getBuffer().getErrors().hasErrors("tutorial"))
			super.state(!session.getTutorial().isPublished(), "tutorial", "assistant.session-tutorial.form.error.published");

		if (!(super.getBuffer().getErrors().hasErrors("timeStart") || super.getBuffer().getErrors().hasErrors("timeEnd"))) {

			if (session.getTimeStart().before(session.getTimeEnd())) {
				super.state(session.getDuration() >= 1.0 && session.getDuration() <= 5.0, "timeStart", "assistant.session-tutorial.form.error.duration");
				super.state(session.getDuration() >= 1.0 && session.getDuration() <= 5.0, "timeEnd", "assistant.session-tutorial.form.error.duration");

			}
			super.state(session.getTimeStart().before(session.getTimeEnd()), "timeStart", "assistant.session-tutorial.form.error.start-is-after-end");
			super.state(session.getTimeStart().before(session.getTimeEnd()), "timeEnd", "assistant.session-tutorial.form.error.end-is-before-start");

			final Date currentDay = MomentHelper.getCurrentMoment();
			super.state(session.getTimeStart().after(currentDay), "timeStart", "assistant.session-tutorial.form.error.start-is-before-current-moment");
			super.state(MomentHelper.computeDuration(currentDay, session.getTimeStart()).toHours() > 24, "timeStart", "assistant.session-tutorial.form.error.start-is-before-current-moment");
		}
	}

	@Override
	public void perform(final SessionTutorial session) {
		assert session != null;

		this.repository.save(session);
	}

	@Override
	public void unbind(final SessionTutorial session) {
		assert session != null;

		Tuple tuple;
		Collection<Tutorial> tutorials;
		SelectChoices choices;
		SelectChoices types;
		Assistant assistant;

		assistant = this.repository.findAssistant(super.getRequest().getPrincipal().getAccountId());
		tutorials = session.getTutorial().isPublished() ? this.repository.findAllTutorialsByAssistant(assistant) : this.repository.findAllNotPublishedTutorialsByAssistant(assistant);
		choices = SelectChoices.from(tutorials, "code", session.getTutorial());
		types = SelectChoices.from(LectureType.class, session.getType());

		tuple = super.unbind(session, "title", "abstractMessage", "type", "timeStart", "timeEnd", "link");
		tuple.put("tutorial", choices.getSelected().getLabel());
		tuple.put("tutorials", choices);
		tuple.put("types", types);

		super.getResponse().setData(tuple);
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals(HttpMethod.POST))
			PrincipalHelper.handleUpdate();
	}

}
