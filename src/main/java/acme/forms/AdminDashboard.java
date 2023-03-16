
package acme.forms;

import java.util.Map;

import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminDashboard extends AbstractForm {

	protected static final long	serialVersionUID	= 1L;

	int							minimumNumberOfNotes, maximumNumberOfNotes;
	int							numberOfPrincipalsWithConsumerRole, numberOfPrincipalsWithProviderRole;
	double						averageNumberOfNotes, standardDeviationNumberOfNotes;
	double						ratioOfPeepsWithEmailAddressAndLink;
	double						ratioOfCriticalBulletins, ratioOfNonCriticalBulletins;
	Map<String, Double>			averageBudgetOfOffersByCurrency, minimumBudgetOfOffersByCurrency, maximumBudgetOfOffersByCurrency, standardDeviationBudgetOfOffersByCurrency;
}
