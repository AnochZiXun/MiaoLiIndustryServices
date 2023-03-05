package tw.org.itri.mii.repository;

import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tw.org.itri.mii.entity.District;
import tw.org.itri.mii.entity.IndustrialDistrict;

/**
 * @author	P-C Lin (a.k.a 高科技黑手)
 */
@Repository
public interface IndustrialDistrictRepository extends JpaRepository<IndustrialDistrict, Short>, JpaSpecificationExecutor<IndustrialDistrict> {

	/**
	 * @param district 行政區
	 * @param name 工業區名稱
	 * @return
	 */
	public long countByDistrictAndName(@Param("district") District district, @Param("name") String name);

	/**
	 * @param district 行政區
	 * @return 位於「行政區」裡的「工業區」
	 */
	public Collection<IndustrialDistrict> findByDistrict(@Param("district") District district);
}
