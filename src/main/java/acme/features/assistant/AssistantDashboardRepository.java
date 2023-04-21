
package acme.features.assistant;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.session.SessionTutorial;
import acme.entities.tutorial.Tutorial;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Assistant;

@Repository
public interface AssistantDashboardRepository extends AbstractRepository {

	@Query("select t from Tutorial t where t.assistant = :assistant")
	Collection<Tutorial> getTutorials(Assistant assistant);

	@Query("select a from Assistant a where a.id = :assistantId")
	Assistant getAssistantById(int assistantId);

	@Query("select s from SessionTutorial s where s.tutorial.assistant = :assistant")
	Collection<SessionTutorial> getSessions(Assistant assistant);

}
