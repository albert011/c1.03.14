
package acme.features.student.enrolment;

import java.util.Collection;

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
public class StudentEnrolmentFinalizeService extends AbstractService<Student, Enrolment> {

	@Autowired
	protected StudentEnrolmentRepository repository;

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
		object.setHolderName(this.getRequest().getData("holderName", String.class));
		super.bind(object, "holderName", "lowerNibble");
	}

	@Override
	public void validate(final Enrolment object) {
		assert object != null;
		final String card = super.getRequest().getData("creditCard", String.class);
		if (!super.getBuffer().getErrors().hasErrors("creditCard"))
			super.state(card.matches("^\\d{4}\\/\\d{4}\\/\\d{4}\\/\\d{4}$"), "creditCard", "student.enrolment.form.error.card");

		final String holderName = super.getRequest().getData("holderName", String.class);
		if (!super.getBuffer().getErrors().hasErrors("holderName"))
			super.state(!holderName.isEmpty(), "holderName", "student.enrolment.form.error.holder");
		final String cvc = super.getRequest().getData("cvc", String.class);
		if (!super.getBuffer().getErrors().hasErrors("cvc"))
			super.state(cvc.matches("^\\d{3}$"), "cvc", "student.enrolment.form.error.cvc");
		final String expiryDate = super.getRequest().getData("expiryDate", String.class);
		if (!super.getBuffer().getErrors().hasErrors("expiryDate"))
			super.state(expiryDate.matches("^\\d{2}\\/\\d{2}$"), "expiryDate", "student.enrolment.form.error.expiryDate");
	}

	@Override
	public void perform(final Enrolment object) {
		assert object != null;
		object.setLowerNibble(this.getRequest().getData("creditCard", String.class).substring(this.getRequest().getData("creditCard", String.class).length() - 4));
		this.repository.save(object);
	}

	@Override
	public void unbind(final Enrolment object) {
		assert object != null;

		//int studentId;
		Collection<Course> courses;
		SelectChoices choices;
		Tuple tuple;
		Double workTime = 0.;
		boolean finalized = false;

		//studentId = super.getRequest().getPrincipal().getActiveRoleId();
		courses = this.repository.findAllCourses();
		choices = SelectChoices.from(courses, "title", object.getCourse());
		if (!this.repository.findManyActivitiesById(object.getId()).isEmpty())
			workTime = this.repository.findManyActivitiesById(object.getId()).stream().map(Activity::getWorkTime).reduce(Double::sum).get();

		if (object.getHolderName() != null && object.getLowerNibble() != null)
			finalized = true;

		tuple = super.unbind(object, "code", "motivation", "goals", "holderName", "lowerNibble");
		tuple.put("course", choices.getSelected().getKey());
		tuple.put("courses", choices);
		tuple.put("workTime", workTime);
		super.getResponse().setData(tuple);
		tuple.put("finalized", finalized);
	}

}
