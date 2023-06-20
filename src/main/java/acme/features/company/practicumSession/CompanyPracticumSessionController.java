
package acme.features.company.practicumSession;

import org.springframework.stereotype.Controller;

import acme.entities.practicumSessions.PracticumSession;
import acme.framework.controllers.AbstractController;
import acme.roles.Company;

@Controller
public class CompanyPracticumSessionController extends AbstractController<Company, PracticumSession> {

	// Internal state ---------------------------------------------------------

	//	@Autowired
	//	protected CompanyPracticumSessionShowService	showService;
	//
	//	@Autowired
	//	protected CompanyPracticumSessionCreateService	createService;
	//
	//	@Autowired
	//	protected CompanyPracticumSessionUpdateService	updateService;
	//
	//	@Autowired
	//	protected CompanyPracticumSessionDeleteService	deleteService;
	//
	//	@Autowired
	//	protected CompanyPracticumSessionListService	listService;
	//
	//	@Autowired
	//	protected CompanyPracticumSessionPublishService	publishService;
	//
	//	// Constructors -----------------------------------------------------------
	//
	//
	//	@PostConstruct
	//	protected void initialise() {
	//		super.addBasicCommand("show", this.showService);
	//		super.addBasicCommand("create", this.createService);
	//		super.addBasicCommand("update", this.updateService);
	//		super.addBasicCommand("delete", this.deleteService);
	//
	//		super.addCustomCommand("list-mine", "list", this.listService);
	//		super.addCustomCommand("publish", "update", this.publishService);
	//	}

}
