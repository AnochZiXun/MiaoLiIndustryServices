package tw.org.itri.mii.repository;

import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tw.org.itri.mii.entity.IndustrialDistrict;
import tw.org.itri.mii.entity.Vendor;

/**
 * @author	P-C Lin (a.k.a 高科技黑手)
 */
@Repository
public interface VendorRepository extends JpaRepository<Vendor, Short>, JpaSpecificationExecutor<Vendor> {

	/**
	 * @param industrialDistrict 工業區
	 * @return 位於「工業區」裡的「廠商」
	 */
	public Collection<Vendor> findByIndustrialDistrict(@Param("industrialDistrict") IndustrialDistrict industrialDistrict);

	/**
	 * @return 不在任何「工業區」裡的「廠商」
	 */
	public Collection<Vendor> findByIndustrialDistrictNull();
}
