
package acme.features.company.dashboard;

import java.time.Month;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.forms.CompanyDashboard;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanyDashboardShowService extends AbstractService<Company, CompanyDashboard> {

	// Internal state ---------------------------------------------------------
	@Autowired
	private CompanyDashboardRepository repository;


	// AbstractService Interface ----------------------------------------------
	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		boolean status;
		Company company;
		Principal principal;
		int userAccountId;

		principal = super.getRequest().getPrincipal();
		userAccountId = principal.getAccountId();
		company = this.repository.findOneCompanyByUserAccountId(userAccountId);

		status = company != null && principal.hasRole(Company.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		int companyId;
		CompanyDashboard companyDashboard;
		Principal principal;
		int userAccountId;
		Company company;

		final Integer numPracticum;
		final Map<String, Long> numPracticumByMonthLastYear;
		final Double averagePracticumLength;
		final Double deviationPracticumLength;
		final Double minimumPracticumLength;
		final Double maximumPracticumLength;

		final Integer numSession;
		final Double averageSessionLength;
		final Double deviationSessionLength;
		final Double minimumSessionLength;
		final Double maximumSessionLength;

		principal = super.getRequest().getPrincipal();
		userAccountId = principal.getAccountId();
		company = this.repository.findOneCompanyByUserAccountId(userAccountId);
		companyId = company.getId();

		companyDashboard = new CompanyDashboard();

		//Practicum

		averagePracticumLength = this.repository.averagePracticumLength(companyId);
		deviationPracticumLength = this.repository.deviationPracticumLength(companyId);
		minimumPracticumLength = this.repository.minimumPracticumLength(companyId);
		maximumPracticumLength = this.repository.maximumPracticumLength(companyId);
		numPracticum = this.repository.numPracticum(companyId);

		final String momentActual = MomentHelper.getCurrentMoment().toString();
		final String[] split = momentActual.split("CEST ");
		final Integer lastYear = Integer.valueOf(split[1].trim()) - 1;

		numPracticumByMonthLastYear = this.repository.numPracticumByMonthLastYear(companyId, lastYear).stream().collect(Collectors.toMap(key -> Month.of((int) key[0]).toString(), value -> (long) value[1]));

		for (final Month month : Month.values())
			if (!numPracticumByMonthLastYear.containsKey(month.toString()))
				numPracticumByMonthLastYear.put(month.toString(), (long) 0);

		companyDashboard.setNumPracticumByMonthLastYear(numPracticumByMonthLastYear);
		companyDashboard.setAveragePracticumLength(averagePracticumLength);
		companyDashboard.setDeviationPracticumLength(deviationPracticumLength);
		companyDashboard.setMinimumPracticumLength(minimumPracticumLength);
		companyDashboard.setMaximumPracticumLength(maximumPracticumLength);
		companyDashboard.setNumPracticum(numPracticum);

		//PracticumSession

		averageSessionLength = this.repository.averageSessionLength(companyId);
		deviationSessionLength = this.repository.deviationSessionLength(companyId);
		minimumSessionLength = this.repository.minimumSessionLength(companyId);
		maximumSessionLength = this.repository.maximumSessionLength(companyId);
		numSession = this.repository.numSession(companyId);

		companyDashboard.setAverageSessionLength(averageSessionLength);
		companyDashboard.setDeviationSessionLength(deviationSessionLength);
		companyDashboard.setMinimumSessionLength(minimumSessionLength);
		companyDashboard.setMaximumSessionLength(maximumSessionLength);
		companyDashboard.setNumSession(numSession);

		super.getBuffer().setData(companyDashboard);
	}

	@Override
	public void unbind(final CompanyDashboard companyDashboard) {
		Tuple tuple;

		tuple = super.unbind(companyDashboard, "numPracticumByMonthLastYear", "averagePracticumLength", "deviationPracticumLength", "minimumPracticumLength", "maximumPracticumLength", "numPracticum", "averageSessionLength", "deviationPracticumLength",
			"minimumSessionLength", "maximumSessionLength", "numSession");

		super.getResponse().setData(tuple);
	}

}
