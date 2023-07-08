
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
public class LecturersCoursesLecturesCreateService extends AbstractService<Lecturer, CourseLecture> {

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
		boolean status;
		int courseId;
		int lecturerId;
		int ownerCourseId;

		courseId = super.getRequest().getData("courseId", int.class);
		lecturerId = super.getRequest().getPrincipal().getActiveRoleId();
		ownerCourseId = this.repository.findCourseOwnerByCourseId(courseId).getId();
		status = lecturerId == ownerCourseId;

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		CourseLecture object;
		Course course;

		course = this.repository.findCourseById(super.getRequest().getData("courseId", int.class));

		object = new CourseLecture();
		object.setCourse(course);
		object.setLecture(new Lecture());
		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final CourseLecture object) {
		assert object != null;
		final int courseId = super.getRequest().getData("course", int.class);
		final Course course = this.repository.findCourseById(courseId);
		object.setCourse(course);
		final int lectureId = super.getRequest().getData("lecture", int.class);
		final Lecture lecture = this.repository.findLectureById(lectureId);
		object.setLecture(lecture);
	}

	@Override
	public void validate(final CourseLecture object) {
		assert object != null;
		if (!super.getBuffer().getErrors().hasErrors("course"))
			super.state(object.getCourse().isDraftMode(), "*", "lecturer.course-lecture.form.error.course-published");
		if (!super.getBuffer().getErrors().hasErrors("lecture") && !super.getBuffer().getErrors().hasErrors("course")) {
			final Collection<Lecture> lecturesInCourse = this.repository.findLecturesByCourseId(object.getCourse().getId());
			super.state(!lecturesInCourse.contains(object.getLecture()), "lecture", "lecturer.course-lecture.form.error.duplicated-course-lecture");
		}
	}

	@Override
	public void perform(final CourseLecture object) {
		assert object != null;
		this.repository.save(object);
	}

	@Override
	public void unbind(final CourseLecture object) {
		assert object != null;
		int lecturerId;
		Collection<Course> courses;
		Collection<Lecture> lectures;
		Tuple tuple;

		lecturerId = super.getRequest().getPrincipal().getActiveRoleId();
		courses = this.repository.findManyCoursesByLecturerId(lecturerId);
		lectures = this.repository.findManyLecturesByLecturerId(lecturerId);
		tuple = new Tuple();
		tuple.put("courses", SelectChoices.from(courses, "code", object.getCourse()));
		tuple.put("lectures", SelectChoices.from(lectures, "title", null));
		super.getResponse().setData(tuple);
		super.getResponse().setGlobal("courseId", object.getCourse().getId());
	}
}
