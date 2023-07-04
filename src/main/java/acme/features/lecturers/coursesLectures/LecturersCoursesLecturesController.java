
package acme.features.lecturers.coursesLectures;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.course.CoursesLectures;
import acme.framework.controllers.AbstractController;
import acme.roles.Lecturer;

@Controller
public class LecturersCoursesLecturesController extends AbstractController<Lecturer, CoursesLectures> {

	@Autowired
	protected LecturersCoursesLecturesListMineService	listMineService;

	@Autowired
	protected LecturersCoursesLecturesCreateService		createService;

	@Autowired
	protected LecturersCoursesLecturesDeleteService		deleteService;

	@Autowired
	protected LecturersCoursesLecturesShowService		showService;

	@Autowired
	protected LecturersCoursesLecturesUpdateService		updateService;


	protected void initialise() {
		super.addCustomCommand("list-mine", "list", this.listMineService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("delete", this.deleteService);
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("update", this.updateService);

	}
}
