
package acme.features.student.activity;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.enrolments.Activity;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface StudentActivityRepository extends AbstractRepository {

	@Query("SELECT a FROM Activity a JOIN a.enrolment.student es WHERE es.id = :id")
	List<Activity> findStudentsActivities(int id);

}
