
package acme.features.tutorial.assistant;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.session.SessionTutorial;
import acme.entities.tutorial.Tutorial;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantTutorialDeleteService extends AbstractService<Assistant, Tutorial> {
	// Internal state ---------------------------------------------------------

	@Autowired
	protected AssistantTutorialRepository repository;

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
		Tutorial tutorial;

		id = super.getRequest().getData("id", int.class);
		tutorial = this.repository.findTutorialById(id);
		status = tutorial != null && super.getRequest().getPrincipal().hasRole(tutorial.getAssistant()) && !tutorial.isPublished();
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Tutorial tutorial;
		int id;

		id = super.getRequest().getData("id", int.class);
		tutorial = this.repository.findTutorialById(id);

		super.getBuffer().setData(tutorial);
	}

	@Override
	public void bind(final Tutorial tutorial) {
		assert tutorial != null;

		super.bind(tutorial, "code", "title", "abstractMessage", "goals", "estimatedTotalTime");
	}

	@Override
	public void validate(final Tutorial tutorial) {
		assert tutorial != null;

		final Collection<SessionTutorial> sessions = this.repository.findSessionsOfTutorial(tutorial);
		super.state(sessions.stream().allMatch(p -> p.isDraftMode()), "*", "tutorial-delete-error-session-published");

	}

	@Override
	public void perform(final Tutorial tutorial) {
		assert tutorial != null;

		final Collection<SessionTutorial> sessions = this.repository.findSessionsOfTutorial(tutorial);
		this.repository.deleteAll(sessions);

		this.repository.delete(tutorial);
	}

	@Override
	public void unbind(final Tutorial tutorial) {
		assert tutorial != null;

		Tuple tuple;

		tuple = super.unbind(tutorial, "code", "title", "abstractMessage");

		super.getResponse().setData(tuple);
	}

}
