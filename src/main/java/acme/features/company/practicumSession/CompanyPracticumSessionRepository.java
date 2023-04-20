
package acme.features.company.practicumSession;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.practicumSessions.PracticumSession;
import acme.entities.practicums.Practicum;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Company;

@Repository
public interface CompanyPracticumSessionRepository extends AbstractRepository {

	@Query("select ps from PracticumSession ps where ps.id = :id")
	PracticumSession findOnePracticumSessionById(int id);

	@Query("select ps from PracticumSession ps where ps.id = :id")
	Collection<PracticumSession> findManyPracticumSessionsById(int id);

	@Query("select ps.practicum from PracticumSession ps where ps.id = :id")
	Practicum findOnePracticumByPracticumSessionId(int id);

	@Query("select c from Company c")
	Collection<Company> findAllCompanies();

	@Query("select c from Company c where c.id = :id")
	Collection<Company> findManyCompaniesById(int id);

	@Query("select c from Company c where c.id = :id")
	Company findOneCompanyById(int id);

	@Query("select ps from PracticumSession ps where ps.company.id = :companyId")
	Collection<PracticumSession> findManyPracticumSessionsByCompanyId(int companyId);

	@Query("select p from Practicum p where p.id = :id")
	Practicum findOnePracticumById(int id);

	@Query("select p from Practicum p where p.company.id = :companyId")
	Collection<Practicum> findManyPracticumsByCompanyId(int companyId);

}
