package tw.org.itri.mii.repository;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import tw.org.itri.mii.entity.Event;

/**
 * @author	P-C Lin (a.k.a 高科技黑手)
 */
@org.springframework.stereotype.Repository
public interface EventRepository extends JpaRepository<Event, Short>, JpaSpecificationExecutor<Event>, EventRepositoryCustom {

	/**
	 * @return 可顯示於前臺的活動花絮
	 */
	@Query(name = "Event.findDisplayable")
	public Page<Event> findDisplayable(Pageable pageRequest);
}
