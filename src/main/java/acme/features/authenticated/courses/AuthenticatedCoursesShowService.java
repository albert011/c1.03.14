
package acme.features.authenticated.courses;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.course.Course;
import acme.entities.lecture.Lecture;
import acme.entities.lecture.LectureType;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedCoursesShowService extends AbstractService<Authenticated, Course> {

	@Autowired
	protected AuthenticatedCoursesRepository repository;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		final boolean status;
		int id;
		Course course;
		Collection<Course> courses;

		id = super.getRequest().getData("id", int.class);
		course = this.repository.findOneCourseById(id);

		courses = this.repository.findAllCoursesWithAudit();

		status = course != null && courses.contains(course);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Course object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneCourseById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Course object) {
		assert object != null;

		Tuple tuple;
		Collection<Lecture> lectures;
		int totalTheoretical = 0;
		int totalHandsOn = 0;

		lectures = this.repository.findManyLecturesByCourseId(object.getId());

		for (final Lecture lecture : lectures)
			if (lecture.getType().equals(LectureType.THEORETICAL))
				totalTheoretical++;
			else
				totalHandsOn++;

		final LectureType type = totalHandsOn == totalTheoretical ? LectureType.HANDS_ON : totalHandsOn > totalTheoretical ? LectureType.HANDS_ON : LectureType.THEORETICAL;
		tuple = super.unbind(object, "code", "title", "Abstract", "retailPrice", "type", "link", "draftMode");
		tuple.put("type", type);

		super.getResponse().setData(tuple);
	}
}
