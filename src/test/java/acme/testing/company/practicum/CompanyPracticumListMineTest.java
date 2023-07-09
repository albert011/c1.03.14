
package acme.testing.company.practicum;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class CompanyPracticumListMineTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/company/practicum/list-mine-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String code, final String courseCode, final String title) {

		super.signIn("company1", "company1");

		super.clickOnMenu("Company", "List my practicums");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(recordIndex, 0, code);
		super.checkColumnHasValue(recordIndex, 1, courseCode);
		super.checkColumnHasValue(recordIndex, 2, title);

		super.signOut();
	}

	@Test
	public void test200Negative() {

	}

	@Test
	public void test300Hacking() {
		super.checkLinkExists("Sign in");
		super.request("/company/practicum/list-mine");
		super.checkPanicExists();

		super.signIn("administrator", "administrator");
		super.request("/company/practicum/list-mine");
		super.checkPanicExists();
		super.signOut();

		super.signIn("lecturer1", "lecturer1");
		super.request("/company/practicum/list-mine");
		super.checkPanicExists();
		super.signOut();

		super.signIn("student1", "student1");
		super.request("/company/practicum/list-mine");
		super.checkPanicExists();
		super.signOut();

		super.signIn("assistant1", "assistant1");
		super.request("/company/practicum/list-mine");
		super.checkPanicExists();
		super.signOut();

		super.signIn("auditor1", "auditor1");
		super.request("/company/practicum/list-mine");
		super.checkPanicExists();
		super.signOut();
	}

}
