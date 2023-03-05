package tw.org.itri.mii.entity;

import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * 行政區劃
 *
 * @author	P-C Lin (a.k.a 高科技黑手)
 */
@Entity
@NamedQueries({
	@NamedQuery(name = "District.findAll", query = "SELECT d FROM District d")
})
@Table(catalog = "\"MiiDB\"", schema = "\"public\"", name = "\"District\"", uniqueConstraints = {
	@UniqueConstraint(columnNames = {"\"upperLevel\"", "\"name\""})
})
public class District implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "\"id\"", nullable = false)
	private Short id;

	@JoinColumn(name = "\"upperLevel\"", referencedColumnName = "\"id\"")
	@ManyToOne
	private District upperLevel;

	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 2147483647)
	@Column(name = "\"name\"", nullable = false, length = 2147483647)
	private String name;

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof District)) {
			return false;
		}
		District other = (District) object;
		return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
	}

	@Override
	public String toString() {
		return "tw.org.itri.mii.entity.District[ id=" + id + " ]";
	}

	/**
	 * 建構子
	 */
	public District() {
	}

	/**
	 * @param id 主鍵
	 */
	protected District(Short id) {
		this.id = id;
	}

	/**
	 * @param name 行政區名稱
	 */
	public District(String name) {
		this.name = name;
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
	 * @return 上層行政區
	 */
	public District getUpperLevel() {
		return upperLevel;
	}

	/**
	 * @param upperLevel 上層行政區
	 */
	public void setUpperLevel(District upperLevel) {
		this.upperLevel = upperLevel;
	}

	/**
	 * @return 行政區名稱
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name 行政區名稱
	 */
	public void setName(String name) {
		this.name = name;
	}
}
