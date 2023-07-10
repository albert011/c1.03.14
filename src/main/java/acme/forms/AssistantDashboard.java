
package acme.forms;

import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssistantDashboard extends AbstractForm {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	protected int				numberOfTutorialsHandsOn;

	protected int				numberOfTutorialsTheory;

	protected double			averageTimeSessions;

	protected double			deviationTimeSessions;

	protected double			minTimeSessions;

	protected double			maxTimeSessions;

	protected double			averageTimeTutorials;

	protected double			deviationTimeTutorials;

	protected double			minTimeTutorials;

	protected double			maxTimeTutorials;

}
