
package acme.testing.assistant.session;

import org.springframework.data.jpa.repository.Query;

import acme.entities.session.SessionTutorial;
import acme.entities.tutorial.Tutorial;
import acme.framework.repositories.AbstractRepository;

public interface AssistantSessionTestRepository extends AbstractRepository {

	@Query("select st from SessionTutorial st where st.id = :id")
	SessionTutorial getSession(int id);

	@Query("select t from Tutorial t where t.code = :code")
	Tutorial getTutorial(String code);

}
