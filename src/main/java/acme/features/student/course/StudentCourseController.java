
package acme.features.student.course;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.course.Course;
import acme.framework.controllers.AbstractController;
import acme.roles.Student;

@Controller
public class StudentCourseController extends AbstractController<Student, Course> {

	@Autowired
	protected StudentCourseListAllService	listAllService;

	@Autowired
	protected StudentCourseShowService		showService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("show", this.showService);
		super.addCustomCommand("list-all", "list", this.listAllService);
	}

}
