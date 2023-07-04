
package acme.features.lecturers.coursesLectures;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.course.CoursesLectures;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturersCoursesLecturesListMineService extends AbstractService<Lecturer, CoursesLectures> {

	@Autowired
	protected LecturersCoursesLecturesRepository repository;


	@Override
	public void check() {
		boolean status;
		status = super.getRequest().hasData("courseId", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<CoursesLectures> coursesLectures;
		int courseId;
		if (super.getRequest().hasData("courseId")) {
			courseId = super.getRequest().getData("courseId", int.class);
			coursesLectures = this.repository.findManyCoursesLecturesByCourseId(courseId);
		} else

		{
			final int lecturerId = super.getRequest().getPrincipal().getActiveRoleId();
			coursesLectures = this.repository.findManyCoursesLecturesByLecturerId(lecturerId);
		}

		super.getBuffer().setData(coursesLectures);
	}

	@Override
	public void unbind(final CoursesLectures object) {
		assert object != null;
		Tuple tuple;
		tuple = super.unbind(object, "id");
		tuple.put("courseCode", object.getCourse().getCode());
		tuple.put("lectureTitle", object.getLecture().getTitle());

		super.getResponse().setData(tuple);
	}
}
