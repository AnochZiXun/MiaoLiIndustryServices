package tw.org.itri.mii.entity;

import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * 人員
 *
 * @author	P-C Lin (a.k.a 高科技黑手)
 */
@Entity
@NamedQueries({
	@NamedQuery(name = "Individual.findAll", query = "SELECT i FROM Individual i")
})
@Table(catalog = "\"MiiDB\"", schema = "\"public\"", name = "\"Individual\"", uniqueConstraints = {
	@UniqueConstraint(columnNames = {"\"email\""})
})
public class Individual implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "\"id\"", nullable = false)
	private Short id;

	@JoinColumn(name = "\"clan\"", referencedColumnName = "id")
	@ManyToOne
	private Clan clan;

	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 2147483647)
	@Column(name = "\"name\"", nullable = false, length = 2147483647)
	private String name;

	@Pattern(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message = "Invalid email")
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 2147483647)
	@Column(name = "\"email\"", nullable = false, length = 2147483647)
	private String email;

	@Size(max = 2147483647)
	@Column(name = "\"contactNumber\"", length = 2147483647)
	private String contactNumber;

	@Size(max = 2147483647)
	@Column(name = "\"contactCellular\"", length = 2147483647)
	private String contactCellular;

	@Size(max = 2147483647)
	@Column(name = "\"major\"", length = 2147483647)
	private String major;

	@Size(max = 2147483647)
	@Column(name = "\"title\"", length = 2147483647)
	private String title;

	@JoinColumn(name = "\"institution\"", referencedColumnName = "id")
	@ManyToOne
	private Institution institution;

	@Size(max = 2147483647)
	@Column(name = "\"specialty\"", length = 2147483647)
	private String specialty;

	@Size(max = 2147483647)
	@Column(name = "\"experience\"", length = 2147483647)
	private String experience;

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
		if (!(object instanceof Individual)) {
			return false;
		}
		Individual other = (Individual) object;
		return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
	}

	@Override
	public String toString() {
		return "tw.org.itri.mii.entity.Individual[ id=" + id + " ]";
	}

	/**
	 * 建構子
	 */
	public Individual() {
	}

	/**
	 * @param id 主鍵
	 */
	protected Individual(Short id) {
		this.id = id;
	}

	/**
	 * @param clan 群組
	 * @param name 人員姓名
	 * @param email 電子郵件(帳號)
	 * @param shadow 密碼
	 */
	public Individual(Clan clan, String name, String email, String shadow) {
		this.clan = clan;
		this.name = name;
		this.email = email;
		this.shadow = shadow;
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
	 * @return 群組
	 */
	public Clan getClan() {
		return clan;
	}

	/**
	 * @param clan 群組
	 */
	public void setClan(Clan clan) {
		this.clan = clan;
	}

	/**
	 * @return 人員姓名
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name 人員姓名
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return 電子郵件(帳號)
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email 電子郵件(帳號)
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return 聯絡電話
	 */
	public String getContactNumber() {
		return contactNumber;
	}

	/**
	 * @param contactNumber 聯絡電話
	 */
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	/**
	 * @return 聯絡手機
	 */
	public String getContactCellular() {
		return contactCellular;
	}

	/**
	 * @param contactCellular 聯絡手機
	 */
	public void setContactCellular(String contactCellular) {
		this.contactCellular = contactCellular;
	}

	/**
	 * @return 系/所/中心
	 */
	public String getMajor() {
		return major;
	}

	/**
	 * @param major 系/所/中心
	 */
	public void setMajor(String major) {
		this.major = major;
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
	 * @return 單位
	 */
	public Institution getInstitution() {
		return institution;
	}

	/**
	 * @param institution 單位
	 */
	public void setInstitution(Institution institution) {
		this.institution = institution;
	}

	/**
	 * @return 專長
	 */
	public String getSpecialty() {
		return specialty;
	}

	/**
	 * @param specialty 專長
	 */
	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}

	/**
	 * @return 經歷
	 */
	public String getExperience() {
		return experience;
	}

	/**
	 * @param experience 經歷
	 */
	public void setExperience(String experience) {
		this.experience = experience;
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
