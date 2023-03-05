package tw.org.itri.mii.repository;

import java.util.Collection;
import tw.org.itri.mii.entity.Event;

/**
 * @author	P-C Lin (a.k.a 高科技黑手)
 */
public interface EventRepositoryCustom {

	/**
	 * @return 前四筆可顯示於前臺首頁的活動花絮
	 */
	public Collection<Event> findTop4Displayable();
}
