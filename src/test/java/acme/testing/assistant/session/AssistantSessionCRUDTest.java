
package acme.testing.assistant.session;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.session.SessionTutorial;
import acme.testing.TestHarness;

public class AssistantSessionCRUDTest extends TestHarness {

	@Autowired
	protected AssistantSessionTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/session/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100PositiveCreate(final int recordIndex, final String title, final String abstractMessage, final String tutorial, final String type, final String timeStart, final String timeEnd, final String link) {

		super.signIn("assistant1", "assistant1");
		super.clickOnMenu("Assistant", "Sessions");
		super.checkListingExists();
		super.clickOnButton("Create Session");
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("abstractMessage", abstractMessage);
		super.fillInputBoxIn("tutorial", tutorial);
		super.fillInputBoxIn("type", type);
		super.fillInputBoxIn("timeStart", timeStart);
		super.fillInputBoxIn("timeEnd", timeEnd);
		if (link != null)
			super.fillInputBoxIn("link", link);
		super.clickOnSubmit("Create Session");

		super.clickOnMenu("Assistant", "Sessions");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(recordIndex, 0, title);
		super.checkColumnHasValue(recordIndex, 1, abstractMessage);
		super.checkColumnHasValue(recordIndex, 2, timeStart);
		super.checkColumnHasValue(recordIndex, 3, timeEnd);
		super.checkColumnHasValue(recordIndex, 4, "true");

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		final String query = super.getCurrentQuery();
		final int idSession = Integer.valueOf(query.replace("?id=", ""));
		final SessionTutorial session = this.repository.getSession(idSession);
		assert session.getTutorial().getCode().equals(tutorial);
		assert session.getType().toString().equals(type);
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("abstractMessage", abstractMessage);
		super.checkInputBoxHasValue("timeStart", timeStart);
		super.checkInputBoxHasValue("timeEnd", timeEnd);
		if (link != null)
			super.checkInputBoxHasValue("link", link);

		super.signOut();
	}

	@Test
	public void test100PositiveUpdate() {
		final String[] data = {
			"Z Example title", "Z Example message", "A0000", "HANDS_ON", "2023/08/03 15:30", "2023/08/03 17:30"
		};
		super.signIn("assistant1", "assistant1");
		super.clickOnMenu("Assistant", "Sessions");
		super.checkListingExists();
		super.clickOnButton("Create Session");
		super.fillInputBoxIn("title", data[0]);
		super.fillInputBoxIn("abstractMessage", data[1]);
		super.fillInputBoxIn("tutorial", data[2]);
		super.fillInputBoxIn("type", data[3]);
		super.fillInputBoxIn("timeStart", data[4]);
		super.fillInputBoxIn("timeEnd", data[5]);
		super.clickOnSubmit("Create Session");

		super.clickOnMenu("Assistant", "Sessions");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(6);
		super.checkFormExists();
		final String query = super.getCurrentQuery();
		final int idSession = Integer.valueOf(query.replace("?id=", ""));
		final SessionTutorial session = this.repository.getSession(idSession);
		assert session.isDraftMode();
		super.fillInputBoxIn("title", "Modified title");
		super.clickOnSubmit("Update");
		super.checkNotErrorsExist();

		super.signOut();

	}

	@Test
	public void test100PositiveDelete() {
		final String[] data = {
			"Z Example title", "Z Example message", "A0000", "HANDS_ON", "2023/08/03 15:30", "2023/08/03 17:30"
		};
		super.signIn("assistant1", "assistant1");
		super.clickOnMenu("Assistant", "Sessions");
		super.checkListingExists();
		super.clickOnButton("Create Session");
		super.fillInputBoxIn("title", data[0]);
		super.fillInputBoxIn("abstractMessage", data[1]);
		super.fillInputBoxIn("tutorial", data[2]);
		super.fillInputBoxIn("type", data[3]);
		super.fillInputBoxIn("timeStart", data[4]);
		super.fillInputBoxIn("timeEnd", data[5]);
		super.clickOnSubmit("Create Session");

		super.clickOnMenu("Assistant", "Sessions");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(6);
		super.checkFormExists();
		final String query = super.getCurrentQuery();
		final int idSession = Integer.valueOf(query.replace("?id=", ""));
		final SessionTutorial session = this.repository.getSession(idSession);
		assert session.isDraftMode();
		super.clickOnSubmit("Delete");
		super.checkNotErrorsExist();

		super.signOut();

	}

