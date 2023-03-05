package tw.org.itri.mii.entity;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * 工業區
 *
 * @author	P-C Lin (a.k.a 高科技黑手)
 */
@StaticMetamodel(IndustrialDistrict.class)
public class IndustrialDistrict_ {

	public static volatile SingularAttribute<IndustrialDistrict, Short> id;

	public static volatile SingularAttribute<IndustrialDistrict, District> district;

	public static volatile SingularAttribute<IndustrialDistrict, String> name;
}
