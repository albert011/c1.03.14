
package acme.testing.auditor.auditRecord;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class AuditorAuditRecordListTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/auditRecord/list-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive() {
		//TODO
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/auditRecord/list-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative() {
		//TODO
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/auditRecord/list-hacking.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test300Hacking() {
		//TODO
	}
}
