
package acme.features.administrator.banner;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.banner.Banner;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;

@Service
public class AdministratorBannerUpdateService extends AbstractService<Administrator, Banner> {

	@Autowired
	protected AdministratorBannerRepository repository;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int masterId;
		Banner banner;

		masterId = super.getRequest().getData("id", int.class);
		banner = this.repository.findOneBannerById(masterId);
		status = banner != null && super.getRequest().getPrincipal().isAuthenticated();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Banner object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneBannerById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Banner object) {
		assert object != null;

		super.bind(object, "instantiationMoment", "displayPeriodStart", "displayPeriodEnd", "pictureLink", "slogan", "targetLink");
	}

	@Override
	public void validate(final Banner object) {
		assert object != null;
		if (!super.getBuffer().getErrors().hasErrors("displayPeriodEnd"))
			super.state(MomentHelper.isAfter(object.getDisplayPeriodEnd(), object.getDisplayPeriodStart()), "displayPeriodEnd", "administrator.banner.form.error.display-period-end-before-display");
		{
			final Date displayStart = object.getDisplayPeriodStart();
			Date actualDate;

			actualDate = MomentHelper.deltaFromMoment(displayStart, 1, ChronoUnit.WEEKS);
			super.state(MomentHelper.isAfterOrEqual(object.getDisplayPeriodEnd(), actualDate), "*", "administrator.banner.form.error.display-period-time");
		}

	}

	@Override
	public void perform(final Banner object) {
		assert object != null;

		this.repository.save(object);
	}
	@Override
	public void unbind(final Banner object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "instantiationMoment", "displayPeriodStart", "displayPeriodEnd", "pictureLink", "slogan", "targetLink");
		super.getResponse().setData(tuple);
	}
}
