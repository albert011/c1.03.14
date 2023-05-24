
package acme.testing.student.enrolment;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class StudentEnrolmentFinaliseTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/student/enrolment/finalise-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String creditCard, final String cvc, final String expiryDate, final String holderName, final String lowerNibble) {

		super.signIn("student1", "student1");

		super.clickOnMenu("Student", "Enrolment");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();

		super.fillInputBoxIn("creditCard", creditCard);
		super.fillInputBoxIn("cvc", cvc);
		super.fillInputBoxIn("expiryDate", expiryDate);
		super.fillInputBoxIn("holderName", holderName);
		super.clickOnSubmit("Finalise");

		super.clickOnMenu("Student", "Enrolment");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();

		super.checkInputBoxHasValue("holderName", holderName);
		super.checkInputBoxHasValue("lowerNibble", lowerNibble);

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/student/enrolment/finalise-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int recordIndex, final String creditCard, final String cvc, final String expiryDate, final String holderName, final String lowerNibble) {
		// HINT: this test attempts to create jobs with incorrect data.

		super.signIn("student1", "student1");

		super.clickOnMenu("Student", "Enrolment");
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();

		super.fillInputBoxIn("creditCard", creditCard);
		super.fillInputBoxIn("cvc", cvc);
		super.fillInputBoxIn("expiryDate", expiryDate);
		super.fillInputBoxIn("holderName", holderName);
		super.clickOnSubmit("Finalise");

		super.checkErrorsExist();

		super.signOut();

	}

	@Test
	public void test300Hacking() {
		// HINT: this test tries to create a job using principals with
		// HINT+ inappropriate roles.

		super.checkLinkExists("Sign in");
		super.request("/student/enrolment/finalise");
		super.checkPanicExists();

		super.signIn("administrator", "administrator");
		super.request("/student/enrolment/finalise");
		super.checkPanicExists();
		super.signOut();
	}

}
