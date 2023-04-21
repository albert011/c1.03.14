
package acme.features.messages.bulletin;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.messages.BulletinMessage;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface BulletinMessageRepository extends AbstractRepository {

	@Query("select b from BulletinMessage b")
	Collection<BulletinMessage> findAllBulletins();

	@Query("select b from BulletinMessage b where b.id = :id")
	BulletinMessage findOneBulletinById(int id);

}
