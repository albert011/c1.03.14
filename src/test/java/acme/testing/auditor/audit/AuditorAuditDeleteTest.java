
package acme.testing.auditor.audit;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.testing.TestHarness;

public class AuditorAuditDeleteTest extends TestHarness {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuditorAuditTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/audit/delete-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive() {

	}

	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/audit/delete-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative() {

	}

	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/audit/delete-hacking.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test300Hacking() {

	}
}
