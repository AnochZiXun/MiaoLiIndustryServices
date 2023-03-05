package tw.org.itri.mii.entity;

import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * 工業區
 *
 * @author	P-C Lin (a.k.a 高科技黑手)
 */
@Entity
@NamedQueries({
	@NamedQuery(name = "IndustrialDistrict.findAll", query = "SELECT i FROM IndustrialDistrict i")
})
@Table(catalog = "\"MiiDB\"", schema = "\"public\"", name = "\"IndustrialDistrict\"", uniqueConstraints = {
	@UniqueConstraint(columnNames = {"\"district\"", "\"name\""})
})
public class IndustrialDistrict implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "\"id\"", nullable = false)
	private Short id;

	@JoinColumn(name = "\"district\"", referencedColumnName = "\"id\"", nullable = false)
	@ManyToOne(optional = false)
	private District district;

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
		if (!(object instanceof IndustrialDistrict)) {
			return false;
		}
		IndustrialDistrict other = (IndustrialDistrict) object;
		return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
	}

	@Override
	public String toString() {
		return "tw.org.itri.mii.entity.IndustrialDistrict[ id=" + id + " ]";
	}

	/**
	 * 建構子
	 */
	public IndustrialDistrict() {
	}

	/**
	 * @param id 主鍵
	 */
	protected IndustrialDistrict(Short id) {
		this.id = id;
	}

	/**
	 * @param name
	 */
	public IndustrialDistrict(String name) {
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
	 * @return 行政區
	 */
	public District getDistrict() {
		return district;
	}

	/**
	 * @param district 行政區
	 */
	public void setDistrict(District district) {
		this.district = district;
	}

	/**
	 * @return 工業區名稱
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name 工業區名稱
	 */
	public void setName(String name) {
		this.name = name;
	}
}
