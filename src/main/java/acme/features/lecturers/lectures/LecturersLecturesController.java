
package acme.features.lecturers.lectures;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.lecture.Lecture;
import acme.framework.controllers.AbstractController;
import acme.roles.Lecturer;

@Controller
public class LecturersLecturesController extends AbstractController<Lecturer, Lecture> {

	@Autowired
	protected LecturersLecturesListMineService	listMineService;

	@Autowired
	protected LecturersLecturesShowService		showService;

	@Autowired
	protected LecturersLecturesCreateService	createService;

	@Autowired
	protected LecturersLecturesPublishService	publishService;

	@Autowired
	protected LecturersLecturesDeleteService	deleteService;

	@Autowired
	protected LecturersLecturesUpdateService	updateService;


	@PostConstruct
	protected void initialise() {
		super.addCustomCommand("list-mine", "list", this.listMineService);
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("update", this.updateService);
		super.addBasicCommand("delete", this.deleteService);
		super.addCustomCommand("publish", "update", this.publishService);
		super.addBasicCommand("create", this.createService);
	}
}
