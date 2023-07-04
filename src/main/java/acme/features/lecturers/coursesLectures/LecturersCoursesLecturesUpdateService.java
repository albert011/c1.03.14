
package acme.features.lecturers.coursesLectures;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.course.Course;
import acme.entities.course.CoursesLectures;
import acme.entities.lecture.Lecture;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturersCoursesLecturesUpdateService extends AbstractService<Lecturer, CoursesLectures> {

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
		CoursesLectures courseLecture;

		courseLectureId = super.getRequest().getData("id", int.class);
		courseLecture = this.repository.findCourseLectureById(courseLectureId);
		status = courseLecture != null ? super.getRequest().getPrincipal().getActiveRoleId() == courseLecture.getCourse().getLecturer().getId() : false;

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		CoursesLectures object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findCourseLectureById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final CoursesLectures object) {
		assert object != null;
		final CoursesLectures courseLecture = this.repository.findCourseLectureById(object.getId());
		object.setCourse(courseLecture.getCourse());
		object.setLecture(courseLecture.getLecture());
	}

	@Override
	public void validate(final CoursesLectures object) {
		assert object != null;
	}

	@Override
	public void perform(final CoursesLectures object) {
		assert object != null;
		this.repository.save(object);
	}

	@Override
	public void unbind(final CoursesLectures object) {
		assert object != null;
		Tuple tuple = new Tuple();
		final int lecturerId = super.getRequest().getPrincipal().getActiveRoleId();
		tuple = super.unbind(object, "id");
		final Collection<Course> courses = this.repository.findManyCoursesByLecturerId(lecturerId);
		final Collection<Lecture> lectures = this.repository.findManyLecturesByLecturerId(lecturerId);
		tuple.put("draftMode", object.getCourse().isDraftMode());
		tuple.put("courses", SelectChoices.from(courses, "code", object.getCourse()));
		tuple.put("lectures", SelectChoices.from(lectures, "title", object.getLecture()));
		super.getResponse().setData(tuple);
		super.getResponse().setGlobal("draftMode", object.getCourse().isDraftMode());
		super.getResponse().setGlobal("courseId", object.getCourse().getId());
		super.getResponse().setGlobal("lectureId", object.getLecture().getId());
	}
}
