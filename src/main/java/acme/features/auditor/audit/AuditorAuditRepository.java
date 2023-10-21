
package acme.features.auditor.audit;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.audit.Audit;
import acme.entities.audit.AuditRecord;
import acme.entities.audit.Mark;
import acme.entities.course.Course;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Auditor;

@Repository
public interface AuditorAuditRepository extends AbstractRepository {

	@Query("select a from Audit a where a.id = :id")
	Audit findOneAuditById(int id);

	@Query("select a from Audit a where a.auditor.id = :id")
	Collection<Audit> findManyAuditsByAuditorId(int id);

	@Query("select a.mark from AuditRecord a where a.audit.id = :id")
	Collection<Mark> findMarksOfAuditByAuditId(int id);

	@Query("select a from AuditRecord a where a.audit.id = :id")
	Collection<AuditRecord> findAuditRecordsOfAuditByAuditId(int id);

	@Query("select count(a) from AuditRecord a where a.audit.id = :id")
	Long countRecordsFromAuditById(int id);

	@Query("select a from Auditor a where a.id = :id")
	Auditor findOneAuditorById(int id);

	@Query("select c from Course c where c.title like :title")
	Course findOneCourseByTitle(String title);

	@Query("select c from Course c where c.id =:id")
	Course findOneCourseById(int id);

	@Query("select a from Audit a where a.course.title = :title")
	Audit findOneAuditByCourseTitle(String title);

	@Query("select c from Course c")
	Collection<Course> findCourses();

	@Query("select c from Course c where c.draftMode=true")
	Collection<Course> findCoursesPublished();

}
