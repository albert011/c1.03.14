
package acme.features.administrator.offer;

import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.offers.Offer;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;

@Service
public class AdministratorOfferCreateService extends AbstractService<Administrator, Offer> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AdministratorOfferRepository repository;

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
		Offer object;
		Date moment;

		moment = MomentHelper.getCurrentMoment();

		object = new Offer();
		object.setInstantiationMoment(moment);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Offer object) {
		assert object != null;

		super.bind(object, "heading", "summary", "availabilityPeriodStart", "availabilityPeriodEnd", "price", "link");
	}

	@Override
	public void validate(final Offer object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("price"))
			super.state(object.getPrice().getAmount() > 0, "price", "administrator.offer.form.error.price.negative-or-zero");

		if (!super.getBuffer().getErrors().hasErrors("price")) {
			Collection<String> currencySystemConfiguration;
			currencySystemConfiguration = this.repository.findAllCurrencySystemConfiguration();

			super.state(currencySystemConfiguration.contains(object.getPrice().getCurrency()), "price", "administrator.offer.form.error.price.non-existent-currency");
		}

		if (!super.getBuffer().getErrors().hasErrors("availabilityPeriodStart")) {
			Date dayAfterInstatiation;
			dayAfterInstatiation = MomentHelper.deltaFromMoment(object.getInstantiationMoment(), 1, ChronoUnit.DAYS);

			super.state(MomentHelper.isAfterOrEqual(object.getAvailabilityPeriodStart(), dayAfterInstatiation), "availabilityPeriodStart", "administrator.offer.form.error.less-than-a-day-after-instantiation");
		}

		if (!super.getBuffer().getErrors().hasErrors("availabilityPeriodStart") && !super.getBuffer().getErrors().hasErrors("availabilityPeriodEnd")) {
			Date minimumPeriod;
			minimumPeriod = MomentHelper.deltaFromMoment(object.getAvailabilityPeriodStart(), 7, ChronoUnit.DAYS);

			super.state(MomentHelper.isAfterOrEqual(object.getAvailabilityPeriodEnd(), minimumPeriod), "availabilityPeriodEnd", "administrator.offer.form.error.period-too-short");
		}

	}

	@Override
	public void perform(final Offer object) {
		assert object != null;

		Date moment;

		moment = MomentHelper.getCurrentMoment();
		object.setInstantiationMoment(moment);
		this.repository.save(object);
	}

	@Override
	public void unbind(final Offer object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "heading", "summary", "availabilityPeriodStart", "availabilityPeriodEnd", "price", "link");

		super.getResponse().setData(tuple);
	}

}
