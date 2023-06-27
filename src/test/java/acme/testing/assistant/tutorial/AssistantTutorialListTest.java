
package acme.testing.assistant.tutorial;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class AssistantTutorialListTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/tutorial/list.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String code, final String title, final String isPublished, final String abstractMessage, final String estimatedTime) {

		super.signIn("assistant1", "assistant1");

		super.clickOnMenu("Assistant", "Tutorials");

		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(recordIndex, 0, code);
		super.checkColumnHasValue(recordIndex, 1, title);
		super.checkColumnHasValue(recordIndex, 2, isPublished);
		super.checkColumnHasValue(recordIndex, 3, abstractMessage);
		super.checkColumnHasValue(recordIndex, 4, estimatedTime);

	}

	@Test
	public void test300Hacking() {

		super.checkLinkExists("Sign in");

		super.request("/assistant/tutorial/list");
		super.checkPanicExists();

		super.signIn("administrator", "administrator");
		super.request("/assistant/tutorial/list");
		super.checkPanicExists();
		super.signOut();

		super.signIn("lecturer1", "lecturer1");
		super.request("/assistant/tutorial/list");
		super.checkPanicExists();
		super.signOut();

		super.signIn("company1", "company1");
		super.request("/assistant/tutorial/list");
		super.checkPanicExists();
		super.signOut();

		super.signIn("auditor1", "auditor1");
		super.request("/assistant/tutorial/list");
		super.checkPanicExists();
		super.signOut();

		super.signIn("student1", "student1");
		super.request("/assistant/tutorial/list");
		super.checkPanicExists();
		super.signOut();

		super.signIn("assistant1", "assistant1");
		super.request("/assistant/tutorial/list");
		super.checkNotPanicExists();
		super.signOut();

		super.signIn("assistant2", "assistant2");
		super.request("/assistant/tutorial/list");
		super.checkNotPanicExists();
		super.signOut();

	}

}
