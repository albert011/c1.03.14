
package acme.features.lecturers.courses;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.course.Course;
import acme.entities.course.CoursesLecturers;
import acme.entities.lecture.Lecture;
import acme.framework.components.accounts.UserAccount;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Lecturer;

@Repository
public interface LecturersCoursesRepository extends AbstractRepository {

	@Query("select cl.courses from CoursesLecturers cl where cl.lecturers.id = :id")
	Collection<Course> findCoursesByLecturer(int id);

	@Query("select cl from CoursesLecturers cl where cl.courses.id = :coursesId")
	Collection<CoursesLecturers> findCourseLecturerByCourse(int coursesId);

	@Query("select c from Course c where c.id = :id")
	Course findOneCourseById(int id);

	@Query("select l from Lecturer l where l.userAccount.id = :id")
	Lecturer findLecturerbyUserAccountId(int id);

	@Query("select ua from UserAccount ua where ua.id = :id")
	UserAccount findOneUserAccountById(int id);

	@Query("select l from Lecturer l where l.id = :id")
	Lecturer findOneLecturerById(int id);

	@Query("select l from Lecture l where l.courses.id = :coursesId")
	Collection<Lecture> findManyLecturesByCourseId(int coursesId);

	@Query("select l from Lecture l where l.draftMode = true and l.courses.id =:coursesId")
	Collection<Lecture> findManyLecturesUnpublishedByCourse(int coursesId);

	@Query("select l from Lecture l where l.isTheoretical = false and l.courses.id =:coursesId")
	Collection<Lecture> findManyNonTheoreticalLecturesByCourseId(int coursesId);
}
