package tw.org.itri.mii.repository;

import java.util.Collection;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import tw.org.itri.mii.entity.News;

/**
 * @author	P-C Lin (a.k.a 高科技黑手)
 */
public class NewsRepositoryImpl implements NewsRepositoryCustom {

	@Autowired
	private EntityManager entityManager;

	@Override
	public Collection<News> findTop10Displayable() {
		@SuppressWarnings("JPQLValidation")
		TypedQuery<News> typedQuery = entityManager.createNamedQuery("News.findDisplayable", News.class);
		return typedQuery.setMaxResults(10).getResultList();
	}
}
