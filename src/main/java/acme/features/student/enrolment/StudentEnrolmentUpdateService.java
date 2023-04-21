
package acme.features.student.enrolment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.enrolments.Enrolment;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentEnrolmentUpdateService extends AbstractService<Student, Enrolment> {

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

		/*
		 * int contractorId;
		 * Company contractor;
		 * 
		 * contractorId = super.getRequest().getData("contractor", int.class);
		 * contractor = this.repository.findOneContractorById(contractorId);
		 */

		super.bind(object, "code", "motivation", "goals", "workTime");
		//object.setContractor(contractor);
	}

	@Override
	public void validate(final Enrolment object) {
		assert object != null;

		/*
		 * if (!super.getBuffer().getErrors().hasErrors("deadline")) {
		 * Date minimumDeadline;
		 * 
		 * minimumDeadline = MomentHelper.deltaFromCurrentMoment(7, ChronoUnit.DAYS);
		 * super.state(MomentHelper.isAfter(object.getDeadline(), minimumDeadline), "deadline", "employer.job.form.error.too-close");
		 * }
		 */

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Enrolment existing;

			existing = this.repository.findOneEnrolmentByCode(object.getCode());
			super.state(existing == null || existing.equals(object), "code", "employer.job.form.error.duplicated");
		}

		//if (!super.getBuffer().getErrors().hasErrors("salary"))
		//super.state(object.getSalary().getAmount() > 0, "salary", "employer.job.form.error.negative-salary");
	}

	@Override
	public void perform(final Enrolment object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Enrolment object) {
		assert object != null;

		int employerId;
		/*
		 * Collection<Company> contractors;
		 * SelectChoices choices;
		 */
		Tuple tuple;

		employerId = super.getRequest().getPrincipal().getActiveRoleId();
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
