package tw.org.itri.mii.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import tw.org.itri.mii.entity.Counseling;

/**
 * @author	P-C Lin (a.k.a 高科技黑手)
 */
@Repository
public interface CounselingRepository extends JpaRepository<Counseling, Short>, JpaSpecificationExecutor<Counseling> {
}
