package tw.org.itri.mii.specification;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import tw.org.itri.mii.entity.Counseling;
import tw.org.itri.mii.entity.Counseling_;

/**
 * @author	P-C Lin (a.k.a 高科技黑手)
 */
public class CounselingSpecs {

	/**
	 * @param name 部份或全部的字串
	 * @return 搜尋規格
	 */
	public static Specification<Counseling> threeSpecs(final Short taiwaneseYear, final String name, final Boolean closed) {
		return new Specification<Counseling>() {
			@Override
			public Predicate toPredicate(Root<Counseling> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				if (taiwaneseYear != null) {
					predicates.add(criteriaBuilder.equal(root.<Short>get(Counseling_.taiwaneseYear), taiwaneseYear));
				}
				if (name != null) {
					predicates.add(criteriaBuilder.like(root.<String>get(Counseling_.name), "%".concat(name).concat("%")));
				}
				if (closed != null) {
					predicates.add(criteriaBuilder.equal(root.<Boolean>get(Counseling_.closed), closed));
				}
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		};
	}
}
