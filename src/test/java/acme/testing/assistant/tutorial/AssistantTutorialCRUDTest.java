
package acme.testing.assistant.tutorial;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.StaleElementReferenceException;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.course.Course;
import acme.entities.tutorial.Tutorial;
import acme.testing.TestHarness;

public class AssistantTutorialCRUDTest extends TestHarness {

	@Autowired
	protected AssistantTutorialTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/tutorial/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100PositiveCreate(final int recordIndex, final String code, final String title, final String abstractMessage, final String goals, final String estimatedTotalTime, final String course) {

		super.signIn("assistant1", "assistant1");
		super.clickOnMenu("Assistant", "Tutorials");
		super.checkListingExists();

		super.clickOnButton("Create Tutorial");
		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("abstractMessage", abstractMessage);
		super.fillInputBoxIn("goals", goals);
		super.fillInputBoxIn("estimatedTotalTime", estimatedTotalTime);
		super.fillInputBoxIn("course", course);
		super.clickOnSubmit("Create Tutorial");

		//A veces selenium no soporta la carga del hilo y devuelve el error siguiente
		try {
			super.clickOnMenu("Assistant", "Tutorials");
			super.checkListingExists();
		} catch (final StaleElementReferenceException e) {
			// TODO Auto-generated catch block
			super.clickOnMenu("Assistant", "Tutorials");
			super.checkListingExists();
		}

		super.checkColumnHasValue(recordIndex, 0, code);
		super.checkColumnHasValue(recordIndex, 1, title);
		super.checkColumnHasValue(recordIndex, 2, "false");
		super.checkColumnHasValue(recordIndex, 3, abstractMessage);
		super.checkColumnHasValue(recordIndex, 4, estimatedTotalTime);

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();

		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("abstractMessage", abstractMessage);
		super.checkInputBoxHasValue("goals", goals);
		super.checkInputBoxHasValue("estimatedTotalTime", estimatedTotalTime);
		final Course object = this.repository.getCourse(course);
		assert object != null;

		super.signOut();

	}

	@Test
	public void test100PositiveUpdate() {

		final String[] data = {
			"QWE1234", "Test title", "Test message", "Test goals", "1.2", "OKP 011"
		};

		super.signIn("assistant1", "assistant1");
		super.clickOnMenu("Assistant", "Tutorials");
		super.checkListingExists();

		super.clickOnButton("Create Tutorial");
		super.fillInputBoxIn("code", data[0]);
		super.fillInputBoxIn("title", data[1]);
		super.fillInputBoxIn("abstractMessage", data[2]);
		super.fillInputBoxIn("goals", data[3]);
		super.fillInputBoxIn("estimatedTotalTime", data[4]);
		super.fillInputBoxIn("course", data[5]);
		super.clickOnSubmit("Create Tutorial");

		final Tutorial tutorial = this.repository.getTutorialByCode(data[0]);
		assert !tutorial.isPublished();
		super.request("/assistant/tutorial/show", String.format("id=%s", tutorial.getId()));
		super.checkFormExists();

		super.fillInputBoxIn("title", "Title updated");
		super.clickOnSubmit("Update");
		super.checkNotErrorsExist();

		super.signOut();

	}

	@Test
	public void test100PositivePublish() {

		final String[] data = {
			"QWE1235", "Test title", "Test message", "Test goals", "1.2", "OKP 011"
		};

		final String[] dataSession = {
			"Z Example title", "Z Example message", data[0], "HANDS_ON", "2023/08/03 15:30", "2023/08/03 17:30"
		};

		super.signIn("assistant1", "assistant1");
		super.clickOnMenu("Assistant", "Tutorials");
		super.checkListingExists();

		super.clickOnButton("Create Tutorial");
		super.fillInputBoxIn("code", data[0]);
		super.fillInputBoxIn("title", data[1]);
		super.fillInputBoxIn("abstractMessage", data[2]);
		super.fillInputBoxIn("goals", data[3]);
		super.fillInputBoxIn("estimatedTotalTime", data[4]);
		super.fillInputBoxIn("course", data[5]);
		super.clickOnSubmit("Create Tutorial");

		final Tutorial tutorial = this.repository.getTutorialByCode(data[0]);
		assert !tutorial.isPublished();

		super.clickOnMenu("Assistant", "Sessions");
		super.clickOnButton("Create Session");
		super.fillInputBoxIn("title", dataSession[0]);
		super.fillInputBoxIn("abstractMessage", dataSession[1]);
		super.fillInputBoxIn("tutorial", dataSession[2]);
		super.fillInputBoxIn("type", dataSession[3]);
		super.fillInputBoxIn("timeStart", dataSession[4]);
		super.fillInputBoxIn("timeEnd", dataSession[5]);
		super.clickOnSubmit("Create Session");

		super.request("/assistant/tutorial/show", String.format("id=%s", tutorial.getId()));
		super.clickOnSubmit("Publish");
		super.checkNotErrorsExist();

		super.signOut();

	}

	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/tutorial/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200NegativeCreate(final int recordIndex, final String code, final String title, final String abstractMessage, final String goals, final String estimatedTotalTime, final String course) {

		super.signIn("assistant1", "assistant1");
		super.clickOnMenu("Assistant", "Tutorials");
		super.checkListingExists();

		super.clickOnButton("Create Tutorial");
		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("abstractMessage", abstractMessage);
		super.fillInputBoxIn("goals", goals);
		super.fillInputBoxIn("estimatedTotalTime", estimatedTotalTime);
		if (course != null)
			super.fillInputBoxIn("course", course);
		super.clickOnSubmit("Create Tutorial");
		super.checkErrorsExist();

		super.signOut();

	}

