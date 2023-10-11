
package acme.features.authenticated.courses;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.course.Course;
import acme.framework.components.accounts.Authenticated;
import acme.framework.controllers.AbstractController;

@Controller
public class AuthenticatedCoursesController extends AbstractController<Authenticated, Course> {

	@Autowired
	protected AuthenticatedCoursesListService	listService;

	@Autowired
	protected AuthenticatedCoursesShowService	showService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
	}
}
