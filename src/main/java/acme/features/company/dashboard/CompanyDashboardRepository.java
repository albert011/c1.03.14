
package acme.features.company.dashboard;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.framework.repositories.AbstractRepository;
import acme.roles.Company;

@Repository
public interface CompanyDashboardRepository extends AbstractRepository {

	@Query("select c from Company c where c.userAccount.id = :userAccountId")
	Company findOneCompanyByUserAccountId(int userAccountId);

	// Practicum	

	@Query("SELECT COALESCE(count(p), '') FROM Practicum p WHERE p.company.id = :companyId")
	int numPracticum(int companyId);

	@Query("SELECT COALESCE(avg((SELECT SUM(DATEDIFF(ps.endDate, ps.startDate)) FROM PracticumSession ps WHERE ps.practicum.company.id = :companyId AND ps.practicum.id = p.id)), '') FROM Practicum p WHERE p.company.id = :companyId")
	double averagePracticumLength(int companyId);

	@Query("SELECT COALESCE(stddev((SELECT SUM(DATEDIFF(ps.endDate, ps.startDate)) FROM PracticumSession ps WHERE ps.practicum.company.id = :companyId AND ps.practicum.id = p.id)), '') FROM Practicum p WHERE p.company.id = :companyId")
	double deviationPracticumLength(int companyId);

	@Query("SELECT COALESCE(min((SELECT SUM(DATEDIFF(ps.endDate, ps.startDate)) FROM PracticumSession ps WHERE ps.practicum.company.id = :companyId AND ps.practicum.id = p.id)), '') FROM Practicum p WHERE p.company.id = :companyId")
	double minimumPracticumLength(int companyId);

	@Query("SELECT COALESCE(max((SELECT SUM(DATEDIFF(ps.endDate, ps.startDate)) FROM PracticumSession ps WHERE ps.practicum.company.id = :companyId AND ps.practicum.id = p.id)), '') FROM Practicum p WHERE p.company.id = :companyId")
	double maximumPracticumLength(int companyId);

	@Query("SELECT COALESCE(FUNCTION('MONTH', ps.startDate), ''), COALESCE(COUNT(ps), '') FROM PracticumSession ps WHERE ps.practicum.company.id = :companyId GROUP BY FUNCTION('MONTH', ps.startDate) ORDER BY COUNT(ps) DESC")
	List<Object[]> numPracticumByMonth(int companyId);

	// PracticumSession
	@Query("SELECT COALESCE(count(ps), '') FROM PracticumSession ps WHERE ps.practicum.company.id = :companyId")
	int numSession(int companyId);

	@Query("SELECT COALESCE(avg(DATEDIFF(ps.endDate, ps.startDate)), '') FROM PracticumSession ps WHERE ps.practicum.company.id = :companyId")
	double averageSessionLength(int companyId);

	@Query("SELECT COALESCE(stddev(DATEDIFF(ps.endDate, ps.startDate)), '') FROM PracticumSession ps WHERE ps.practicum.company.id = :companyId")
	double deviationSessionLength(int companyId);

	@Query("SELECT COALESCE(min(DATEDIFF(ps.endDate, ps.startDate)), '') FROM PracticumSession ps WHERE ps.practicum.company.id = :companyId")
	double minimumSessionLength(int companyId);

	@Query("SELECT COALESCE(max(DATEDIFF(ps.endDate, ps.startDate)), '') FROM PracticumSession ps WHERE ps.practicum.company.id = :companyId")
	double maximumSessionLength(int companyId);

}
