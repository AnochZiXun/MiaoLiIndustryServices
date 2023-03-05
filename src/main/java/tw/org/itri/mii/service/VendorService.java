package tw.org.itri.mii.service;

import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.Element;
import tw.org.itri.mii.Utils;
import tw.org.itri.mii.entity.*;
import tw.org.itri.mii.repository.*;

/**
 * @author	P-C Lin (a.k.a 高科技黑手)
 */
@org.springframework.stereotype.Service
public class VendorService {

	@Autowired
	DistrictRepository districtRepository;

	@Autowired
	IndustrialDistrictRepository industrialDistrictRepository;

	@Autowired
	VendorRepository vendorRepository;

	/**
	 * 載入。
	 *
	 * @param entity 實體
	 * @param parentNode 父層元素&#60;form/&#62;
	 */
	public void load(Vendor entity, Element parentNode) {
		String name = entity.getName();//廠商名稱
		Utils.createElementWithTextContent("name", parentNode, name == null ? "" : name);

		String unifiedBusinessNumber = entity.getUnifiedBusinessNumber();//統一編號
		Utils.createElementWithTextContent("unifiedBusinessNumber", parentNode, unifiedBusinessNumber == null ? "" : unifiedBusinessNumber);

		String address = entity.getAddress();//廠商地址
		Utils.createElementWithTextContent("address", parentNode, address == null ? "" : address);

		IndustrialDistrict industrialDistrict = entity.getIndustrialDistrict();//工業區
		Element industrialDistrictElement = Utils.createElement("industrialDistrict", parentNode);
		for (District d : districtRepository.findAll()) {
			Element optgroupElement = Utils.createElementWithAttribute("optgroup", industrialDistrictElement, "label", d.getName());
			for (IndustrialDistrict iD : industrialDistrictRepository.findByDistrict(d)) {
				Element optionElement = Utils.createElementWithTextContentAndAttribute("option", optgroupElement, iD.getName(), "value", iD.getId().toString());
				if (Objects.equals(iD, industrialDistrict)) {
					optionElement.setAttribute("selected", null);
				}
			}
			if (!optgroupElement.hasChildNodes()) {
				industrialDistrictElement.removeChild(optgroupElement);
			}
		}

		Short employeeCount = entity.getEmployeeCount();//員工人數
		Utils.createElementWithTextContent("employeeCount", parentNode, employeeCount == null ? "" : employeeCount.toString());

		String contactTitle = entity.getContactTitle();//聯絡人職稱
		Utils.createElementWithTextContent("contactTitle", parentNode, contactTitle == null ? "" : contactTitle);

		String contactName = entity.getContactName();//聯絡人姓名
		Utils.createElementWithTextContent("contactName", parentNode, contactName == null ? "" : contactName);

		String contactNumber = entity.getContactNumber();//聯絡人電話
		Utils.createElementWithTextContent("contactNumber", parentNode, contactNumber == null ? "" : contactNumber);

		String contactEmail = entity.getContactEmail();//聯絡人電子郵件
		Utils.createElementWithTextContent("contactEmail", parentNode, contactEmail == null ? "" : contactEmail);

		String contactFax = entity.getContactFax();//聯絡人傳真
		Utils.createElementWithTextContent("contactFax", parentNode, contactFax == null ? "" : contactFax);

		Integer capital = entity.getCapital();//資本額
		Utils.createElementWithTextContent("capital", parentNode, capital == null ? "" : capital.toString());

		Integer turnover = entity.getTurnover();//營業額
		Utils.createElementWithTextContent("turnover", parentNode, turnover == null ? "" : turnover.toString());

		String business = entity.getBusiness();//營業項目
		Utils.createElementWithTextContent("business", parentNode, business == null ? "" : business);
	}

