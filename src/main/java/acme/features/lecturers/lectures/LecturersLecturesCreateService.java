
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
public class LecturersLecturesCreateService extends AbstractService<Lecturer, Lecture> {

	@Autowired
	protected LecturersLecturesRepository repository;


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
		Lecture object;
		Lecturer lecturer;

		lecturer = this.repository.findOneLecturerById(super.getRequest().getPrincipal().getActiveRoleId());
		object = new Lecture();
		object.setDraftMode(true);
		object.setLecturer(lecturer);

		super.getBuffer().setData(object);

	}

	@Override
	public void bind(final Lecture object) {
		assert object != null;

		int courseId;
		Course course;

		courseId = super.getRequest().getData("courses", int.class);
		course = this.repository.findOneCourseById(courseId);

		super.bind(object, "title", "Abstract", "estimatedLearningTime", "body", "isTheoretical", "link");
		object.setCourses(course);
	}

	@Override
	public void validate(final Lecture object) {
		assert object != null;

	}

	@Override
	public void perform(final Lecture object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Lecture object) {
		assert object != null;

		int lecturerId;
		Collection<Course> courses;
		SelectChoices choices;
		Tuple tuple;

		lecturerId = super.getRequest().getPrincipal().getActiveRoleId();
		courses = this.repository.findManyCoursesByLecturers(lecturerId);
		choices = SelectChoices.from(courses, "code", object.getCourses());

		tuple = super.unbind(object, "title", "Abstract", "estimatedLearningTime", "body", "isTheoretical", "link", "draftMode");
		tuple.put("course", choices.getSelected().getKey());
		tuple.put("courses", choices);

		super.getResponse().setData(tuple);
	}
}
