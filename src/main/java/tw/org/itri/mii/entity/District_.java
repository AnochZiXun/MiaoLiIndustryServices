package tw.org.itri.mii.entity;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * 行政區
 *
 * @author	P-C Lin (a.k.a 高科技黑手)
 */
@StaticMetamodel(District.class)
public class District_ {

	public static volatile SingularAttribute<District, Short> id;

	public static volatile SingularAttribute<District, District> upperLevel;

	public static volatile SingularAttribute<District, String> name;
}
