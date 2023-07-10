
package acme.entities.practicums;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import acme.entities.course.Course;
import acme.entities.practicumSessions.PracticumSession;
import acme.framework.data.AbstractEntity;
import acme.framework.helpers.MomentHelper;
import acme.roles.Company;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Practicum extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "[A-Z]{1,3}[0-9][0-9]{3}")
	protected String			code;

	@NotBlank
	@Size(max = 75)
	protected String			title;

	@NotBlank
	@Size(max = 100)
	protected String			abstractText;

	@NotBlank
	@Size(max = 100)
	protected String			goals;

	protected boolean			draftMode;

	// Derived attributes -----------------------------------------------------


	public String getEstimatedTotalTime(final Collection<PracticumSession> sessions) {
		double res = 0.0;
		for (final PracticumSession ps : sessions)
			res += MomentHelper.computeDuration(ps.getStartDate(), ps.getEndDate()).toHours();
		final double porcentaje = res * 0.1;
		return res + " (+/-" + porcentaje + ")";
	}

	// Relationships ----------------------------------------------------------


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	protected Company	company;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	protected Course	course;

}
