
package acme.features.company.practicum;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.course.Course;
import acme.entities.practicumSessions.PracticumSession;
import acme.entities.practicums.Practicum;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanyPracticumShowService extends AbstractService<Company, Practicum> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected CompanyPracticumRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int masterId;
		Practicum practicum;
		Company company;

		masterId = super.getRequest().getData("id", int.class);
		practicum = this.repository.findOnePracticumById(masterId);
		company = practicum == null ? null : practicum.getCompany();
		status = practicum != null && !practicum.isDraftMode() || super.getRequest().getPrincipal().hasRole(company);

		// Revisar si hay q poner !practicum.isDraftMode()

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Practicum object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOnePracticumById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Practicum object) {
		assert object != null;

		Tuple tuple;
		Collection<Course> courses;
		Collection<PracticumSession> practicumSession;
		String estimatedTotalTime;
		SelectChoices choices;

		courses = this.repository.findManyPublishedCourses();
		choices = SelectChoices.from(courses, "code", object.getCourse());

		practicumSession = this.repository.findManyPracticumSessionsByPracticumId(object.getId());
		estimatedTotalTime = object.getEstimatedTotalTime(practicumSession);

		tuple = super.unbind(object, "code", "title", "abstractText", "goals", "draftMode");
		tuple.put("courseCode", this.repository.findCourseCodeByPracticumId(object.getId()));
		tuple.put("estimatedTotalTime", estimatedTotalTime);
		tuple.put("course", choices.getSelected().getKey());
		tuple.put("courses", choices);

		super.getResponse().setData(tuple);
	}

}
