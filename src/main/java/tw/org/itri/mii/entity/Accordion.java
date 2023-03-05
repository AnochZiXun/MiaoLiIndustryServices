package tw.org.itri.mii.entity;

import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * 手風琴
 *
 * @author	P-C Lin (a.k.a 高科技黑手)
 */
@Entity
@NamedQueries({
	@NamedQuery(name = "Accordion.findAll", query = "SELECT a FROM Accordion a")
})
@Table(catalog = "\"MiiDB\"", schema = "\"public\"", name = "\"Accordion\"", uniqueConstraints = {
	@UniqueConstraint(columnNames = {"\"name\""}),
	@UniqueConstraint(columnNames = {"\"ordinal\""})
})
public class Accordion implements java.io.Serializable {

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
		if (!(object instanceof Accordion)) {
			return false;
		}
		Accordion other = (Accordion) object;
		return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
	}

	@Override
	public String toString() {
		return "tw.org.itri.mii.entity.Accordion[ id=" + id + " ]";
	}

	/**
	 * 建構子
	 */
	public Accordion() {
	}

	/**
	 * @param id 主鍵
	 */
	protected Accordion(Short id) {
		this.id = id;
	}

	/**
	 * @param name 手風琴名稱
	 * @param ordinal 排序
	 */
	public Accordion(String name, short ordinal) {
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
	 * @return 手風琴名稱
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name 手風琴名稱
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
