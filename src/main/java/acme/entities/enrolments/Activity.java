
package acme.entities.enrolments;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Activity extends AbstractEntity {

	protected static final long	serialVersionUID	= 1L;

	@NotBlank
	@Length(max = 75)
	protected String			title;

	@NotBlank
	@Length(max = 100)
	protected String			abstractField;

	@Enumerated(value = EnumType.STRING)
	protected ActivityType		activityType;

	@Temporal(TemporalType.TIMESTAMP)
	protected Date				timePeriod;

	@URL
	protected String			link;


	public enum ActivityType {
		THEORY, HANDS_ON
	}


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	protected Enrolment enrolment;
}
