/*
 * AuthenticatedPracticumRepository.java
 *
 * Copyright (C) 2022-2023 Javier Fernández Castillo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.authenticated.practicum;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.practicumSessions.PracticumSession;
import acme.entities.practicums.Practicum;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuthenticatedPracticumRepository extends AbstractRepository {

	@Query("select p from Practicum p where p.id = :id")
	Practicum findOnePracticumById(int id);

	@Query("select p from Practicum p where p.draftMode = false and p.course.type= 1")
	Collection<Practicum> findManyPublishedPracticums();

	@Query("SELECT ps FROM PracticumSession ps WHERE ps.practicum.id = :id")
	Collection<PracticumSession> findManyPracticumSessionsByPracticumId(int id);

	@Query("select p.course.code from Practicum p where p.id = :id")
	String findCourseCodeByPracticumId(int id);

	@Query("select p.company.name from Practicum p where p.id = :id")
	String findCompanyNameByPracticumId(int id);

}
