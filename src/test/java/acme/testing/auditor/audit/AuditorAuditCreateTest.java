
package acme.testing.auditor.audit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class AuditorAuditCreateTest extends TestHarness {

	// Internal state ---------------------------------------------------------

	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/audit/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int index, final String code, final String conclusion, final String strongPoints, final String weakPoints, final String courseTitle, final String isPublished) {

		super.signIn("auditor1", "auditor1");
		super.clickOnMenu("Auditor", "List my audits");
		super.checkListingExists();

		super.clickOnButton("Create audit");
		super.checkFormExists();
		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("conclusion", conclusion);
		super.fillInputBoxIn("strongPoints", strongPoints);
		super.fillInputBoxIn("weakPoints", weakPoints);
		super.fillInputBoxIn("course", courseTitle);
		super.clickOnSubmit("Create audit");

		super.checkNotErrorsExist();

		super.clickOnMenu("Auditor", "List my audits");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(index, 0, code);
		super.checkColumnHasValue(index, 2, isPublished);
		super.checkColumnHasValue(index, 3, courseTitle);

		super.clickOnListingRecord(index);
		super.checkFormExists();
		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("conclusion", conclusion);
		super.checkInputBoxHasValue("strongPoints", strongPoints);
		super.checkInputBoxHasValue("weakPoints", weakPoints);
		super.checkInputBoxHasValue("course", courseTitle);
		super.checkInputBoxHasValue("auditorUsername", "auditor1");

		super.clickOnButton("Audit Records of this audit");
		super.checkListingExists();
		super.checkListingEmpty();

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/audit/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int index, final String code, final String conclusion, final String strongPoints, final String weakPoints, final String courseTitle) {

		super.signIn("auditor1", "auditor1");
		super.clickOnMenu("Auditor", "List my audits");
		super.checkListingExists();

		super.clickOnButton("Create audit");
		super.checkFormExists();
		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("conclusion", conclusion);
		super.fillInputBoxIn("strongPoints", strongPoints);
		super.fillInputBoxIn("weakPoints", weakPoints);
		super.fillInputBoxIn("course", courseTitle);
		super.clickOnSubmit("Create audit");

		super.checkErrorsExist();

		super.signOut();

	}

	@Test
	public void test300Hacking() {
		super.checkLinkExists("Sign in");
		super.request("/auditor/audit/create");
		super.checkPanicExists();

		super.signIn("administrator", "administrator");
		super.request("/auditor/audit/create");
		super.checkPanicExists();
		super.signOut();

		super.signIn("lecturer1", "lecturer1");
		super.request("/auditor/audit/create");
		super.checkPanicExists();
		super.signOut();

		super.signIn("student1", "student1");
		super.request("/auditor/audit/create");
		super.checkPanicExists();
		super.signOut();

		super.signIn("assistant1", "assistant1");
		super.request("/auditor/audit/create");
		super.checkPanicExists();
		super.signOut();

		super.signIn("company1", "company1");
		super.request("/auditor/audit/create");
		super.checkPanicExists();
		super.signOut();

	}
}
