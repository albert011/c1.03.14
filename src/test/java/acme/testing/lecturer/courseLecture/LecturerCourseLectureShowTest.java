
package acme.testing.lecturer.courseLecture;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.course.Course;
import acme.testing.TestHarness;

public class LecturerCourseLectureShowTest extends TestHarness {

	@Autowired
	protected LecturerCourseLectureTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/course-lecture/show-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int courseIndex, final int recordIndex, final String courseCode, final String lectureTitle) {

		super.signIn("lecturer7", "lecturer7");

		super.clickOnMenu("Lecturer", "List my courses");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(courseIndex, 0, courseCode);
		super.clickOnListingRecord(courseIndex);
		super.checkFormExists();
		super.clickOnButton("Lectures in this course");

		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();

		super.checkInputBoxHasValue("course", courseCode);
		super.checkInputBoxHasValue("lecture", lectureTitle);

		super.signOut();

	}

	@Test
	public void test200Negative() {
		//Listings doesnt't have negative tests
	}

	@Test
	public void test300Hacking() {
		Collection<Course> courses;
		String param;

		courses = this.repository.findManyCoursesByLecturerUsername("lecturer1");
		for (final Course course : courses)
			if (course.isDraftMode()) {
				param = String.format("id=%d", course.getId());
				super.checkLinkExists("Sign in");
				super.request("/lecturer/course-lecture/show", param);
				super.checkPanicExists();

				super.signIn("administrator", "administrator");
				super.request("/lecturer/course-lecture/show", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("auditor1", "auditor1");
				super.request("/lecturer/course-lecture/show", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("assistant1", "assistant1");
				super.request("/lecturer/course-lecture/show", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("company1", "company1");
				super.request("/lecturer/course-lecture/show", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("student1", "student1");
				super.request("/lecturer/course/show", param);
				super.checkPanicExists();
				super.signOut();
			}
	}
}
