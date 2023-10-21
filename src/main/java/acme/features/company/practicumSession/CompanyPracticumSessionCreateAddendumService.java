/*
 * CompanyPracticumSessionCreateAddendumService.java
 *
 * Copyright (C) 2022-2023 Javier Fernández Castillo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

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
public class CompanyPracticumSessionCreateAddendumService extends AbstractService<Company, PracticumSession> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected CompanyPracticumSessionRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("masterId", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int masterId;
		PracticumSession addendumSession;
		Practicum practicum;

		masterId = super.getRequest().getData("masterId", int.class);
		addendumSession = this.repository.findOneAddendumSessionByPracticumId(masterId);
		practicum = this.repository.findOnePracticumById(masterId);
		status = addendumSession == null && practicum != null && !practicum.isDraftMode() && super.getRequest().getPrincipal().hasRole(practicum.getCompany());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		PracticumSession object;
		int masterId;
		Practicum practicum;

		masterId = super.getRequest().getData("masterId", int.class);
		practicum = this.repository.findOnePracticumById(masterId);

		object = new PracticumSession();
		object.setTitle("");
		object.setAddendum(true);
		object.setPracticum(practicum);

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

		boolean isAccepted;

		isAccepted = this.getRequest().getData("accept", boolean.class);
		super.state(isAccepted, "accept", "company.addendumSession.form.error.must-accept");

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

		int masterId;
		Tuple tuple;

		masterId = super.getRequest().getData("masterId", int.class);

		tuple = super.unbind(object, "title", "abstractText", "startDate", "endDate", "link", "isAddendum");
		tuple.put("masterId", masterId);
		tuple.put("confirmation", "true");

		super.getResponse().setData(tuple);
	}

}
