package tw.org.itri.mii.repository;

import java.util.Collection;
import tw.org.itri.mii.entity.News;

/**
 * @author	P-C Lin (a.k.a 高科技黑手)
 */
public interface NewsRepositoryCustom {

	/**
	 * @return 前五筆可顯示於前臺首頁的最新消息
	 */
	public Collection<News> findTop10Displayable();
}
