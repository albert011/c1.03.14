
package acme.features.messages.bulletin;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.messages.BulletinMessage;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class BulletinMessageListService extends AbstractService<Authenticated, BulletinMessage> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected BulletinMessageRepository repository;

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
		Collection<BulletinMessage> bulletins;
		bulletins = this.repository.findAllBulletins();
		super.getBuffer().setData(bulletins);
	}

	@Override
	public void unbind(final BulletinMessage bulletin) {
		assert bulletin != null;
		Tuple tuple;
		tuple = super.unbind(bulletin, "title", "message", "instantiation", "isCritical");
		super.getResponse().setData(tuple);
	}
}
