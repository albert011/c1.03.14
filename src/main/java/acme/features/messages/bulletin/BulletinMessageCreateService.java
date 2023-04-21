
package acme.features.messages.bulletin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.messages.BulletinMessage;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Dataset;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class BulletinMessageCreateService extends AbstractService<Authenticated, BulletinMessage> {

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
		BulletinMessage bulletin;

		bulletin = new BulletinMessage();

		super.getBuffer().setData(bulletin);
	}

	@Override
	public void bind(final BulletinMessage bulletin) {
		assert bulletin != null;
		super.bind(bulletin, "title", "message", "instantiation", "isCritical", "link");
	}

	@Override
	public void validate(final BulletinMessage bulletin) {
		assert bulletin != null;
	}

	@Override
	public void perform(final BulletinMessage bulletin) {
		assert bulletin != null;
		final Dataset req = super.getRequest().getData();
		if (req.containsKey("isCritical") && req.get("isCritical").toString().equals("true"))
			bulletin.setCritical(true);

		this.repository.save(bulletin);
	}

	@Override
	public void unbind(final BulletinMessage bulletin) {
		assert bulletin != null;
		Tuple tuple;
		tuple = super.unbind(bulletin, "title", "message", "instantiation", "isCritical");
		super.getResponse().setData(tuple);
	}
}
