
package acme.features.any.banners;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.banner.Banner;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AnyBannerRepository extends AbstractRepository {

	@Query("SELECT b FROM Banner b WHERE b.displayPeriodStart <= b.instantiationMoment AND b.displayPeriodEnd >= b.instantiationMoment")
	Collection<Banner> findAllBannersDisplayed();
}
