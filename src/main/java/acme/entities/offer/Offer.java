
package acme.entities.offer;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Offer extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotNull
	@Past
	protected LocalDate			instantiationMoment;

	@NotBlank
	@Size(max = 75)
	protected String			heading;

	@NotBlank
	@Size(max = 100)
	protected String			summary;

	@NotNull
	@FutureOrPresent
	protected LocalDate			availabilityPeriodStart;

	@NotNull
	@FutureOrPresent
	protected LocalDate			availabilityPeriodEnd;

	@Positive
	protected double			price;

	protected String			link;


	@AssertTrue(message = "The availability period must be at least one week long and start at least one day after the offer is instantiated.")
	public boolean isAvailabilityPeriodValid() {
		final LocalDate minAvailabilityPeriodEnd = this.instantiationMoment.plusDays(1).plusWeeks(1);
		return this.availabilityPeriodEnd.isEqual(minAvailabilityPeriodEnd) || this.availabilityPeriodEnd.isAfter(minAvailabilityPeriodEnd);
	}
}
