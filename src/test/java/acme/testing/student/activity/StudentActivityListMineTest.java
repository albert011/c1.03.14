
package acme.testing.student.activity;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.enrolments.Enrolment;
import acme.testing.TestHarness;

public class StudentActivityListMineTest extends TestHarness {

	@Autowired
	protected StudentActivityTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/student/activity/list-mine-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String title, final String abstractField, final String activityType) {

		super.signIn("student1", "student1");

		super.clickOnMenu("Student", "Enrolment");
		super.sortListing(0, "asc");
		super.clickOnListingRecord(0);
		super.clickOnButton("Activities");

		super.checkColumnHasValue(recordIndex, 0, title);
		super.checkColumnHasValue(recordIndex, 1, abstractField);
		super.checkColumnHasValue(recordIndex, 2, activityType);

		super.signOut();
	}

	@Test
	public void test200Negative() {
		// HINT: there aren't any negative tests for this feature since it's a listing that
		// HINT+ doesn't involve entering any data into any forms.
	}

	@Test
	public void test300Hacking() {

		List<Enrolment> enrolments;
		String param;

		enrolments = this.repository.findEnrolmentsByStudentUsername("student1");

		for (final Enrolment enrolment : enrolments) {

			param = String.format("masterId=%d", enrolment.getId());

			super.checkLinkExists("Sign in");
			super.request("/student/activity/list-mine", param);
			super.checkPanicExists();

			super.signIn("administrator", "administrator");
			super.request("/student/activity/list-mine", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("student2", "student2");
			super.request("/student/activity/list-mine", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("company1", "company1");
			super.request("/student/activity/list-mine", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("lecturer1", "lecturer1");
			super.request("/student/activity/list-mine", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("assistant1", "assistant1");
			super.request("/student/activity/list-mine", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("auditor1", "auditor1");
			super.request("/student/activity/list-mine", param);
			super.checkPanicExists();
			super.signOut();
		}
	}

}
