package tw.org.itri.mii.entity;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * 友站連結
 *
 * @author	P-C Lin (a.k.a 高科技黑手)
 */
@StaticMetamodel(Ally.class)
public class Ally_ {

	public static volatile SingularAttribute<Ally, Short> id;

	public static volatile SingularAttribute<Ally, String> title;
}
