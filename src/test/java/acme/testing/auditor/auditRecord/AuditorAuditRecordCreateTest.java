
package acme.testing.auditor.auditRecord;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.audit.Audit;
import acme.testing.TestHarness;

public class AuditorAuditRecordCreateTest extends TestHarness {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuditorAuditRecordTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/auditRecord/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int indexAudit, final String codeAudit, final int indexAuditRecord, final String subjectAuditRecord, final String assessmentAuditRecord, final String periodStart, final String periodEnd, final String moreInfo,
		final String markAuditRecord, final String edited) {
		super.signIn("auditor1", "auditor1");

		super.clickOnMenu("Auditor", "List my audits");
		super.sortListing(0, "asc");
		super.checkColumnHasValue(indexAudit, 0, codeAudit);

		super.clickOnListingRecord(indexAudit);
		super.checkFormExists();

		super.checkButtonExists("Audit Records of this audit");
		super.clickOnButton("Audit Records of this audit");

		super.checkListingExists();
		super.clickOnButton("Create audit record");

		super.checkFormExists();
		super.checkSubmitExists("Create audit record");

		super.fillInputBoxIn("subject", subjectAuditRecord);
		super.fillInputBoxIn("assessment", assessmentAuditRecord);
		super.fillInputBoxIn("periodStart", periodStart);
		super.fillInputBoxIn("periodEnd", periodEnd);
		super.fillInputBoxIn("moreInfo", moreInfo);
		super.fillInputBoxIn("mark", markAuditRecord);
		if (edited.equals("true"))
			super.fillInputBoxIn("accept", edited);
		super.clickOnSubmit("Create audit record");

		super.checkNotErrorsExist();

		super.clickOnMenu("Auditor", "List my audits");
		super.sortListing(0, "asc");
		super.checkColumnHasValue(indexAudit, 0, codeAudit);

		super.clickOnListingRecord(indexAudit);
		super.checkFormExists();

		super.checkButtonExists("Audit Records of this audit");
		super.clickOnButton("Audit Records of this audit");

		super.checkListingExists();
		super.sortListing(0, "desc");

		super.checkColumnHasValue(indexAuditRecord, 0, subjectAuditRecord);

		super.clickOnListingRecord(indexAuditRecord);

		super.checkFormExists();

		super.checkInputBoxHasValue("subject", subjectAuditRecord);
		super.checkInputBoxHasValue("assessment", assessmentAuditRecord);
		super.checkInputBoxHasValue("periodStart", periodStart);
		super.checkInputBoxHasValue("periodEnd", periodEnd);
		super.checkInputBoxHasValue("moreInfo", moreInfo);
		super.checkInputBoxHasValue("mark", markAuditRecord);
		super.checkInputBoxHasValue("auditCode", codeAudit);

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/auditRecord/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int indexAudit, final String codeAudit, final int indexAuditRecord, final String subjectAuditRecord, final String assessmentAuditRecord, final String periodStart, final String periodEnd, final String moreInfo,
		final String markAuditRecord, final String edited, final String clickAccept) {
		super.signIn("auditor1", "auditor1");

		super.clickOnMenu("Auditor", "List my audits");
		super.sortListing(0, "asc");
		super.checkColumnHasValue(indexAudit, 0, codeAudit);

		super.clickOnListingRecord(indexAudit);
		super.checkFormExists();

		super.checkButtonExists("Audit Records of this audit");
		super.clickOnButton("Audit Records of this audit");

		super.checkListingExists();
		super.clickOnButton("Create audit record");

		super.checkFormExists();
		super.checkSubmitExists("Create audit record");

		super.fillInputBoxIn("subject", subjectAuditRecord);
		super.fillInputBoxIn("assessment", assessmentAuditRecord);
		super.fillInputBoxIn("periodStart", periodStart);
		super.fillInputBoxIn("periodEnd", periodEnd);
		super.fillInputBoxIn("moreInfo", moreInfo);
		super.fillInputBoxIn("mark", markAuditRecord);
		if (edited.equals("true"))
			super.fillInputBoxIn("accept", clickAccept);
		super.clickOnSubmit("Create audit record");

		super.checkErrorsExist();

		super.signOut();
	}

	@Test
	public void test300Hacking() {
		Collection<Audit> audits;
		String param, base;

		audits = this.repository.findManyAuditsByAuditorUsername("auditor1");

		base = "/auditor/audit-record/create";

		for (final Audit audit : audits) {
			param = String.format("auditId=%d", audit.getId());

			super.checkLinkExists("Sign in");
			super.request(base, param);
			super.checkPanicExists();

			super.signIn("administrator", "administrator");
			super.request(base, param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("company1", "company1");
			super.request(base, param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("student1", "student1");
			super.request(base, param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("auditor2", "auditor2");
			super.request(base, param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("lecturer1", "lecturer1");
			super.request(base, param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("assistant1", "assistant1");
			super.request(base, param);
			super.checkPanicExists();
			super.signOut();
		}
	}
}
