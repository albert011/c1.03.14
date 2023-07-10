
package acme.testing.student.activity;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.enrolments.Activity;
import acme.testing.TestHarness;

public class StudentActivityDeleteTest extends TestHarness {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected StudentActivityTestRepository repository;

	// Test methods ------------------------------------------------------------


	@ParameterizedTest
	@CsvFileSource(resources = "/student/activity/delete-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String title, final String abstractField, final String activityType, final String startPeriod, final String endPeriod, final String link) {

		super.signIn("student1", "student1");

		super.clickOnMenu("Student", "Enrolment");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(0);
		super.clickOnButton("Activities");

		super.checkColumnHasValue(recordIndex, 0, title);
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("abstractField", abstractField);
		super.checkInputBoxHasValue("activityType", activityType);
		super.checkInputBoxHasValue("startPeriod", startPeriod);
		super.checkInputBoxHasValue("endPeriod", endPeriod);
		super.checkInputBoxHasValue("link", link);

		super.clickOnSubmit("Delete");

		super.checkListingExists();
		super.sortListing(0, "asc");
		/*
		 * super.checkColumnHasValue(recordIndex, 0, title);
		 * super.checkColumnHasValue(recordIndex, 1, abstractField);
		 * super.checkColumnHasValue(recordIndex, 2, activityType);
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

		Collection<Activity> activities;
		String param;

		activities = this.repository.findStudentsActivitiesByUsername("student1");
		for (final Activity activity : activities) {

			param = String.format("id=%d", activity.getId());

			super.checkLinkExists("Sign in");
			super.request("/student/activity/delete", param);
			super.checkPanicExists();

			super.signIn("administrator", "administrator");
			super.request("/student/activity/delete", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("student2", "student2");
			super.request("/student/activity/delete", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("company1", "company1");
			super.request("/student/activity/delete", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("lecturer1", "lecturer1");
			super.request("/student/activity/delete", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("assistant1", "assistant1");
			super.request("/student/activity/delete", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("auditor1", "auditor1");
			super.request("/student/activity/delete", param);
			super.checkPanicExists();
			super.signOut();
		}
	}

}
