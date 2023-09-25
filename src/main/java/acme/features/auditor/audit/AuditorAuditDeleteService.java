
package acme.features.auditor.audit;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.audit.Audit;
import acme.entities.audit.AuditRecord;
import acme.entities.audit.Mark;
import acme.entities.course.Course;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorAuditDeleteService extends AbstractService<Auditor, Audit> {

	@Autowired
	protected AuditorAuditRepository repository;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int auditId;
		int auditorId;
		Audit audit;

		auditorId = super.getRequest().getPrincipal().getActiveRoleId();
		auditId = super.getRequest().getData("id", int.class);
		audit = this.repository.findOneAuditById(auditId);

		status = audit != null && super.getRequest().getPrincipal().hasRole(audit.getAuditor()) && audit.getAuditor().getId() == auditorId && !audit.isPublished();
		super.getResponse().setAuthorised(status);

	}

	@Override
	public void load() {
		Audit object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneAuditById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Audit object) {
		assert object != null;

		super.bind(object, "code", "conclusion", "strongPoints", "weakPoints", "isPublished", "mark");

	}

	@Override
	public void validate(final Audit object) {
		assert object != null;

		super.state(!object.isPublished(), "code", "auditor.audit.form.error.course-not-published");

	}

	@Override
	public void perform(final Audit object) {
		assert object != null;

		Collection<AuditRecord> records;

		records = this.repository.findAuditRecordsOfAuditByAuditId(object.getId());

		this.repository.deleteAll(records);
		this.repository.delete(object);
	}

	@Override
	public void unbind(final Audit object) {
		assert object != null;

		Collection<Course> courseOptions;
		SelectChoices marks, courses;
		Tuple tuple;

		marks = SelectChoices.from(Mark.class, object.getMark());

		courseOptions = this.repository.findCoursesPublished();

		courses = SelectChoices.from(courseOptions, "title", object.getCourse());

		tuple = super.unbind(object, "code", "conclusion", "strongPoints", "weakPoints", "isPublished", "mark");
		tuple.put("masterId", object.getAuditor().getId());
		tuple.put("courseTitle", object.getCourse().getTitle());
		tuple.put("marks", marks);
		tuple.put("course", courses.getSelected().getKey());
		tuple.put("courses", courses);
		tuple.put("auditorUsername", object.getAuditor().getUserAccount().getUsername());

		super.getResponse().setData(tuple);
	}
}
