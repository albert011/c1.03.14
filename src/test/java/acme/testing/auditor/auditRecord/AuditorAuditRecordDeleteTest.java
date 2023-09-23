
package acme.testing.auditor.auditRecord;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.testing.TestHarness;

public class AuditorAuditRecordDeleteTest extends TestHarness {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuditorAuditRecordTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/auditRecord/delete-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive() {
		//TODO
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/auditRecord/delete-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative() {
		//TODO
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/auditRecord/delete-hacking.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test300Hacking() {
		//TODO
	}
}
