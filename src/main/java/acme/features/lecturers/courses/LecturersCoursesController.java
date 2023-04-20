
package acme.features.lecturers.courses;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.course.Course;
import acme.framework.controllers.AbstractController;
import acme.roles.Lecturer;

@Controller
public class LecturersCoursesController extends AbstractController<Lecturer, Course> {

	@Autowired
	protected LecturersCoursesListMineService	listMineService;

	@Autowired
	protected LecturersCoursesShowService		showService;

	@Autowired
	protected LecturersCourseCreateService		createService;

	@Autowired
	protected LecturersCoursesDeleteService		deleteService;

	@Autowired
	protected LecturersCoursesPublishService	publishService;

	@Autowired
	protected LecturersCoursesUpdateService		updateService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("update", this.updateService);
		super.addBasicCommand("delete", this.deleteService);
		super.addCustomCommand("list-mine", "list", this.listMineService);
		super.addCustomCommand("publish", "update", this.publishService);
		super.addBasicCommand("show", this.showService);
	}
}
