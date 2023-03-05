package tw.org.itri.mii.service;

import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Element;
import tw.org.itri.mii.Utils;
import tw.org.itri.mii.entity.Clan;
import tw.org.itri.mii.entity.Individual;
import tw.org.itri.mii.entity.Institution;
import tw.org.itri.mii.repository.ClanRepository;
import tw.org.itri.mii.repository.IndividualRepository;
import tw.org.itri.mii.repository.InstitutionRepository;
import tw.org.itri.mii.repository.VendorRepository;

/**
 * 「人員」服務層
 *
 * @author	P-C Lin (a.k.a 高科技黑手)
 */
@Service
public class IndividualService {

	@Autowired
	ClanRepository clanRepository;

	@Autowired
	IndividualRepository individualRepository;

	@Autowired
	InstitutionRepository institutionRepository;

	@Autowired
	VendorRepository vendorRepository;

	/**
	 * 載入。
	 *
	 * @param entity 實體
	 * @param parentNode 父層元素&#60;form/&#62;
	 */
	public void load(Individual entity, Element parentNode) {
		Clan clan = entity.getClan();//群組
		Element clanElement = Utils.createElement("clan", parentNode);
		for (Clan c : clanRepository.findAll()) {
			Element optionElement = Utils.createElementWithTextContentAndAttribute("option", clanElement, c.getName(), "value", c.getId().toString());
			if (Objects.equals(c, clan)) {
				optionElement.setAttribute("selected", null);
			}
		}

		String name = entity.getName();//人員姓名
		Utils.createElementWithTextContent("name", parentNode, name == null ? "" : name);

		String email = entity.getEmail();//電子郵件
		Utils.createElementWithTextContent("email", parentNode, email == null ? "" : email);

		String contactNumber = entity.getContactNumber();//聯絡電話
		Utils.createElementWithTextContent("contactNumber", parentNode, contactNumber == null ? "" : contactNumber);

		String contactCellular = entity.getContactCellular();//聯絡手機
		Utils.createElementWithTextContent("contactCellular", parentNode, contactCellular == null ? "" : contactCellular);

		String major = entity.getMajor();//系/所/中心
		Utils.createElementWithTextContent("major", parentNode, major == null ? "" : major);

		String title = entity.getTitle();//職稱
		Utils.createElementWithTextContent("title", parentNode, title == null ? "" : title);

		Institution institution = entity.getInstitution();//單位
		Element institutionElement = Utils.createElement("institution", parentNode);
		for (Institution i : institutionRepository.findAll()) {
			Element optionElement = Utils.createElementWithTextContentAndAttribute("option", institutionElement, i.getName(), "value", i.getId().toString());
			if (Objects.equals(i, institution)) {
				optionElement.setAttribute("selected", null);
			}
		}

		String specialty = entity.getSpecialty();//專長
		Utils.createElementWithTextContent("specialty", parentNode, specialty == null ? "" : specialty);

		String experience = entity.getExperience();//經歷
		Utils.createElementWithTextContent("experience", parentNode, experience == null ? "" : experience);
	}

