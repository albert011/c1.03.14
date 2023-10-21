
package acme.features.lecturers.courseLecture;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.course.Course;
import acme.entities.course.CourseLecture;
import acme.entities.lecture.Lecture;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Lecturer;

@Repository
public interface LecturersCoursesLecturesRepository extends AbstractRepository {

	@Query("select l from CourseLecture l where l.lecture.lecturer.id = :lecturerId")
	Collection<CourseLecture> findManyCoursesLecturesByLecturerId(int lecturerId);

	@Query("select c from Course c where c.lecturer.id = :id")
	Collection<Course> findCoursesByLecturer(int id);

	@Query("select c from Course c where c.id = :courseId")
	Course findCourseById(int courseId);

	@Query("select cl from CourseLecture cl where cl.id = :id")
	CourseLecture findCourseLectureById(int id);

	@Query("select l from Lecture l where l.id = :lectureId")
	Lecture findLectureById(int lectureId);

	@Query("select cl from CourseLecture cl where cl.course.id = :courseId")
	Collection<CourseLecture> findManyCoursesLecturesByCourseId(int courseId);

	@Query("select cl.lecture from CourseLecture cl where cl.course.id = :id")
	Collection<Lecture> findLecturesByCourseId(int id);

	@Query("select c from Course c where c.lecturer.id = :id")
	Collection<Course> findManyCoursesByLecturerId(int id);

	@Query("select l from Lecture l where l.lecturer.id = :id")
	Collection<Lecture> findManyLecturesByLecturerId(int id);

	@Query("select c.lecturer from Course c where c.id = :courseId")
	Lecturer findCourseOwnerByCourseId(int courseId);

	@Query("select c from Course c where c.id =:courseId AND c.draftMode='true'")
	Course findUnpublishedCourse(int courseId);
}
