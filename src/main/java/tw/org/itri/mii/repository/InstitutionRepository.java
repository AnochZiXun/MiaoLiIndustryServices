package tw.org.itri.mii.repository;

import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tw.org.itri.mii.entity.District;
import tw.org.itri.mii.entity.Institution;

/**
 * @author	P-C Lin (a.k.a 高科技黑手)
 */
@Repository
public interface InstitutionRepository extends JpaRepository<Institution, Short>, JpaSpecificationExecutor<Institution> {

	/**
	 * @param name 單位名稱
	 * @return 符合「單位名稱」的數量
	 */
	public long countByName(@Param("name") String name);

	/**
	 * @param name 單位名稱
	 * @param id 主鍵
	 * @return 符合「單位名稱」但不符合「主鍵」的數量
	 */
	public long countByNameAndIdNot(@Param("name") String name, @Param("id") Short id);

	/**
	 * @param district 單位
	 * @return 位於「行政區」裡的「單位」
	 */
	public Collection<Institution> findByDistrict(@Param("district") District district);
}
