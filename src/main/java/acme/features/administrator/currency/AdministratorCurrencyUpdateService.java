
package acme.features.administrator.currency;

import java.util.Collection;

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

		if (!super.getBuffer().getErrors().hasErrors("systemCurrency")) {
			final Collection<String> acceptedCurrencies = object.getAcceptedCurrenciesAsCollection();

			super.state(acceptedCurrencies.contains(object.getSystemCurrency()), "systemCurrency", "administrator.currency.form.error.currency-not-accepted");
		}

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
