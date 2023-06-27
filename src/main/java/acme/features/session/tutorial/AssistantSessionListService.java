
package acme.features.session.tutorial;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.session.SessionTutorial;
import acme.entities.tutorial.Tutorial;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantSessionListService extends AbstractService<Assistant, SessionTutorial> {

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
		Tutorial tutorial;
		int tutorialId;

		tutorialId = super.getRequest().getData("tutorialId", int.class);
		tutorial = this.repository.findTutorialById(tutorialId);
		assert tutorial != null;
		assert super.getRequest().getPrincipal().getAccountId() == tutorial.getAssistant().getUserAccount().getId();
		sessions = this.repository.findAllSessionsByTutorial(tutorial);
		super.getBuffer().setData(sessions);
	}

	@Override
	public void bind(final SessionTutorial session) {
		assert session != null;

		int tutorialId;
		Tutorial tutorial;

		tutorialId = tutorialId = super.getRequest().getData("tutorialId", int.class);
		tutorial = this.repository.findTutorialById(tutorialId);
		super.bind(session, "title", "abstractMessage", "timeStart", "timeEnd");
	}

	@Override
	public void validate(final SessionTutorial session) {
		assert session != null;
	}

	//bind: datos formulario
	//unbind: datos que se muestran
	@Override
	public void unbind(final SessionTutorial session) {
		assert session != null;
		Tuple tuple;

		tuple = super.unbind(session, "title", "abstractMessage", "timeStart", "timeEnd", "type", "draftMode");
		super.getResponse().setData(tuple);
	}

}
