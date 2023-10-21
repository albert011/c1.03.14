
package acme.features.student.course;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.course.Course;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface StudentCourseRepository extends AbstractRepository {

	@Query("SELECT c FROM Course c WHERE c.draftMode = false")
	Collection<Course> findAllCourses();

	@Query("SELECT c FROM Course c WHERE c.id = :id AND c.draftMode = false")
	Course findOneCourseById(int id);
}
