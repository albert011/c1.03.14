
package acme.testing.auditor.audit;

import java.util.Collection;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.audit.Audit;
import acme.testing.TestHarness;

public class AuditorAuditUpdateTest extends TestHarness {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuditorAuditTestRepository repository;


	@BeforeEach
	public void setup() {

		final String[] data = {
			"A1234", "Testing conclusion", "Testing Strong Points", "Testing Weak Points", "Curso de ruso"
		};

		super.signIn("auditor1", "auditor1");
		super.clickOnMenu("Auditor", "List my audits");

		super.clickOnButton("Create audit");
		super.fillInputBoxIn("code", data[0]);
		super.fillInputBoxIn("conclusion", data[1]);
		super.fillInputBoxIn("strongPoints", data[2]);
		super.fillInputBoxIn("weakPoints", data[3]);
		super.fillInputBoxIn("course", data[4]);
		super.clickOnSubmit("Create audit");

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/audit/update-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int index, final String code, final String conclusion, final String strongPoints, final String weakPoints, final String courseTitle) {
		super.signIn("auditor1", "auditor1");
		super.clickOnMenu("Auditor", "List my audits");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(index, 0, "A1234");
		super.checkColumnHasValue(index, 2, "false");
		super.checkColumnHasValue(index, 3, "Curso de ruso");

		super.clickOnListingRecord(index);

		super.checkFormExists();
		super.checkSubmitExists("Update audit");

		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("conclusion", conclusion);
		super.fillInputBoxIn("strongPoints", strongPoints);
		super.fillInputBoxIn("weakPoints", weakPoints);
		super.fillInputBoxIn("course", courseTitle);

		super.clickOnSubmit("Update audit");

		super.checkNotErrorsExist();

		super.clickOnMenu("Auditor", "List my audits");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(index, 0, code);
		super.checkColumnHasValue(index, 3, courseTitle);

		super.clickOnListingRecord(index);

		super.checkFormExists();

		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("conclusion", conclusion);
		super.checkInputBoxHasValue("strongPoints", strongPoints);
		super.checkInputBoxHasValue("weakPoints", weakPoints);
		super.checkInputBoxHasValue("course", courseTitle);

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/audit/update-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int index, final String code, final String conclusion, final String strongPoints, final String weakPoints, final String courseTitle) {
		super.signIn("auditor1", "auditor1");
		super.clickOnMenu("Auditor", "List my audits");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(index, 0, "A1234");
		super.checkColumnHasValue(index, 2, "false");
		super.checkColumnHasValue(index, 3, "Curso de ruso");

		super.clickOnListingRecord(index);

		super.checkFormExists();
		super.checkSubmitExists("Update audit");

		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("conclusion", conclusion);
		super.fillInputBoxIn("strongPoints", strongPoints);
		super.fillInputBoxIn("weakPoints", weakPoints);
		super.fillInputBoxIn("course", courseTitle);

		super.clickOnSubmit("Update audit");

		super.checkErrorsExist();

		super.clickOnMenu("Auditor", "List my audits");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(index, 0, "A1234");
		super.checkColumnHasValue(index, 2, "false");
		super.checkColumnHasValue(index, 3, "Curso de ruso");

		super.clickOnListingRecord(index);

		super.checkFormExists();

		final String[] data = {
			"A1234", "Testing conclusion", "Testing Strong Points", "Testing Weak Points", "Curso de ruso"
		};

		super.checkInputBoxHasValue("code", data[0]);
		super.checkInputBoxHasValue("conclusion", data[1]);
		super.checkInputBoxHasValue("strongPoints", data[2]);
		super.checkInputBoxHasValue("weakPoints", data[3]);
		super.checkInputBoxHasValue("course", data[4]);

		super.signOut();
	}

	@Test
	public void test300Hacking() {
		Collection<Audit> audits;
		String param;

		audits = this.repository.findManyAuditsByAuditorUsername("auditor1");

		for (final Audit audit : audits) {
			param = String.format("id=%d", audit.getId());

			super.checkLinkExists("Sign in");
			super.request("/auditor/audit/update", param);
			super.checkPanicExists();

			super.signIn("administrator", "administrator");
			super.request("/auditor/audit/update", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("company1", "company1");
			super.request("/auditor/audit/update", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("student1", "student1");
			super.request("/auditor/audit/update", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("auditor2", "auditor2");
			super.request("/auditor/audit/update", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("lecturer1", "lecturer1");
			super.request("/auditor/audit/update", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("assistant1", "assistant1");
			super.request("/auditor/audit/update", param);
			super.checkPanicExists();
			super.signOut();

			if (audit.isPublished()) {
				super.signIn("auditor1", "auditor1");
				super.request("/auditor/audit/update", param);
				super.checkPanicExists();
				super.signOut();
			}
		}
	}

	@AfterEach
	public void cleanup() {

		super.signIn("auditor1", "auditor1");
		super.clickOnMenu("Auditor", "List my audits");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(0);
		super.checkFormExists();
		super.checkSubmitExists("Delete audit");
		super.clickOnSubmit("Delete audit");

		super.signOut();

	}
}
