package tw.org.itri.mii.entity;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * 廠商
 *
 * @author	P-C Lin (a.k.a 高科技黑手)
 */
@StaticMetamodel(Vendor.class)
public class Vendor_ {

	public static volatile SingularAttribute<Vendor, Short> id;

	public static volatile SingularAttribute<Vendor, String> name;

	public static volatile SingularAttribute<Vendor, String> unifiedBusinessNumber;

	public static volatile SingularAttribute<Vendor, String> address;

	public static volatile SingularAttribute<Vendor, IndustrialDistrict> industrialDistrict;

	public static volatile SingularAttribute<Vendor, Short> employeeCount;

	public static volatile SingularAttribute<Vendor, String> contactTitle;

	public static volatile SingularAttribute<Vendor, String> contactName;

	public static volatile SingularAttribute<Vendor, String> contactNumber;

	public static volatile SingularAttribute<Vendor, String> contactEmail;

	public static volatile SingularAttribute<Vendor, String> contactFax;

	public static volatile SingularAttribute<Vendor, Integer> capital;

	public static volatile SingularAttribute<Vendor, Integer> turnover;

	public static volatile SingularAttribute<Vendor, String> business;

	public static volatile SingularAttribute<Vendor, String> shadow;
}
