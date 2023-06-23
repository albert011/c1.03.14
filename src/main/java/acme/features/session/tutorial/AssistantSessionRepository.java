
package acme.features.session.tutorial;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.session.SessionTutorial;
import acme.entities.tutorial.Tutorial;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Assistant;

@Repository
public interface AssistantSessionRepository extends AbstractRepository {

	@Query("select t from Tutorial t where t.id = :tutorialId")
	Tutorial findTutorialById(int tutorialId);

	@Query("select st from SessionTutorial st where st.tutorial = :tutorial")
	Collection<SessionTutorial> findAllSessionsByTutorial(Tutorial tutorial);

	@Query("select t from Tutorial t")
	Collection<Tutorial> findAllTutorials();

	@Query("select st from SessionTutorial st where st.id = :sessionId")
	SessionTutorial findSessionById(int sessionId);

	@Query("select a from Assistant a where a.userAccount.id = :assistantId")
	Assistant findAssistant(int assistantId);

	@Query("select st from SessionTutorial st where st.tutorial.assistant = :assistant")
	Collection<SessionTutorial> findSessionsCreatedByAssistant(Assistant assistant);

	@Query("select t from Tutorial t where t.isPublished = false and t.assistant = :assistant")
	Collection<Tutorial> findAllNotPublishedTutorialsByAssistant(Assistant assistant);

	@Query("select t from Tutorial t where t.assistant = :assistant")
	Collection<Tutorial> findAllTutorialsByAssistant(Assistant assistant);

}
