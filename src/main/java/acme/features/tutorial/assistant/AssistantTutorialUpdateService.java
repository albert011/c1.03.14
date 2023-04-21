
package acme.features.tutorial.assistant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.tutorial.Tutorial;
import acme.framework.components.models.Dataset;
import acme.framework.components.models.Tuple;
import acme.framework.controllers.HttpMethod;
import acme.framework.helpers.PrincipalHelper;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantTutorialUpdateService extends AbstractService<Assistant, Tutorial> {

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
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Assistant.class);

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

		super.bind(tutorial, "title", "abstractMessage", "goals", "estimatedTotalTime", "isPublished");
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

		tuple = super.unbind(tutorial, "code", "title", "abstractMessage", "goals", "estimatedTotalTime");

		super.getResponse().setData(tuple);
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals(HttpMethod.POST))
			PrincipalHelper.handleUpdate();
	}

}
