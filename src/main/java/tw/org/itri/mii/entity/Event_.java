package tw.org.itri.mii.entity;

import java.util.Date;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * 行政區
 *
 * @author	P-C Lin (a.k.a 高科技黑手)
 */
@StaticMetamodel(Event.class)
public class Event_ {

	public static volatile SingularAttribute<Event, Short> id;

	public static volatile SingularAttribute<Event, String> title;

	public static volatile SingularAttribute<Event, String> content;

	public static volatile SingularAttribute<Event, Date> createdOn;

	public static volatile SingularAttribute<Event, Date> since;

	public static volatile SingularAttribute<Event, Date> until;

	public static volatile SingularAttribute<Event, Boolean> censored;
}
