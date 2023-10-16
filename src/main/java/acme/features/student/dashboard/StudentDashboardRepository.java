
package acme.features.student.dashboard;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.framework.repositories.AbstractRepository;
import acme.roles.Student;

@Repository
public interface StudentDashboardRepository extends AbstractRepository {

	@Query("select s from Student s where s.userAccount.id = :userAccountId")
	Student findOneStudentByUserAccountId(int userAccountId);

	@Query("SELECT COALESCE(count(a), '') FROM Activity a JOIN a.enrolment e WHERE e.student.id = :studentId AND a.activityType = 'THEORY'")
	int numTheory(int studentId);

	@Query("SELECT COALESCE(count(a), '') FROM Activity a JOIN a.enrolment e WHERE e.student.id = :studentId AND a.activityType = 'HANDS_ON'")
	int numHandsOn(int studentId);

	@Query("SELECT COALESCE(avg((SELECT SUM(DATEDIFF(a.endPeriod, a.startPeriod)) FROM Activity a JOIN a.enrolment e WHERE e.student.id = :studentId)), '') FROM Enrolment e WHERE e.student.id = :studentId")
	double averagePeriodTime(int studentId);

	@Query("SELECT COALESCE(stddev((SELECT SUM(DATEDIFF(a.endPeriod, a.startPeriod)) FROM Activity a JOIN a.enrolment e WHERE e.student.id = :studentId)), '') FROM Enrolment e WHERE e.student.id = :studentId")
	double deviationPeriodTime(int studentId);

	@Query("SELECT COALESCE(min((SELECT SUM(DATEDIFF(a.endPeriod, a.startPeriod)) FROM Activity a JOIN a.enrolment e WHERE e.student.id = :studentId)), '') FROM Enrolment e WHERE e.student.id = :studentId")
	double minimumPeriodTime(int studentId);

	@Query("SELECT COALESCE(max((SELECT SUM(DATEDIFF(a.endPeriod, a.startPeriod)) FROM Activity a JOIN a.enrolment e WHERE e.student.id = :studentId)), '') FROM Enrolment e WHERE e.student.id = :studentId")
	double maximumPeriodTime(int studentId);

	@Query("SELECT COALESCE(avg((SELECT SUM(cl.lecture.estimatedLearningTime) FROM CourseLecture cl JOIN cl.course c WHERE e.course.id = c.id)), '') FROM Enrolment e WHERE e.student.id = :studentId")
	double averageLearningTime(int studentId);

	@Query("SELECT COALESCE(stddev((SELECT SUM(cl.lecture.estimatedLearningTime) FROM CourseLecture cl JOIN cl.course c WHERE e.course.id = c.id)), '') FROM Enrolment e WHERE e.student.id = :studentId")
	double deviationLearningTime(int studentId);

	@Query("SELECT COALESCE(min((SELECT SUM(cl.lecture.estimatedLearningTime) FROM CourseLecture cl JOIN cl.course c WHERE e.course.id = c.id)), '') FROM Enrolment e WHERE e.student.id = :studentId")
	double minimumLearningTime(int studentId);

	@Query("SELECT COALESCE(max((SELECT SUM(cl.lecture.estimatedLearningTime) FROM CourseLecture cl JOIN cl.course c WHERE e.course.id = c.id)), '') FROM Enrolment e WHERE e.student.id = :studentId")
	double maximumLearningTime(int studentId);
}
