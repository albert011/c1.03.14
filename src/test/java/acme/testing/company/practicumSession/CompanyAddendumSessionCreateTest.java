
package acme.testing.company.practicumSession;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.practicumSessions.PracticumSession;
import acme.entities.practicums.Practicum;
import acme.testing.TestHarness;

public class CompanyAddendumSessionCreateTest extends TestHarness {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected CompanyPracticumSessionTestRepository repository;

	// Test methods -----------------------------------------------------------


	@ParameterizedTest
	@CsvFileSource(resources = "/company/practicumSession/create-addendum-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Negative(final int practicumRecordIndex, final String code, final String course, final String practicumTitle, final int practicumSessionRecordIndex, final String practicumSessionTitle, final String abstractText,
		final String startDate, final String endDate, final String link, final String accept) {

		super.signIn("company1", "company1");

		super.clickOnMenu("Company", "List my practicums");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(practicumRecordIndex, 0, code);
		super.checkColumnHasValue(practicumRecordIndex, 1, course);
		super.checkColumnHasValue(practicumRecordIndex, 2, practicumTitle);
		super.clickOnListingRecord(practicumRecordIndex);
		super.clickOnButton("Practicum sessions");

		super.clickOnButton("Create addendum session");
		super.fillInputBoxIn("title", practicumSessionTitle);
		super.fillInputBoxIn("abstractText", abstractText);
		super.fillInputBoxIn("startDate", startDate);
		super.fillInputBoxIn("endDate", endDate);
		super.fillInputBoxIn("link", link);
		super.fillInputBoxIn("accept", accept);
		super.clickOnSubmit("Create");
		super.checkErrorsExist();

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/company/practicumSession/create-addendum-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Positive(final int practicumRecordIndex, final String code, final String course, final String practicumTitle, final int practicumSessionRecordIndex, final String practicumSessionTitle, final String abstractText,
		final String startDate, final String endDate, final String link, final String accept) {

		super.signIn("company1", "company1");

		super.clickOnMenu("Company", "List my practicums");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(practicumRecordIndex, 0, code);
		super.checkColumnHasValue(practicumRecordIndex, 1, course);
		super.checkColumnHasValue(practicumRecordIndex, 2, practicumTitle);
		super.clickOnListingRecord(practicumRecordIndex);
		super.clickOnButton("Practicum sessions");

		super.clickOnButton("Create addendum session");
		super.fillInputBoxIn("title", practicumSessionTitle);
		super.fillInputBoxIn("abstractText", abstractText);
		super.fillInputBoxIn("startDate", startDate);
		super.fillInputBoxIn("endDate", endDate);
		super.fillInputBoxIn("link", link);
		super.fillInputBoxIn("accept", accept);
		super.clickOnSubmit("Create");

		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(practicumSessionRecordIndex, 0, practicumSessionTitle);
		super.checkColumnHasValue(practicumSessionRecordIndex, 2, startDate);
		super.checkColumnHasValue(practicumSessionRecordIndex, 3, endDate);

		super.clickOnListingRecord(practicumSessionRecordIndex);
		super.checkInputBoxHasValue("title", practicumSessionTitle);
		super.checkInputBoxHasValue("abstractText", abstractText);
		super.checkInputBoxHasValue("startDate", startDate);
		super.checkInputBoxHasValue("endDate", endDate);
		super.checkInputBoxHasValue("link", link);

		super.signOut();
	}

	@Test
	public void test300Hacking() {

		Collection<Practicum> practica;
		String param;

		practica = this.repository.findManyPracticumsByCompanyUsername("company1");
		for (final Practicum practicum : practica) {
			param = String.format("masterId=%d", practicum.getId());

			super.checkLinkExists("Sign in");
			super.request("/company/practicum-session/create-addendum", param);
			super.checkPanicExists();

			super.signIn("administrator", "administrator");
			super.request("/company/practicum-session/create-addendum", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("lecturer1", "lecturer1");
			super.request("/company/practicum-session/create-addendum", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("student1", "student1");
			super.request("/company/practicum-session/create-addendum", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("assistant1", "assistant1");
			super.request("/company/practicum-session/create-addendum", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("company2", "company2");
			super.request("/company/practicum-session/create-addendum", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("auditor1", "auditor1");
			super.request("/company/practicum-session/create-addendum", param);
			super.checkPanicExists();
			super.signOut();
		}
	}

	@Test
	public void test301Hacking() {

		Collection<Practicum> practica;
		Collection<PracticumSession> practicumSessions;

		String param;

		super.checkLinkExists("Sign in");
		super.signIn("company1", "company1");
		practica = this.repository.findManyPracticumsByCompanyUsername("company1");
		for (final Practicum practicum : practica) {
			practicumSessions = this.repository.findManyPracticumSessionsByPracticumId(practicum.getId());
			if (!practicum.isDraftMode() && practicumSessions.stream().anyMatch(ps -> ps.isAddendum())) {
				param = String.format("masterId=%d", practicum.getId());
				super.request("/company/practicum-session/create-addendum", param);
				super.checkPanicExists();
			}
		}
	}

	@Test
	public void test302Hacking() {

		Collection<Practicum> practica;

		String param;

		super.checkLinkExists("Sign in");
		super.signIn("company1", "company1");
		practica = this.repository.findManyPracticumsByCompanyUsername("company1");
		for (final Practicum practicum : practica)
			if (practicum.isDraftMode()) {
				param = String.format("masterId=%d", practicum.getId());
				super.request("/company/practicum-session/create-addendum", param);
				super.checkPanicExists();
			}
	}

}
