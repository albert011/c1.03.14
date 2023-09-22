
package acme.features.auditor.auditRecord;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.audit.Audit;
import acme.entities.audit.AuditRecord;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorAuditRecordListService extends AbstractService<Auditor, AuditRecord> {

	@Autowired
	protected AuditorAuditRecordRepository repository;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("auditId", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int auditId;
		int auditorId;
		Audit audit;

		auditorId = super.getRequest().getPrincipal().getActiveRoleId();
		auditId = super.getRequest().getData("auditId", int.class);
		audit = this.repository.findOneAuditById(auditId);
		status = audit != null && super.getRequest().getPrincipal().hasRole(audit.getAuditor()) && audit.getAuditor().getId() == auditorId;

		super.getResponse().setAuthorised(status);

	}

	@Override
	public void load() {
		Collection<AuditRecord> objects;
		int auditId;

		auditId = super.getRequest().getData("auditId", int.class);
		objects = this.repository.findAuditRecordsOfAuditByAuditId(auditId);

		super.getBuffer().setData(objects);
		super.getResponse().setGlobal("auditId", auditId);
	}

	@Override
	public void unbind(final AuditRecord object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "subject", "mark");

		super.getResponse().setData(tuple);

	}

}
