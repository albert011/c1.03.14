
package acme.testing.peep;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class PeepPublishTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/peep/publish-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String title, final String message, final String link, final String email, final String nickName) {

		super.clickOnMenu("Anonymous", "Peep messages");
		super.checkListingExists();

		super.clickOnButton("Publish New Message");
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("message", message);
		super.fillInputBoxIn("link", link);
		super.fillInputBoxIn("email", email);
		super.fillInputBoxIn("nickname", nickName);
		super.clickOnSubmit("Publish");

		super.clickOnMenu("Anonymous", "Peep messages");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(recordIndex, 0, nickName);
		super.checkColumnHasValue(recordIndex, 2, title);

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("message", message);
		super.checkInputBoxHasValue("link", link);
		super.checkInputBoxHasValue("email", email);
		super.checkInputBoxHasValue("nickname", nickName);

	}

	@ParameterizedTest
	@CsvFileSource(resources = "/peep/publish-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int recordIndex, final String title, final String message, final String link, final String email, final String nickName) {
		// HINT: this test attempts to create jobs with incorrect data.

		super.clickOnMenu("Anonymous", "Peep messages");
		super.clickOnButton("Publish New Message");
		super.checkFormExists();

		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("message", message);
		super.fillInputBoxIn("link", link);
		super.fillInputBoxIn("email", email);
		super.fillInputBoxIn("nickname", nickName);
		super.clickOnSubmit("Publish");

		super.checkErrorsExist();

	}

	@Test
	public void test300Hacking() {

	}

}
