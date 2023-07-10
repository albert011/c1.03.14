
package acme.features.lecturers.courses;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.audit.Audit;
import acme.entities.audit.AuditRecord;
import acme.entities.course.Course;
import acme.entities.course.CourseLecture;
import acme.entities.enrolments.Activity;
import acme.entities.enrolments.Enrolment;
import acme.entities.lecture.Lecture;
import acme.entities.lecture.LectureType;
import acme.entities.session.SessionTutorial;
import acme.entities.tutorial.Tutorial;
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
		Lecturer lecturer;

		masterId = super.getRequest().getData("id", int.class);
		course = this.repository.findOneCourseById(masterId);
		lecturer = course == null ? null : course.getLecturer();
		status = course != null && course.isDraftMode() && super.getRequest().getPrincipal().hasRole(lecturer);

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

		super.bind(object, "code", "title", "Abstract", "retailPrice", "link");
	}

	@Override
	public void validate(final Course object) {
		assert object != null;
	}

	@Override
	public void perform(final Course object) {
		assert object != null;

		Collection<CourseLecture> courseLecture;
		List<Audit> audits;
		List<Enrolment> enrolments;
		Collection<Activity> activities;
		Enrolment enrolment;
		Audit audit;
		List<Tutorial> tutorials;
		Collection<AuditRecord> auditRecords;
		Collection<SessionTutorial> session;
		Tutorial tutorial;

		tutorials = (List<Tutorial>) this.repository.findManyTutorialsByCourseId(object.getId());
		enrolments = (List<Enrolment>) this.repository.findManyEnrolmentsByCourseId(object.getId());
		audits = (List<Audit>) this.repository.findManyAuditsByCourseId(object.getId());
		courseLecture = this.repository.findCourseLectureByCourse(object.getId());

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
		for (int i = 0; i < tutorials.size(); i++) {
			tutorial = tutorials.get(i);
			session = this.repository.findManySessionsByTutorial(tutorial.getId());
			this.repository.deleteAll(session);
		}

		this.repository.deleteAll(enrolments);
		this.repository.deleteAll(audits);
		this.repository.deleteAll(courseLecture);
		this.repository.deleteAll(tutorials);

		this.repository.delete(object);

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
