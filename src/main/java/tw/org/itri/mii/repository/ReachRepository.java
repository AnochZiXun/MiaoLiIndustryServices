package tw.org.itri.mii.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tw.org.itri.mii.entity.Reach;

/**
 * @author	P-C Lin (a.k.a 高科技黑手)
 */
@Repository
public interface ReachRepository extends JpaRepository<Reach, Integer>/*, JpaSpecificationExecutor<Reach>*/ {
}
