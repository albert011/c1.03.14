
package acme.features.student.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.forms.StudentDashboard;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentDashboardShowService extends AbstractService<Student, StudentDashboard> {

	// Internal state ---------------------------------------------------------
	@Autowired
	private StudentDashboardRepository repository;


	// AbstractService Interface ----------------------------------------------
	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		boolean status;
		Student student;
		Principal principal;
		int userAccountId;

		principal = super.getRequest().getPrincipal();
		userAccountId = principal.getAccountId();
		student = this.repository.findOneStudentByUserAccountId(userAccountId);

		status = student != null && principal.hasRole(Student.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		int studentId;
		StudentDashboard studentDashboard;
		Principal principal;
		int userAccountId;
		Student student;

		final Integer totalTheoryActivities;
		final Integer totalHandsOnActivities;
		final Double averageTimePeriod;
		final Double deviationTimePeriod;
		final Double minimumTimePeriod;
		final Double maximumTimePeriod;

		final Double averageLearningTime;
		final Double deviationLearningTime;
		final Double minimumLearningTime;
		final Double maximumLearningTime;

		principal = super.getRequest().getPrincipal();
		userAccountId = principal.getAccountId();
		student = this.repository.findOneStudentByUserAccountId(userAccountId);
		studentId = student.getId();

		studentDashboard = new StudentDashboard();

		totalTheoryActivities = this.repository.numTheory(studentId);
		totalHandsOnActivities = this.repository.numHandsOn(studentId);
		averageTimePeriod = this.repository.averagePeriodTime(studentId);
		deviationTimePeriod = this.repository.deviationPeriodTime(studentId);
		minimumTimePeriod = this.repository.maximumPeriodTime(studentId);
		maximumTimePeriod = this.repository.minimumPeriodTime(studentId);

		studentDashboard.setTotalTheoryActivities(totalTheoryActivities);
		studentDashboard.setTotalHandsOnActivities(totalHandsOnActivities);
		studentDashboard.setAverageTimePeriod(averageTimePeriod);
		studentDashboard.setDeviationTimePeriod(deviationTimePeriod);
		studentDashboard.setMaximumTimePeriod(minimumTimePeriod);
		studentDashboard.setMinimumTimePeriod(maximumTimePeriod);

		averageLearningTime = this.repository.averageLearningTime(studentId);
		deviationLearningTime = this.repository.deviationLearningTime(studentId);
		minimumLearningTime = this.repository.minimumLearningTime(studentId);
		maximumLearningTime = this.repository.maximumLearningTime(studentId);

		studentDashboard.setAverageLearningTime(averageLearningTime);
		studentDashboard.setDeviationLearningTime(deviationLearningTime);
		studentDashboard.setMinimumLearningTime(minimumLearningTime);
		studentDashboard.setMaximumLearningTime(maximumLearningTime);

		super.getBuffer().setData(studentDashboard);
	}

	@Override
	public void unbind(final StudentDashboard studentDashboard) {
		Tuple tuple;

		tuple = super.unbind(studentDashboard, "totalTheoryActivities", "totalHandsOnActivities", "averageTimePeriod", "deviationTimePeriod", "minimumTimePeriod", "maximumTimePeriod", "averageLearningTime", "deviationLearningTime", "minimumLearningTime",
			"maximumLearningTime");

		super.getResponse().setData(tuple);
	}

}
