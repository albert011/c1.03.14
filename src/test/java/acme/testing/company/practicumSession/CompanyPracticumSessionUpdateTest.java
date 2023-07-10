
package acme.testing.company.practicumSession;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.practicumSessions.PracticumSession;
import acme.testing.TestHarness;

public class CompanyPracticumSessionUpdateTest extends TestHarness {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected CompanyPracticumSessionTestRepository repository;

	// Test methods -----------------------------------------------------------


	@ParameterizedTest
	@CsvFileSource(resources = "/company/practicumSession/update-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int practicumRecordIndex, final String code, final String course, final String practicumTitle, final int practicumSessionRecordIndex, final String practicumSessionTitle, final String abstractText,
		final String startDate, final String endDate, final String link) {

		super.signIn("company1", "company1");

		super.clickOnMenu("Company", "List my practicums");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(practicumRecordIndex, 0, code);
		super.checkColumnHasValue(practicumRecordIndex, 1, course);
		super.checkColumnHasValue(practicumRecordIndex, 2, practicumTitle);
		super.clickOnListingRecord(practicumRecordIndex);

		super.clickOnButton("Practicum sessions");
		super.clickOnListingRecord(practicumSessionRecordIndex);
		super.checkSubmitExists("Update");

		super.fillInputBoxIn("title", practicumSessionTitle);
		super.fillInputBoxIn("abstractText", abstractText);
		super.fillInputBoxIn("startDate", startDate);
		super.fillInputBoxIn("endDate", endDate);
		super.fillInputBoxIn("link", link);

		super.clickOnSubmit("Update");

		super.clickOnMenu("Company", "List my practicums");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(practicumRecordIndex, 0, code);
		super.checkColumnHasValue(practicumRecordIndex, 1, course);
		super.checkColumnHasValue(practicumRecordIndex, 2, practicumTitle);
		super.clickOnListingRecord(practicumRecordIndex);
		super.checkInputBoxHasValue("code", code);

		super.clickOnButton("Practicum sessions");

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

	@ParameterizedTest
	@CsvFileSource(resources = "/company/practicumSession/update-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int practicumRecordIndex, final String code, final String course, final String practicumTitle, final int practicumSessionRecordIndex, final String practicumSessionTitle, final String abstractText,
		final String startDate, final String endDate, final String link) {

		super.signIn("company1", "company1");

		super.clickOnMenu("Company", "List my practicums");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(practicumRecordIndex, 0, code);
		super.checkColumnHasValue(practicumRecordIndex, 1, course);
		super.checkColumnHasValue(practicumRecordIndex, 2, practicumTitle);
		super.clickOnListingRecord(practicumRecordIndex);
		super.clickOnButton("Practicum sessions");

		super.checkListingExists();
		super.clickOnListingRecord(practicumSessionRecordIndex);
		super.checkFormExists();

		super.fillInputBoxIn("title", practicumSessionTitle);
		super.fillInputBoxIn("abstractText", abstractText);
		super.fillInputBoxIn("startDate", startDate);
		super.fillInputBoxIn("endDate", endDate);
		super.fillInputBoxIn("link", link);
		super.clickOnSubmit("Update");
		super.checkErrorsExist();

		super.signOut();
	}

	@Test
	public void test300Hacking() {

		Collection<PracticumSession> practicumSessions;
		String param;

		practicumSessions = this.repository.findManyPracticumSessionsByCompanyUsername("company1");
		for (final PracticumSession practicumSession : practicumSessions) {
			param = String.format("id=%d", practicumSession.getId());

			super.checkLinkExists("Sign in");
			super.request("/company/practicum-session/update", param);
			super.checkPanicExists();

			super.signIn("administrator", "administrator");
			super.request("/company/practicum-session/update", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("lecturer1", "lecturer1");
			super.request("/company/practicum-session/update", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("student1", "student1");
			super.request("/company/practicum-session/update", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("company2", "company2");
			super.request("/company/practicum-session/update", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("assistant1", "assistant1");
			super.request("/company/practicum-session/update", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("auditor1", "auditor1");
			super.request("/company/practicum-session/update", param);
			super.checkPanicExists();
			super.signOut();
		}
	}

	@Test
	public void test301Hacking() {

		Collection<PracticumSession> practicumSessions;
		String param;

		super.checkLinkExists("Sign in");
		super.signIn("company1", "company1");
		practicumSessions = this.repository.findManyPracticumSessionsByCompanyUsername("company1");
		for (final PracticumSession practicumSession : practicumSessions)
			if (!practicumSession.isDraftMode()) {
				param = String.format("id=%d", practicumSession.getId());
				super.request("/company/practicum-session/update", param);
				super.checkPanicExists();
			}
	}

}
