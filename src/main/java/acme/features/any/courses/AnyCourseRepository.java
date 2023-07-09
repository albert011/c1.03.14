
package acme.features.any.courses;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.course.Course;
import acme.entities.lecture.Lecture;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AnyCourseRepository extends AbstractRepository {

	@Query("select c from Course c where c.draftMode='false'")
	Collection<Course> findAllPublishedCourses();

	@Query("select c from Course c where c.id = :courseId")
	Course findCourseById(int courseId);

	@Query("select cl.lecture from CourseLecture cl where cl.course.id = :courseId")
	Collection<Lecture> findManyLecturesByCourseId(int courseId);

}
