package tw.org.itri.mii.entity;

import java.util.Date;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * 行政區
 *
 * @author	P-C Lin (a.k.a 高科技黑手)
 */
@StaticMetamodel(News.class)
public class News_ {

	public static volatile SingularAttribute<News, Short> id;

	public static volatile SingularAttribute<News, String> title;

	public static volatile SingularAttribute<News, String> content;

	public static volatile SingularAttribute<News, Date> createdOn;

	public static volatile SingularAttribute<News, Date> since;

	public static volatile SingularAttribute<News, Date> until;

	public static volatile SingularAttribute<News, Boolean> censored;
}
