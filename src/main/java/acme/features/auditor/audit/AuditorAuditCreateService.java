
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

		super.bind(object, "code", "conclusion", "strongPoints", "weakPoints", "isPublished", "mark", "course");
	}

	@Override
	public void validate(final Audit object) {
		assert object != null;
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

		marks = SelectChoices.from(Mark.class, object.getMark());

		courseOptions = this.repository.findCourses();

		courses = SelectChoices.from(courseOptions, "title", object.getCourse());

		tuple = super.unbind(object, "code", "conclusion", "strongPoints", "weakPoints", "isPublished", "mark");
		tuple.put("masterId", object.getAuditor().getId());
		tuple.put("marks", marks);
		tuple.put("course", courses.getSelected().getKey());
		tuple.put("courses", courses);

		super.getResponse().setData(tuple);
	}

}
