package tw.org.itri.mii.repository;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import tw.org.itri.mii.entity.News;

/**
 * @author	P-C Lin (a.k.a 高科技黑手)
 */
@org.springframework.stereotype.Repository
public interface NewsRepository extends JpaRepository<News, Short>, JpaSpecificationExecutor<News>, NewsRepositoryCustom {

	/**
	 * @return 可顯示於前臺的最新消息
	 */
	@Query(name = "News.findDisplayable")
	public Page<News> findDisplayable(Pageable pageRequest);
}
