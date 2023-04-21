
package acme.features.student.lecture;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.lecture.Lecture;
import acme.framework.controllers.AbstractController;
import acme.roles.Student;

@Controller
public class StudentLectureController extends AbstractController<Student, Lecture> {

	@Autowired
	StudentLectureListByCourseService listByCourseService;


	@PostConstruct
	protected void initialise() {
		super.addCustomCommand("list-lecture", "list", this.listByCourseService);
	}

}
