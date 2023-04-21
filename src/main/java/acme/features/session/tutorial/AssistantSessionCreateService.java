
package acme.features.session.tutorial;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.session.SessionTutorial;
import acme.entities.session.SessionType;
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
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int id;
		SessionTutorial session;

		id = super.getRequest().getData("id", int.class);
		session = this.repository.findSessionById(id);
		status = session != null && super.getRequest().getPrincipal().hasRole(session.getTutorial().getAssistant());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		SessionTutorial session;
		Tutorial tutorial;
		int tutorialId;
		SessionType type;
		int typeId;
		Date timeStart;
		Date timeEnd;

		tutorialId = super.getRequest().getData("tutorialId", int.class);
		tutorial = this.repository.findTutorialById(tutorialId);

		typeId = super.getRequest().getData("typeId", int.class);
		type = this.repository.findTypeOfSessionById(typeId);

		timeStart = new Date();
		timeEnd = new Date();

		session = new SessionTutorial();
		session.setTitle("");
		session.setAbstractMessage("");
		session.setSessionType(type);
		session.setTimeStart(timeStart);
		session.setTimeEnd(timeEnd);
		session.setLink(null);
		session.setTutorial(tutorial);

		super.getBuffer().setData(session);
	}

	@Override
	public void bind(final SessionTutorial session) {
		assert session != null;

		super.bind(session, "title", "abstractMessage", "sessionType", "timeStart", "timeEnd", "link");
	}

	@Override
	public void validate(final SessionTutorial session) {
		assert session != null;
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
