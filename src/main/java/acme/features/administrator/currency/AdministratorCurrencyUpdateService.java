
package acme.features.administrator.currency;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.currency.Currency;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AdministratorCurrencyUpdateService extends AbstractService<Administrator, Currency> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AdministratorCurrencyRepository repository;

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
		Currency object;

		object = this.repository.findCurrency();

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Currency object) {
		assert object != null;

		super.bind(object, "systemCurrency", "acceptedCurrencies");

	}

	@Override
	public void validate(final Currency object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("systemCurrency"))
			super.state(object.getSystemCurrency().matches("^[a-zA-Z]{3}$"), "systemCurrency", "administrator.currency.form.error.non-string-system-currency");
		if (!super.getBuffer().getErrors().hasErrors("acceptedCurrencies"))
			super.state(object.getAcceptedCurrencies().matches("^([a-zA-Z]{3},)*[a-zA-Z]{3}$"), "acceptedCurrencies", "administrator.currency.form.error.non-string-accepted-currencies");

	}

	@Override
	public void perform(final Currency object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Currency object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "systemCurrency", "acceptedCurrencies");

		super.getResponse().setData(tuple);
	}

}
