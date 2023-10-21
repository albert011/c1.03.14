
package acme.forms;

import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentDashboard extends AbstractForm {

	protected static final long	serialVersionUID	= 1L;

	Integer						totalTheoryActivities;
	Integer						totalHandsOnActivities;
	Double						averageTimePeriod;
	Double						deviationTimePeriod;
	Double						minimumTimePeriod;
	Double						maximumTimePeriod;
	Double						averageLearningTime;
	Double						deviationLearningTime;
	Double						minimumLearningTime;
	Double						maximumLearningTime;

}
