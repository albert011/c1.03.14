
package acme.entities.note;

import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.components.accounts.Principal;
import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Note extends AbstractEntity {

	protected static final long	serialVersionUID	= 1L;

	@NotBlank
	@Length(max = 75)
	protected String			title;

	@NotBlank
	@Length(max = 75)
	@Pattern(regexp = "〈username〉 - 〈surname, name〉")
	protected String			author;

	@NotBlank
	@Length(max = 100)
	protected String			message;

	@Email
	protected String			email_address;

	@URL
	protected String			link;

	protected Principal			principals;
}
