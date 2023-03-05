package tw.org.itri.mii.entity;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * 單位
 *
 * @author	P-C Lin (a.k.a 高科技黑手)
 */
@StaticMetamodel(Institution.class)
public class Institution_ {

	public static volatile SingularAttribute<Institution, Short> id;

	public static volatile SingularAttribute<Institution, District> district;

	public static volatile SingularAttribute<Institution, String> name;
}
