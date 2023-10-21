
package acme.features.company.practicumSession;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.practicumSessions.PracticumSession;
import acme.entities.practicums.Practicum;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanyPracticumSessionUpdateService extends AbstractService<Company, PracticumSession> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected CompanyPracticumSessionRepository repository;


	// AbstractService interface ----------------------------------------------
	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int practicumSessionId;
		PracticumSession practicumSession;
		Practicum practicum;

		practicumSessionId = super.getRequest().getData("id", int.class);
		practicum = this.repository.findOnePracticumByPracticumSessionId(practicumSessionId);
		practicumSession = this.repository.findOnePracticumSessionById(practicumSessionId);
		status = practicum != null && practicumSession.isDraftMode() && super.getRequest().getPrincipal().hasRole(practicum.getCompany());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		PracticumSession object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOnePracticumSessionById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final PracticumSession object) {
		assert object != null;

		super.bind(object, "title", "abstractText", "startDate", "endDate", "link", "isAddendum");
	}

	@Override
	public void validate(final PracticumSession object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("startDate")) {
			Date minimumStartDate;
			minimumStartDate = MomentHelper.deltaFromCurrentMoment(7, ChronoUnit.DAYS);
			super.state(MomentHelper.isAfterOrEqual(object.getStartDate(), minimumStartDate), "startDate", "company.practicumSession.form.error.start-date");

			if (!super.getBuffer().getErrors().hasErrors("endDate")) {
				Date minimumEndDate;
				minimumEndDate = MomentHelper.deltaFromMoment(object.getStartDate(), 7, ChronoUnit.DAYS);
				super.state(MomentHelper.isAfterOrEqual(object.getEndDate(), minimumEndDate), "endDate", "company.practicumSession.form.error.end-date");
			}
		}

	}

	@Override
	public void perform(final PracticumSession object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final PracticumSession object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "title", "abstractText", "startDate", "endDate", "link", "isAddendum");
		tuple.put("masterId", object.getPracticum().getId());
		tuple.put("draftMode", object.isDraftMode());
		tuple.put("confirmation", "false");

		super.getResponse().setData(tuple);
	}

}
