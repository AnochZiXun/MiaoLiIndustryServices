package tw.org.itri.mii.entity;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * 手風琴
 *
 * @author	P-C Lin (a.k.a 高科技黑手)
 */
@StaticMetamodel(Route.class)
public class Route_ {

	public static volatile SingularAttribute<Route, Short> id;

	public static volatile SingularAttribute<Route, Accordion> accordion;

	public static volatile SingularAttribute<Route, String> label;

	public static volatile SingularAttribute<Route, String> hyerlink;

	public static volatile SingularAttribute<Route, Short> ordinal;
}
