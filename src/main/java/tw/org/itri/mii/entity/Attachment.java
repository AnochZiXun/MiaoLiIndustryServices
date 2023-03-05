package tw.org.itri.mii.entity;

import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * 附件
 *
 * @author	P-C Lin (a.k.a 高科技黑手)
 */
@Entity
@NamedQueries({
	@NamedQuery(name = "Attachment.findAll", query = "SELECT a FROM Attachment a")
})
@Table(catalog = "\"MiiDB\"", schema = "\"public\"", name = "\"Attachment\"", uniqueConstraints = {
	@UniqueConstraint(columnNames = {"\"news\"", "\"filename\""}),
	@UniqueConstraint(columnNames = {"\"event\"", "\"filename\""})
})
public class Attachment implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "\"id\"", nullable = false)
	private Long id;

	@JoinColumn(name = "\"news\"", referencedColumnName = "\"id\"")
	@ManyToOne
	private News news;

	@JoinColumn(name = "\"event\"", referencedColumnName = "\"id\"")
	@ManyToOne
	private Event event;

	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 2147483647)
	@Column(name = "\"filename\"", nullable = false, length = 2147483647)
	private String filename;

	@Basic(optional = false)
	@NotNull
	@Lob
	@Column(name = "\"content\"", nullable = false)
	private byte[] content;

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Attachment)) {
			return false;
		}
		Attachment other = (Attachment) object;
		return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
	}

	@Override
	public String toString() {
		return "tw.org.itri.mii.entity.Attachment[ id=" + id + " ]";
	}

	/**
	 * 建構子
	 */
	public Attachment() {
	}

	/**
	 * @param id 主鍵
	 */
	protected Attachment(Long id) {
		this.id = id;
	}

	/**
	 * @param filename 原始檔名
	 * @param content 檔案內容
	 */
	public Attachment(String filename, byte[] content) {
		this.filename = filename;
		this.content = content;
	}

	/**
	 * @return 主鍵
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id 主鍵
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return 最新消息
	 */
	public News getNews() {
		return news;
	}

	/**
	 * @param news 最新消息
	 */
	public void setNews(News news) {
		this.news = news;
	}

	/**
	 * @return 活動花絮
	 */
	public Event getEvent() {
		return event;
	}

	/**
	 * @param event 活動花絮
	 */
	public void setEvent(Event event) {
		this.event = event;
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
	 * @return 檔案內容
	 */
	public byte[] getContent() {
		return content;
	}

	/**
	 * @param content 檔案內容
	 */
	public void setContent(byte[] content) {
		this.content = content;
	}
}
