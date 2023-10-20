
package acme.features.student.enrolment;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.course.Course;
import acme.entities.enrolments.Activity;
import acme.entities.enrolments.Enrolment;
import acme.framework.components.accounts.Principal;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentEnrolmentDeleteService extends AbstractService<Student, Enrolment> {

	@Autowired
	protected StudentEnrolmentRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		final boolean status;
		final Principal principal;
		final int masterId;

		masterId = super.getRequest().getData("id", int.class);
		principal = super.getRequest().getPrincipal();

		status = this.repository.findOneEnrolmentById(masterId).getStudent().getId() == principal.getActiveRoleId();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Enrolment object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneEnrolmentById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Enrolment object) {
		assert object != null;
		int studentId;
		Student student;
		int courseId;
		Course course;
		studentId = super.getRequest().getPrincipal().getActiveRoleId();
		student = this.repository.findOneStudentById(studentId);
		courseId = super.getRequest().getData("course", int.class);
		course = this.repository.findOneCourseById(courseId);
		super.bind(object, "code", "motivation", "goals", "workTime");
		object.setStudent(student);
		object.setCourse(course);
	}

	@Override
	public void validate(final Enrolment object) {
		assert object != null;
	}

	@Override
	public void perform(final Enrolment object) {
		assert object != null;

		Collection<Activity> activity;
		activity = this.repository.findManyActivitiesById(object.getId());
		this.repository.deleteAll(activity);
		this.repository.delete(object);
	}

	@Override
	public void unbind(final Enrolment object) {
		assert object != null;

		//int studentId;
		Collection<Course> courses;
		SelectChoices choices;
		Tuple tuple;
		final double workTime = 0.;
		boolean finalized = false;

		//studentId = super.getRequest().getPrincipal().getActiveRoleId();
		courses = this.repository.findAllCourses();
		choices = SelectChoices.from(courses, "code", object.getCourse());
		final List<Activity> list = this.repository.findManyActivitiesById(object.getId());

		if (object.getHolderName() != null && object.getLowerNibble() != null)
			finalized = true;

		tuple = super.unbind(object, "code", "motivation", "goals", "holderName", "lowerNibble");
		tuple.put("course", choices.getSelected().getKey());
		tuple.put("courses", choices);
		tuple.put("workTime", object.workTime(list));
		super.getResponse().setData(tuple);
		tuple.put("finalized", finalized);
	}

}
