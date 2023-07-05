
package acme.features.student.lecture;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.lecture.Lecture;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Lecturer;

@Repository
public interface StudentLectureRepository extends AbstractRepository {

	@Query("select cl.lecture from CourseLecture cl where cl.course.id = :courseId")
	List<Lecture> findLecturesByCourse(int courseId);

	@Query("SELECT ll FROM Lecture l JOIN l.lecturer ll WHERE l.id = :id")
	Lecturer findOneLecturerByLectureId(int id);
}
