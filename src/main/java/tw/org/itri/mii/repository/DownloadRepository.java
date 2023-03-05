package tw.org.itri.mii.repository;

import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tw.org.itri.mii.entity.Download;

/**
 * @author	P-C Lin (a.k.a 高科技黑手)
 */
@Repository
public interface DownloadRepository extends JpaRepository<Download, Short>, JpaSpecificationExecutor<Download> {

	/**
	 * @param filename 原始檔名
	 * @return 符合原始檔名的下載
	 */
	public long countByFilename(@Param("filename") String filename);

	/**
	 * @param filename 原始檔名
	 * @param id 主鍵
	 * @return 符合原始檔名但非主鍵的下載
	 */
	public long countByFilenameAndIdNot(@Param("filename") String filename, @Param("id") Short id);

	/**
	 * @param filename 原始檔名
	 * @return 下載
	 */
	public Download findOneByFilename(@Param("filename") String filename);

	/**
	 * @return 可顯示於前臺的下載
	 */
	@Query(name = "Download.findDisplayable")
	public Collection<Download> findDisplayable();
}
