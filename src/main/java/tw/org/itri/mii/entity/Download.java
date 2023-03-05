package tw.org.itri.mii.entity;

import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * 下載
 *
 * @author	P-C Lin (a.k.a 高科技黑手)
 */
@Entity
@NamedQueries({
	@NamedQuery(name = "Download.findAll", query = "SELECT d FROM Download d"),
	@NamedQuery(name = "Download.findDisplayable", query = "SELECT d FROM Download d WHERE CURRENT_DATE>=d.since AND CURRENT_DATE<=d.until AND d.censored=TRUE ORDER BY d.id DESC")
})
@SuppressWarnings("JPQLValidation")
@Table(catalog = "\"MiiDB\"", schema = "\"public\"", name = "\"Download\"")
public class Download implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "\"id\"", nullable = false)
	private Short id;

	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 2147483647)
	@Column(name = "\"filename\"", nullable = false, length = 2147483647)
	private String filename;

	@Size(max = 2147483647)
	@Column(name = "\"contentType\"", length = 2147483647)
	private String contentType;

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
		if (!(object instanceof Download)) {
			return false;
		}
		Download other = (Download) object;
		return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
	}

	@Override
	public String toString() {
		return "tw.org.itri.mii.entity.Download[ id=" + id + " ]";
	}

	/**
	 * 建構子
	 */
	public Download() {
	}

	/**
	 * @param id 主鍵
	 */
	protected Download(Short id) {
		this.id = id;
	}

	/**
	 * @param filename 原始檔名
	 * @param content 檔案內容
	 * @param since 上架日期
	 * @param until 下架日期
	 * @param censored 發佈審查
	 */
	public Download(String filename, Date since, Date until, boolean censored) {
		this.filename = filename;
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
	 * @return 原始檔名
	 */
	public String getFilename() {
		return filename;
	}

	/**
	 * @param filename 原始檔名
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}

	/**
	 * @return 檔案類型
	 */
	public String getContentType() {
		return contentType;
	}

	/**
	 * @param contentType 檔案類型
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
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
