
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
public class AdministratorBannerCreateService extends AbstractService<Administrator, Banner> {

	@Autowired
	protected AdministratorBannerRepository repository;


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
		Banner object;
		Date moment;

		moment = MomentHelper.getCurrentMoment();

		object = new Banner();
		object.setInstantiationMoment(moment);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Banner object) {
		assert object != null;
		Date moment;

		moment = MomentHelper.getCurrentMoment();

		super.bind(object, "displayPeriodStart", "displayPeriodEnd", "pictureLink", "slogan", "targetLink");
		object.setInstantiationMoment(moment);
	}
	@Override
	public void validate(final Banner object) {
		assert object != null;
		if (!super.getBuffer().getErrors().hasErrors("displayPeriodEnd"))
			super.state(MomentHelper.isAfter(object.getDisplayPeriodEnd(), object.getDisplayPeriodStart()), "displayPeriodEnd", "administrator.banner.form.error.display-period-end-before-display");
		{
			Date actualDate = object.getDisplayPeriodStart();

			actualDate = MomentHelper.deltaFromCurrentMoment(7, ChronoUnit.DAYS);
			super.state(MomentHelper.isAfter(object.getDisplayPeriodEnd(), actualDate), "*", "administrator.banner.form.error.display-period-time");
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
		Date moment;

		moment = MomentHelper.getCurrentMoment();

		Tuple tuple;

		tuple = super.unbind(object, "displayPeriodStart", "displayPeriodEnd", "pictureLink", "slogan", "targetLink");
		tuple.put("instantiationMoment", moment);

		super.getResponse().setData(tuple);
	}
}
