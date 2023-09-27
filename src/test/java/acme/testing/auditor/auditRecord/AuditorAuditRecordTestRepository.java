
package acme.testing.auditor.auditRecord;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.entities.audit.Audit;
import acme.framework.repositories.AbstractRepository;

public interface AuditorAuditRecordTestRepository extends AbstractRepository {

	@Query("select ar.audit from AuditRecord ar where ar.audit.auditor.userAccount.username = :username")
	Collection<Audit> findManyAuditsWithAuditRecordByAuditorUsername(String username);

}
