
package acme.testing.company.practicumSession;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.practicumSessions.PracticumSession;
import acme.testing.TestHarness;

public class CompanyPracticumSessionDeleteTest extends TestHarness {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected CompanyPracticumSessionTestRepository repository;

	// Test methods -----------------------------------------------------------


	@ParameterizedTest
	@CsvFileSource(resources = "/company/practicumSession/delete-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int practicumRecordIndex, final String code, final String course, final String practicumTitle, final int practicumSessionRecordIndex, final String practicumSessionTitle, final String startDate, final String endDate) {

		String currentQuery;

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
		super.checkColumnHasValue(practicumSessionRecordIndex, 0, practicumSessionTitle);
		super.checkColumnHasValue(practicumSessionRecordIndex, 2, startDate);
		super.checkColumnHasValue(practicumSessionRecordIndex, 3, endDate);
		super.clickOnListingRecord(practicumSessionRecordIndex);
		super.checkFormExists();

		currentQuery = super.getCurrentQuery();
		if (currentQuery.charAt(0) == '?')
			currentQuery = currentQuery.substring(1);

		super.clickOnSubmit("Delete");

		super.request("/company/practicum-session/show", currentQuery);
		super.checkPanicExists();

		super.signOut();
	}

	@Test
	public void test200Negative() {

	}

	@Test
	public void test300Hacking() {

		Collection<PracticumSession> practicumSessions;
		String param;

		practicumSessions = this.repository.findManyPracticumSessionsByCompanyUsername("company1");
		for (final PracticumSession practicumSession : practicumSessions) {
			param = String.format("id=%d", practicumSession.getId());

			super.checkLinkExists("Sign in");
			super.request("/company/practicum-session/delete", param);
			super.checkPanicExists();

			super.signIn("administrator", "administrator");
			super.request("/company/practicum-session/delete", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("lecturer1", "lecturer1");
			super.request("/company/practicum-session/delete", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("student1", "student1");
			super.request("/company/practicum-session/delete", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("auditor1", "auditor1");
			super.request("/company/practicum-session/delete", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("company2", "company2");
			super.request("/company/practicum-session/delete", param);
			super.checkPanicExists();
			super.signOut();
		}
	}

	@Test
	public void test301Hacking() {

		final Collection<PracticumSession> practicumSessions;
		String param;

		super.checkLinkExists("Sign in");
		super.signIn("company1", "company1");
		practicumSessions = this.repository.findManyPracticumSessionsByCompanyUsername("company1");
		for (final PracticumSession practicumSession : practicumSessions)
			if (!practicumSession.getPracticum().isDraftMode()) {
				param = String.format("id=%d", practicumSession.getId());
				super.request("/company/practicum-session/delete", param);
				super.checkPanicExists();
			}
	}

}
