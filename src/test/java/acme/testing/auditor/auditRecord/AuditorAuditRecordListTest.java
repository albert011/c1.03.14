
package acme.testing.auditor.auditRecord;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.audit.Audit;
import acme.testing.TestHarness;

public class AuditorAuditRecordListTest extends TestHarness {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuditorAuditRecordTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/auditRecord/list-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int indexAudit, final String codeAudit, final int indexAuditRecord, final String subjectAuditRecord, final String markAuditRecord, final String editedAuditRecord) {
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
		super.checkColumnHasValue(indexAuditRecord, 1, markAuditRecord);
		super.checkColumnHasValue(indexAuditRecord, 2, editedAuditRecord);

		super.signOut();

	}

	@Test
	public void test200Negative() {

	}

	@Test
	public void test300Hacking() {
		Collection<Audit> audits;
		String base, param;

		base = "/auditor/audit-record/list";

		audits = this.repository.findManyAuditsWithAuditRecordByAuditorUsername("auditor1");

		for (final Audit audit : audits) {
			param = String.format("auditId=%d", audit.getId());

			super.checkLinkExists("Sign in");
			super.request(base, param);
			super.checkPanicExists();

			super.signIn("administrator", "administrator");
			super.request(base, param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("lecturer1", "lecturer1");
			super.request(base, param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("student1", "student1");
			super.request(base, param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("assistant1", "assistant1");
			super.request(base, param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("company1", "company1");
			super.request(base, param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("auditor2", "auditor2");
			super.request(base, param);
			super.checkPanicExists();
			super.signOut();
		}
	}
}
