
package acme.features.auditor.audit;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.audit.Audit;
import acme.entities.audit.Mark;
import acme.entities.course.Course;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;
import acme.utils.MarkUtils;

@Service
public class AuditorAuditUpdateService extends AbstractService<Auditor, Audit> {

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

		Collection<Mark> marksCollection;

		marksCollection = this.repository.findMarksOfAuditByAuditId(object.getId());

		if (!marksCollection.isEmpty()) {
			Mark finalMark;
			finalMark = MarkUtils.getNewMark(marksCollection);

			object.setMark(finalMark);
		}

		final int courseId = super.getRequest().getData("course", int.class);
		final Course c = this.repository.findOneCourseById(courseId);

		object.setCourse(c);

		super.bind(object, "code", "conclusion", "strongPoints", "weakPoints", "isPublished");

	}

	@Override
	public void validate(final Audit object) {
		assert object != null;
		if (!super.getBuffer().getErrors().hasErrors("course")) {
			final int courseid = super.getRequest().getData("course", int.class);
			final Course c = this.repository.findOneCourseById(courseid);
			super.state(c.isDraftMode(), "course", "auditor.audit.form.error.course-not-published");
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

		Collection<Course> courseOptions;
		SelectChoices marks, courses;
		Tuple tuple;

		if (object.getMark() == null)
			marks = SelectChoices.from(Mark.class, null);
		else
			marks = SelectChoices.from(Mark.class, object.getMark());
		courseOptions = this.repository.findCoursesPublished();

		courses = SelectChoices.from(courseOptions, "title", object.getCourse());

		tuple = super.unbind(object, "code", "conclusion", "strongPoints", "weakPoints", "isPublished", "mark");
		tuple.put("masterId", object.getAuditor().getId());
		tuple.put("marks", marks);
		tuple.put("course", courses.getSelected().getKey());
		tuple.put("courses", courses);
		tuple.put("auditorUsername", object.getAuditor().getUserAccount().getUsername());

		super.getResponse().setData(tuple);
	}

}
