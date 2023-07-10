
package acme.testing.company.practicum;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.practicums.Practicum;
import acme.testing.TestHarness;

public class CompanyPracticumDeleteTest extends TestHarness {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected CompanyPracticumTestRepository repository;

	// Test methods ------------------------------------------------------------


	@ParameterizedTest
	@CsvFileSource(resources = "/company/practicum/delete-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String code, final String course, final String title) {

		String currentQuery;

		super.signIn("company1", "company1");

		super.clickOnMenu("Company", "List my practicums");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(recordIndex, 0, code);
		super.checkColumnHasValue(recordIndex, 1, course);
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();

		currentQuery = super.getCurrentQuery();
		if (currentQuery.charAt(0) == '?')
			currentQuery = currentQuery.substring(1);

		super.clickOnSubmit("Delete");

		super.request("/company/practicum/show", currentQuery);
		super.checkPanicExists();

		super.signOut();

	}

	@Test
	public void test200Negative() {

	}

	@Test
	public void test300Hacking() {

		Collection<Practicum> practicums;
		String param;

		practicums = this.repository.findManyPracticumsByCompanyUsername("company1");

		for (final Practicum practicum : practicums)
			if (practicum.isDraftMode()) {
				param = String.format("id=%d", practicum.getId());

				super.checkLinkExists("Sign in");
				super.request("/company/practicum/delete", param);
				super.checkPanicExists();

				super.signIn("administrator", "administrator");
				super.request("/company/practicum/delete", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("company2", "company2");
				super.request("/company/practicum/delete", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("student1", "student1");
				super.request("/company/practicum/delete", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("auditor1", "auditor1");
				super.request("/company/practicum/delete", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("lecturer1", "lecturer1");
				super.request("/company/practicum/delete", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("assistant1", "assistant1");
				super.request("/company/practicum/delete", param);
				super.checkPanicExists();
				super.signOut();
			}
	}

	@Test
	public void test301Hacking() {

		Collection<Practicum> practicums;
		String param;

		practicums = this.repository.findManyPracticumsByCompanyUsername("company1");

		for (final Practicum practicum : practicums)
			if (!practicum.isDraftMode()) {
				param = String.format("id=%d", practicum.getId());

				super.signIn("company1", "company1");
				super.request("/company/practicum/delete", param);
				super.checkPanicExists();
				super.signOut();

			}
	}

}
