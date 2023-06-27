
package acme.testing.assistant.session;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.tutorial.Tutorial;
import acme.testing.TestHarness;

public class AssistantSessionListFromTutorialTest extends TestHarness {

	@Autowired
	protected AssistantSessionTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/session/list.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String title, final String abstractMessage, final String timeStart, final String timeEnd, final String draftMode) {

		super.signIn("assistant1", "assistant1");
		final Tutorial tutorial = this.repository.getTutorial("A0000");
		super.request("/assistant/session-tutorial/list", String.format("tutorialId=%s", tutorial.getId()));
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(recordIndex, 0, title);
		super.checkColumnHasValue(recordIndex, 1, abstractMessage);
		super.checkColumnHasValue(recordIndex, 2, timeStart);
		super.checkColumnHasValue(recordIndex, 3, timeEnd);
		super.checkColumnHasValue(recordIndex, 4, draftMode);

	}

	@Test
	public void test300Hacking() {

		super.checkLinkExists("Sign in");

		super.signIn("assistant1", "assistant1");
		super.request("/assistant/tutorial/list");
		super.checkNotPanicExists();
		super.clickOnListingRecord(0);
		super.clickOnButton("List Sessions");
		String id = super.getCurrentQuery();
		id = id.replace("?", "");
		super.checkNotPanicExists();
		super.request("/assistant/session-tutorial/list", "tutorialId=999");
		super.checkPanicExists();
		super.signOut();

		super.request("/assistant/tutorial/list");
		super.checkPanicExists();
		super.request("/assistant/session-tutorial/list", id);
		super.checkPanicExists();

		super.signIn("administrator", "administrator");
		super.request("/assistant/tutorial/list");
		super.checkPanicExists();
		super.request("/assistant/session-tutorial/list", id);
		super.checkPanicExists();
		super.signOut();

		super.signIn("lecturer1", "lecturer1");
		super.request("/assistant/tutorial/list");
		super.checkPanicExists();
		super.request("/assistant/session-tutorial/list", id);
		super.checkPanicExists();
		super.signOut();

		super.signIn("company1", "company1");
		super.request("/assistant/tutorial/list");
		super.checkPanicExists();
		super.request("/assistant/session-tutorial/list", id);
		super.checkPanicExists();
		super.signOut();

		super.signIn("auditor1", "auditor1");
		super.request("/assistant/tutorial/list");
		super.checkPanicExists();
		super.request("/assistant/session-tutorial/list", id);
		super.checkPanicExists();
		super.signOut();

		super.signIn("student1", "student1");
		super.request("/assistant/tutorial/list");
		super.checkPanicExists();
		super.request("/assistant/session-tutorial/list", id);
		super.checkPanicExists();
		super.signOut();

		super.signIn("assistant2", "assistant2");
		super.request("/assistant/tutorial/list");
		super.checkNotPanicExists();
		super.request("/assistant/session-tutorial/list", id);
		super.checkPanicExists();
		super.signOut();

	}

}
