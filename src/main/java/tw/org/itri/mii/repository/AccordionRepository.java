package tw.org.itri.mii.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tw.org.itri.mii.entity.Accordion;

/**
 * @author	P-C Lin (a.k.a 高科技黑手)
 */
@Repository
public interface AccordionRepository extends JpaRepository<Accordion, Short>, JpaSpecificationExecutor<Accordion> {

	/**
	 * @param name 手風琴名稱
	 * @return 符合「手風琴名稱」的數量
	 */
	public long countByName(@Param("name") String name);

	/**
	 * @param name 手風琴名稱
	 * @param id 主鍵
	 * @return 符合「手風琴名稱」但不符合「主鍵」的數量
	 */
	public long countByNameAndIdNot(@Param("name") String name, @Param("id") Short id);
}
