
package acme.features.lecturers.courses;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.course.Course;
import acme.entities.lecture.Lecture;
import acme.entities.lecture.LectureType;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturersCoursesShowService extends AbstractService<Lecturer, Course> {

	@Autowired
	protected LecturersCoursesRepository repository;


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

		id = super.getRequest().getData("id", int.class);
		course = this.repository.findOneCourseById(id);
		status = course != null;

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Course object;
		int id;
		Collection<Lecture> theoreticalLectures;
		Collection<Lecture> handsOnLectures;
		LectureType lectureType;
		id = super.getRequest().getData("id", int.class);

		handsOnLectures = this.repository.findManyNonTheoreticalLecturesByCourseId(id);
		theoreticalLectures = this.repository.findManyTheoreticalLecturesByCourseId(id);

		if (handsOnLectures.size() > theoreticalLectures.size())
			lectureType = LectureType.HANDS_ON;
		else
			lectureType = LectureType.THEORETICAL;

		object = this.repository.findOneCourseById(id);
		object.setType(lectureType);

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Course object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "code", "title", "Abstract", "retailPrice", "type", "link", "draftMode");

		super.getResponse().setData(tuple);
	}
}
