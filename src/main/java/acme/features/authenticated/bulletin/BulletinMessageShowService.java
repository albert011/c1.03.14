
package acme.features.authenticated.bulletin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.bulletin.BulletinMessage;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class BulletinMessageShowService extends AbstractService<Authenticated, BulletinMessage> {

	@Autowired
	protected BulletinMessageRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		boolean status;
		int id;
		BulletinMessage bulletin;

		id = super.getRequest().getData("id", int.class);
		bulletin = this.repository.findOneBulletinById(id);
		status = bulletin != null;

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		BulletinMessage bulletin;
		int id;

		id = super.getRequest().getData("id", int.class);
		bulletin = this.repository.findOneBulletinById(id);

		super.getBuffer().setData(bulletin);
	}

	@Override
	public void unbind(final BulletinMessage bulletin) {
		assert bulletin != null;
		Tuple tuple;
		tuple = super.unbind(bulletin, "title", "message", "instantiation", "isCritical");
		super.getResponse().setData(tuple);
	}

}
