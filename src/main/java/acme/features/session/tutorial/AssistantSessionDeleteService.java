
package acme.features.session.tutorial;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.session.SessionTutorial;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantSessionDeleteService extends AbstractService<Assistant, SessionTutorial> {

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
		status = session != null && super.getRequest().getPrincipal().hasRole(session.getTutorial().getAssistant()) && session.isDraftMode();
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

		super.bind(session, "title", "abstractMessage", "timeStart", "timeEnd", "link");
	}

	@Override
	public void validate(final SessionTutorial session) {
		assert session != null;

	}

	@Override
	public void perform(final SessionTutorial session) {
		assert session != null;

		this.repository.delete(session);
	}

	@Override
	public void unbind(final SessionTutorial session) {
		assert session != null;

		Tuple tuple;

		tuple = super.unbind(session, "title", "abstractMessage", "type");

		super.getResponse().setData(tuple);
	}

}
