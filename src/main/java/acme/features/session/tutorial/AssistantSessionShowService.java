
package acme.features.session.tutorial;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.session.SessionTutorial;
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
		tuple = super.unbind(session, "title", "abstractMessage", "sessionType");
		super.getResponse().setData(tuple);
	}

}
