
package acme.features.company.practicum;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.course.Course;
import acme.entities.practicumSessions.PracticumSession;
import acme.entities.practicums.Practicum;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Company;

@Repository
public interface CompanyPracticumRepository extends AbstractRepository {

	@Query("select p from Practicum p where p.id = :id")
	Practicum findOnePracticumById(int id);

	@Query("select p from Practicum p where p.company.id = :companyId")
	Collection<Practicum> findManyPracticumsByCompanyId(int companyId);

	@Query("select ps from PracticumSession ps where ps.practicum.id = :id")
	Collection<PracticumSession> findManyPracticumSessionsByPracticumId(int id);

	@Query("select c from Course c where c.draftMode = false and c.type= 1")
	Collection<Course> findManyPublishedHandsOnCourses();

	@Query("select c from Course c where c.id = :id")
	Course findOneCourseById(int id);

	@Query("select p.course.code from Practicum p where p.id = :id")
	String findCourseCodeByPracticumId(int id);

	@Query("select c from Company c where c.id = :id")
	Company findOneCompanyById(int id);

	@Query("select c from Company c")
	Collection<Company> findAllCompanies();

	@Query("select p from Practicum p")
	Collection<Practicum> findAllPracticums();

	@Query("select c from Company c where c.id = :companyId")
	Collection<Company> findManyCompaniesById(int companyId);

	@Query("select p from Practicum p where p.code = :code")
	Practicum findOnePracticumByCode(String code);

}
