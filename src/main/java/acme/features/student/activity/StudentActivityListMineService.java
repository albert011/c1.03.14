
package acme.features.student.activity;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.enrolments.Activity;
import acme.entities.enrolments.Enrolment;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentActivityListMineService extends AbstractService<Student, Activity> {

	// Internal state ---------------------------------------------------------

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
		Collection<Activity> objects;
		int masterId;
		Enrolment enrolment;

		masterId = super.getRequest().getData("masterId", int.class);
		objects = this.repository.findActivitiesByEnrolment(masterId);
		enrolment = this.repository.findOneEnrolmentById(masterId);
		if (enrolment.getHolderName() != null && !enrolment.getHolderName().isEmpty())
			super.getResponse().setGlobal("showCreate", true);
		super.getResponse().setGlobal("enrolment", masterId);
		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final Activity object) {
		assert object != null;
		Tuple tuple;
		boolean showCreate = false;

		if (object.getEnrolment().getHolderName() != null && !object.getEnrolment().getHolderName().isEmpty())
			showCreate = true;

		tuple = super.unbind(object, "title", "abstractField", "activityType");
		super.getResponse().setData(tuple);
		super.getResponse().setGlobal("showCreate", showCreate);
	}
}
