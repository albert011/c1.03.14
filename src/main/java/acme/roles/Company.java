
package acme.roles;

import javax.annotation.Nullable;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import acme.framework.data.AbstractRole;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Company extends AbstractRole {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@Size(max = 75)
	protected String			name;

	@NotBlank
	@Size(max = 25)
	protected String			VATNumber;

	@NotBlank
	@Size(max = 100)
	protected String			summary;

	@Nullable
	protected String			link;
}
