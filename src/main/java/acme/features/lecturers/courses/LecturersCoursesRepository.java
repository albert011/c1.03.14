
package acme.features.lecturers.courses;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.audit.Audit;
import acme.entities.audit.AuditRecord;
import acme.entities.course.Course;
import acme.entities.course.CoursesLecturers;
import acme.entities.enrolments.Activity;
import acme.entities.enrolments.Enrolment;
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

	@Query("select a from Audit a where a.course.id = :courseId")
	Collection<Audit> findManyAuditsByCourseId(int courseId);

	@Query("select e from Enrolment e where e.course.id = :courseId")
	Collection<Enrolment> findManyEnrolmentsByCourseId(int courseId);

	@Query("select count(l) from Lecture l where l.type = 1 and l.courses.id =:coursesId")
	Long findManyNonTheoreticalLecturesByCourseId(int coursesId);

	@Query("select count(l) from Lecture l where l.type = 0 and l.courses.id =:coursesId")
	Long findManyTheoreticalLecturesByCourseId(int coursesId);

	@Query("select c from Course c where c.code = :code")
	Course findOneCourseByCode(String code);

	@Query("select a from Activity a where a.enrolment.id = :enrolmentId")
	Collection<Activity> findManyActivitiesByEnrolmentId(int enrolmentId);

	@Query("select a from AuditRecord a where a.audit.id = :auditId")
	Collection<AuditRecord> findManyAuditRecordByAuditId(int auditId);
}
