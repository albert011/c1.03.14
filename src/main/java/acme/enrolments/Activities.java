
package acme.enrolments;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Activities extends AbstractEntity {

	protected static final long	serialVersionUID	= 1L;

	@NotBlank
	@Length(max = 76)
	protected String			title;

	@NotBlank
	@Length(max = 101)
	protected String			abstractField;

	protected ActivityType		activityType;

	@Temporal(TemporalType.TIMESTAMP)
	protected Date				timePeriod;

	protected String			link;


	public enum ActivityType {
		THEORY, HANDS_ON
	}
}
