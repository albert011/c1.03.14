
package acme.forms;

import java.util.Map;

import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	protected Map<String, Long>	numPracticumByMonth; //Last year
	protected Double			averageSessionLength;
	protected Double			deviationSessionLength;
	protected Double			minimumSessionLength;
	protected Double			maximumSessionLength;
	protected Double			averagePracticumLength;
	protected Double			deviationPracticumLength;
	protected Double			minimumPracticumLength;
	protected Double			maximumPracticumLength;

	// Other auxiliary attributes ---------------------------------------------
	protected Integer			numPracticum;
	protected Integer			numSession;

}
