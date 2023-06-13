
package acme.features.session.tutorial;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.session.SessionTutorial;
import acme.entities.tutorial.Tutorial;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantSessionCreateService extends AbstractService<Assistant, SessionTutorial> {
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
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		SessionTutorial session;

		session = new SessionTutorial();
		session.setDraftMode(true);
		super.getBuffer().setData(session);
	}

	@Override
	public void bind(final SessionTutorial session) {
		assert session != null;

		int tutorialId;
		Tutorial tutorial;

		tutorialId = super.getRequest().getData("tutorial", int.class);
		tutorial = this.repository.findTutorialById(tutorialId);

		super.bind(session, "title", "abstractMessage", "sessionType", "timeStart", "timeEnd", "link");
		session.setTutorial(tutorial);
	}

	@Override
	public void validate(final SessionTutorial session) {
		assert session != null;
		assert session.getDuration() >= 1.0;
		assert session.getDuration() <= 5.0;
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

		tuple = super.unbind(session, "title", "abstractMessage", "sessionType");

		super.getResponse().setData(tuple);
	}

}
