
package acme.features.lecturers.courseLecture;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.course.Course;
import acme.entities.course.CourseLecture;
import acme.entities.lecture.Lecture;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturersCoursesLecturesShowService extends AbstractService<Lecturer, CourseLecture> {

	@Autowired
	protected LecturersCoursesLecturesRepository repository;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int courseLectureId;
		CourseLecture courseLecture;

		courseLectureId = super.getRequest().getData("id", int.class);
		courseLecture = this.repository.findCourseLectureById(courseLectureId);
		status = courseLecture != null && super.getRequest().getPrincipal().getActiveRoleId() == courseLecture.getCourse().getLecturer().getId();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		CourseLecture object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findCourseLectureById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final CourseLecture object) {
		assert object != null;
		int lecturerId;
		Collection<Course> courses;
		Collection<Lecture> lectures;
		Tuple tuple;
		tuple = new Tuple();

		lecturerId = super.getRequest().getPrincipal().getActiveRoleId();
		tuple = super.unbind(object, "id");
		courses = this.repository.findManyCoursesByLecturerId(lecturerId);
		lectures = this.repository.findManyLecturesByLecturerId(lecturerId);
		tuple.put("courses", SelectChoices.from(courses, "code", object.getCourse()));
		tuple.put("lectures", SelectChoices.from(lectures, "title", object.getLecture()));
		super.getResponse().setData(tuple);
		super.getResponse().setGlobal("courseId", object.getCourse().getId());
		super.getResponse().setGlobal("lectureId", object.getLecture().getId());
	}
}
