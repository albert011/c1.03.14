
package acme.roles;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.entities.course;
import acme.framework.data.AbstractRole;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Lecturer extends AbstractRole {

	protected static final long	serialVersionUID	= 1L;

	@NotBlank
	@Length(max = 75)
	private String				almaMater;

	@NotBlank
	@Length(max = 75)
	private String				resume;

	@NotBlank
	@Length(max = 100)
	private String				listOfQualifications;

	@URL
	private String				link;

	@ManyToOne()
	protected course			courses;
}
