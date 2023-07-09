
package acme.testing.company.practicum;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.practicums.Practicum;
import acme.testing.TestHarness;

public class CompanyPracticumUpdateTest extends TestHarness {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected CompanyPracticumTestRepository repository;

	// Test methods ------------------------------------------------------------


	@ParameterizedTest
	@CsvFileSource(resources = "/company/practicum/update-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String code, final String title, final String abstractText, final String goals, final String course) {
		// HINT: this test logs in as a company, lists his or her practica,
		// HINT+ selects one of them, updates it, and then checks that
		// HINT+ the update has actually been performed.

		super.signIn("company1", "company1");

		super.clickOnMenu("Company", "List my practicums");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(recordIndex, 0, code);
		super.clickOnListingRecord(recordIndex);

		super.checkFormExists();
		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("abstractText", abstractText);
		super.fillInputBoxIn("goals", goals);
		super.fillInputBoxIn("course", course);
		super.clickOnSubmit("Update");

		super.clickOnMenu("Company", "List my practicums");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(recordIndex, 0, code);
		super.checkColumnHasValue(recordIndex, 1, course);
		super.checkColumnHasValue(recordIndex, 2, title);

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("abstractText", abstractText);
		super.checkInputBoxHasValue("goals", goals);
		super.checkInputBoxHasValue("course", course);

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/company/practicum/update-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int recordIndex, final String code, final String title, final String abstractText, final String goals, final String course) {
		// HINT: this test attempts to update a practicum with wrong data.

		super.signIn("company1", "company1");

		super.clickOnMenu("Company", "List my practicums");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("abstractText", abstractText);
		super.fillInputBoxIn("goals", goals);
		super.fillInputBoxIn("course", course);
		super.clickOnSubmit("Update");

		super.checkErrorsExist();

		super.signOut();
	}

	@Test
	public void test300Hacking() {
		// HINT: this test tries to update a practicum with a role other than "Company",
		// HINT+ or using a company who is not the owner.

		Collection<Practicum> practicums;
		String param;

		practicums = this.repository.findManyPracticumsByCompanyUsername("company1");

		for (final Practicum practicum : practicums) {
			param = String.format("id=%d", practicum.getId());

			super.checkLinkExists("Sign in");
			super.request("/company/practicum/update", param);
			super.checkPanicExists();

			super.signIn("administrator", "administrator");
			super.request("/company/practicum/update", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("assistant1", "assistant1");
			super.request("/company/practicum/update", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("lecturer1", "lecturer1");
			super.request("/company/practicum/update", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("student1", "student1");
			super.request("/company/practicum/update", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("company2", "company2");
			super.request("/company/practicum/update", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("auditor1", "auditor1");
			super.request("/company/practicum/update", param);
			super.checkPanicExists();
			super.signOut();

		}
	}

	@Test
	public void test301Hacking() {
		// HINT: this test attempts to update a practicum that has been published.

		Collection<Practicum> practica;
		String param;

		practica = this.repository.findManyPracticumsByCompanyUsername("company1");

		for (final Practicum practicum : practica)
			if (!practicum.isDraftMode()) {
				param = String.format("id=%d", practicum.getId());

				super.signIn("company1", "company1");
				super.request("/company/practicum/update", param);
				super.checkPanicExists();
				super.signOut();
			}
	}

}
