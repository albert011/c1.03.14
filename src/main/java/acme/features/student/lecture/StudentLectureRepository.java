
package acme.features.student.lecture;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.lecture.Lecture;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Lecturer;

@Repository
public interface StudentLectureRepository extends AbstractRepository {

	@Query("SELECT l FROM Lecture l JOIN l.courses lc WHERE lc.id = :id")
	List<Lecture> findLecturesByCourse(int id);

	@Query("SELECT ll FROM Lecture l JOIN l.lecturer ll WHERE l.id = :id")
	Lecturer findOneLecturerByLectureId(int id);

	@Query("SELECT l FROM Lecture l WHERE l.id = :id")
	Lecture findById(int id);
}
