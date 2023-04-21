
package acme.features.auditor.audit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.audit.Audit;
import acme.entities.audit.Mark;
import acme.entities.course.Course;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorAuditCreateService extends AbstractService<Auditor, Audit> {

	@Autowired
	protected AuditorAuditRepository repository;


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Audit object;
		Auditor auditor;

		auditor = this.repository.findOneAuditorById(super.getRequest().getPrincipal().getActiveRoleId());
		object = new Audit();
		object.setPublished(false);
		object.setAuditor(auditor);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Audit object) {
		assert object != null;

		String courseTitle;
		Course course;

		courseTitle = super.getRequest().getData("courseTitle", String.class);
		course = this.repository.findOneCourseByTitle(courseTitle);

		super.bind(object, "code", "conclusion", "strongPoints", "weakPoints", "isPublished", "mark");
		object.setCourse(course);
	}

	@Override
	public void validate(final Audit object) {
		assert object != null;
		if (!super.getBuffer().getErrors().hasErrors("courseTitle")) {
			Audit existing;
			String courseTitle;

			courseTitle = super.getRequest().getData("courseTitle", String.class);
			existing = this.repository.findOneAuditByCourseTitle(courseTitle);

			super.state(existing == null || existing.equals(object), "courseTitle", "auditor.audit.form.error.duplicated");
		}
	}
	@Override
	public void perform(final Audit object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Audit object) {
		assert object != null;

		SelectChoices marks;
		Tuple tuple;

		marks = SelectChoices.from(Mark.class, object.getMark());

		tuple = super.unbind(object, "code", "conclusion", "strongPoints", "weakPoints", "isPublished", "mark");
		tuple.put("masterId", object.getAuditor().getId());
		tuple.put("marks", marks);

		super.getResponse().setData(tuple);
	}

}
