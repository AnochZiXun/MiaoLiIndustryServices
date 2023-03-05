package tw.org.itri.mii.entity;

import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * 廠商
 *
 * @author	P-C Lin (a.k.a 高科技黑手)
 */
@Entity
@NamedQueries({
	@NamedQuery(name = "Vendor.findAll", query = "SELECT v FROM Vendor v")
})
@Table(catalog = "\"MiiDB\"", schema = "\"public\"", name = "\"Vendor\"", uniqueConstraints = {
	@UniqueConstraint(columnNames = {"\"unifiedBusinessNumber\""})
})
public class Vendor implements java.io.Serializable {

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

	@Size(max = 2147483647)
	@Column(name = "\"unifiedBusinessNumber\"", length = 2147483647)
	private String unifiedBusinessNumber;

	@Size(max = 2147483647)
	@Column(name = "\"address\"", length = 2147483647)
	private String address;

	@JoinColumn(name = "\"industrialDistrict\"", referencedColumnName = "\"id\"")
	@ManyToOne
	private IndustrialDistrict industrialDistrict;

	@Column(name = "\"employeeCount\"")
	private Short employeeCount;

	@Size(max = 2147483647)
	@Column(name = "\"contactTitle\"", length = 2147483647)
	private String contactTitle;

	@Size(max = 2147483647)
	@Column(name = "\"contactName\"", length = 2147483647)
	private String contactName;

	@Size(max = 2147483647)
	@Column(name = "\"contactNumber\"", length = 2147483647)
	private String contactNumber;

	@Size(max = 2147483647)
	@Column(name = "\"contactEmail\"", length = 2147483647)
	private String contactEmail;

	@Size(max = 2147483647)
	@Column(name = "\"contactFax\"", length = 2147483647)
	private String contactFax;

	@Column(name = "\"capital\"")
	private Integer capital;

	@Column(name = "\"turnover\"")
	private Integer turnover;

	@Size(max = 2147483647)
	@Column(name = "\"business\"", length = 2147483647)
	private String business;

	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 2147483647)
	@Column(name = "\"shadow\"", nullable = false, length = 2147483647)
	private String shadow;

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Vendor)) {
			return false;
		}
		Vendor other = (Vendor) object;
		return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
	}

	@Override
	public String toString() {
		return "tw.org.itri.mii.entity.Vendor[ id=" + id + " ]";
	}

	/**
	 * 建構子
	 */
	public Vendor() {
	}

	/**
	 * @param id 主鍵
	 */
	protected Vendor(Short id) {
		this.id = id;
	}

	/**
	 * @param name 廠商名稱
	 */
	public Vendor(String name) {
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
	 * @return 廠商名稱
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name 廠商名稱
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return 統一編號
	 */
	public String getUnifiedBusinessNumber() {
		return unifiedBusinessNumber;
	}

	/**
	 * @param unifiedBusinessNumber 統一編號
	 */
	public void setUnifiedBusinessNumber(String unifiedBusinessNumber) {
		this.unifiedBusinessNumber = unifiedBusinessNumber;
	}

	/**
	 * @return 廠商地址
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address 廠商地址
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return 工業區
	 */
	public IndustrialDistrict getIndustrialDistrict() {
		return industrialDistrict;
	}

	/**
	 * @param industrialDistrict 工業區
	 */
	public void setIndustrialDistrict(IndustrialDistrict industrialDistrict) {
		this.industrialDistrict = industrialDistrict;
	}

	/**
	 * @return 員工人數
	 */
	public Short getEmployeeCount() {
		return employeeCount;
	}

	/**
	 * @param employeeCount 員工人數
	 */
	public void setEmployeeCount(Short employeeCount) {
		this.employeeCount = employeeCount;
	}

	/**
	 * @return 聯絡人職稱
	 */
	public String getContactTitle() {
		return contactTitle;
	}

	/**
	 * @param contactTitle 聯絡人職稱
	 */
	public void setContactTitle(String contactTitle) {
		this.contactTitle = contactTitle;
	}

	/**
	 * @return 聯絡人姓名
	 */
	public String getContactName() {
		return contactName;
	}

	/**
	 * @param contactName 聯絡人姓名
	 */
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	/**
	 * @return 聯絡人電話
	 */
	public String getContactNumber() {
		return contactNumber;
	}

	/**
	 * @param contactNumber 聯絡人電話
	 */
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	/**
	 * @return 聯絡人電子郵件
	 */
	public String getContactEmail() {
		return contactEmail;
	}

	/**
	 * @param contactEmail 聯絡人電子郵件
	 */
	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	/**
	 * @return 聯絡人傳真
	 */
	public String getContactFax() {
		return contactFax;
	}

	/**
	 * @param contactFax 聯絡人傳真
	 */
	public void setContactFax(String contactFax) {
		this.contactFax = contactFax;
	}

	/**
	 * @return 資本額
	 */
	public Integer getCapital() {
		return capital;
	}

	/**
	 * @param capital 資本額
	 */
	public void setCapital(Integer capital) {
		this.capital = capital;
	}

	/**
	 * @return 營業額
	 */
	public Integer getTurnover() {
		return turnover;
	}

	/**
	 * @param turnover 營業額
	 */
	public void setTurnover(Integer turnover) {
		this.turnover = turnover;
	}

	/**
	 * @return 營業項目
	 */
	public String getBusiness() {
		return business;
	}

	/**
	 * @param business 營業項目
	 */
	public void setBusiness(String business) {
		this.business = business;
	}

	/**
	 * @return 密碼
	 */
	public String getShadow() {
		return shadow;
	}

	/**
	 * @param shadow 密碼
	 */
	public void setShadow(String shadow) {
		this.shadow = shadow;
	}
}
