
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
public class PracticumCreateService extends AbstractService<Company, Practicum> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected PracticumRepository repository;

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
		Practicum object;
		Company company;

		company = this.repository.findOneCompanyById(super.getRequest().getPrincipal().getActiveRoleId());
		object = new Practicum();
		object.setDraftMode(true);
		object.setCompany(company);

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

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Practicum existing;

			existing = this.repository.findOnePracticumByCode(object.getCode());
			super.state(existing == null, "code", "company.practicum.form.error.duplicated");
		}

	}

	@Override
	public void perform(final Practicum object) {
		assert object != null;

		this.repository.save(object);
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
