package tw.org.itri.mii.entity;

import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * 活動花絮
 *
 * @author	P-C Lin (a.k.a 高科技黑手)
 */
@Entity
@NamedQueries({
	@NamedQuery(name = "Event.findAll", query = "SELECT e FROM Event e"),
	@NamedQuery(name = "Event.findDisplayable", query = "SELECT e FROM Event e WHERE CURRENT_DATE>=e.since AND CURRENT_DATE<=e.until AND e.censored=TRUE ORDER BY e.since DESC")
})
@SuppressWarnings("JPQLValidation")
@Table(catalog = "\"MiiDB\"", schema = "\"public\"", name = "\"Event\"")
public class Event implements java.io.Serializable {

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
	@Column(name = "\"content\"", length = 2147483647)
	private String content;

	@Basic(optional = false)
	@NotNull
	@Column(name = "\"createdOn\"", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdOn;

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

	@Basic(optional = false)
	@Column(name = "\"image\"", nullable = false)
	@Lob
	private byte[] image;

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Event)) {
			return false;
		}
		Event other = (Event) object;
		return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
	}

	@Override
	public String toString() {
		return "tw.org.itri.mii.entity.Event[ id=" + id + " ]";
	}

	/**
	 * 建構子
	 */
	public Event() {
		this.createdOn = tw.org.itri.mii.Utils.DEFAULT_CALENDAR.getTime();
		this.censored = false;
	}

	/**
	 * @param id 主鍵
	 */
	protected Event(Short id) {
		this.id = id;
	}

	/**
	 * @param title 標題
	 * @param createdOn 建立時戳
	 * @param since 上架日期
	 * @param until 下架日期
	 * @param censored 發佈審查
	 */
	public Event(String title, Date createdOn, Date since, Date until, boolean censored) {
		this.title = title;
		this.createdOn = createdOn;
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
	 * @return 內容
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content 內容
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return 建立時戳
	 */
	public Date getCreatedOn() {
		return createdOn;
	}

	/**
	 * @param createdOn 建立時戳
	 */
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
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

	/**
	 * @return 圖片
	 */
	public byte[] getImage() {
		return image;
	}

	/**
	 * @param image 圖片
	 */
	public void setImage(byte[] image) {
		this.image = image;
	}
}
