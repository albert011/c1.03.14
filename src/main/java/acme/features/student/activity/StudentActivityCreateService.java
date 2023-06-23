
package acme.features.student.activity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.enrolments.Activity;
import acme.entities.enrolments.Activity.ActivityType;
import acme.framework.components.accounts.Principal;
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

		final boolean status;
		final Principal principal;
		final int masterId;

		masterId = super.getRequest().getData("enrolment", int.class);
		principal = super.getRequest().getPrincipal();

		status = this.repository.findStudentByEnrolment(masterId).getId() == principal.getActiveRoleId();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Activity object;
		int enrolmentId;

		enrolmentId = super.getRequest().getData("enrolment", int.class);
		object = new Activity();
		object.setEnrolment(this.repository.findOneEnrolmentById(enrolmentId));

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Activity object) {
		assert object != null;
		super.bind(object, "title", "abstractField", "activityType", "startPeriod", "endPeriod", "link");
	}

	@Override
	public void validate(final Activity object) {
		assert object != null;
		if (!super.getBuffer().getErrors().hasErrors("endPeriod"))
			super.state(object.getEndPeriod().after(object.getStartPeriod()), "endPeriod", "student.activity.form.error.endPeriod");
	}

	@Override
	public void perform(final Activity object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Activity object) {
		assert object != null;
		Tuple tuple;
		SelectChoices choices;

		boolean finalised = false;

		if (object.getEnrolment().getHolderName() != null && !object.getEnrolment().getHolderName().isEmpty())
			finalised = true;

		choices = SelectChoices.from(ActivityType.class, object.getActivityType());
		tuple = super.unbind(object, "title", "abstractField", "startPeriod", "endPeriod", "link");
		tuple.put("activityType", choices.getSelected().getKey());
		tuple.put("activityTypes", choices);
		tuple.put("finalised", finalised);
		super.getResponse().setData(tuple);
		super.getResponse().setGlobal("enrolment", object.getEnrolment().getId());
	}

}
