
package acme.testing.peep;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class PeepListAllTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/peep/list-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String nickName, final String instantiation, final String title) {
		// HINT: this test authenticates as an employer, then lists his or her jobs, 
		// HINT+ selects one of them, and check that it has the expected duties.

		super.clickOnMenu("Anonymous", "Peep messages");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(recordIndex, 0, nickName);
		super.checkColumnHasValue(recordIndex, 1, instantiation);
		super.checkColumnHasValue(recordIndex, 2, title);

	}

	@Test
	public void test200Negative() {

	}

	@Test
	public void test300Hacking() {

	}

}
