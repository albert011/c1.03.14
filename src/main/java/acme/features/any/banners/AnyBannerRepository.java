
package acme.features.any.banners;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.banner.Banner;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AnyBannerRepository extends AbstractRepository {

	@Query("SELECT b FROM Banner b WHERE b.displayPeriodStart <= CURRENT_TIMESTAMP AND b.displayPeriodEnd >= CURRENT_TIMESTAMP")
	Collection<Banner> findAllBannersDisplayed();
}
