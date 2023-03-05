package tw.org.itri.mii.entity;

import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * 存取記錄
 *
 * @author	P-C Lin (a.k.a 高科技黑手)
 */
@Entity
@NamedQueries({
	@NamedQuery(name = "AccessLogValve.findAll", query = "SELECT a FROM AccessLogValve a"),
	@NamedQuery(name = "AccessLogValve.countByQuery", query = "SELECT COUNT(a.id) FROM AccessLogValve a WHERE a.query = :query")
})
@Table(catalog = "\"MiiDB\"", schema = "\"public\"", name = "\"AccessLogValve\"")
public class AccessLogValve implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "\"id\"", nullable = false)
	private Long id;

	@Size(max = 2147483647)
	@Column(name = "\"remoteHost\"", length = 2147483647)
	private String remoteHost;

	@Size(max = 2147483647)
	@Column(name = "\"userName\"", length = 2147483647)
	private String userName;

	@Basic(optional = false)
	@NotNull
	@Column(name = "\"timestamp\"", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;

	@Size(max = 2147483647)
	@Column(name = "\"virtualHost\"", length = 2147483647)
	private String virtualHost;

	@Size(max = 2147483647)
	@Column(name = "\"method\"", length = 2147483647)
	private String method;

	@Size(max = 2147483647)
	@Column(name = "\"query\"", length = 2147483647)
	private String query;

	@Column(name = "\"status\"")
	private Short status;

	@Basic(optional = false)
	@NotNull
	@Column(name = "\"bytes\"", nullable = false)
	private long bytes;

	@Size(max = 2147483647)
	@Column(name = "\"referer\"", length = 2147483647)
	private String referer;

	@Size(max = 2147483647)
	@Column(name = "\"userAgent\"", length = 2147483647)
	private String userAgent;

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof AccessLogValve)) {
			return false;
		}
		AccessLogValve other = (AccessLogValve) object;
		return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
	}

	@Override
	public String toString() {
		return "tw.org.itri.mii.entity.AccessLogValve[ id=" + id + " ]";
	}

	/**
	 * 建構子
	 */
	public AccessLogValve() {
	}

	/**
	 * @param id 主鍵
	 */
	protected AccessLogValve(Long id) {
		this.id = id;
	}

	/**
	 * @param timestamp server-determined timestamp
	 * @param bytes number of bytes returned
	 */
	public AccessLogValve(Date timestamp, long bytes) {
		this.timestamp = timestamp;
		this.bytes = bytes;
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
	 * @return remote host
	 */
	public String getRemoteHost() {
		return remoteHost;
	}

	/**
	 * @param remoteHost remote host
	 */
	public void setRemoteHost(String remoteHost) {
		this.remoteHost = remoteHost;
	}

	/**
	 * @return remote user name
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName remote user name
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return server-determined timestamp
	 */
	public Date getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp server-determined timestamp
	 */
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * @return virtual host information (this is in fact the server name)
	 */
	public String getVirtualHost() {
		return virtualHost;
	}

	/**
	 * @param virtualHost virtual host information (this is in fact the
	 * server name)
	 */
	public void setVirtualHost(String virtualHost) {
		this.virtualHost = virtualHost;
	}

	/**
	 * @return HTTP request method
	 */
	public String getMethod() {
		return method;
	}

	/**
	 * @param method HTTP request method
	 */
	public void setMethod(String method) {
		this.method = method;
	}

	/**
	 * @return URL part of the HTTP query
	 */
	public String getQuery() {
		return query;
	}

	/**
	 * @param query URL part of the HTTP query
	 */
	public void setQuery(String query) {
		this.query = query;
	}

	/**
	 * @return HTTP response status code
	 */
	public Short getStatus() {
		return status;
	}

	/**
	 * @param status HTTP response status code
	 */
	public void setStatus(Short status) {
		this.status = status;
	}

	/**
	 * @return number of bytes returned
	 */
	public long getBytes() {
		return bytes;
	}

	/**
	 * @param bytes number of bytes returned
	 */
	public void setBytes(long bytes) {
		this.bytes = bytes;
	}

	/**
	 * @return referer
	 */
	public String getReferer() {
		return referer;
	}

	/**
	 * @param referer referer
	 */
	public void setReferer(String referer) {
		this.referer = referer;
	}

	/**
	 * @return user agent
	 */
	public String getUserAgent() {
		return userAgent;
	}

	/**
	 * @param userAgent user agent
	 */
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}
}
