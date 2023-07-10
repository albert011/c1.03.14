
package acme.testing.lecturer.courseLecture;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.entities.course.Course;
import acme.framework.repositories.AbstractRepository;

public interface LecturerCourseLectureTestRepository extends AbstractRepository {

	@Query("SELECT c FROM Course c WHERE c.lecturer.userAccount.username = :username")
	Collection<Course> findManyCoursesByLecturerUsername(String username);
}
