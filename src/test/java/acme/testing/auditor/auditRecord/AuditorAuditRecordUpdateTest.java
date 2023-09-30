
package acme.testing.auditor.auditRecord;

import java.util.Collection;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.audit.AuditRecord;
import acme.testing.TestHarness;

public class AuditorAuditRecordUpdateTest extends TestHarness {

	final String[]								auditData	= {
		"A1234", "Testing conclusion", "Testing Strong Points", "Testing Weak Points", "Curso de ruso"
	};

	final String[]								recordData	= {
		"Testing Subject", "Testing Assessment", "1984/02/05 09:00", "1984/02/06 10:00", "https://www.example.com/", "A+"
	};

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuditorAuditRecordTestRepository	repository;


	@BeforeEach
	public void setup() {

		super.signIn("auditor1", "auditor1");
		super.clickOnMenu("Auditor", "List my audits");

		super.clickOnButton("Create audit");
		super.fillInputBoxIn("code", this.auditData[0]);
		super.fillInputBoxIn("conclusion", this.auditData[1]);
		super.fillInputBoxIn("strongPoints", this.auditData[2]);
		super.fillInputBoxIn("weakPoints", this.auditData[3]);
		super.fillInputBoxIn("course", this.auditData[4]);
		super.clickOnSubmit("Create audit");

		super.clickOnMenu("Auditor", "List my audits");
		super.sortListing(0, "asc");

		super.clickOnListingRecord(0);

		super.clickOnButton("Audit Records of this audit");

		super.clickOnButton("Create audit record");

		super.fillInputBoxIn("subject", this.recordData[0]);
		super.fillInputBoxIn("assessment", this.recordData[1]);
		super.fillInputBoxIn("periodStart", this.recordData[2]);
		super.fillInputBoxIn("periodEnd", this.recordData[3]);
		super.fillInputBoxIn("moreInfo", this.recordData[4]);
		super.fillInputBoxIn("mark", this.recordData[5]);
		super.clickOnSubmit("Create audit record");

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/auditRecord/update-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int indexAudit, final int indexAuditRecord, final String subjectAuditRecord, final String assessmentAuditRecord, final String periodStart, final String periodEnd, final String moreInfo, final String markAuditRecord,
		final String edited) {

		super.signIn("auditor1", "auditor1");
		super.clickOnMenu("Auditor", "List my audits");
		super.sortListing(0, "asc");
		super.checkColumnHasValue(indexAudit, 0, this.auditData[0]);

		super.clickOnListingRecord(indexAudit);
		super.checkFormExists();
		super.checkInputBoxHasValue("mark", this.recordData[5]);
		super.checkButtonExists("Audit Records of this audit");
		super.clickOnButton("Audit Records of this audit");

		super.checkListingExists();
		super.checkColumnHasValue(indexAuditRecord, 0, this.recordData[0]);
		super.clickOnListingRecord(indexAuditRecord);

		super.checkFormExists();
		super.checkSubmitExists("Update audit record");

		//Actualizamos valores
		super.fillInputBoxIn("subject", subjectAuditRecord);
		super.fillInputBoxIn("assessment", assessmentAuditRecord);
		super.fillInputBoxIn("periodStart", periodStart);
		super.fillInputBoxIn("periodEnd", periodEnd);
		super.fillInputBoxIn("moreInfo", moreInfo);
		super.fillInputBoxIn("mark", markAuditRecord);

		super.clickOnSubmit("Update audit record");
		super.checkNotErrorsExist();

		//Comprobamos que se han actualizado bien
		super.clickOnMenu("Auditor", "List my audits");
		super.sortListing(0, "asc");
		super.checkColumnHasValue(indexAudit, 0, this.auditData[0]);

		super.clickOnListingRecord(indexAudit);

		super.checkFormExists();
		super.checkInputBoxHasValue("mark", markAuditRecord);

		super.checkButtonExists("Audit Records of this audit");
		super.clickOnButton("Audit Records of this audit");

		super.checkListingExists();

		super.checkColumnHasValue(indexAuditRecord, 0, subjectAuditRecord);
		super.clickOnListingRecord(indexAuditRecord);

		super.checkFormExists();
		super.checkInputBoxHasValue("subject", subjectAuditRecord);
		super.checkInputBoxHasValue("assessment", assessmentAuditRecord);
		super.checkInputBoxHasValue("periodStart", periodStart);
		super.checkInputBoxHasValue("periodEnd", periodEnd);
		super.checkInputBoxHasValue("moreInfo", moreInfo);
		super.checkInputBoxHasValue("mark", markAuditRecord);
		super.checkInputBoxHasValue("auditCode", this.auditData[0]);
		super.checkInputBoxHasValue("edited", edited);

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/auditRecord/update-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int indexAudit, final int indexAuditRecord, final String subjectAuditRecord, final String assessmentAuditRecord, final String periodStart, final String periodEnd, final String moreInfo, final String markAuditRecord) {

		super.signIn("auditor1", "auditor1");
		super.clickOnMenu("Auditor", "List my audits");
		super.sortListing(0, "asc");
		super.checkColumnHasValue(indexAudit, 0, this.auditData[0]);

		super.clickOnListingRecord(indexAudit);
		super.checkFormExists();
		super.checkInputBoxHasValue("mark", this.recordData[5]);
		super.checkButtonExists("Audit Records of this audit");
		super.clickOnButton("Audit Records of this audit");

		super.checkListingExists();
		super.checkColumnHasValue(indexAuditRecord, 0, this.recordData[0]);
		super.clickOnListingRecord(indexAuditRecord);

		super.checkFormExists();
		super.checkSubmitExists("Update audit record");

		//Actualizamos valores
		super.fillInputBoxIn("subject", subjectAuditRecord);
		super.fillInputBoxIn("assessment", assessmentAuditRecord);
		super.fillInputBoxIn("periodStart", periodStart);
		super.fillInputBoxIn("periodEnd", periodEnd);
		super.fillInputBoxIn("moreInfo", moreInfo);
		super.fillInputBoxIn("mark", markAuditRecord);

		super.clickOnSubmit("Update audit record");
		super.checkErrorsExist();

		//Comprobamos que no se han actualizado
		super.clickOnMenu("Auditor", "List my audits");
		super.sortListing(0, "asc");
		super.checkColumnHasValue(indexAudit, 0, this.auditData[0]);

		super.clickOnListingRecord(indexAudit);

		super.checkFormExists();
		super.checkInputBoxHasValue("mark", this.recordData[5]);

		super.checkButtonExists("Audit Records of this audit");
		super.clickOnButton("Audit Records of this audit");

		super.checkListingExists();

		super.checkColumnHasValue(indexAuditRecord, 0, this.recordData[0]);
		super.clickOnListingRecord(indexAuditRecord);

		super.checkFormExists();
		super.checkInputBoxHasValue("subject", this.recordData[0]);
		super.checkInputBoxHasValue("assessment", this.recordData[1]);
		super.checkInputBoxHasValue("periodStart", this.recordData[2]);
		super.checkInputBoxHasValue("periodEnd", this.recordData[3]);
		super.checkInputBoxHasValue("moreInfo", this.recordData[4]);
		super.checkInputBoxHasValue("mark", this.recordData[5]);
		super.checkInputBoxHasValue("auditCode", this.auditData[0]);
		super.checkInputBoxHasValue("edited", "false");

		super.signOut();
	}

	@Test
	public void test300Hacking() {
		Collection<AuditRecord> audits;
		String param, base;

		audits = this.repository.findManyAuditRecordsByAuditorUsername("auditor1");

		base = "/auditor/audit-record/update";

		for (final AuditRecord audit : audits) {
			param = String.format("id=%d", audit.getId());

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

			if (audit.getAudit().isPublished()) {
				super.signIn("auditor1", "auditor1");
				super.request(base, param);
				super.checkPanicExists();
				super.signOut();
			}
		}
	}

	@AfterEach
	public void cleanup() {
		super.signIn("auditor1", "auditor1");
		super.clickOnMenu("Auditor", "List my audits");
		super.sortListing(0, "asc");

		super.clickOnListingRecord(0);

		super.clickOnSubmit("Delete audit");

		super.signOut();

	}
}
