
package acme.features.assistant;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.course.LectureType;
import acme.entities.session.SessionTutorial;
import acme.entities.tutorial.Tutorial;
import acme.forms.AssistantDashboard;
import acme.forms.UtilsMath;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantDashboardShowService extends AbstractService<Assistant, AssistantDashboard> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AssistantDashboardRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		AssistantDashboard dashboard;
		final Assistant assistant;
		int assistantId;
		Collection<Tutorial> tutorials;
		Collection<SessionTutorial> sessions;

		int numberOfTutorialsHandsOn;
		int numberOfTutorialsTheory;
		double averageTimeSessions;
		double deviationTimeSessions;
		double minTimeSessions;
		double maxTimeSessions;
		double averageTimeTutorials;
		double deviationTimeTutorials;
		double minTimeTutorials;
		double maxTimeTutorials;

		assistantId = super.getRequest().getPrincipal().getAccountId();
		assistant = this.repository.getAssistantById(assistantId);
		tutorials = this.repository.getTutorials(assistant);
		sessions = this.repository.getSessions(assistant);

		final List<Double> estimatedTimeTutorials = tutorials.stream().mapToDouble(x -> x.getEstimatedTotalTime()).boxed().collect(Collectors.toList());
		final List<Double> durationSessions = sessions.stream().mapToDouble(x -> (x.getTimeEnd().getTime() - x.getTimeStart().getTime()) / 3600000).boxed().collect(Collectors.toList());
		numberOfTutorialsHandsOn = (int) tutorials.stream().filter(p -> p.getCourse().getType().equals(LectureType.HANDS_ON)).count();
		numberOfTutorialsTheory = (int) tutorials.stream().filter(p -> p.getCourse().getType().equals(LectureType.THEORETICAL)).count();
		averageTimeSessions = UtilsMath.getAverage(durationSessions);
		deviationTimeSessions = UtilsMath.getDeviation(durationSessions);
		minTimeSessions = UtilsMath.getMinimum(durationSessions);
		maxTimeSessions = UtilsMath.getMaximum(durationSessions);
		averageTimeTutorials = UtilsMath.getAverage(estimatedTimeTutorials);
		deviationTimeTutorials = UtilsMath.getDeviation(estimatedTimeTutorials);
		minTimeTutorials = UtilsMath.getMinimum(estimatedTimeTutorials);
		maxTimeTutorials = UtilsMath.getMaximum(estimatedTimeTutorials);

		dashboard = new AssistantDashboard();
		dashboard.setNumberOfTutorialsHandsOn(numberOfTutorialsHandsOn);
		dashboard.setNumberOfTutorialsTheory(numberOfTutorialsTheory);
		dashboard.setAverageTimeSessions(averageTimeSessions);
		dashboard.setDeviationTimeSessions(deviationTimeSessions);
		dashboard.setMinTimeSessions(minTimeSessions);
		dashboard.setMaxTimeSessions(maxTimeSessions);
		dashboard.setAverageTimeTutorials(averageTimeTutorials);
		dashboard.setDeviationTimeTutorials(deviationTimeTutorials);
		dashboard.setMinTimeTutorials(minTimeTutorials);
		dashboard.setMaxTimeTutorials(maxTimeTutorials);

		super.getBuffer().setData(dashboard);
	}

	@Override
	public void unbind(final AssistantDashboard object) {
		Tuple tuple;

		tuple = super.unbind(object, //
			"numberOfTutorialsHandsOn", "numberOfTutorialsTheory", "averageTimeSessions", "deviationTimeSessions",//
			"minTimeSessions", "maxTimeSessions", "averageTimeTutorials", // 
			"deviationTimeTutorials", "minTimeTutorials", "maxTimeTutorials");

		super.getResponse().setData(tuple);
	}
}
