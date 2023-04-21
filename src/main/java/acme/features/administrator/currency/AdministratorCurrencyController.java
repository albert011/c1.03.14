
package acme.features.administrator.currency;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.currency.Currency;
import acme.framework.components.accounts.Administrator;
import acme.framework.controllers.AbstractController;

@Controller
public class AdministratorCurrencyController extends AbstractController<Administrator, Currency> {

	// Internal state ------------------------------------------------------------

	@Autowired
	protected AdministratorCurrencyShowService		showService;

	@Autowired
	protected AdministratorCurrencyUpdateService	updateService;

	// Constructors --------------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("update", this.updateService);
	}

}
