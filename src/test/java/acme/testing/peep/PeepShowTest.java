
package acme.testing.peep;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class PeepShowTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/peep/show-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String title, final String message, final String instantiation, final String link, final String email, final String nickName) {

		super.clickOnMenu("Anonymous", "Peep messages");
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();

		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("message", message);
		super.checkInputBoxHasValue("instantiation", instantiation);
		super.checkInputBoxHasValue("link", link);
		super.checkInputBoxHasValue("email", email);
		super.checkInputBoxHasValue("nickname", nickName);

	}

	@Test
	public void test200Negative() {

	}

	@Test
	public void test300Hacking() {

	}

}
