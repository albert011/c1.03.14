
package acme.testing.auditor.auditRecord;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.audit.AuditRecord;
import acme.testing.TestHarness;

public class AuditorAuditRecordShowTest extends TestHarness {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuditorAuditRecordTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/auditRecord/show-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int indexAudit, final String codeAudit, final int indexAuditRecord, final String subjectAuditRecord, final String assessmentAuditRecord, final String periodStart, final String periodEnd, final String moreInfo,
		final String markAuditRecord) {
		super.signIn("auditor1", "auditor1");

		super.clickOnMenu("Auditor", "List my audits");
		super.sortListing(0, "asc");
		super.checkColumnHasValue(indexAudit, 0, codeAudit);

		super.clickOnListingRecord(indexAudit);
		super.checkFormExists();

		super.checkButtonExists("Audit Records of this audit");
		super.clickOnButton("Audit Records of this audit");

		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(indexAuditRecord, 0, subjectAuditRecord);

		super.clickOnListingRecord(indexAuditRecord);

		super.checkFormExists();

		super.checkInputBoxHasValue("subject", subjectAuditRecord);
		super.checkInputBoxHasValue("assessment", assessmentAuditRecord);
		super.checkInputBoxHasValue("assessment", assessmentAuditRecord);
		super.checkInputBoxHasValue("periodStart", periodStart);
		super.checkInputBoxHasValue("periodEnd", periodEnd);
		super.checkInputBoxHasValue("moreInfo", moreInfo);
		super.checkInputBoxHasValue("mark", markAuditRecord);
		super.checkInputBoxHasValue("auditCode", codeAudit);

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/auditRecord/show-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative() {
		//TODO
	}

	@Test
	public void test300Hacking() {

		Collection<AuditRecord> audits;
		String param;

		audits = this.repository.findManyAuditRecordsByAuditorUsername("auditor1");

		for (final AuditRecord record : audits) {
			param = String.format("id=%d", record.getId());

			super.checkLinkExists("Sign in");
			super.request("/auditor/audit-record/show", param);
			super.checkPanicExists();

			super.signIn("administrator", "administrator");
			super.request("/auditor/audit-record/show", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("lecturer1", "lecturer1");
			super.request("/auditor/audit-record/show", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("student1", "student1");
			super.request("/auditor/audit-record/show", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("assistant1", "assistant1");
			super.request("/auditor/audit-record/show", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("company1", "company1");
			super.request("/auditor/audit-record/show", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("auditor2", "auditor2");
			super.request("/auditor/audit-record/show", param);
			super.checkPanicExists();
			super.signOut();
		}
	}
}
