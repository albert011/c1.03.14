
package acme.features.any.courses;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.course.Course;
import acme.entities.lecture.Lecture;
import acme.entities.lecture.LectureType;
import acme.framework.components.accounts.Any;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AnyCourseShowService extends AbstractService<Any, Course> {

	@Autowired
	protected AnyCourseRepository repository;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		int courseId;
		Course object;

		courseId = super.getRequest().getData("id", int.class);
		object = this.repository.findCourseById(courseId);

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Course object) {
		assert object != null;
		Collection<Lecture> lectures;
		int totalTheoretical = 0;
		int totalHandsOn = 0;

		lectures = this.repository.findManyLecturesByCourseId(object.getId());

		for (final Lecture lecture : lectures)
			if (lecture.getType().equals(LectureType.THEORETICAL))
				totalTheoretical++;
			else
				totalHandsOn++;

		Tuple tuple;

		final LectureType type = totalHandsOn == totalTheoretical ? LectureType.HANDS_ON : totalHandsOn > totalTheoretical ? LectureType.HANDS_ON : LectureType.THEORETICAL;
		tuple = super.unbind(object, "code", "title", "Abstract", "retailPrice", "type", "link", "draftMode");
		tuple.put("type", type);

		super.getResponse().setData(tuple);
	}
}
