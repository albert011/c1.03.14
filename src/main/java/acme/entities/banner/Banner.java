
package acme.entities.banner;

import java.util.Date;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Banner extends AbstractEntity {

	protected static final long	serialVersionUID	= 1L;

	@Past
	private Date				createdDate; // fecha de creación

	private Date				displayStartDate; // fecha de inicio de la visualización

	private Date				displayEndDate; // fecha de fin de la visualización

	@URL
	private String				pictureLink; // enlace a la imagen del banner

	@NotBlank
	@Length(max = 75)
	private String				slogan; // lema del banner

	@URL
	private String				targetLink; // enlace al documento web de destino
}
