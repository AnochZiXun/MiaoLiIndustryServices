package tw.org.itri.mii.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tw.org.itri.mii.entity.Individual;

/**
 * @author	P-C Lin (a.k.a 高科技黑手)
 */
@Repository
public interface IndividualRepository extends JpaRepository<Individual, Short>, JpaSpecificationExecutor<Individual> {

	/**
	 * @param email 電子郵件
	 * @return 符合「電子郵件」的數量
	 */
	public long countByEmail(@Param("email") String email);

	/**
	 * @param email 電子郵件
	 * @param id 主鍵
	 * @return 符合「電子郵件」但不符合「主鍵」的數量
	 */
	public long countByEmailAndIdNot(@Param("email") String email, @Param("id") Short id);

	/**
	 * @param email 電子郵件(帳號)
	 * @param shadow 密碼
	 * @return 人員
	 */
	public Individual findOneByEmailAndShadow(@Param("email") String email, @Param("shadow") String shadow);
}
