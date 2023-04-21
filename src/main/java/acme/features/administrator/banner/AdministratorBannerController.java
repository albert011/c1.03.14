
package acme.features.administrator.banner;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.banner.Banner;
import acme.framework.components.accounts.Administrator;
import acme.framework.controllers.AbstractController;

@Controller
public class AdministratorBannerController extends AbstractController<Administrator, Banner> {

	@Autowired
	protected AdministratorBannerListAllService	listAllService;

	@Autowired
	protected AdministratorBannerCreateService	createService;

	@Autowired
	protected AdministratorBannerDeleteService	deleteService;

	@Autowired
	protected AdministratorBannerUpdateService	updateService;

	@Autowired
	protected AdministratorBannerShowService	showService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("delete", this.deleteService);
		super.addBasicCommand("update", this.updateService);
		super.addBasicCommand("create", this.createService);
		super.addCustomCommand("list-all", "list", this.listAllService);
		super.addBasicCommand("show", this.showService);
	}
}
