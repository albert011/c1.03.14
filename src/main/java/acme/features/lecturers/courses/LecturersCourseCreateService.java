
package acme.features.lecturers.courses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.course.Course;
import acme.entities.course.CoursesLecturers;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturersCourseCreateService extends AbstractService<Lecturer, Course> {

	@Autowired
	protected LecturersCoursesRepository repository;


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Course object;

		object = new Course();
		object.setDraftMode(true);

		super.getBuffer().setData(object);

	}

	@Override
	public void bind(final Course object) {
		assert object != null;

		super.bind(object, "code", "title", "Abstract", "retailPrice", "isTheoretical", "link");
	}

	@Override
	public void validate(final Course object) {
		assert object != null;
	}

	@Override
	public void perform(final Course object) {
		assert object != null;
		final CoursesLecturers courseLecturer = new CoursesLecturers();
		Course course;
		Lecturer lecturer;

		this.repository.save(object);
		course = this.repository.findOneCourseById(object.getId());
		lecturer = this.repository.findOneLecturerById(super.getRequest().getPrincipal().getActiveRoleId());
		courseLecturer.setCourses(course);
		courseLecturer.setLecturers(lecturer);
		this.repository.save(courseLecturer);
	}

	@Override
	public void unbind(final Course object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "code", "title", "Abstract", "retailPrice", "isTheoretical", "link", "draftMode");
		super.getResponse().setData(tuple);
	}
}
