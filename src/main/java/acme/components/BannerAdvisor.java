
package acme.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import acme.entities.banner.Banner;

@ControllerAdvice
public class BannerAdvisor {

	@Autowired
	protected BannerRepository repository;


	@ModelAttribute("bannerToDisplay")
	public Banner getRandomBanner() {
		Banner result;

		try {
			result = this.repository.getRandomBanner();
		} catch (final Throwable oops) {
			result = null;
		}

		return result;
	}

	@ModelAttribute("availableBanners")
	public Banner[] availableBanners() {
		return this.repository.findAllDisplayableBanners();
	}

	@ModelAttribute("nAvailableBanners")
	public int nAvailableBanners() {
		return this.repository.countDisplayableBanners();
	}
}
