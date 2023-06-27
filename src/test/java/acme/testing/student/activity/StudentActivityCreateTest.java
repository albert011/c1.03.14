
package acme.testing.student.activity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class StudentActivityCreateTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/student/activity/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String title, final String abstractField, final String activityType, final String startPeriod, final String endPeriod, final String link) {
		// HINT: this test authenticates as an employer and then lists his or her
		// HINT: jobs, creates a new one, and check that it's been created properly.

		super.signIn("student1", "student1");

		super.clickOnMenu("Student", "Enrolment");
		super.sortListing(0, "asc");
		super.clickOnListingRecord(0);
		super.clickOnButton("Activities");

		super.clickOnButton("Create");
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("abstractField", abstractField);
		super.fillInputBoxIn("activityType", activityType);
		super.fillInputBoxIn("startPeriod", startPeriod);
		super.fillInputBoxIn("endPeriod", endPeriod);
		super.fillInputBoxIn("link", link);
		super.clickOnSubmit("Create");

		super.clickOnMenu("Student", "Enrolment");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(0);
		super.clickOnButton("Activities");
		super.checkColumnHasValue(recordIndex, 0, title);
		super.checkColumnHasValue(recordIndex, 1, abstractField);
		super.checkColumnHasValue(recordIndex, 2, activityType);

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("abstractField", abstractField);
		super.checkInputBoxHasValue("activityType", activityType);
		super.checkInputBoxHasValue("startPeriod", startPeriod);
		super.checkInputBoxHasValue("endPeriod", endPeriod);
		super.checkInputBoxHasValue("link", link);

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/student/activity/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int recordIndex, final String title, final String abstractField, final String activityType, final String startPeriod, final String endPeriod, final String link) {
		// HINT: this test attempts to create jobs with incorrect data.

		super.signIn("student1", "student1");

		super.clickOnMenu("Student", "Enrolment");
		super.clickOnListingRecord(0);
		super.clickOnButton("Activities");
		super.clickOnButton("Create");
		super.checkFormExists();

		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("abstractField", abstractField);
		super.fillInputBoxIn("activityType", activityType);
		super.fillInputBoxIn("startPeriod", startPeriod);
		super.fillInputBoxIn("endPeriod", endPeriod);
		super.fillInputBoxIn("link", link);
		super.clickOnSubmit("Create");

		super.checkErrorsExist();

		super.signOut();

	}

	@Test
	public void test300Hacking() {
		// HINT: this test tries to create a job using principals with
		// HINT+ inappropriate roles.

		super.checkLinkExists("Sign in");
		super.request("/student/activity/create");
		super.checkPanicExists();

		super.signIn("administrator", "administrator");
		super.request("/student/activity/create");
		super.checkPanicExists();
		super.signOut();
	}

}
