
package acme.features.administrator.currency;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.currency.Currency;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AdministratorCurrencyRepository extends AbstractRepository {

	@Query("SELECT c from Currency c")
	Currency findCurrency();

}
