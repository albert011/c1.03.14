
package acme.features.authenticated.bulletin;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.bulletin.BulletinMessage;
import acme.framework.components.accounts.Authenticated;
import acme.framework.controllers.AbstractController;

@Controller
public class BulletinMessageController extends AbstractController<Authenticated, BulletinMessage> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected BulletinMessageListService	listService;

	@Autowired
	protected BulletinMessageShowService	showService;

	@Autowired
	protected BulletinMessageCreateService	createService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("create", this.createService);
	}

}
