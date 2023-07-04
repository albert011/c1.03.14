
package acme.features.lecturers.coursesLectures;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.course.Course;
import acme.entities.course.CoursesLectures;
import acme.entities.lecture.Lecture;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface LecturersCoursesLecturesRepository extends AbstractRepository {

	@Query("select l from CoursesLectures l where l.lecture.lecturer.id = :lecturerId")
	Collection<CoursesLectures> findManyCoursesLecturesByLecturerId(int lecturerId);

	@Query("select c from Course c where c.lecturer.id = :id")
	Collection<Course> findCoursesByLecturer(int id);

	@Query("select c from Course c where c.id = :courseId")
	Course findCourseById(int courseId);

	@Query("select cl from CoursesLectures cl where cl.id = :id")
	CoursesLectures findCourseLectureById(int id);

	@Query("select l from Lecture l where l.id = :lectureId")
	Lecture findLectureById(int lectureId);

	@Query("select cl from CoursesLectures cl where cl.course.id = :courseId")
	Collection<CoursesLectures> findManyCoursesLecturesByCourseId(int courseId);

	@Query("select cl.lecture from CoursesLectures cl where cl.course.id = :id")
	Collection<Lecture> findLecturesByCourseId(int id);

	@Query("select c from Course c where c.lecturer.id = :id")
	Collection<Course> findManyCoursesByLecturerId(int id);

	@Query("select l from Lecture l where l.lecturer.id = :id")
	Collection<Lecture> findManyLecturesByLecturerId(int id);
}
