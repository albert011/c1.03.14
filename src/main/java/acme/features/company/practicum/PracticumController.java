
package acme.features.company.practicum;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.practicums.Practicum;
import acme.framework.controllers.AbstractController;
import acme.roles.Company;

@Controller
public class PracticumController extends AbstractController<Company, Practicum> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected PracticumShowService		showService;

	@Autowired
	protected PracticumCreateService	createService;

	@Autowired
	protected PracticumUpdateService	updateService;

	@Autowired
	protected PracticumDeleteService	deleteService;

	@Autowired
	protected PracticumListAllService	listAllService;

	@Autowired
	protected PracticumListMineService	listMineService;

	@Autowired
	protected PracticumPublishService	publishService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("update", this.updateService);
		super.addBasicCommand("delete", this.deleteService);

		super.addCustomCommand("list-mine", "list", this.listMineService);
		super.addCustomCommand("publish", "update", this.publishService);
	}

}