	@Test
	public void test100PositivePublish() {
		final String[] data = {
			"Z Example title", "Z Example message", "A0000", "HANDS_ON", "2023/08/03 15:30", "2023/08/03 17:30"
		};
		super.signIn("assistant1", "assistant1");
		super.clickOnMenu("Assistant", "Sessions");
		super.checkListingExists();
		super.clickOnButton("Create Session");
		super.fillInputBoxIn("title", data[0]);
		super.fillInputBoxIn("abstractMessage", data[1]);
		super.fillInputBoxIn("tutorial", data[2]);
		super.fillInputBoxIn("type", data[3]);
		super.fillInputBoxIn("timeStart", data[4]);
		super.fillInputBoxIn("timeEnd", data[5]);
		super.clickOnSubmit("Create Session");

		super.clickOnMenu("Assistant", "Sessions");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(6);
		super.checkFormExists();
		final String query = super.getCurrentQuery();
		final int idSession = Integer.valueOf(query.replace("?id=", ""));
		final SessionTutorial session = this.repository.getSession(idSession);
		assert session.isDraftMode();
		super.clickOnSubmit("Publish");
		super.checkNotErrorsExist();

		super.signOut();

	}

	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/session/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200NegativeCreate(final int recordIndex, final String title, final String abstractMessage, final String tutorial, final String type, final String timeStart, final String timeEnd, final String link) {

		super.signIn("assistant1", "assistant1");
		super.clickOnMenu("Assistant", "Sessions");
		super.checkListingExists();
		super.clickOnButton("Create Session");
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("abstractMessage", abstractMessage);
		if (tutorial != null)
			super.fillInputBoxIn("tutorial", tutorial);
		if (type != null)
			super.fillInputBoxIn("type", type);
		super.fillInputBoxIn("timeStart", timeStart);
		super.fillInputBoxIn("timeEnd", timeEnd);
		super.fillInputBoxIn("link", link);
		super.clickOnSubmit("Create Session");
		super.checkErrorsExist();

		super.signOut();

	}

	@Test
	public void test300Hacking() {

		super.checkLinkExists("Sign in");

		super.signIn("assistant1", "assistant1");
		super.request("/assistant/session-tutorial/create");
		super.checkNotPanicExists();
		super.request("/assistant/session-tutorial/list-mine");
		super.checkNotPanicExists();
		super.clickOnListingRecord(0);
		super.checkNotPanicExists();
		final String query = super.getCurrentQuery();
		final int id = Integer.valueOf(query.replace("?id=", ""));
		final SessionTutorial session = this.repository.getSession(id);
		super.signOut();

		super.request("/assistant/session-tutorial/create");
		super.checkPanicExists();
		super.request("/assistant/session-tutorial/list-mine");
		super.checkPanicExists();
		super.request("/assistant/session-tutorial/show", String.format("id=%s", session.getId()));
		super.checkPanicExists();

		super.signIn("administrator", "administrator");
		super.request("/assistant/session-tutorial/create");
		super.checkPanicExists();
		super.request("/assistant/session-tutorial/list-mine");
		super.checkPanicExists();
		super.request("/assistant/session-tutorial/show", String.format("id=%s", session.getId()));
		super.checkPanicExists();
		super.signOut();

		super.signIn("lecturer1", "lecturer1");
		super.request("/assistant/session-tutorial/create");
		super.checkPanicExists();
		super.request("/assistant/session-tutorial/list-mine");
		super.checkPanicExists();
		super.request("/assistant/session-tutorial/show", String.format("id=%s", session.getId()));
		super.checkPanicExists();
		super.signOut();

		super.signIn("company1", "company1");
		super.request("/assistant/session-tutorial/create");
		super.checkPanicExists();
		super.request("/assistant/session-tutorial/list-mine");
		super.checkPanicExists();
		super.request("/assistant/session-tutorial/show", String.format("id=%s", session.getId()));
		super.checkPanicExists();
		super.signOut();

		super.signIn("auditor1", "auditor1");
		super.request("/assistant/session-tutorial/create");
		super.checkPanicExists();
		super.request("/assistant/session-tutorial/list-mine");
		super.checkPanicExists();
		super.request("/assistant/session-tutorial/show", String.format("id=%s", session.getId()));
		super.checkPanicExists();
		super.signOut();

		super.signIn("student1", "student1");
		super.request("/assistant/session-tutorial/create");
		super.checkPanicExists();
		super.request("/assistant/session-tutorial/list-mine");
		super.checkPanicExists();
		super.request("/assistant/session-tutorial/show", String.format("id=%s", session.getId()));
		super.checkPanicExists();
		super.signOut();

		super.signIn("assistant2", "assistant2");
		super.request("/assistant/session-tutorial/create");
		super.checkNotPanicExists();
		super.request("/assistant/session-tutorial/list-mine");
		super.checkNotPanicExists();
		super.request("/assistant/session-tutorial/show", String.format("id=%s", session.getId()));
		super.checkPanicExists();
		super.signOut();

	}

}
