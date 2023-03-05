package tw.org.itri.mii.repository;

import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import tw.org.itri.mii.entity.District;

/**
 * @author	P-C Lin (a.k.a 高科技黑手)
 */
@Repository
public interface DistrictRepository extends JpaRepository<District, Short>, JpaSpecificationExecutor<District> {

	/**
	 * @return 第一或第二級行政區
	 */
	public Collection<District> findByUpperLevelNull();
}
