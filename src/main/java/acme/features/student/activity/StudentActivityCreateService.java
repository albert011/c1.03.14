
package acme.features.student.activity;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.enrolments.Activity;
import acme.entities.enrolments.Enrolment;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentActivityCreateService extends AbstractService<Student, Activity> {

	@Autowired
	protected StudentActivityRepository repository;

	// AbstractService interface ----------------------------------------------


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
		Activity object;

		object = new Activity();

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Activity object) {
		assert object != null;

		Enrolment enrolment;
		int enrolmentId;
		enrolmentId = super.getRequest().getData("enrolment", int.class);
		enrolment = this.repository.findOneEnrolmentById(enrolmentId);

		super.bind(object, "title", "abstractField", "activityType", "startPeriod", "endPeriod", "link");
		object.setEnrolment(enrolment);
	}

	@Override
	public void validate(final Activity object) {
		assert object != null;

		/*
		 * if (!super.getBuffer().getErrors().hasErrors("code")) {
		 * Enrolment existing;
		 * 
		 * existing = this.repository.findOneEnrolmentByCode(object.getCode());
		 * super.state(existing == null, "code", "student.enrolment.form.error.duplicated");
		 * }
		 */
	}

	@Override
	public void perform(final Activity object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Activity object) {
		assert object != null;

		int studentId;
		Collection<Enrolment> enrolments;
		SelectChoices choices;
		Tuple tuple;

		studentId = super.getRequest().getPrincipal().getActiveRoleId();
		enrolments = this.repository.findEnrolmentsByStudent(studentId);
		choices = SelectChoices.from(enrolments, "code", object.getEnrolment());

		tuple = super.unbind(object, "title", "abstractField", "activityType", "startPeriod", "endPeriod", "link");
		tuple.put("enrolment", choices.getSelected().getKey());
		tuple.put("enrolments", choices);
		super.getResponse().setData(tuple);
	}

}
