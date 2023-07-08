
package acme.features.lecturers.courseLecture;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.course.CourseLecture;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturersCoursesLecturesListMineService extends AbstractService<Lecturer, CourseLecture> {

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
		boolean status;
		int courseId;
		int lecturerId;
		int ownerCourseId;

		courseId = super.getRequest().getData("courseId", int.class);
		lecturerId = super.getRequest().getPrincipal().getActiveRoleId();
		ownerCourseId = this.repository.findCourseOwnerByCourseId(courseId).getId();
		status = lecturerId == ownerCourseId;

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<CourseLecture> coursesLectures;
		int courseId;
		courseId = super.getRequest().getData("courseId", int.class);
		coursesLectures = this.repository.findManyCoursesLecturesByCourseId(courseId);

		super.getBuffer().setData(coursesLectures);
	}

	@Override
	public void unbind(final CourseLecture object) {
		assert object != null;

		Tuple tuple;
		tuple = super.unbind(object, "id");
		tuple.put("draftMode", object.getCourse().isDraftMode());
		tuple.put("courseCode", object.getCourse().getCode());
		tuple.put("lectureTitle", object.getLecture().getTitle());

		super.getResponse().setData(tuple);
	}
}
