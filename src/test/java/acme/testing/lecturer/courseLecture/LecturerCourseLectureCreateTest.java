
package acme.testing.lecturer.courseLecture;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class LecturerCourseLectureCreateTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/course-lecture/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String courseCode, final String lectureTitle) {

		super.signIn("lecturer7", "lecturer7");

		super.clickOnMenu("Lecturer", "List my courses");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(recordIndex, 0, courseCode);
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.clickOnButton("Lectures in this course");

		super.checkListingExists();
		super.clickOnButton("Create");

		super.checkFormExists();
		super.fillInputBoxIn("course", courseCode);
		super.fillInputBoxIn("lecture", lectureTitle);
		super.clickOnSubmit("Create");

		super.clickOnMenu("Lecturer", "List my courses");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(recordIndex, 0, courseCode);
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.clickOnButton("Lectures in this course");

		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(recordIndex, 0, courseCode);

		super.signOut();

	}

	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/course-lecture/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int recordIndex, final String courseCode, final String lectureTitle) {

		super.signIn("lecturer7", "lecturer7");

		super.clickOnMenu("Lecturer", "List my courses");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(recordIndex, 0, courseCode);
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.clickOnButton("Lectures in this course");

		super.checkListingExists();
		super.clickOnButton("Create");

		super.checkFormExists();
		super.fillInputBoxIn("course", courseCode);
		super.fillInputBoxIn("lecture", lectureTitle);
		super.clickOnSubmit("Create");

		super.checkErrorsExist();

		super.signOut();
	}

	@Test
	public void test300Hacking() {

		super.checkLinkExists("Sign in");
		super.request("/lecturer/course-lecture/create");
		super.checkPanicExists();

		super.signIn("administrator", "administrator");
		super.request("/lecturer/course-lecture/create");
		super.checkPanicExists();
		super.signOut();

		super.signIn("auditor1", "auditor1");
		super.request("/lecturer/course-lecture/create");
		super.checkPanicExists();
		super.signOut();

		super.signIn("assistant1", "assistant1");
		super.request("/lecturer/course-lecture/create");
		super.checkPanicExists();
		super.signOut();

		super.signIn("company1", "company1");
		super.request("/lecturer/course-lecture/create");
		super.checkPanicExists();
		super.signOut();

		super.signIn("student1", "student1");
		super.request("/lecturer/course-lecture/create");
		super.checkPanicExists();
		super.signOut();

	}
}
