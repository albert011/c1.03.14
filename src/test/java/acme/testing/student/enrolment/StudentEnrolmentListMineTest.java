
package acme.testing.student.enrolment;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class StudentEnrolmentListMineTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/student/enrolment/list-mine-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String code, final String motivation) {

		super.signIn("student1", "student1");

		super.clickOnMenu("Student", "Enrolment");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(recordIndex, 0, code);
		super.checkColumnHasValue(recordIndex, 1, motivation);

		super.signOut();
	}

	@Test
	public void test200Negative() {
		// HINT: there aren't any negative tests for this feature since it's a listing that
		// HINT+ doesn't involve entering any data into any forms.
	}

	@Test
	public void test300Hacking() {
		super.checkLinkExists("Sign in");
		super.request("/student/enrolment/list-mine");
		super.checkPanicExists();

		super.signIn("administrator", "administrator");
		super.request("/student/enrolment/list-mine");
		super.checkPanicExists();
		super.signOut();

	}

}
