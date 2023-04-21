
package acme.features.student.enrolment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.enrolments.Enrolment;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentEnrolmentCreateService extends AbstractService<Student, Enrolment> {

	@Autowired
	protected StudentEnrolmentRepository repository;

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
		Enrolment object;
		Student student;

		student = this.repository.findOneStudentById(super.getRequest().getPrincipal().getActiveRoleId());
		object = new Enrolment();
		object.setStudent(student);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Enrolment object) {
		assert object != null;

		super.bind(object, "code", "motivation", "goals", "workTime");
	}

	@Override
	public void validate(final Enrolment object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Enrolment existing;

			existing = this.repository.findOneEnrolmentByCode(object.getCode());
			super.state(existing == null, "code", "student.enrolment.form.error.duplicated");
		}

		/*
		 * if (!super.getBuffer().getErrors().hasErrors("deadline")) {
		 * Date minimumDeadline;
		 * 
		 * minimumDeadline = MomentHelper.deltaFromCurrentMoment(7, ChronoUnit.DAYS);
		 * super.state(MomentHelper.isAfter(object.getDeadline(), minimumDeadline), "deadline", "employer.job.form.error.too-close");
		 * }
		 */

		/*
		 * if (!super.getBuffer().getErrors().hasErrors("salary"))
		 * super.state(object.getSalary().getAmount() > 0, "salary", "employer.job.form.error.negative-salary");
		 */
	}

	@Override
	public void perform(final Enrolment object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Enrolment object) {
		assert object != null;

		int studentId;
		//Collection<Company> contractors;
		final SelectChoices choices;
		Tuple tuple;

		studentId = super.getRequest().getPrincipal().getActiveRoleId();
		/*
		 * contractors = this.repository.findManyContractorsByEmployerId(employerId);
		 * choices = SelectChoices.from(contractors, "name", object.getContractor());
		 */

		tuple = super.unbind(object, "code", "motivation", "goals", "workTime");
		/*
		 * tuple.put("contractor", choices.getSelected().getKey());
		 * tuple.put("contractors", choices);
		 */

		super.getResponse().setData(tuple);
	}

}
