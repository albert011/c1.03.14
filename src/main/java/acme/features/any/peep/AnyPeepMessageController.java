
package acme.features.any.peep;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.peep.PeepMessage;
import acme.framework.components.accounts.Any;
import acme.framework.controllers.AbstractController;

@Controller
public class AnyPeepMessageController extends AbstractController<Any, PeepMessage> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AnyPeepMessageListService		listService;

	@Autowired
	protected AnyPeepMessageShowService		showService;

	@Autowired
	protected AnyPeepMessagePublishService	publishService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
		super.addCustomCommand("publish", "create", this.publishService);
	}

}
