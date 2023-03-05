package tw.org.itri.mii.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tw.org.itri.mii.entity.AccessLogValve;

/**
 * @author	P-C Lin (a.k.a 高科技黑手)
 */
@Repository
public interface AccessLogValveRepository extends JpaRepository<AccessLogValve, Long> {

	@Query(name = "AccessLogValve.countByQuery")
	public long countByQuery(@Param("query") String query);
}
