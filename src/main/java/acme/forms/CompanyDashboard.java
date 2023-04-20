
package acme.forms;

import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	Integer						totalNumberOfPracticasPerMonthInLastYear;
	Double						averageSessionLength;
	Double						deviationSessionLength;
	Double						minimumSessionLength;
	Double						maximumSessionLength;
	Double						averagePracticaLength;
	Double						deviationPracticaLength;
	Double						minimumPracticaLength;
	Double						maximumPracticaLength;

}
