
package acme.components;

import java.util.concurrent.ThreadLocalRandom;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.banner.Banner;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface BannerRepository extends AbstractRepository {

	@Query("SELECT count(b) FROM Banner b WHERE b.displayPeriodStart <= CURRENT_TIMESTAMP AND b.displayPeriodEnd >= CURRENT_TIMESTAMP")
	int countDisplayableBanners();

	@Query("SELECT b FROM Banner b WHERE b.displayPeriodStart <= CURRENT_TIMESTAMP AND b.displayPeriodEnd >= CURRENT_TIMESTAMP")
	Banner[] findAllDisplayableBanners();

	default Banner getRandomBanner() {
		Banner result;
		int count, index;
		ThreadLocalRandom random;
		Banner[] banners;

		count = this.countDisplayableBanners();
		if (count == 0)
			result = null;
		else {
			random = ThreadLocalRandom.current();
			index = random.nextInt(0, count);
			banners = this.findAllDisplayableBanners();
			result = banners.length == 0 ? null : banners[index];
		}

		return result;
	}
}
