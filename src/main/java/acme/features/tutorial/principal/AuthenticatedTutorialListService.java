
package acme.features.tutorial.principal;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.tutorial.Tutorial;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedTutorialListService extends AbstractService<Authenticated, Tutorial> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuthenticatedTutorialRepository repository;

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

		tutorials = this.repository.findAllTutorials();
		super.getBuffer().setData(tutorials);
	}

	@Override
	public void unbind(final Tutorial tutorial) {
		assert tutorial != null;
		Tuple tuple;
		tuple = super.unbind(tutorial, "code", "title", "abstractMessage", "goals", "estimatedTotalTime");
		tuple.put("assistant", tutorial.getAssistant().getUserAccount().getUsername());
		super.getResponse().setData(tuple);
	}

}
