
package acme.forms;

import java.util.Map;

import acme.framework.components.datatypes.Money;
import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminDashboard extends AbstractForm {

	protected static final long	serialVersionUID	= 1L;

	Map<String, Integer>		numberOfPrincipalsByRole;
	int							minimumNumberOfNotes, maximumNumberOfNotes;
	double						averageNumberOfNotes, standardDeviationNumberOfNotes;
	double						ratioOfPeepsWithEmailAddressAndLink;
	double						ratioOfCriticalBulletins, ratioOfNonCriticalBulletins;
	Map<Money, Double>			averageBudgetOfOffersByCurrency, minimumBudgetOfOffersByCurrency, maximumBudgetOfOffersByCurrency, standardDeviationBudgetOfOffersByCurrency;
}
