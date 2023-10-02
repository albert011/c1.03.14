
package acme.features.auditor.auditorDashboard;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.audit.Audit;
import acme.entities.audit.AuditRecord;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Auditor;

@Repository
public interface AuditorDashboardRepository extends AbstractRepository {

	@Query("select a from Audit a where a.auditor = :auditor")
	Collection<Audit> getAudits(Auditor auditor);

	@Query("select a from Auditor a where a.userAccount.id = :auditorId")
	Auditor getAuditorById(int auditorId);

	@Query("select ar from AuditRecord ar where ar.audit.auditor = :auditor")
	Collection<AuditRecord> getAuditRecords(Auditor auditor);

}
