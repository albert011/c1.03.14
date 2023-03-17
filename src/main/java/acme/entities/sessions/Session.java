
package acme.entities.sessions;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import acme.entities.practicums.Practicum;
import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Session extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@ManyToOne
	@JoinColumn(name = "practicum_id")
	protected Practicum			practicum;

	@NotBlank
	@Size(max = 75)
	protected String			title;

	@NotBlank
	@Size(max = 100)
	protected String			abstractText;

	@NotNull
	@FutureOrPresent
	protected LocalDate			startDate;

	@NotNull
	@Future
	protected LocalDate			endDate;

	protected String			link;

	// Getters and setters
	// ...
}
