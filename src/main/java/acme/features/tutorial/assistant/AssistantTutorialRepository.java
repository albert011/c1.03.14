
package acme.features.tutorial.assistant;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.tutorial.Tutorial;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Assistant;

@Repository
public interface AssistantTutorialRepository extends AbstractRepository {

	@Query("select t from Tutorial t where t.assistant = :assistant")
	Collection<Tutorial> findAllTutorialsByAssistant(Assistant assistant);

	@Query("select a from Assistant a where a.userAccount.id = :assistantId")
	Assistant findAssistant(int assistantId);

	@Query("select t from Tutorial t where t.id = :tutorialId")
	Tutorial findTutorialById(int tutorialId);

}
