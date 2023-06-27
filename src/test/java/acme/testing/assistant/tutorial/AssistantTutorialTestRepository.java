
package acme.testing.assistant.tutorial;

import org.springframework.data.jpa.repository.Query;

import acme.entities.course.Course;
import acme.entities.tutorial.Tutorial;
import acme.framework.repositories.AbstractRepository;

public interface AssistantTutorialTestRepository extends AbstractRepository {

	@Query("select c from Course c where c.code = :course")
	Course getCourse(String course);

	@Query("select t from Tutorial t where t.code = :string")
	Tutorial getTutorialByCode(String string);

}
