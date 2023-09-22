
package acme.features.auditor.auditRecord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.audit.Audit;
import acme.entities.audit.AuditRecord;
import acme.entities.audit.Mark;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorAuditRecordDeleteService extends AbstractService<Auditor, AuditRecord> {

	@Autowired
	protected AuditorAuditRecordRepository repository;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int auditorId, auditRecordId;
		Audit audit;
		AuditRecord auditRecord;

		auditorId = super.getRequest().getPrincipal().getActiveRoleId();
		auditRecordId = super.getRequest().getData("id", int.class);
		auditRecord = this.repository.findOneAuditRecordById(auditRecordId);

		audit = auditRecord.getAudit();

		status = audit != null && super.getRequest().getPrincipal().hasRole(audit.getAuditor()) && audit.getAuditor().getId() == auditorId && !audit.isPublished();

		super.getResponse().setAuthorised(status);

	}

	@Override
	public void load() {
		AuditRecord object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneAuditRecordById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final AuditRecord object) {
		assert object != null;

		super.bind(object, "subject", "assessment", "periodStart", "periodEnd", "mark", "moreInfo");
	}

	@Override
	public void validate(final AuditRecord object) {
		assert object != null;
		if (!super.getBuffer().getErrors().hasErrors("auditCode"))
			super.state(!object.getAudit().isPublished(), "auditCode", "auditor.audit-record.form.error.published-audit");
	}

	@Override
	public void perform(final AuditRecord object) {
		assert object != null;

		this.repository.delete(object);
	}

	@Override
	public void unbind(final AuditRecord object) {
		assert object != null;

		SelectChoices marks;
		Tuple tuple;

		marks = SelectChoices.from(Mark.class, object.getMark());

		tuple = super.unbind(object, "subject", "assessment", "periodStart", "periodEnd", "mark", "moreInfo");
		tuple.put("marks", marks);
		tuple.put("auditId", object.getAudit().getId());
		tuple.put("auditCode", object.getAudit().getCode());

		super.getResponse().setData(tuple);
	}
}