	/**
	 * 儲存。
	 *
	 * @param entity 實體
	 * @param clanId 群組(外鍵)
	 * @param name 人員姓名
	 * @param email 電子郵件(帳號)
	 * @param contactNumber 聯絡電話
	 * @param contactCellular 聯絡手機
	 * @param major 系/所/中心
	 * @param title 職稱
	 * @param institutionId 單位(外鍵)
	 * @param specialty 專長
	 * @param experience 經歷
	 * @param parentNode 父層元素&#60;form/&#62;
	 * @return 錯誤訊息
	 */
	public String save(Individual entity, Short clanId, String name, String email, String contactNumber, String contactCellular, String major, String title, Short institutionId, String specialty, String experience, Element parentNode) {
		String errorMessage = null;

		/*
		 群組
		 */
		Clan clan = null;
		try {
			clan = clanRepository.findOne(clanId);
			if (clan == null) {
				throw new NullPointerException();
			}
		} catch (NullPointerException nullPointerException) {
			errorMessage = "「群組」為必選！";
		} catch (Exception ignore) {
			errorMessage = "選擇的「群組」出現不明的錯誤！";
		}
		Element clanElement = Utils.createElement("clan", parentNode);
		for (Clan c : clanRepository.findAll()) {
			Element optionElement = Utils.createElementWithTextContentAndAttribute("option", clanElement, c.getName(), "value", c.getId().toString());
			if (Objects.equals(c, clan)) {
				optionElement.setAttribute("selected", null);
			}
		}

		/*
		 人員姓名
		 */
		try {
			name = name.trim();
			if (name.isEmpty()) {
				name = null;
				throw new NullPointerException();
			}
		} catch (NullPointerException nullPointerException) {
			errorMessage = "「人員姓名」為必填！";
		} catch (Exception ignore) {
			errorMessage = "輸入的「人員姓名」出現不明的錯誤！";
		}
		Utils.createElementWithTextContent("name", parentNode, name == null ? "" : name);

		/*
		 電子郵件
		 */
		try {
			email = email.trim().toLowerCase();
			if (email.isEmpty()) {
				email = null;
				throw new NullPointerException();
			}

			Short id = entity.getId();
			if (id == null) {
				if (individualRepository.countByEmail(email) > 0) {
					errorMessage = "不得有重複的「電子郵件」！";
				}
			} else if (individualRepository.countByEmailAndIdNot(email, id) > 0) {
				errorMessage = "不得有重複的「電子郵件」！";
			}
		} catch (NullPointerException nullPointerException) {
			errorMessage = "「電子郵件」為必填！";
		} catch (Exception ignore) {
			errorMessage = "輸入的「電子郵件」出現不明的錯誤！";
		}
		Utils.createElementWithTextContent("email", parentNode, email == null ? "" : email);

		/*
		 聯絡電話
		 */
		try {
			contactNumber = contactNumber.trim();
			if (contactNumber.isEmpty()) {
				contactNumber = null;
			}
		} catch (Exception ignore) {
			errorMessage = "輸入的「聯絡電話」出現不明的錯誤！";
		}
		Utils.createElementWithTextContent("contactNumber", parentNode, contactNumber == null ? "" : contactNumber);

		/*
		 聯絡手機
		 */
		try {
			contactCellular = contactCellular.trim();
			if (contactCellular.isEmpty()) {
				contactCellular = null;
			}
		} catch (Exception ignore) {
			errorMessage = "輸入的「聯絡手機」出現不明的錯誤！";
		}
		Utils.createElementWithTextContent("contactCellular", parentNode, contactCellular == null ? "" : contactCellular);

		/*
		 系/所/中心
		 */
		try {
			major = major.trim();
			if (major.isEmpty()) {
				major = null;
			}
		} catch (Exception ignore) {
			errorMessage = "輸入的「系/所/中心」出現不明的錯誤！";
		}
		Utils.createElementWithTextContent("major", parentNode, major == null ? "" : major);

		/*
		 職稱
		 */
		try {
			title = title.trim();
			if (title.isEmpty()) {
				title = null;
			}
		} catch (Exception ignore) {
			errorMessage = "輸入的「職稱」出現不明的錯誤！";
		}
		Utils.createElementWithTextContent("title", parentNode, title == null ? "" : title);

		/*
		 單位
		 */
		Institution institution = null;
		try {
			if (institutionId != null) {
				institution = institutionRepository.findOne(institutionId);
			}
		} catch (Exception ignore) {
			errorMessage = "選擇的「單位」出現不明的錯誤！";
		}
		Element institutionElement = Utils.createElement("institution", parentNode);
		for (Institution i : institutionRepository.findAll()) {
			Element optionElement = Utils.createElementWithTextContentAndAttribute("option", institutionElement, i.getName(), "value", i.getId().toString());
			if (Objects.equals(i, institution)) {
				optionElement.setAttribute("selected", null);
			}
		}

		/*
		 專長
		 */
		try {
			specialty = specialty.trim();
			if (specialty.isEmpty()) {
				specialty = null;
			}
		} catch (Exception ignore) {
			errorMessage = "輸入的「職稱」出現不明的錯誤！";
		}
		Utils.createElementWithTextContent("specialty", parentNode, specialty == null ? "" : specialty);

		/*
		 經歷
		 */
		try {
			experience = experience.trim();
			if (experience.isEmpty()) {
				experience = null;
			}
		} catch (Exception ignore) {
			errorMessage = "輸入的「職稱」出現不明的錯誤！";
		}
		Utils.createElementWithTextContent("experience", parentNode, experience == null ? "" : experience);

		if (errorMessage == null) {
			entity.setClan(clan);
			entity.setName(name);
			entity.setEmail(email);
			entity.setContactNumber(contactNumber);
			entity.setContactCellular(contactCellular);
			entity.setMajor(major);
			entity.setTitle(title);
			entity.setInstitution(institution);
			entity.setSpecialty(specialty);
			entity.setExperience(experience);
			if (entity.getId() == null) {
				entity.setShadow(Utils.md5(email));
			}
			individualRepository.saveAndFlush(entity);
			return null;
		}
		return errorMessage;
	}
}