	/**
	 * 儲存。
	 *
	 * @param entity 實體
	 * @param name 廠商名稱
	 * @param unifiedBusinessNumber 統一編號
	 * @param address 廠商地址
	 * @param industrialDistrictId 工業區(外鍵)
	 * @param employeeCount 員工人數
	 * @param contactTitle 聯絡人職稱
	 * @param contactName 聯絡人姓名
	 * @param contactNumber 聯絡人電話
	 * @param contactEmail 聯絡人電子郵件
	 * @param contactFax 聯絡人傳真
	 * @param capital 資本額
	 * @param turnover 營業額
	 * @param business 營業項目
	 * @param parentNode 父層元素&#60;form/&#62;
	 * @return 錯誤訊息
	 */
	public String save(Vendor entity, String name, String unifiedBusinessNumber, String address, Short industrialDistrictId, Short employeeCount, String contactTitle, String contactName, String contactNumber, String contactEmail, String contactFax, Integer capital, Integer turnover, String business, Element parentNode) {
		String errorMessage = null;

		/*
		 廠商名稱
		 */
		try {
			name = name.trim();
			if (name.isEmpty()) {
				name = null;
				throw new NullPointerException();
			}
		} catch (NullPointerException nullPointerException) {
			errorMessage = "「廠商名稱」為必填！";
		} catch (Exception ignore) {
			errorMessage = "輸入的「廠商名稱」出現不明的錯誤！";
		}
		Utils.createElementWithTextContent("name", parentNode, name == null ? "" : name);

		/*
		 統一編號
		 */
		try {
			unifiedBusinessNumber = unifiedBusinessNumber.trim();
			if (unifiedBusinessNumber.isEmpty()) {
				unifiedBusinessNumber = null;
				throw new NullPointerException();
			}
		} catch (NullPointerException nullPointerException) {
			errorMessage = "「統一編號」為必填！";
		} catch (Exception ignore) {
			errorMessage = "輸入的「統一編號」出現不明的錯誤！";
		}
		Utils.createElementWithTextContent("unifiedBusinessNumber", parentNode, unifiedBusinessNumber == null ? "" : unifiedBusinessNumber);

		/*
		 廠商地址
		 */
		try {
			address = address.trim();
			if (address.isEmpty()) {
				address = null;
				throw new NullPointerException();
			}
		} catch (NullPointerException nullPointerException) {
			errorMessage = "「廠商地址」為必填！";
		} catch (Exception ignore) {
			errorMessage = "輸入的「廠商地址」出現不明的錯誤！";
		}
		Utils.createElementWithTextContent("address", parentNode, address == null ? "" : address);

		/*
		 工業區
		 */
		IndustrialDistrict industrialDistrict = null;
		try {
			industrialDistrict = industrialDistrictRepository.findOne(industrialDistrictId);
		} catch (Exception ignore) {
		}
		Element industrialDistrictElement = Utils.createElement("industrialDistrict", parentNode);
		for (District d : districtRepository.findAll()) {
			Element optgroupElement = Utils.createElementWithAttribute("optgroup", industrialDistrictElement, "label", d.getName());
			for (IndustrialDistrict iD : industrialDistrictRepository.findByDistrict(d)) {
				Element optionElement = Utils.createElementWithTextContentAndAttribute("option", optgroupElement, iD.getName(), "value", iD.getId().toString());
				if (Objects.equals(iD, industrialDistrict)) {
					optionElement.setAttribute("selected", null);
				}
			}
			if (!optgroupElement.hasChildNodes()) {
				industrialDistrictElement.removeChild(optgroupElement);
			}
		}

		/*
		 員工人數
		 */
		try {
			if (employeeCount < 0 || employeeCount >= 32767) {
				errorMessage = "「員工人數」超出可被允許的範圍！";
			}
		} catch (NullPointerException nullPointerException) {
			errorMessage = "「員工人數」為必填！";
		} catch (Exception ignore) {
		}
		Utils.createElementWithTextContent("employeeCount", parentNode, employeeCount == null ? "" : employeeCount.toString());

		/*
		 聯絡人職稱
		 */
		try {
			contactTitle = contactTitle.trim();
			if (contactTitle.isEmpty()) {
				contactTitle = null;
			}
		} catch (Exception ignore) {
			errorMessage = "輸入的「聯絡人職稱」出現不明的錯誤！";
		}
		Utils.createElementWithTextContent("contactTitle", parentNode, contactTitle == null ? "" : contactTitle);

		/*
		 聯絡人職稱
		 */
		try {
			contactTitle = contactTitle.trim();
			if (contactTitle.isEmpty()) {
				contactTitle = null;
			}
		} catch (Exception ignore) {
		}
		Utils.createElementWithTextContent("condition", parentNode, contactTitle == null ? "" : contactTitle);

		/*
		 聯絡人姓名
		 */
		try {
			contactName = contactName.trim();
			if (contactName.isEmpty()) {
				contactName = null;
				throw new NullPointerException();
			}
		} catch (NullPointerException nullPointerException) {
			errorMessage = "「聯絡人姓名」為必填！";
		} catch (Exception ignore) {
			errorMessage = "輸入的「聯絡人姓名」出現不明的錯誤！";
		}
		Utils.createElementWithTextContent("contactName", parentNode, contactName == null ? "" : contactName);

		/*
		 聯絡人電話
		 */
		try {
			contactNumber = contactNumber.trim();
			if (contactNumber.isEmpty()) {
				contactNumber = null;
				throw new NullPointerException();
			}
		} catch (NullPointerException nullPointerException) {
			errorMessage = "「聯絡人電話」為必填！";
		} catch (Exception ignore) {
			errorMessage = "輸入的「聯絡人電話」出現不明的錯誤！";
		}
		Utils.createElementWithTextContent("contactNumber", parentNode, contactNumber == null ? "" : contactNumber);

		/*
		 聯絡人電子郵件
		 */
		try {
			contactEmail = contactEmail.trim();
			if (contactEmail.isEmpty()) {
				contactEmail = null;
				throw new NullPointerException();
			}
		} catch (NullPointerException nullPointerException) {
			errorMessage = "「聯絡人電子郵件」為必填！";
		} catch (Exception ignore) {
			errorMessage = "輸入的「聯絡人電子郵件」出現不明的錯誤！";
		}
		Utils.createElementWithTextContent("contactEmail", parentNode, contactEmail == null ? "" : contactEmail);

		/*
		 聯絡人傳真
		 */
		try {
			contactFax = contactFax.trim();
			if (contactFax.isEmpty()) {
				contactFax = null;
			}
		} catch (Exception ignore) {
		}
		Utils.createElementWithTextContent("contactFax", parentNode, contactFax == null ? "" : contactFax);

		/*
		 資本額
		 */
		try {
			if (capital < 0 || capital >= 2147483647) {
				errorMessage = "「資本額」超出可被允許的範圍！";
			}
		} catch (Exception ignore) {
		}
		Utils.createElementWithTextContent("capital", parentNode, capital == null ? "" : capital.toString());

		/*
		 資本額
		 */
		try {
			if (turnover < 0 || turnover >= 2147483647) {
				errorMessage = "「營業額」超出可被允許的範圍！";
			}
		} catch (Exception ignore) {
		}
		Utils.createElementWithTextContent("turnover", parentNode, turnover == null ? "" : turnover.toString());

		/*
		 營業項目
		 */
		try {
			business = business.trim();
			if (business.isEmpty()) {
				business = null;
				throw new NullPointerException();
			}
		} catch (NullPointerException nullPointerException) {
			errorMessage = "「營業項目」為必填！";
		} catch (Exception ignore) {
			errorMessage = "輸入的「營業項目」出現不明的錯誤！";
		}
		Utils.createElementWithTextContent("business", parentNode, business == null ? "" : business);

		if (errorMessage == null) {
			entity.setName(name);
			entity.setUnifiedBusinessNumber(unifiedBusinessNumber);
			entity.setAddress(address);
			entity.setIndustrialDistrict(industrialDistrict);
			entity.setEmployeeCount(employeeCount);
			entity.setContactTitle(contactTitle);
			entity.setContactName(contactName);
			entity.setContactNumber(contactNumber);
			entity.setContactEmail(contactEmail);
			entity.setContactFax(contactFax);
			entity.setCapital(capital);
			entity.setTurnover(turnover);
			entity.setBusiness(business);
			if (entity.getShadow() == null) {
				entity.setShadow("");
			}
			vendorRepository.saveAndFlush(entity);
			return null;
		}
		return errorMessage;
	}
}
