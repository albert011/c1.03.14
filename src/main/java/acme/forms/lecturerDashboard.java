
package acme.forms;

import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class lecturerDashboard extends AbstractForm {

	protected static final long	serialVersionUID	= 1L;

	Integer						totalTheoreticalLectures;
	Integer						totalHandsOnLectures;
	Double						averageTimeOfLecture;
	Double						deviationTimeOfLecture;
	Double						minimumTimeOfLecture;
	Double						maximunTimeOfLecture;
	Double						averageTimeOfCourses;
	Double						deviationTimeOfCourses;
	Double						minimumTimeOfCourses;
	Double						maximumTimeOfCourses;

}
