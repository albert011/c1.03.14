
package acme.features.lecturers.lectures;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.lecture.Lecture;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturersLecturesListMineService extends AbstractService<Lecturer, Lecture> {

	@Autowired
	protected LecturersLecturesRepository repository;


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		boolean status;
		int lecturerId;
		final int ownerCourseId;
		int courseId;

		if (super.getRequest().hasData("courseId", int.class)) {
			lecturerId = super.getRequest().getPrincipal().getActiveRoleId();
			courseId = super.getRequest().getData("courseId", int.class);
			ownerCourseId = this.repository.findCourseOwnerByCourseId(courseId).getId();
			status = lecturerId == ownerCourseId;
		} else
			status = true;
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<Lecture> objects;
		Principal principal;

		if (super.getRequest().hasData("courseId", int.class)) {
			final Integer courseId = super.getRequest().getData("courseId", int.class);
			objects = this.repository.findManyLecturesByCourseId(courseId);
		} else {
			principal = super.getRequest().getPrincipal();
			objects = this.repository.findLecturesByLecturer(principal.getActiveRoleId());
		}
		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final Lecture object) {
		assert object != null;

		Tuple tuple;
		tuple = super.unbind(object, "title", "type");

		super.getResponse().setData(tuple);
	}
}
