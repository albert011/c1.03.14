
package acme.features.student.activity;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.enrolments.Activity;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
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
		final Date moment = MomentHelper.getCurrentMoment();
		if (!super.getBuffer().getErrors().hasErrors("startPeriod"))
			super.state(!moment.after(object.getStartPeriod()), "startPeriod", "student.activity.form.error.moment");
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

		tuple = super.unbind(object, "title", "abstractField", "activityType", "startPeriod", "endPeriod", "link");

		super.getResponse().setData(tuple);
	}

}
