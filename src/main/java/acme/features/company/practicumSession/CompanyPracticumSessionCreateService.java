
package acme.features.company.practicumSession;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.practicumSessions.PracticumSession;
import acme.entities.practicums.Practicum;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Dataset;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanyPracticumSessionCreateService extends AbstractService<Company, PracticumSession> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected CompanyPracticumSessionRepository repository;

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
		PracticumSession object;
		Company company;

		company = this.repository.findOneCompanyById(super.getRequest().getPrincipal().getActiveRoleId());
		object = new PracticumSession();
		object.setDraftMode(true);
		object.setCompany(company);

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

	}

	@Override
	public void perform(final PracticumSession object) {
		assert object != null;

		final Dataset req = super.getRequest().getData();
		if (req.containsKey("isAddendum") && req.get("isAddendum").toString().equals("true"))
			object.setAddendum(true);

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
