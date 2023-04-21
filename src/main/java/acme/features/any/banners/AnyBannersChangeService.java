
package acme.features.any.banners;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.banner.Banner;
import acme.framework.components.accounts.Any;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AnyBannersChangeService extends AbstractService<Any, Banner> {

	@Autowired
	protected AnyBannerRepository repository;


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Banner object;
		Collection<Banner> objects;
		final List<Banner> banners = new ArrayList<>();

		objects = this.repository.findAllBannersDisplayed();
		banners.addAll(objects);
		final int randomIndex = (int) (Math.random() * objects.size());
		object = banners.get(randomIndex);

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Banner object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "pictureLink");
		super.getResponse().setData(tuple);
	}
}
