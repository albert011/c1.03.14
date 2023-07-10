
package acme.testing.lecturer.course;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.entities.course.Course;
import acme.framework.repositories.AbstractRepository;

public interface LecturerCourseTestRepository extends AbstractRepository {

	@Query("SELECT c FROM Course c WHERE c.lecturer.userAccount.username = :username")
	Collection<Course> findManyCoursesByLecturerUsername(String username);
}
