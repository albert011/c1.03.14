
package acme.entities.course;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import acme.framework.data.AbstractEntity;
import acme.roles.Lecturer;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CoursesLecturers extends AbstractEntity {

	protected static final long	serialVersionUID	= 1L;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	protected Course			courses;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	protected Lecturer			lecturers;
}
