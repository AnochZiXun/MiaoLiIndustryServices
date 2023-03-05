package tw.org.itri.mii.entity;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * 手風琴
 *
 * @author	P-C Lin (a.k.a 高科技黑手)
 */
@StaticMetamodel(Clan.class)
public class Clan_ {

	public static volatile SingularAttribute<Clan, Short> id;

	public static volatile SingularAttribute<Clan, String> name;

	public static volatile SingularAttribute<Clan, Short> ordinal;
}
