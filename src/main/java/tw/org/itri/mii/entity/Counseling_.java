package tw.org.itri.mii.entity;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * 輔導案例
 *
 * @author	P-C Lin (a.k.a 高科技黑手)
 */
@StaticMetamodel(Counseling.class)
public class Counseling_ {

	public static volatile SingularAttribute<Counseling, Short> id;

	public static volatile SingularAttribute<Counseling, Short> taiwaneseYear;

	public static volatile SingularAttribute<Counseling, String> name;

	public static volatile SingularAttribute<Counseling, Short> vendor;

	public static volatile SingularAttribute<Counseling, Integer> amount;

	public static volatile SingularAttribute<Counseling, String> condition;

	public static volatile SingularAttribute<Counseling, String> items;

	public static volatile SingularAttribute<Counseling, String> performance;

	public static volatile SingularAttribute<Counseling, Boolean> closed;
}
