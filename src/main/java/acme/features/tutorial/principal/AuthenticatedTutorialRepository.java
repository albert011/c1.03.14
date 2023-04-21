
package acme.features.tutorial.principal;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.tutorial.Tutorial;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuthenticatedTutorialRepository extends AbstractRepository {

	@Query("select t from Tutorial t where t.isPublished = true")
	Collection<Tutorial> findAllTutorials();

	@Query("select t from Tutorial t where t.id = :id")
	Tutorial findOneTutorialById(int id);

}
