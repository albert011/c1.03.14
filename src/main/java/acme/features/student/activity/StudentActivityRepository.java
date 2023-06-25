
package acme.features.student.activity;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.enrolments.Activity;
import acme.entities.enrolments.Activity.ActivityType;
import acme.entities.enrolments.Enrolment;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Student;

@Repository
public interface StudentActivityRepository extends AbstractRepository {

	@Query("SELECT a FROM Activity a JOIN a.enrolment.student es WHERE es.id = :id")
	List<Activity> findStudentsActivities(int id);

	@Query("SELECT es FROM Activity a JOIN a.enrolment.student es WHERE a.id = :id")
	Student findStudentByActivity(int id);

	@Query("SELECT es FROM Enrolment e JOIN e.student es WHERE e.id = :id")
	Student findStudentByEnrolment(int id);

	@Query("SELECT a FROM Activity a WHERE a.id = :id")
	Activity findOneActivityById(int id);

	@Query("SELECT e FROM Enrolment e JOIN e.student es WHERE es.id = :id")
	List<Enrolment> findEnrolmentsByStudent(int id);

	@Query("SELECT e FROM Enrolment e WHERE e.id = :id")
	Enrolment findOneEnrolmentById(int id);

	@Query("SELECT a FROM Activity a JOIN a.enrolment ae WHERE ae.id = :id")
	List<Activity> findActivitiesByEnrolment(int id);

	@Query("SELECT a.activityType FROM Activity a WHERE a.id = :id")
	ActivityType findActivityTypeById(int id);

}
