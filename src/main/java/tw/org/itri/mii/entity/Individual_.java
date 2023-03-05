package tw.org.itri.mii.entity;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * 人員
 *
 * @author	P-C Lin (a.k.a 高科技黑手)
 */
@StaticMetamodel(Individual.class)
public class Individual_ {

	public static volatile SingularAttribute<Individual, Short> id;

	public static volatile SingularAttribute<Individual, Clan> clan;

	public static volatile SingularAttribute<Individual, String> name;

	public static volatile SingularAttribute<Individual, String> email;

	public static volatile SingularAttribute<Individual, String> contactNumber;

	public static volatile SingularAttribute<Individual, String> contactCellular;

	public static volatile SingularAttribute<Individual, String> major;

	public static volatile SingularAttribute<Individual, String> title;

	public static volatile SingularAttribute<Individual, Individual> institution;

	public static volatile SingularAttribute<Individual, String> specialty;

	public static volatile SingularAttribute<Individual, String> experience;

	public static volatile SingularAttribute<Individual, String> shadow;
}
