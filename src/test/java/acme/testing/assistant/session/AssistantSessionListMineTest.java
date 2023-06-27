
package acme.testing.assistant.session;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class AssistantSessionListMineTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/session/list-mine.csv", encoding = "utf-8", numLinesToSkip = 1)
	void test100Positive(final int recordIndex, final String title, final String abstractMessage, final String timeStart, final String timeEnd, final String draftMode) {

		super.signIn("assistant1", "assistant1");

		super.clickOnMenu("Assistant", "Sessions");

		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(recordIndex, 0, title);
		super.checkColumnHasValue(recordIndex, 1, abstractMessage);
		super.checkColumnHasValue(recordIndex, 2, timeStart);
		super.checkColumnHasValue(recordIndex, 3, timeEnd);
		super.checkColumnHasValue(recordIndex, 4, draftMode);

	}

	//No test negative required

	@Test
	void test300Hacking() {

		super.checkLinkExists("Sign in");
		super.request("/assistant/session-tutorial/list-mine");
		super.checkPanicExists();

		super.signIn("administrator", "administrator");
		super.request("/assistant/session-tutorial/list-mine");
		super.checkPanicExists();
		super.signOut();

		super.signIn("lecturer1", "lecturer1");
		super.request("/assistant/session-tutorial/list-mine");
		super.checkPanicExists();
		super.signOut();

		super.signIn("company1", "company1");
		super.request("/assistant/session-tutorial/list-mine");
		super.checkPanicExists();
		super.signOut();

		super.signIn("auditor1", "auditor1");
		super.request("/assistant/session-tutorial/list-mine");
		super.checkPanicExists();
		super.signOut();

		super.signIn("student1", "student1");
		super.request("/assistant/session-tutorial/list-mine");
		super.checkPanicExists();
		super.signOut();

		super.signIn("assistant1", "assistant1");
		super.request("/assistant/session-tutorial/list-mine");
		super.checkNotPanicExists();
		super.signOut();

		super.signIn("assistant2", "assistant2");
		super.request("/assistant/session-tutorial/list-mine");
		super.checkNotPanicExists();
		super.signOut();

	}

}
