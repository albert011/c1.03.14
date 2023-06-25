/*
 * EmployerJobTestRepository.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.testing.student.enrolment;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;

import acme.entities.course.Course;
import acme.entities.enrolments.Activity;
import acme.entities.enrolments.Enrolment;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Student;

public interface StudentEnrolmentTestRepository extends AbstractRepository {

	@Query("SELECT e FROM Enrolment e JOIN e.student s WHERE s.id = :id")
	List<Enrolment> listMyEnrolments(int id);

	@Query("SELECT e FROM Enrolment e JOIN e.student s WHERE s.userAccount.username = :name")
	List<Enrolment> listEnrolmentsByStudentUsername(String name);

	@Query("SELECT e FROM Enrolment e WHERE e.id = :id")
	Enrolment findOneEnrolmentById(int id);

	@Query("SELECT s FROM Student s WHERE s.id = :id")
	Student findOneStudentById(int id);

	@Query("SELECT e FROM Enrolment e WHERE e.code = :code")
	Enrolment findOneEnrolmentByCode(String code);

	@Query("SELECT c FROM Course c WHERE c.draftMode = false")
	List<Course> findAllCourses();

	@Query("SELECT c FROM Course c WHERE c.id = :id")
	Course findOneCourseById(int id);

	@Query("select a from Activity a where a.enrolment.id = :id")
	Collection<Activity> findManyActivitiesById(int id);

}
