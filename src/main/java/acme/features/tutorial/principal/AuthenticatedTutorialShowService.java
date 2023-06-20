
package acme.features.tutorial.principal;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.course.Course;
import acme.entities.tutorial.Tutorial;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedTutorialShowService extends AbstractService<Authenticated, Tutorial> {

	@Autowired
	protected AuthenticatedTutorialRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		boolean status;
		final int id;
		final Tutorial tutorial;

		//id = super.getRequest().getData("id", int.class);
		//tutorial = this.repository.findOneTutorialById(id);
		//status = tutorial != null;
		status = true;

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Tutorial tutorial;
		int id;

		id = super.getRequest().getData("id", int.class);
		tutorial = this.repository.findOneTutorialById(id);

		super.getBuffer().setData(tutorial);
	}

	@Override
	public void unbind(final Tutorial tutorial) {
		assert tutorial != null;
		Tuple tuple;
		Collection<Course> courses;
		SelectChoices choices;

		courses = this.repository.getCourses();
		choices = SelectChoices.from(courses, "code", tutorial.getCourse());
		tuple = super.unbind(tutorial, "code", "title", "abstractMessage", "goals", "estimatedTotalTime");
		tuple.put("assistant", tutorial.getAssistant().getUserAccount().getUsername());
		tuple.put("course", choices.getSelected().getLabel());
		tuple.put("courses", choices);
		super.getResponse().setData(tuple);
	}

}
