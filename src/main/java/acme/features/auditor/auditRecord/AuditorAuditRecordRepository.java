
package acme.features.auditor.auditRecord;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.audit.Audit;
import acme.entities.audit.AuditRecord;
import acme.entities.audit.Mark;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuditorAuditRecordRepository extends AbstractRepository {

	@Query("select a from Audit a where a.id = :id")
	Audit findOneAuditById(int id);

	@Query("select a from Audit a where a.code = :code")
	Audit findOneAuditByCode(String code);

	@Query("select a from AuditRecord a where a.id = :id")
	AuditRecord findOneAuditRecordById(int id);

	@Query("select ar.mark from AuditRecord ar where ar.audit.id =:id")
	Collection<Mark> findMarksOfAuditRecordsByAuditId(int id);

	@Query("select a from AuditRecord a where a.audit.id = :id")
	Collection<AuditRecord> findAuditRecordsOfAuditByAuditId(int id);
}
