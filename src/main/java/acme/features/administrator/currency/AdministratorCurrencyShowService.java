
package acme.features.administrator.currency;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.currency.Currency;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AdministratorCurrencyShowService extends AbstractService<Administrator, Currency> {

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
	public void unbind(final Currency object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "systemCurrency", "acceptedCurrencies");

		super.getResponse().setData(tuple);
	}

}
