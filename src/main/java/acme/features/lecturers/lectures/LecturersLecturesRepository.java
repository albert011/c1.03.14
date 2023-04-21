
package acme.features.lecturers.lectures;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.course.Course;
import acme.entities.lecture.Lecture;
import acme.entities.lecture.LectureType;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Lecturer;

@Repository
public interface LecturersLecturesRepository extends AbstractRepository {

	@Query("select l from Lecture l where l.lecturer.id = :lecturerId")
	Collection<Lecture> findLecturesByLecturer(int lecturerId);

	@Query("select l from Lecture l where l.id=:id")
	Lecture findOneLectureById(int id);

	@Query("select l from Lecturer l where l.id=:id")
	Lecturer findOneLecturerById(int id);

	@Query("select c from Course c where c.id= :courseId")
	Course findOneCourseById(int courseId);

	@Query("select cl.courses from CoursesLecturers cl where cl.lecturers.id = :lecturerId and cl.courses.draftMode = true")
	Collection<Course> findManyCoursesByLecturers(int lecturerId);

	@Query("select c from Course c")
	Collection<Course> findAllCourses();

	@Query("select l.type from Lecture l where l.id = :id")
	LectureType findLectureTypeById(int id);
}
