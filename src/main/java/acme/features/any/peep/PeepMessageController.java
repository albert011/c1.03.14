
package acme.features.any.peep;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.peep.PeepMessage;
import acme.framework.components.accounts.Any;
import acme.framework.controllers.AbstractController;

@Controller
public class PeepMessageController extends AbstractController<Any, PeepMessage> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected PeepMessageListService	listService;

	@Autowired
	protected PeepMessageShowService	showService;

	@Autowired
	protected PeepMessageCreateService	createService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("create", this.createService);
	}

}
