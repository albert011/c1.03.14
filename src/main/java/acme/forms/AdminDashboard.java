
package acme.forms;

import java.util.Map;

import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminDashboard extends AbstractForm {

	protected static final long	serialVersionUID	= 1L;

	Integer						numberOfPrincipalsWithConsumerRole;
	Integer						numberOfPrincipalsWithProviderRole;
	Double						ratioOfPeepsWithEmailAddressAndLink;
	Double						ratioOfCriticalBulletins;
	Double						ratioOfNonCriticalBulletins;
	Map<String, Double>			averageBudgetOfOffersByCurrency;
	Map<String, Double>			minimumBudgetOfOffersByCurrency;
	Map<String, Double>			maximumBudgetOfOffersByCurrency;
	Map<String, Double>			standardDeviationBudgetOfOffersByCurrency;
	Double						averageNumberOfNotes;
	Integer						minimumNumberOfNotes;
	Integer						maximumNumberOfNotes;
	Double						standardDeviationNumberOfNotes;
}
