
package acme.features.session.tutorial;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.session.SessionTutorial;
import acme.framework.components.models.Tuple;
import acme.framework.controllers.HttpMethod;
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

		super.bind(session, "title", "abstractMessage", "timeStart", "timeEnd", "link");
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

		tuple = super.unbind(session, "title", "abstractMessage");

		super.getResponse().setData(tuple);
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals(HttpMethod.POST))
			PrincipalHelper.handleUpdate();
	}

}
