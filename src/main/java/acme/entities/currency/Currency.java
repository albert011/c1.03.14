
package acme.entities.currency;

import java.util.Arrays;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Currency extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@Pattern(regexp = "^[A-Z]{3}$")
	protected String			systemCurrency;

	@NotNull
	@Pattern(regexp = "^([A-Z]{3},)*[A-Z]{3}$")
	protected String			acceptedCurrencies;

	// Methods ----------------------------------------------------------------


	@Transient
	public Collection<String> getAcceptedCurrenciesAsCollection() {
		return Arrays.asList(this.acceptedCurrencies.split(","));
	}

}
