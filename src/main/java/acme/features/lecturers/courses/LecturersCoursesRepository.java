
package acme.features.lecturers.courses;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.audit.Audit;
import acme.entities.audit.AuditRecord;
import acme.entities.course.Course;
import acme.entities.course.CoursesLectures;
import acme.entities.enrolments.Activity;
import acme.entities.enrolments.Enrolment;
import acme.entities.lecture.Lecture;
import acme.framework.components.accounts.UserAccount;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Lecturer;

@Repository
public interface LecturersCoursesRepository extends AbstractRepository {

	@Query("select c from Course c where c.lecturer.id = :id")
	Collection<Course> findCoursesByLecturer(int id);

	@Query("select cl from CoursesLectures cl where cl.course.id = :coursesId")
	Collection<CoursesLectures> findCourseLectureByCourse(int coursesId);

	@Query("select c from Course c where c.id = :id")
	Course findOneCourseById(int id);

	@Query("select l from Lecturer l where l.userAccount.id = :id")
	Lecturer findLecturerbyUserAccountId(int id);

	@Query("select ua from UserAccount ua where ua.id = :id")
	UserAccount findOneUserAccountById(int id);

	@Query("select l from Lecturer l where l.id = :id")
	Lecturer findOneLecturerById(int id);

	@Query("select cl.lecture from CoursesLectures cl where cl.course.id = :courseId")
	Collection<Lecture> findManyLecturesByCourseId(int courseId);

	@Query("SELECT cl.lecture FROM CoursesLectures cl JOIN cl.lecture l JOIN cl.course c WHERE c.id = :courseId AND l.draftMode=true")
	Collection<Lecture> findManyLecturesUnpublishedByCourse(int courseId);

	@Query("SELECT cl.lecture FROM CoursesLectures cl JOIN cl.lecture l JOIN cl.course c WHERE c.id = :courseId AND l.type='HANDS_ON'")
	Collection<Lecture> findManyNonTheoreticalLecturesByCourseId(int courseId);

	@Query("select a from Audit a where a.course.id = :courseId")
	Collection<Audit> findManyAuditsByCourseId(int courseId);

	@Query("select e from Enrolment e where e.course.id = :courseId")
	Collection<Enrolment> findManyEnrolmentsByCourseId(int courseId);

	@Query("select c from Course c where c.code = :code")
	Course findOneCourseByCode(String code);

	@Query("select a from Activity a where a.enrolment.id = :enrolmentId")
	Collection<Activity> findManyActivitiesByEnrolmentId(int enrolmentId);

	@Query("select a from AuditRecord a where a.audit.id = :auditId")
	Collection<AuditRecord> findManyAuditRecordByAuditId(int auditId);
}
