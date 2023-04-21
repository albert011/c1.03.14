
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
public class LecturersCoursesPublishService extends AbstractService<Lecturer, Course> {

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
		boolean status;
		int masterId;
		Course course;

		masterId = super.getRequest().getData("id", int.class);
		course = this.repository.findOneCourseById(masterId);
		status = course != null && course.isDraftMode();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Course object;
		int id;
		Long theoreticalLectures;
		Long handsOnLectures;
		LectureType lectureType;
		id = super.getRequest().getData("id", int.class);

		handsOnLectures = this.repository.findManyNonTheoreticalLecturesByCourseId(id);
		theoreticalLectures = this.repository.findManyTheoreticalLecturesByCourseId(id);

		if (handsOnLectures > theoreticalLectures)
			lectureType = LectureType.HANDS_ON;
		else
			lectureType = LectureType.THEORETICAL;

		object = this.repository.findOneCourseById(id);
		object.setType(lectureType);

		super.getBuffer().setData(object);
	}
	@Override
	public void bind(final Course object) {
		assert object != null;

		super.bind(object, "code", "title", "Abstract", "retailPrice", "link");
	}

	@Override
	public void validate(final Course object) {
		assert object != null;
		if (!super.getBuffer().getErrors().hasErrors("retailPrice"))
			super.state(object.getRetailPrice().getAmount() > 0, "retailPrice", "lecturer.lecture.form.error.negative-price");

		if (!super.getBuffer().getErrors().hasErrors("retailPrice"))
			super.state(object.getRetailPrice().getCurrency().equals("EUR") ||
				object.getRetailPrice().getCurrency().equals("GBP") ||
				object.getRetailPrice().getCurrency().equals("USD"), "retailPrice", "lecturer.lecture.form.error.currency");

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Course course;

			course = this.repository.findOneCourseByCode(object.getCode());
			super.state(course == null || course.equals(object), "code", "lecturer.course.form.error.duplicated");
		}

		{
			Collection<Lecture> lectures;

			lectures = this.repository.findManyLecturesUnpublishedByCourse(object.getId());
			super.state(lectures.isEmpty(), "*", "lecturer.course.form.error.lectures-unpublished");
		}
		{
			Long lectures;

			lectures = this.repository.findManyNonTheoreticalLecturesByCourseId(object.getId());
			super.state(!(lectures == 0), "*", "lecturer.course.form.error.lectures-theoretical");
		}
		object.setType(LectureType.HANDS_ON);
	}

	@Override
	public void perform(final Course object) {
		assert object != null;

		object.setDraftMode(false);
		this.repository.save(object);
	}

	@Override
	public void unbind(final Course object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "code", "title", "Abstract", "retailPrice", "type", "link", "draftMode");
		super.getResponse().setData(tuple);
	}

}
