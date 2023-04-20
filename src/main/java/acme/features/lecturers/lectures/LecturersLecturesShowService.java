
package acme.features.lecturers.lectures;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.course.Course;
import acme.entities.lecture.Lecture;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturersLecturesShowService extends AbstractService<Lecturer, Lecture> {

	@Autowired
	protected LecturersLecturesRepository repository;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int id;
		Lecturer lecturer;
		Lecture lecture;

		id = super.getRequest().getData("id", int.class);
		lecture = this.repository.findOneLectureById(id);
		lecturer = lecture == null ? null : lecture.getLecturer();
		status = super.getRequest().getPrincipal().hasRole(lecturer) || lecture != null && !lecture.isDraftMode();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Lecture object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneLectureById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Lecture object) {
		assert object != null;

		int lecturerId;
		Collection<Course> courses;
		final SelectChoices choices;
		Tuple tuple;

		if (!object.isDraftMode())
			courses = this.repository.findAllCourses();
		else {
			lecturerId = super.getRequest().getPrincipal().getActiveRoleId();
			courses = this.repository.findManyCoursesByLecturers(lecturerId);
		}
		choices = SelectChoices.from(courses, "code", object.getCourses());

		tuple = super.unbind(object, "title", "Abstract", "estimatedLearningTime", "body", "isTheoretical", "link", "draftMode");
		tuple.put("course", choices.getSelected().getKey());
		tuple.put("courses", choices);
		super.getResponse().setData(tuple);
	}
}
