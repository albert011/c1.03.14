
package acme.features.session.tutorial;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.course.LectureType;
import acme.entities.session.SessionTutorial;
import acme.entities.tutorial.Tutorial;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantSessionShowService extends AbstractService<Assistant, SessionTutorial> {

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
		int id;
		SessionTutorial session;

		id = super.getRequest().getData("id", int.class);
		session = this.repository.findSessionById(id);
		status = session != null;

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

		tuple = super.unbind(session, "title", "abstractMessage", "type", "timeStart", "timeEnd", "link", "draftMode");
		tuple.put("tutorial", choices.getSelected().getLabel());
		tuple.put("tutorials", choices);
		tuple.put("types", types);

		super.getResponse().setData(tuple);
	}

}
