
package acme.forms;

import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentDashboard extends AbstractForm {

	protected static final long	serialVersionUID	= 1L;

	Double						totalTheoryActivities;
	Double						averageTimePeriod;
	Double						deviationTimePeriod;
	Double						minimunTimePeriod;
	Double						maximunTimePeriod;
	Double						averageWorkTime;
	Double						deviationWorkTime;
	Double						minimunWorkTime;
	Double						maximunWorkTime;

}
