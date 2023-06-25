
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
public class AdministratorOfferUpdateService extends AbstractService<Administrator, Offer> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AdministratorOfferRepository repository;

	// AbstractService interface ----------------------------------------------


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
		Offer Offer;

		masterId = super.getRequest().getData("id", int.class);
		Offer = this.repository.findOneOfferById(masterId);
		status = Offer != null && super.getRequest().getPrincipal().isAuthenticated();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Offer object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneOfferById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Offer object) {
		assert object != null;

		super.bind(object, "instantiationMoment", "heading", "summary", "availabilityPeriodStart", "availabilityPeriodEnd", "price", "link");
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

		this.repository.save(object);
	}
	@Override
	public void unbind(final Offer object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "instantiationMoment", "heading", "summary", "availabilityPeriodStart", "availabilityPeriodEnd", "price", "link");
		super.getResponse().setData(tuple);
	}
}
