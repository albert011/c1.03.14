
package acme.features.company.practicumSession;

import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.practicumSessions.PracticumSession;
import acme.entities.practicums.Practicum;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanyPracticumSessionPublishService extends AbstractService<Company, PracticumSession> {

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
		Company company;

		practicumSessionId = super.getRequest().getData("id", int.class);
		practicumSession = this.repository.findOnePracticumSessionById(practicumSessionId);
		company = practicumSession == null ? null : practicumSession.getCompany();
		status = practicumSession != null && practicumSession.isDraftMode() && super.getRequest().getPrincipal().hasRole(company);

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

		int practicumId;
		Practicum practicum;

		practicumId = super.getRequest().getData("practicum", int.class);
		practicum = this.repository.findOnePracticumById(practicumId);

		super.bind(object, "title", "abstractText", "startDate", "endDate", "link", "isAddendum");
		object.setPracticum(practicum);

	}

	@Override
	public void validate(final PracticumSession object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("startDate") && !super.getBuffer().getErrors().hasErrors("endDate")) {
			Date minimumPeriod;

			minimumPeriod = MomentHelper.deltaFromMoment(object.getStartDate(), 7, ChronoUnit.DAYS);
			super.state(MomentHelper.isAfterOrEqual(object.getEndDate(), minimumPeriod), "endDate", "company.practicumSession.form.error.period-too-short");
		}

		if (object.isAddendum() && !object.getPracticum().isDraftMode()) {
			Integer countAddendumSessions;

			countAddendumSessions = this.repository.countAddendumSessionsByPracticumId(object.getPracticum().getId()).intValue();
			super.state(countAddendumSessions <= 1, "isAddendum", "company.practicumSession.form.error.addendum-limit");
		}

	}

	@Override
	public void perform(final PracticumSession object) {
		assert object != null;

		object.setDraftMode(false);
		this.repository.save(object);
	}

	@Override
	public void unbind(final PracticumSession object) {
		assert object != null;

		final int companyId;
		Collection<Practicum> practicums;
		SelectChoices choices;
		Tuple tuple;

		companyId = super.getRequest().getPrincipal().getActiveRoleId();
		practicums = this.repository.findManyPracticumsByCompanyId(companyId);
		choices = SelectChoices.from(practicums, "code", object.getPracticum());

		tuple = super.unbind(object, "title", "abstractText", "startDate", "endDate", "link", "draftMode", "isAddendum");
		tuple.put("practicum", choices.getSelected().getKey());
		tuple.put("practicums", choices);

		super.getResponse().setData(tuple);
	}

}
