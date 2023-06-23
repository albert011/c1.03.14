
package acme.features.tutorial.assistant;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.tutorial.Tutorial;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantTutorialListService extends AbstractService<Assistant, Tutorial> {

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
		Collection<Tutorial> tutorials;
		Assistant assistant;
		int assistantId;

		assistantId = super.getRequest().getPrincipal().getAccountId();
		assistant = this.repository.findAssistant(assistantId);
		tutorials = this.repository.findAllTutorialsByAssistant(assistant);
		super.getBuffer().setData(tutorials);
	}

	//bind: datos formulario
	//unbind: datos que se muestran
	@Override
	public void unbind(final Tutorial tutorial) {
		assert tutorial != null;
		Tuple tuple;
		tuple = super.unbind(tutorial, "code", "title", "estimatedTotalTime", "isPublished", "abstractMessage");
		super.getResponse().setData(tuple);
	}

}
