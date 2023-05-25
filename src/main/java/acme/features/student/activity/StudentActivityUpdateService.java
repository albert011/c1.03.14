
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
public class StudentActivityUpdateService extends AbstractService<Student, Activity> {

	@Autowired
	protected StudentActivityRepository repository;

	// AbstractService<Employer, Job> -------------------------------------


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

		status = this.repository.findStudentByActivity(masterId).getId() == principal.getActiveRoleId();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Activity object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneActivityById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Activity object) {
		assert object != null;
		final ActivityType activityType;
		//activityType = this.repository.findActivityTypeById(object.getId());
		super.bind(object, "title", "abstractField", "activityType", "startPeriod", "endPeriod", "link");
		//object.setActivityType(activityType);
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
		SelectChoices activityTypes;
		boolean finalised = false;

		activityTypes = SelectChoices.from(ActivityType.class, object.getActivityType());

		if (object.getEnrolment().getHolderName() != null && !object.getEnrolment().getHolderName().isEmpty())
			finalised = true;

		tuple = super.unbind(object, "title", "abstractField", "startPeriod", "endPeriod", "link");
		tuple.put("finalised", finalised);
		tuple.put("activityType", activityTypes.getSelected().getKey());
		tuple.put("activityTypes", activityTypes);
		super.getResponse().setData(tuple);
	}
}
