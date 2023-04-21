
package acme.entities.audit;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import acme.entities.course.Course;
import acme.framework.data.AbstractEntity;
import acme.roles.Auditor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Audit extends AbstractEntity {

	protected static final long	serialVersionUID	= 1L;

	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "[A-Z]{1,3}[0-9][0-9]{3}", message = "default.error.conversion")
	protected String			code;

	@NotBlank
	@Length(max = 100)
	protected String			conclusion;

	@NotBlank
	@Length(max = 100)
	protected String			strongPoints;

	@NotBlank
	@Length(max = 100)
	protected String			weakPoints;

	@NotNull
	@Enumerated(value = EnumType.STRING)
	protected Mark				mark;

	@NotNull
	@Valid
	@OneToOne(optional = false)
	protected Course			course;

	protected boolean			isPublished;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	protected Auditor			auditor;

	/*
	 * a mark (computed as the mode of the marks in the corresponding auditing records;
	 * ties must be broken arbitrarily if necessary).
	 */
}
