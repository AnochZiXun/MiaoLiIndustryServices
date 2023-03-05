package tw.org.itri.mii.repository;

import java.util.Collection;
import tw.org.itri.mii.entity.Ally;

/**
 * @author	P-C Lin (a.k.a 高科技黑手)
 */
public interface AllyRepositoryCustom {

	/**
	 * @return 前十五筆可顯示於前臺首頁的友站連結
	 */
	public Collection<Ally> findTop15Displayable();
}
