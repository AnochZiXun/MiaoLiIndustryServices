package tw.org.itri.mii.entity;

import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * 路由
 *
 * @author	P-C Lin (a.k.a 高科技黑手)
 */
@Entity
@NamedQueries({
	@NamedQuery(name = "Route.findAll", query = "SELECT r FROM Route r")
})
@Table(catalog = "\"MiiDB\"", schema = "\"public\"", name = "\"Route\"", uniqueConstraints = {
	@UniqueConstraint(columnNames = {"\"accordion\"", "\"ordinal\""}),
	@UniqueConstraint(columnNames = {"label\""}),
	@UniqueConstraint(columnNames = {"\"hyperlink\""}),
	@UniqueConstraint(columnNames = {"\"pattern\""})
})
public class Route implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "\"id\"", nullable = false)
	private Short id;

	@JoinColumn(name = "\"accordion\"", referencedColumnName = "\"id\"", nullable = false)
	@ManyToOne(optional = false)
	private Accordion accordion;

	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 2147483647)
	@Column(name = "\"label\"", nullable = false, length = 2147483647)
	private String label;

	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 2147483647)
	@Column(name = "\"hyperlink\"", nullable = false, length = 2147483647)
	private String hyperlink;

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
		if (!(object instanceof Route)) {
			return false;
		}
		Route other = (Route) object;
		return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
	}

	@Override
	public String toString() {
		return "tw.org.itri.mii.entity.Route[ id=" + id + " ]";
	}

	/**
	 * 建構子
	 */
	public Route() {
	}

	/**
	 * @param id 主鍵
	 */
	protected Route(Short id) {
		this.id = id;
	}

	/**
	 * @param accordion 手風琴
	 * @param label 顯示名稱
	 * @param hyperlink 連結
	 * @param ordinal 排序
	 */
	public Route(Accordion accordion, String label, String hyperlink, short ordinal) {
		this.accordion = accordion;
		this.label = label;
		this.hyperlink = hyperlink;
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
	 * @return 手風琴
	 */
	public Accordion getAccordion() {
		return accordion;
	}

	/**
	 * @param accordion 手風琴
	 */
	public void setAccordion(Accordion accordion) {
		this.accordion = accordion;
	}

	/**
	 * @return 顯示名稱
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @param label 顯示名稱
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * @return 連結
	 */
	public String getHyperlink() {
		return hyperlink;
	}

	/**
	 * @param hyperlink 連結
	 */
	public void setHyperlink(String hyperlink) {
		this.hyperlink = hyperlink;
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
