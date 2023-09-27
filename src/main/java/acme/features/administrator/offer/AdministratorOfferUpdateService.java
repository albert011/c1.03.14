
package acme.features.administrator.offer;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
		int offerId;
		Offer offer;

		offerId = super.getRequest().getData("id", int.class);
		offer = this.repository.findOneOfferById(offerId);
		status = offer != null && super.getRequest().getPrincipal().hasRole(Administrator.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {

		Date moment;
		Offer offer;
		int offerId;

		offerId = super.getRequest().getData("id", int.class);
		offer = this.repository.findOneOfferById(offerId);
		moment = MomentHelper.getCurrentMoment();
		offer.setInstantiationMoment(moment);

		super.getBuffer().setData(offer);
	}

	@Override
	public void bind(final Offer object) {
		assert object != null;

		super.bind(object, "instantiationMoment", "heading", "summary", "availabilityPeriodStart", "availabilityPeriodEnd", "price", "link");
	}

	@Override
	public void validate(final Offer object) {
		assert object != null;

		// comprueba que inicio sea como minimo un dia despues de instanciacion

		if (!super.getBuffer().getErrors().hasErrors("availabilityPeriodStart")) {
			Date dayAfterInstatiation;
			dayAfterInstatiation = MomentHelper.deltaFromMoment(object.getInstantiationMoment(), 1, ChronoUnit.DAYS);

			super.state(MomentHelper.isAfterOrEqual(object.getAvailabilityPeriodStart(), dayAfterInstatiation), "availabilityPeriodStart", "administrator.offer.form.error.less-than-a-day-after-instantiation");
		}

		// comprueba que inicio no sea antes de instanciacion

		if (!super.getBuffer().getErrors().hasErrors("availabilityPeriodStart")) {
			boolean startDateStatus;

			startDateStatus = MomentHelper.isAfter(object.getAvailabilityPeriodStart(), object.getInstantiationMoment());

			super.state(startDateStatus, "availabilityPeriodStart", "administrator.offer.error.availabilityPeriodStart.start-before-instantiation");
		}

		// comprueba que fin no sea antes de instanciacion

		if (!super.getBuffer().getErrors().hasErrors("availabilityPeriodEnd")) {
			boolean endDateStatus;

			endDateStatus = MomentHelper.isAfter(object.getAvailabilityPeriodEnd(), object.getInstantiationMoment());

			super.state(endDateStatus, "availabilityPeriodEnd", "administrator.offer.error.availabilityPeriodEnd.end-before-instantiation");
		}

		if (!super.getBuffer().getErrors().hasErrors("availabilityPeriodStart") && !super.getBuffer().getErrors().hasErrors("availabilityPeriodEnd")) {
			boolean isStartBeforeEnd;

			isStartBeforeEnd = MomentHelper.isBefore(object.getAvailabilityPeriodStart(), object.getAvailabilityPeriodEnd());

			super.state(isStartBeforeEnd, "availabilityPeriodEnd", "administrator.offer.form.error.end-before-start");
		}

		// comprueba que la oferta dure como minimo una semana

		if (!super.getBuffer().getErrors().hasErrors("availabilityPeriodStart") && !super.getBuffer().getErrors().hasErrors("availabilityPeriodEnd")) {
			Date minimumPeriod;
			minimumPeriod = MomentHelper.deltaFromMoment(object.getAvailabilityPeriodStart(), 7, ChronoUnit.DAYS);

			super.state(MomentHelper.isAfterOrEqual(object.getAvailabilityPeriodEnd(), minimumPeriod), "availabilityPeriodEnd", "administrator.offer.form.error.period-too-short");
		}

		// comprueba que precio sea mayor a 0

		if (!super.getBuffer().getErrors().hasErrors("price"))
			super.state(object.getPrice().getAmount() > 0, "price", "administrator.offer.form.error.price.negative-or-zero");

		// comprueba que la moneda este entre las aceptadas en el sistema

		if (!super.getBuffer().getErrors().hasErrors("price")) {
			String currencySystemConfiguration;
			currencySystemConfiguration = this.repository.findAllCurrencySystemConfiguration();
			List<String> split = new ArrayList<>();
			split = Arrays.asList(currencySystemConfiguration.split(","));

			super.state(split.contains(object.getPrice().getCurrency()), "price", "administrator.offer.form.error.price.non-existent-currency");
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
