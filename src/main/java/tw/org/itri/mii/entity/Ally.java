package tw.org.itri.mii.entity;

import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * 友站連結
 *
 * @author	P-C Lin (a.k.a 高科技黑手)
 */
@Entity
@NamedQueries({
	@NamedQuery(name = "Ally.findAll", query = "SELECT a FROM Ally a"),
	@NamedQuery(name = "Ally.findDisplayable", query = "SELECT a FROM Ally a WHERE CURRENT_DATE>=a.since AND CURRENT_DATE<=a.until AND a.censored=TRUE ORDER BY a.since ASC")
})
@SuppressWarnings("JPQLValidation")
@Table(catalog = "\"MiiDB\"", schema = "\"public\"", name = "\"Ally\"")
public class Ally implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "\"id\"", nullable = false)
	private Short id;

	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 2147483647)
	@Column(name = "\"title\"", nullable = false, length = 2147483647)
	private String title;

	@Size(max = 2147483647)
	@Column(name = "\"phone\"", length = 2147483647)
	private String phone;

	@Basic(optional = false)
	@Column(name = "\"image\"", nullable = false)
	@Lob
	private byte[] image;

	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 2147483647)
	@Column(name = "\"fqdn\"", nullable = false)
	private String fqdn;

	@Basic(optional = false)
	@NotNull
	@Column(name = "\"since\"", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date since;

	@Basic(optional = false)
	@NotNull
	@Column(name = "\"until\"", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date until;

	@Basic(optional = false)
	@NotNull
	@Column(name = "\"censored\"", nullable = false)
	private boolean censored;

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Ally)) {
			return false;
		}
		Ally other = (Ally) object;
		return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
	}

	@Override
	public String toString() {
		return "tw.org.itri.mii.entity.Ally[ id=" + id + " ]";
	}

	/**
	 * 建構子
	 */
	public Ally() {
		this.censored = false;
	}

	/**
	 * @param id 主鍵
	 */
	protected Ally(Short id) {
		this.id = id;
	}

	/**
	 * @param title 標題
	 * @param fqdn 參考網址
	 * @param since 上架日期
	 * @param until 下架日期
	 * @param censored 發佈審查
	 */
	public Ally(String title, String fqdn, Date since, Date until, boolean censored) {
		this.title = title;
		this.fqdn = fqdn;
		this.since = since;
		this.until = until;
		this.censored = censored;
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
	 * @return 標題
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title 標題
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return 聯絡電話
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone 聯絡電話
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return 縮圖
	 */
	public byte[] getImage() {
		return image;
	}

	/**
	 * @param image 縮圖
	 */
	public void setImage(byte[] image) {
		this.image = image;
	}

	/**
	 * @return 參考網址
	 */
	public String getFqdn() {
		return fqdn;
	}

	/**
	 * @param fqdn 參考網址
	 */
	public void setFqdn(String fqdn) {
		this.fqdn = fqdn;
	}

	/**
	 * @return 上架日期
	 */
	public Date getSince() {
		return since;
	}

	/**
	 * @param since 上架日期
	 */
	public void setSince(Date since) {
		this.since = since;
	}

	/**
	 * @return 下架日期
	 */
	public Date getUntil() {
		return until;
	}

	/**
	 * @param until 下架日期
	 */
	public void setUntil(Date until) {
		this.until = until;
	}

	/**
	 * @return 發佈審查
	 */
	public boolean isCensored() {
		return censored;
	}

	/**
	 * @param censored 發佈審查
	 */
	public void setCensored(boolean censored) {
		this.censored = censored;
	}
}
