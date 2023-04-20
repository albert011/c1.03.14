
package acme.features.company.practicum;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.practicums.Practicum;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Company;

@Repository
public interface PracticumRepository extends AbstractRepository {

	@Query("select p from Practicum p where p.id = :id")
	Practicum findOnePracticumById(int id);

	@Query("select p from Practicum p where p.company.id = :companyId")
	Collection<Practicum> findManyPracticumsByCompanyId(int companyId);

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
