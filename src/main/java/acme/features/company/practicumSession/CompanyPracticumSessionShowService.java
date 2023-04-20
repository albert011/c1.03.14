
package acme.features.company.practicumSession;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.practicumSessions.PracticumSession;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanyPracticumSessionShowService extends AbstractService<Company, PracticumSession> {

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
		int masterId;
		PracticumSession practicumSession;
		Company company;

		masterId = super.getRequest().getData("id", int.class);
		practicumSession = this.repository.findOnePracticumSessionById(masterId);
		company = practicumSession == null ? null : practicumSession.getCompany();
		status = super.getRequest().getPrincipal().hasRole(company) || practicumSession != null && !practicumSession.isDraftMode();

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
	public void unbind(final PracticumSession object) {
		assert object != null;

		final int companyId;
		Collection<Company> companies;
		SelectChoices choices;
		Tuple tuple;

		if (!object.isDraftMode())
			companies = this.repository.findAllCompanies();
		else {
			companyId = super.getRequest().getPrincipal().getActiveRoleId();
			companies = this.repository.findManyCompaniesById(companyId);
		}
		choices = SelectChoices.from(companies, "name", object.getCompany());

		tuple = super.unbind(object, "title", "abstractText", "startDate", "endDate", "link", "draftMode", "isAddendum");
		tuple.put("company", choices.getSelected().getKey());
		tuple.put("companies", choices);

		super.getResponse().setData(tuple);
	}

}
