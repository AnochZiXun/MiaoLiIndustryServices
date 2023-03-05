package tw.org.itri.mii.entity;

import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * 輔導案例
 *
 * @author	P-C Lin (a.k.a 高科技黑手)
 */
@Entity
@NamedQueries({
	@NamedQuery(name = "Counseling.findAll", query = "SELECT c FROM Counseling c")
})
@Table(catalog = "\"MiiDB\"", schema = "\"public\"", name = "\"Counseling\"")
public class Counseling implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "\"id\"", nullable = false)
	private Short id;

	@Column(name = "\"taiwaneseYear\"")
	private Short taiwaneseYear;

	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 2147483647)
	@Column(name = "\"name\"", nullable = false, length = 2147483647)
	private String name;

	@JoinColumn(name = "\"vendor\"", referencedColumnName = "\"id\"", nullable = false)
	@ManyToOne(optional = false)
	private Vendor vendor;

	@Column(name = "\"amount\"")
	private Integer amount;

	@Size(max = 2147483647)
	@Column(name = "\"condition\"", length = 2147483647)
	private String condition;

	@Size(max = 2147483647)
	@Column(name = "\"items\"", length = 2147483647)
	private String items;

	@Size(max = 2147483647)
	@Column(name = "\"performance\"", length = 2147483647)
	private String performance;

	@Column(name = "\"photo\"")
	@Lob
	private byte[] photo;

	@Basic(optional = false)
	@NotNull
	@Column(name = "\"closed\"", nullable = false)
	private boolean closed;

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Counseling)) {
			return false;
		}
		Counseling other = (Counseling) object;
		return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
	}

	@Override
	public String toString() {
		return "tw.org.itri.mii.entity.Counseling[ id=" + id + " ]";
	}

	/**
	 * 建構子
	 */
	public Counseling() {
	}

	/**
	 * @param id 主鍵
	 */
	protected Counseling(Short id) {
		this.id = id;
	}

	/**
	 * @param name 計畫名稱
	 * @param closed 案件狀態
	 */
	public Counseling(String name, boolean closed) {
		this.name = name;
		this.closed = closed;
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
	 * @return 年度
	 */
	public Short getTaiwaneseYear() {
		return taiwaneseYear;
	}

	/**
	 * @param taiwaneseYear 年度
	 */
	public void setTaiwaneseYear(Short taiwaneseYear) {
		this.taiwaneseYear = taiwaneseYear;
	}

	/**
	 * @return 計畫名稱
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name 計畫名稱
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return 受輔導廠商
	 */
	public Vendor getVendor() {
		return vendor;
	}

	/**
	 * @param vendor 受輔導廠商
	 */
	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

	/**
	 * @return 補助金額
	 */
	public Integer getAmount() {
		return amount;
	}

	/**
	 * @param amount 補助金額
	 */
	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	/**
	 * @return 業者現況
	 */
	public String getCondition() {
		return condition;
	}

	/**
	 * @param condition 業者現況
	 */
	public void setCondition(String condition) {
		this.condition = condition;
	}

	/**
	 * @return 輔導項目
	 */
	public String getItems() {
		return items;
	}

	/**
	 * @param items 輔導項目
	 */
	public void setItems(String items) {
		this.items = items;
	}

	/**
	 * @return 輔導效益
	 */
	public String getPerformance() {
		return performance;
	}

	/**
	 * @param performance 輔導效益
	 */
	public void setPerformance(String performance) {
		this.performance = performance;
	}

	/**
	 * @return 相關照片
	 */
	public byte[] getPhoto() {
		return photo;
	}

	/**
	 * @param photo 相關照片
	 */
	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	/**
	 * @return 案件狀態
	 */
	public boolean isClosed() {
		return closed;
	}

	/**
	 * @param closed 案件狀態
	 */
	public void setClosed(boolean closed) {
		this.closed = closed;
	}
}
