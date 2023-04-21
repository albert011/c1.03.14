
package acme.features.lecturers.courses;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.audit.Audit;
import acme.entities.audit.AuditRecord;
import acme.entities.course.Course;
import acme.entities.course.CoursesLecturers;
import acme.entities.enrolments.Activity;
import acme.entities.enrolments.Enrolment;
import acme.entities.lecture.Lecture;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturersCoursesDeleteService extends AbstractService<Lecturer, Course> {

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

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneCourseById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Course object) {
		assert object != null;

		super.bind(object, "code", "title", "Abstract", "retailPrice", "body", "isTheoretical", "link");
	}

	@Override
	public void validate(final Course object) {
		assert object != null;
	}

	@Override
	public void perform(final Course object) {
		assert object != null;

		Collection<Lecture> lectures;
		Collection<CoursesLecturers> courseLecturer;
		List<Audit> audits;
		List<Enrolment> enrolments;
		Collection<Activity> activities;
		Enrolment enrolment;
		Audit audit;
		Collection<AuditRecord> auditRecords;

		enrolments = (List<Enrolment>) this.repository.findManyEnrolmentsByCourseId(object.getId());
		audits = (List<Audit>) this.repository.findManyAuditsByCourseId(object.getId());
		lectures = this.repository.findManyLecturesByCourseId(object.getId());
		courseLecturer = this.repository.findCourseLecturerByCourse(object.getId());

		for (int i = 0; i < audits.size(); i++) {
			audit = audits.get(i);
			auditRecords = this.repository.findManyAuditRecordByAuditId(audit.getId());
			this.repository.deleteAll(auditRecords);
		}
		for (int i = 0; i < enrolments.size(); i++) {
			enrolment = enrolments.get(i);
			activities = this.repository.findManyActivitiesByEnrolmentId(enrolment.getId());
			this.repository.deleteAll(activities);
		}
		this.repository.deleteAll(enrolments);
		this.repository.deleteAll(audits);
		this.repository.deleteAll(lectures);
		this.repository.deleteAll(courseLecturer);

		this.repository.delete(object);

	}

	@Override
	public void unbind(final Course object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "code", "title", "Abstract", "retailPrice", "body", "isTheoretical", "link", "draftMode");
		super.getResponse().setData(tuple);
	}
}
