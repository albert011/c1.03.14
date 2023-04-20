
package acme.features.company.practicum;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.practicums.Practicum;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class PracticumDeleteService extends AbstractService<Company, Practicum> {
	// Internal state ---------------------------------------------------------

	@Autowired
	protected PracticumRepository repository;

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
		Practicum practicum;
		Company company;

		masterId = super.getRequest().getData("id", int.class);
		practicum = this.repository.findOnePracticumById(masterId);
		company = practicum == null ? null : practicum.getCompany();
		status = super.getRequest().getPrincipal().hasRole(company) || practicum != null && practicum.isDraftMode();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Practicum object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOnePracticumById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Practicum object) {
		assert object != null;

		int companyId;
		Company company;

		companyId = super.getRequest().getData("company", int.class);
		company = this.repository.findOneCompanyById(companyId);

		super.bind(object, "code", "title", "abstractText", "goals", "estimatedTotalTime");
		object.setCompany(company);
	}

	@Override
	public void validate(final Practicum object) {
		assert object != null;
	}

	@Override
	public void perform(final Practicum object) {
		assert object != null;

		Collection<Company> companies;

		companies = this.repository.findManyCompaniesById(object.getId());
		this.repository.deleteAll(companies);
		this.repository.delete(object);
	}

	@Override
	public void unbind(final Practicum object) {
		assert object != null;

		final int companyId;
		Collection<Company> companies;
		SelectChoices choices;
		Tuple tuple;

		companyId = super.getRequest().getPrincipal().getActiveRoleId();
		companies = this.repository.findManyCompaniesById(companyId);
		choices = SelectChoices.from(companies, "name", object.getCompany());

		tuple = super.unbind(object, "code", "title", "abstractText", "goals", "estimatedTotalTime", "draftMode");
		tuple.put("company", choices.getSelected().getKey());
		tuple.put("companies", choices);

		super.getResponse().setData(tuple);

	}

}
