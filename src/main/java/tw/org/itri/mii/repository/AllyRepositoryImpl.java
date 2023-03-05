package tw.org.itri.mii.repository;

import java.util.Collection;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import tw.org.itri.mii.entity.Ally;

/**
 * @author	P-C Lin (a.k.a 高科技黑手)
 */
public class AllyRepositoryImpl implements AllyRepositoryCustom {

	@Autowired
	private EntityManager entityManager;

	@Override
	public Collection<Ally> findTop15Displayable() {
		//TypedQuery<Ally> typedQuery = (TypedQuery<Ally>) entityManager.createNativeQuery("SELECT*FROM\"Ally\"WHERE CURRENT_DATE>=\"since\"AND CURRENT_DATE<=\"until\"AND\"censored\"=TRUE ORDER BY\"random\"()LIMIT 15", Ally.class);
		TypedQuery<Ally> typedQuery = (TypedQuery<Ally>) entityManager.createNativeQuery("SELECT*FROM\"Ally\"WHERE CURRENT_DATE>=\"since\"AND CURRENT_DATE<=\"until\"AND\"censored\"=TRUE ORDER BY\"since\"LIMIT 15", Ally.class);
		return typedQuery.getResultList();
	}
}
