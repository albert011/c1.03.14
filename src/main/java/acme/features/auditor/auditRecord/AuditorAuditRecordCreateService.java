
package acme.features.auditor.auditRecord;

import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.audit.Audit;
import acme.entities.audit.AuditRecord;
import acme.entities.audit.Mark;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;
import acme.utils.MarkUtils;

@Service
public class AuditorAuditRecordCreateService extends AbstractService<Auditor, AuditRecord> {

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
		int auditId;
		Audit audit;
		AuditRecord object;

		auditId = super.getRequest().getData("auditId", int.class);
		audit = this.repository.findOneAuditById(auditId);
		object = new AuditRecord();

		object.setAudit(audit);
		object.setEdited(audit.isPublished());

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final AuditRecord object) {
		assert object != null;

		String auditCode, markString;
		Audit audit;

		auditCode = super.getRequest().getData("auditCode", String.class);
		audit = this.repository.findOneAuditByCode(auditCode);
		object.setEdited(audit.isPublished());

		markString = super.getRequest().getData("mark", String.class);

		object.setMark(MarkUtils.getMarkFromStringValue(markString));

		object.setAudit(audit);

		super.bind(object, "subject", "assessment", "periodStart", "periodEnd", "moreInfo");

	}

	@Override
	public void validate(final AuditRecord object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("periodStart") && !super.getBuffer().getErrors().hasErrors("periodEnd")) {
			Date minimumPeriod;

			minimumPeriod = MomentHelper.deltaFromMoment(object.getPeriodStart(), 1, ChronoUnit.HOURS);
			super.state(MomentHelper.isAfterOrEqual(object.getPeriodEnd(), minimumPeriod), "periodEnd", "auditor.audit-record.form.error.period-too-short");
		}
		if (object.getAudit().isPublished()) {
			boolean isAccepted;
			isAccepted = this.getRequest().getData("accept", boolean.class);
			super.state(isAccepted, "accept", "auditor.audit-record.form.error.must-accept");
		}

	}

	@Override
	public void perform(final AuditRecord object) {
		assert object != null;

		Collection<Mark> marksCollection;
		Mark finalMark;
		Audit audit;
		audit = object.getAudit();

		marksCollection = this.repository.findMarksOfAuditRecordsByAuditId(audit.getId());

		//Añadimos la del auditReport nuevo porque todavia no se encuentra en la base de datos
		marksCollection.add(object.getMark());

		finalMark = MarkUtils.getNewMark(marksCollection);

		audit.setMark(finalMark);

		audit = this.repository.save(audit);

		object.setAudit(audit);
		this.repository.save(object);

	}

	@Override
	public void unbind(final AuditRecord object) {
		assert object != null;

		SelectChoices marks;
		Tuple tuple;

		marks = SelectChoices.from(Mark.class, object.getMark());

		tuple = super.unbind(object, "subject", "assessment", "periodStart", "periodEnd", "mark", "moreInfo", "edited");
		tuple.put("marks", marks);
		tuple.put("auditId", object.getAudit().getId());
		tuple.put("auditCode", object.getAudit().getCode());

		super.getResponse().setData(tuple);
	}

}
