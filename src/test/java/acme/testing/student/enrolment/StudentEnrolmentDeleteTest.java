
package acme.testing.student.enrolment;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.enrolments.Enrolment;
import acme.testing.TestHarness;

public class StudentEnrolmentDeleteTest extends TestHarness {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected StudentEnrolmentTestRepository repository;

	// Test methods ------------------------------------------------------------


	@ParameterizedTest
	@CsvFileSource(resources = "/student/enrolment/delete-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String code, final String motivation, final String goals, final String course) {

		super.signIn("student1", "student1");

		super.clickOnMenu("Student", "Enrolment");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(recordIndex, 0, code);
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("motivation", motivation);
		super.fillInputBoxIn("goals", goals);
		super.fillInputBoxIn("course", course);
		super.clickOnSubmit("Delete");

		super.checkListingExists();
		super.sortListing(0, "asc");
		/*
		 * super.checkColumnHasValue(recordIndex, 0, code);
		 * super.checkColumnHasValue(recordIndex, 1, motivation);
		 */

		super.signOut();
	}

	@Test
	public void test200Negative() {
	}

	@Test
	public void test300Hacking() {
		// HINT: this test tries to update a job with a role other than "Employer",
		// HINT+ or using an employer who is not the owner.

		final Collection<Enrolment> enrolments;
		String param;

		enrolments = this.repository.listEnrolmentsByStudentUsername("student1");
		for (final Enrolment enrolment : enrolments) {
			param = String.format("id=%d", enrolment.getId());

			super.checkLinkExists("Sign in");
			super.request("/student/enrolment/delete", param);
			super.checkPanicExists();

			super.signIn("administrator", "administrator");
			super.request("/student/enrolment/delete", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("student2", "student2");
			super.request("/student/enrolment/delete", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("company1", "company1");
			super.request("/student/enrolment/delete", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("lecturer1", "lecturer1");
			super.request("/student/enrolment/delete", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("assistant1", "assistant1");
			super.request("/student/enrolment/delete", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("auditor1", "auditor1");
			super.request("/student/enrolment/delete", param);
			super.checkPanicExists();
			super.signOut();
		}
	}

}
