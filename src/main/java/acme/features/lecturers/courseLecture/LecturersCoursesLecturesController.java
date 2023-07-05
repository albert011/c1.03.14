
package acme.features.lecturers.courseLecture;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.course.CourseLecture;
import acme.framework.controllers.AbstractController;
import acme.roles.Lecturer;

@Controller
public class LecturersCoursesLecturesController extends AbstractController<Lecturer, CourseLecture> {

	@Autowired
	protected LecturersCoursesLecturesListMineService	listMineService;

	@Autowired
	protected LecturersCoursesLecturesCreateService		createService;

	@Autowired
	protected LecturersCoursesLecturesDeleteService		deleteService;

	@Autowired
	protected LecturersCoursesLecturesShowService		showService;


	@PostConstruct
	protected void initialise() {
		super.addCustomCommand("list-mine", "list", this.listMineService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("delete", this.deleteService);
		super.addBasicCommand("show", this.showService);
	}
}
