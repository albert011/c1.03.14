
package acme.forms;

import java.time.Month;
import java.util.Map;

import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	protected static final long		serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	protected Map<Month, Integer>	totalNumberOfTheoreticalPracticumPerMonthInLastYear;
	protected Map<Month, Integer>	totalNumberOfHandsOnPracticumPerMonthInLastYear;
	protected Double				averageSessionLength;
	protected Double				deviationSessionLength;
	protected Double				minimumSessionLength;
	protected Double				maximumSessionLength;
	protected Double				averagePracticaLength;
	protected Double				deviationPracticumLength;
	protected Double				minimumPracticumLength;
	protected Double				maximumPracticumLength;

}
