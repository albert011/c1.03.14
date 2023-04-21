
package acme.features.messages.peep;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.messages.PeepMessage;
import acme.framework.components.accounts.Any;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class PeepMessageListService extends AbstractService<Any, PeepMessage> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected PeepMessageRepository repository;

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
		Collection<PeepMessage> peeps;
		peeps = this.repository.findAllPeeps();
		super.getBuffer().setData(peeps);
	}

	@Override
	public void unbind(final PeepMessage peep) {
		assert peep != null;
		Tuple tuple;
		tuple = super.unbind(peep, "title", "instantiation", "nickname", "link", "email", "message");
		super.getResponse().setData(tuple);
	}

}
