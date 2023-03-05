package tw.org.itri.mii.entity;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * 手風琴
 *
 * @author	P-C Lin (a.k.a 高科技黑手)
 */
@StaticMetamodel(Accordion.class)
public class Accordion_ {

	public static volatile SingularAttribute<Accordion, Short> id;

	public static volatile SingularAttribute<Accordion, String> name;

	public static volatile SingularAttribute<Accordion, Short> ordinal;
}
