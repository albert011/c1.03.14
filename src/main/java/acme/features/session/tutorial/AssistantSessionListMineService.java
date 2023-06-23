
package acme.features.session.tutorial;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.session.SessionTutorial;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantSessionListMineService extends AbstractService<Assistant, SessionTutorial> {

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
		Collection<SessionTutorial> sessions;
		Assistant assistant;
		int assistantId;

		assistantId = super.getRequest().getPrincipal().getAccountId();
		assistant = this.repository.findAssistant(assistantId);
		sessions = this.repository.findSessionsCreatedByAssistant(assistant);
		super.getBuffer().setData(sessions);

	}

	@Override
	public void unbind(final SessionTutorial session) {
		assert session != null;
		Tuple tuple;

		tuple = super.unbind(session, "title", "abstractMessage", "timeStart", "timeEnd", "type", "draftMode");

		super.getResponse().setData(tuple);

	}

}
