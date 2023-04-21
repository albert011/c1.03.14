
package acme.features.tutorial.assistant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.tutorial.Tutorial;
import acme.framework.components.models.Dataset;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantTutorialCreateService extends AbstractService<Assistant, Tutorial> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AssistantTutorialRepository repository;

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
		Tutorial tutorial;
		Assistant assistant;
		int assistantId;

		assistantId = super.getRequest().getPrincipal().getAccountId();
		assistant = this.repository.findAssistant(assistantId);

		tutorial = new Tutorial();
		tutorial.setCode("");
		tutorial.setTitle("");
		tutorial.setAbstractMessage("");
		tutorial.setGoals("");
		tutorial.setEstimatedTotalTime(0.);
		tutorial.setAssistant(assistant);

		super.getBuffer().setData(tutorial);
	}

	@Override
	public void bind(final Tutorial tutorial) {
		assert tutorial != null;

		super.bind(tutorial, "code", "title", "abstractMessage", "goals", "estimatedTotalTime", "isPublished");
	}

	@Override
	public void validate(final Tutorial tutorial) {
		assert tutorial != null;
	}

	@Override
	public void perform(final Tutorial tutorial) {
		assert tutorial != null;
		final Dataset req = super.getRequest().getData();
		if (req.containsKey("isPublished") && req.get("isPublished").toString().equals("true"))
			tutorial.setPublished(true);

		this.repository.save(tutorial);
	}

	@Override
	public void unbind(final Tutorial tutorial) {
		assert tutorial != null;

		Tuple tuple;

		tuple = super.unbind(tutorial, "code", "title", "abstractMessage");

		super.getResponse().setData(tuple);
	}

}
