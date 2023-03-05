package tw.org.itri.mii.entity;

import javax.persistence.*;

/**
 * 權限
 *
 * @author	P-C Lin (a.k.a 高科技黑手)
 */
@Entity
@NamedQueries({
	@NamedQuery(name = "Privilege.findAll", query = "SELECT p FROM Privilege p")
})
@Table(catalog = "\"MiiDB\"", schema = "\"public\"", name = "\"Privilege\"", uniqueConstraints = {
	@javax.persistence.UniqueConstraint(columnNames = {"\"route\"", "\"clan\""})
})
public class Privilege implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "\"id\"", nullable = false)
	private Short id;

	@JoinColumn(name = "\"route\"", referencedColumnName = "\"id\"", nullable = false)
	@ManyToOne(optional = false)
	private Route route;

	@JoinColumn(name = "\"clan\"", referencedColumnName = "\"id\"", nullable = false)
	@ManyToOne(optional = false)
	private Clan clan;

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Privilege)) {
			return false;
		}
		Privilege other = (Privilege) object;
		return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
	}

	@Override
	public String toString() {
		return "tw.org.itri.mii.entity.Privilege[ id=" + id + " ]";
	}

	/**
	 * 建構子
	 */
	public Privilege() {
	}

	/**
	 * @param id 主鍵
	 */
	protected Privilege(Short id) {
		this.id = id;
	}

	/**
	 * @param route 路由
	 * @param clan 群組
	 */
	public Privilege(Route route, Clan clan) {
		this.route = route;
		this.clan = clan;
	}

	/**
	 * @return 主鍵
	 */
	public Short getId() {
		return id;
	}

	/**
	 * @param id 主鍵
	 */
	public void setId(Short id) {
		this.id = id;
	}

	/**
	 * @return 路由
	 */
	public Route getRoute() {
		return route;
	}

	/**
	 * @param route 路由
	 */
	public void setRoute(Route route) {
		this.route = route;
	}

	/**
	 * @return 群組
	 */
	public Clan getClan() {
		return clan;
	}

	/**
	 * @param clan 群組
	 */
	public void setClan(Clan clan) {
		this.clan = clan;
	}
}
