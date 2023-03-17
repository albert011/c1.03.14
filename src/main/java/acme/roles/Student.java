
package acme.roles;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.data.AbstractRole;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Student extends AbstractRole {

	protected static final long	serialVersionUID	= 1L;

	@NotBlank
	@Length(max = 75)
	private String				statement;

	@NotBlank
	@Length(max = 100)
	private String				strongFeatures;

	@NotBlank
	@Length(max = 100)
	private String				weakFeatures;

	@URL
	private String				link;
}
