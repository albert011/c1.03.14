
package acme.entities.session;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

import acme.entities.tutorial.Tutorial;
import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class SessionTutorial extends AbstractEntity {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	@NotBlank
	@Size(max = 75)
	protected String			title;

	@NotBlank
	@Size(max = 100)
	protected String			abstractMessage;

	@NotNull
	@ManyToOne(optional = false)
	protected SessionType		sessionType;

	@NotNull
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	protected Date				timeStart;

	@NotNull
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	protected Date				timeEnd;

	@URL
	protected String			link;

	@ManyToOne(optional = false)
	protected Tutorial			tutorial;


	@Transient
	protected Double getDuration() {
		//miliseconds
		final long duration = this.timeEnd.getTime() - this.timeStart.getTime();
		return (double) duration / (1000 * 60 * 60);
	}

}
