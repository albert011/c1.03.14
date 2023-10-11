
package acme.features.any.peep;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.peep.PeepMessage;
import acme.framework.components.accounts.Any;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AnyPeepMessageShowService extends AbstractService<Any, PeepMessage> {

	@Autowired
	protected AnyPeepMessageRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		boolean status;
		int id;
		PeepMessage peep;

		id = super.getRequest().getData("id", int.class);
		peep = this.repository.findOnePeepById(id);
		status = peep != null;

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		PeepMessage peep;
		int id;

		id = super.getRequest().getData("id", int.class);
		peep = this.repository.findOnePeepById(id);

		super.getBuffer().setData(peep);
	}

	@Override
	public void unbind(final PeepMessage peep) {
		assert peep != null;
		Tuple tuple;
		tuple = super.unbind(peep, "title", "message", "instantiation", "nickname", "email", "link");
		super.getResponse().setData(tuple);
	}

}
