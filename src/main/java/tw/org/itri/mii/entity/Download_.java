package tw.org.itri.mii.entity;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * 下載專區
 *
 * @author	P-C Lin (a.k.a 高科技黑手)
 */
@StaticMetamodel(Ally.class)
public class Download_ {

	public static volatile SingularAttribute<Download, Short> id;

	public static volatile SingularAttribute<Download, String> filename;
}
