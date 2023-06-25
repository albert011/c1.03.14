
package acme.testing.peep;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class PeepPublishAuthenticatedTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/peep/create-authenticated-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String title, final String message, final String instantiation, final String link, final String email, final String nickName) {

		super.signIn("lecturer1", "lecturer1");

		super.clickOnMenu("Any Role", "Peep messages");
		super.checkListingExists();

		super.clickOnButton("Publish New Message");
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("message", message);
		super.fillInputBoxIn("instantiation", instantiation);
		super.fillInputBoxIn("link", link);
		super.fillInputBoxIn("email", email);
		super.clickOnSubmit("Publish");

		super.clickOnMenu("Any Role", "Peep messages");
		super.checkListingExists();
		super.sortListing(0, "desc");
		super.checkColumnHasValue(recordIndex, 0, nickName);
		super.checkColumnHasValue(recordIndex, 1, instantiation);
		super.checkColumnHasValue(recordIndex, 2, title);

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("message", message);
		super.checkInputBoxHasValue("instantiation", instantiation);
		super.checkInputBoxHasValue("link", link);
		super.checkInputBoxHasValue("email", email);
		super.checkInputBoxHasValue("nickname", nickName);

		super.signOut();

	}

	@ParameterizedTest
	@CsvFileSource(resources = "/peep/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int recordIndex, final String title, final String message, final String instantiation, final String link, final String email, final String nickName) {
		// HINT: this test attempts to create jobs with incorrect data.

		super.signIn("lecturer1", "lecturer1");

		super.clickOnMenu("Any Role", "Peep messages");
		super.clickOnButton("Publish New Message");
		super.checkFormExists();

		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("message", message);
		super.fillInputBoxIn("instantiation", instantiation);
		super.fillInputBoxIn("link", link);
		super.fillInputBoxIn("email", email);
		super.fillInputBoxIn("nickname", nickName);
		super.clickOnSubmit("Publish");

		super.checkErrorsExist();

		super.signOut();

	}

	@Test
	public void test300Hacking() {

	}

}