	@Test
	public void test200NegativePublish() {
		final String[] data = {
			"QWE1236", "Test title", "Test message", "Test goals", "1.2", "OKP 011"
		};

		super.signIn("assistant1", "assistant1");
		super.clickOnMenu("Assistant", "Tutorials");
		super.checkListingExists();

		super.clickOnButton("Create Tutorial");
		super.fillInputBoxIn("code", data[0]);
		super.fillInputBoxIn("title", data[1]);
		super.fillInputBoxIn("abstractMessage", data[2]);
		super.fillInputBoxIn("goals", data[3]);
		super.fillInputBoxIn("estimatedTotalTime", data[4]);
		super.fillInputBoxIn("course", data[5]);
		super.clickOnSubmit("Create Tutorial");

		final Tutorial tutorial = this.repository.getTutorialByCode(data[0]);
		assert !tutorial.isPublished();

		super.request("/assistant/tutorial/show", String.format("id=%s", tutorial.getId()));
		super.clickOnSubmit("Publish");
		super.checkErrorsExist();
		super.signOut();

	}

	@Test
	public void test300Hacking() {

		super.checkLinkExists("Sign in");
		super.request("/assistant/tutorial/create");
		super.checkPanicExists();
		final Tutorial tutorial = this.repository.getTutorialByCode("A0000");
		super.request("/assistant/tutorial/show", String.format("id=%s", tutorial.getId()));
		super.checkPanicExists();

		super.signIn("administrator", "administrator");
		super.request("/assistant/tutorial/create");
		super.checkPanicExists();
		super.request("/assistant/tutorial/show", String.format("id=%s", tutorial.getId()));
		super.checkPanicExists();
		super.signOut();

		super.signIn("lecturer1", "lecturer1");
		super.request("/assistant/tutorial/create");
		super.checkPanicExists();
		super.request("/assistant/tutorial/show", String.format("id=%s", tutorial.getId()));
		super.checkPanicExists();
		super.signOut();

		super.signIn("company1", "company1");
		super.request("/assistant/tutorial/create");
		super.checkPanicExists();
		super.request("/assistant/tutorial/show", String.format("id=%s", tutorial.getId()));
		super.checkPanicExists();
		super.signOut();

		super.signIn("auditor1", "auditor1");
		super.request("/assistant/tutorial/create");
		super.checkPanicExists();
		super.request("/assistant/tutorial/show", String.format("id=%s", tutorial.getId()));
		super.checkPanicExists();
		super.signOut();

		super.signIn("student1", "student1");
		super.request("/assistant/tutorial/create");
		super.checkPanicExists();
		super.request("/assistant/tutorial/show", String.format("id=%s", tutorial.getId()));
		super.checkPanicExists();
		super.signOut();

		super.signIn("assistant1", "assistant1");
		super.request("/assistant/tutorial/create");
		super.checkNotPanicExists();
		super.request("/assistant/tutorial/show", String.format("id=%s", tutorial.getId()));
		super.checkNotPanicExists();
		super.signOut();

		super.signIn("assistant2", "assistant2");
		super.request("/assistant/tutorial/create");
		super.checkNotPanicExists();
		super.request("/assistant/tutorial/show", String.format("id=%s", tutorial.getId()));
		super.checkPanicExists();
		super.signOut();

	}

}
