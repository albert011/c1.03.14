
package acme.features.student.activity;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.enrolments.Activity;
import acme.framework.controllers.AbstractController;
import acme.roles.Student;

@Controller
public class StudentActivityController extends AbstractController<Student, Activity> {

	@Autowired
	protected StudentActivityListAllService listAllService;


	@PostConstruct
	protected void initialise() {
		super.addCustomCommand("list-all", "list", this.listAllService);
	}

}
