package tw.org.itri.mii.entity;

import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * 聯絡我們
 *
 * @author	P-C Lin (a.k.a 高科技黑手)
 */
@Entity
@NamedQueries({
	@NamedQuery(name = "Reach.findAll", query = "SELECT r FROM Reach r")
})
@Table(catalog = "\"MiiDB\"", schema = "\"public\"", name = "\"Reach\"")
public class Reach implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "\"id\"", nullable = false)
	private Integer id;

	@Size(max = 2147483647)
	@Column(name = "\"company\"", length = 2147483647)
	private String company;

	@Size(max = 2147483647)
	@Column(name = "\"name\"", length = 2147483647)
	private String name;

	@Size(max = 2147483647)
	@Column(name = "\"title\"", length = 2147483647)
	private String title;

	@Size(max = 2147483647)
	@Column(name = "\"phone\"", length = 2147483647)
	private String phone;

	@Pattern(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message = "Invalid email")
	@Size(max = 2147483647)
	@Column(name = "\"email\"", length = 2147483647)
	private String email;

	@Size(max = 2147483647)
	@Column(name = "\"address\"", length = 2147483647)
	private String address;

	@Size(max = 2147483647)
	@Column(name = "\"content\"", length = 2147483647)
	private String content;

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Reach)) {
			return false;
		}
		Reach other = (Reach) object;
		return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
	}

	@Override
	public String toString() {
		return "tw.org.itri.mii.entity.Reach[ id=" + id + " ]";
	}

	/**
	 * 建構子
	 */
	public Reach() {
	}

	/**
	 * @param id 主鍵
	 */
	protected Reach(Integer id) {
		this.id = id;
	}

	/**
	 * @return 主鍵
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id 主鍵
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return 公司名稱
	 */
	public String getCompany() {
		return company;
	}

	/**
	 * @param company 公司名稱
	 */
	public void setCompany(String company) {
		this.company = company;
	}

	/**
	 * @return 大名
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name 大名
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return 職稱
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title 職稱
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
	 * @return 電子郵件
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email 電子郵件
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return 地址
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address 地址
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return 諮詢或發問的內容
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content 諮詢或發問的內容
	 */
	public void setContent(String content) {
		this.content = content;
	}
}
