
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
public class LecturersCoursesLecturesCreateService extends AbstractService<Lecturer, CoursesLectures> {

	@Autowired
	protected LecturersCoursesLecturesRepository repository;


	@Override
	public void check() {
		boolean status;
		status = super.getRequest().hasData("courseId", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		CoursesLectures object;
		Course course;

		course = this.repository.findCourseById(super.getRequest().getData("courseId", int.class));

		object = new CoursesLectures();
		object.setCourse(course);
		object.setLecture(new Lecture());
		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final CoursesLectures object) {
		assert object != null;
		final int courseId = super.getRequest().getData("course", int.class);
		final Course course = this.repository.findCourseById(courseId);
		object.setCourse(course);
		final int lectureId = super.getRequest().getData("lecture", int.class);
		final Lecture lecture = this.repository.findLectureById(lectureId);
		object.setLecture(lecture);
	}

	@Override
	public void perform(final CoursesLectures object) {
		assert object != null;
		this.repository.save(object);
	}

	@Override
	public void unbind(final CoursesLectures object) {
		assert object != null;
		final int lecturerId = super.getRequest().getPrincipal().getActiveRoleId();
		final Collection<Course> courses = this.repository.findManyCoursesByLecturerId(lecturerId);
		final Collection<Lecture> lectures = this.repository.findManyLecturesByLecturerId(lecturerId);
		final Tuple tuple = new Tuple();
		tuple.put("courses", SelectChoices.from(courses, "code", object.getCourse()));
		tuple.put("lectures", SelectChoices.from(lectures, "code", null));
		super.getResponse().setData(tuple);
		super.getResponse().setGlobal("draftMode", true);
		super.getResponse().setGlobal("courseId", object.getCourse().getId());
	}
}
