
package acme.features.session.tutorial;

import java.util.Collection;

import org.springframework.stereotype.Repository;

import acme.entities.session.SessionTutorial;
import acme.entities.session.SessionType;
import acme.entities.tutorial.Tutorial;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AssistantSessionRepository extends AbstractRepository {

	Tutorial findTutorialById(int tutorialId);

	Collection<SessionTutorial> findAllSessionsByTutorial(Tutorial tutorial);

	SessionTutorial findSessionById(int sessionId);

	SessionType findTypeOfSessionById(int typeId);

}
