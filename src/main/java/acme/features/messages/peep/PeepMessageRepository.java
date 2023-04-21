
package acme.features.messages.peep;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.messages.PeepMessage;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface PeepMessageRepository extends AbstractRepository {

	@Query("select peep from PeepMessage peep")
	Collection<PeepMessage> findAllPeeps();

	@Query("select peep from PeepMessage peep where peep.id = :id")
	PeepMessage findOnePeepById(int id);

}
