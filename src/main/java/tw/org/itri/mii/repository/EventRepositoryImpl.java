package tw.org.itri.mii.repository;

import java.util.Collection;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import tw.org.itri.mii.entity.Event;

/**
 * @author	P-C Lin (a.k.a 高科技黑手)
 */
public class EventRepositoryImpl implements EventRepositoryCustom {

	@Autowired
	private EntityManager entityManager;

	@Override
	public Collection<Event> findTop4Displayable() {
		@SuppressWarnings("JPQLValidation")
		TypedQuery<Event> typedQuery = entityManager.createNamedQuery("Event.findDisplayable", Event.class);
		return typedQuery.setMaxResults(4).getResultList();
	}
}
