
package acme.features.tutorial.assistant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.tutorial.Tutorial;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantTutorialShowService extends AbstractService<Assistant, Tutorial> {

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
		int id;
		Tutorial tutorial;

		id = super.getRequest().getData("id", int.class);
		tutorial = this.repository.findTutorialById(id);
		status = tutorial != null;

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
	public void unbind(final Tutorial tutorial) {
		assert tutorial != null;
		Tuple tuple;
		tuple = super.unbind(tutorial, "code", "title", "abstractMessage", "goals", "estimatedTotalTime", "isPublished");
		super.getResponse().setData(tuple);
	}

}
