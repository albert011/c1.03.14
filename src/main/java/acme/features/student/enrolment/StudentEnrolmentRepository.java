
package acme.features.student.enrolment;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.course.Course;
import acme.entities.enrolments.Activity;
import acme.entities.enrolments.Enrolment;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Student;

@Repository
public interface StudentEnrolmentRepository extends AbstractRepository {

	@Query("SELECT e FROM Enrolment e JOIN e.student s WHERE s.id = :id")
	List<Enrolment> listMyEnrolments(int id);

	@Query("SELECT e FROM Enrolment e WHERE e.id = :id")
	Enrolment findOneEnrolmentById(int id);

	@Query("SELECT s FROM Student s WHERE s.id = :id")
	Student findOneStudentById(int id);

	@Query("SELECT e FROM Enrolment e WHERE e.code = :code")
	Enrolment findOneEnrolmentByCode(String code);

	@Query("SELECT c FROM Course c WHERE c.draftMode = false")
	List<Course> findAllCourses();

	@Query("SELECT c FROM Course c WHERE c.id = :id")
	Course findOneCourseById(int id);

	@Query("select a from Activity a where a.enrolment.id = :id")
	Collection<Activity> findManyActivitiesById(int id);

}
