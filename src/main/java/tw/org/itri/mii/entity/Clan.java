package tw.org.itri.mii.entity;

import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * 群組
 *
 * @author	P-C Lin (a.k.a 高科技黑手)
 */
@Entity
@Table(catalog = "\"MiiDB\"", schema = "\"public\"", name = "\"Clan\"", uniqueConstraints = {
	@UniqueConstraint(columnNames = {"\"name\""}),
	@UniqueConstraint(columnNames = {"\"ordinal\""})
})
@NamedQueries({
	@NamedQuery(name = "Clan.findAll", query = "SELECT c FROM Clan c")
})
public class Clan implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "\"id\"", nullable = false)
	private Short id;

	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 2147483647)
	@Column(name = "\"name\"", nullable = false, length = 2147483647)
	private String name;

	@Basic(optional = false)
	@NotNull
	@Column(name = "\"ordinal\"", nullable = false)
	private short ordinal;

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Clan)) {
			return false;
		}
		Clan other = (Clan) object;
		return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
	}

	@Override
	public String toString() {
		return "tw.org.itri.mii.entity.Clan[ id=" + id + " ]";
	}

	/**
	 * 建構子
	 */
	public Clan() {
	}

	/**
	 * @param id 主鍵
	 */
	protected Clan(Short id) {
		this.id = id;
	}

	/**
	 * @param name 群組名稱
	 * @param ordinal 排序
	 */
	public Clan(String name, short ordinal) {
		this.name = name;
		this.ordinal = ordinal;
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
	 * @return 群組名稱
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name 群組名稱
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return 排序
	 */
	public short getOrdinal() {
		return ordinal;
	}

	/**
	 * @param ordinal 排序
	 */
	public void setOrdinal(short ordinal) {
		this.ordinal = ordinal;
	}
}
