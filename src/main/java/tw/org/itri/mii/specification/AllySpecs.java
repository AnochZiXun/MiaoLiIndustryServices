package tw.org.itri.mii.specification;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import tw.org.itri.mii.entity.Ally;
import tw.org.itri.mii.entity.Ally_;

/**
 * @author	P-C Lin (a.k.a 高科技黑手)
 */
public class AllySpecs {

	/**
	 * @param title 部份或全部的字串
	 * @return 搜尋規格
	 */
	public static Specification<Ally> titleSpec(final String title) {
		return new Specification<Ally>() {
			@Override
			public Predicate toPredicate(Root<Ally> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				predicates.add(criteriaBuilder.like(root.<String>get(Ally_.title), "%".concat(title).concat("%")));
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		};
	}
}
