
package acme.enrolments;

import java.util.List;

import javax.persistence.OneToMany;

import acme.framework.data.AbstractEntity;

public class WorkBook extends AbstractEntity {

	protected static final long	serialVersionUID	= 1L;

	@OneToMany()
	private List<Activities>	activities;
}
