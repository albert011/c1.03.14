
package acme.features.lecturers.lectures;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.course.Course;
import acme.entities.lecture.Lecture;
import acme.entities.lecture.LectureType;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturersLecturesPublishService extends AbstractService<Lecturer, Lecture> {

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
		int lectureId;
		Lecture lecture;
		Lecturer lecturer;

		lectureId = super.getRequest().getData("id", int.class);
		lecture = this.repository.findOneLectureById(lectureId);
		lecturer = lecture == null ? null : lecture.getLecturer();
		status = lecture != null && lecture.isDraftMode() && super.getRequest().getPrincipal().hasRole(lecturer);

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
	public void bind(final Lecture object) {
		assert object != null;

		int courseId;
		Course course;
		LectureType lectureType;

		lectureType = this.repository.findLectureTypeById(object.getId());
		courseId = super.getRequest().getData("courses", int.class);
		course = this.repository.findOneCourseById(courseId);

		super.bind(object, "title", "Abstract", "estimatedLearningTime", "body", "link");
		object.setCourses(course);
		object.setType(lectureType);
	}

	@Override
	public void validate(final Lecture object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("estimatedLearningTime"))
			super.state(object.getEstimatedLearningTime() > 0.0, "estimatedLearningTime", "lecturer.lecture.form.error.negative-estimatedLearningTime");
	}

	@Override
	public void perform(final Lecture object) {
		assert object != null;

		object.setDraftMode(false);
		this.repository.save(object);
	}

	@Override
	public void unbind(final Lecture object) {
		assert object != null;

		int lecturerId;
		Collection<Course> courses;
		final SelectChoices choices;
		Tuple tuple;
		SelectChoices types;

		lecturerId = super.getRequest().getPrincipal().getActiveRoleId();
		courses = this.repository.findManyCoursesByLecturers(lecturerId);
		choices = SelectChoices.from(courses, "code", object.getCourses());
		types = SelectChoices.from(LectureType.class, object.getType());

		tuple = super.unbind(object, "title", "Abstract", "estimatedLearningTime", "body", "type", "link", "draftMode");
		tuple.put("course", choices.getSelected().getKey());
		tuple.put("courses", choices);
		tuple.put("type", types.getSelected().getKey());
		tuple.put("types", types);

		super.getResponse().setData(tuple);
	}
}
