
package acme.features.tutorial.assistant;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.course.Course;
import acme.entities.tutorial.Tutorial;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Dataset;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantTutorialCreateService extends AbstractService<Assistant, Tutorial> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AssistantTutorialRepository repository;

	// AbstractService interface ----------------------------------------------


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
		Tutorial tutorial;
		Assistant assistant;
		int assistantId;

		assistantId = super.getRequest().getPrincipal().getAccountId();
		assistant = this.repository.findAssistant(assistantId);

		tutorial = new Tutorial();
		tutorial.setAssistant(assistant);

		super.getBuffer().setData(tutorial);
	}

	@Override
	public void bind(final Tutorial tutorial) {
		assert tutorial != null;

		int courseCode;
		Course course;

		courseCode = super.getRequest().getData("course", int.class);
		course = this.repository.findCourseById(courseCode);

		super.bind(tutorial, "code", "title", "abstractMessage", "goals", "estimatedTotalTime", "isPublished");
		tutorial.setCourse(course);
	}

	@Override
	public void validate(final Tutorial tutorial) {
		assert tutorial != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Tutorial existing;

			existing = this.repository.findTutorialByCode(tutorial.getCode());
			super.state(existing == null, "code", "assistant.tutorial.form.error.duplicated");
		}
	}

	@Override
	public void perform(final Tutorial tutorial) {
		assert tutorial != null;
		final Dataset req = super.getRequest().getData();
		if (req.containsKey("isPublished") && req.get("isPublished").toString().equals("true"))
			tutorial.setPublished(true);

		this.repository.save(tutorial);
	}

	@Override
	public void unbind(final Tutorial tutorial) {
		assert tutorial != null;

		Tuple tuple;
		Collection<Course> courses;
		SelectChoices choices;

		courses = this.repository.findAllCourses();
		choices = SelectChoices.from(courses, "code", tutorial.getCourse());

		tuple = super.unbind(tutorial, "code", "title", "abstractMessage", "goals", "estimatedTotalTime", "isPublished");

		tuple.put("course", choices.getSelected().getLabel());
		tuple.put("courses", choices);

		super.getResponse().setData(tuple);
	}

}
