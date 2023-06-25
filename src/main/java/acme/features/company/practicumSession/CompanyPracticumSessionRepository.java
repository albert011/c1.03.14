
package acme.features.company.practicumSession;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.practicumSessions.PracticumSession;
import acme.entities.practicums.Practicum;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface CompanyPracticumSessionRepository extends AbstractRepository {

	@Query("select p from Practicum p where p.id = :id")
	Practicum findOnePracticumById(int id);

	@Query("select ps.practicum from PracticumSession ps where ps.id = :id")
	Practicum findOnePracticumByPracticumSessionId(int id);

	@Query("select ps from PracticumSession ps where ps.id = :id")
	PracticumSession findOnePracticumSessionById(int id);

	@Query("select ps from PracticumSession ps where ps.practicum.id = :practicumId")
	Collection<PracticumSession> findManyPracticumSessionsByPracticumId(int practicumId);

	@Query("select ps from PracticumSession ps where ps.practicum.id = :practicumId AND ps.isAddendum = 1")
	PracticumSession findOneAddendumSessionByPracticumId(int practicumId);

}
