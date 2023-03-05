package tw.org.itri.mii.service;

import java.util.Calendar;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Element;
import tw.org.itri.mii.Utils;
import tw.org.itri.mii.entity.Counseling;
import tw.org.itri.mii.entity.IndustrialDistrict;
import tw.org.itri.mii.entity.Vendor;
import tw.org.itri.mii.repository.CounselingRepository;
import tw.org.itri.mii.repository.IndustrialDistrictRepository;
import tw.org.itri.mii.repository.VendorRepository;

/**
 *
 * @author	P-C Lin (a.k.a 高科技黑手)
 */
@Service
public class CounselingService {

	@Autowired
	CounselingRepository counselingRepository;

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
	public void load(Counseling entity, Element parentNode) {
		Short taiwaneseYear = entity.getTaiwaneseYear();//年度
		Utils.createElementWithTextContent("taiwaneseYear", parentNode, taiwaneseYear == null ? "" : taiwaneseYear.toString());

		String name = entity.getName();//計畫名稱
		Utils.createElementWithTextContent("name", parentNode, name == null ? "" : name);

		Vendor vendor = entity.getVendor();//受輔導廠商
		Element vendorElement = Utils.createElement("vendor", parentNode);
		for (IndustrialDistrict iD : industrialDistrictRepository.findAll()) {
			Element optgroupElement = Utils.createElementWithAttribute("optgroup", vendorElement, "label", iD.getName());
			for (Vendor v : vendorRepository.findByIndustrialDistrict(iD)) {
				Element optionElement = Utils.createElementWithTextContentAndAttribute("option", optgroupElement, v.getName(), "value", v.getId().toString());
				if (Objects.equals(v, vendor)) {
					optionElement.setAttribute("selected", null);
				}
			}
			if (!optgroupElement.hasChildNodes()) {
				vendorElement.removeChild(optgroupElement);
			}
		}
		Element optgroupElement = Utils.createElementWithAttribute("optgroup", vendorElement, "label", "其它不在工業園區者");
		for (Vendor v : vendorRepository.findByIndustrialDistrictNull()) {
			Element optionElement = Utils.createElementWithTextContentAndAttribute("option", optgroupElement, v.getName(), "value", v.getId().toString());
			if (Objects.equals(v, vendor)) {
				optionElement.setAttribute("selected", null);
			}
		}
		if (!optgroupElement.hasChildNodes()) {
			vendorElement.removeChild(optgroupElement);
		}

		Integer amount = entity.getAmount();//補助金額
		Utils.createElementWithTextContent("amount", parentNode, amount == null ? "" : amount.toString());

		String condition = entity.getCondition();//業者現況
		Utils.createElementWithTextContent("condition", parentNode, condition == null ? "" : condition);

		String items = entity.getItems();//輔導項目
		Utils.createElementWithTextContent("items", parentNode, items == null ? "" : items);

		String performance = entity.getPerformance();//輔導效益
		Utils.createElementWithTextContent("performance", parentNode, performance == null ? "" : performance);

		byte[] photo = entity.getPhoto();//相關照片
		Utils.createElementWithTextContent("photo", parentNode, photo == null ? "" : entity.getId().toString());

		boolean closed = entity.isClosed();//案件狀態
		Utils.createElementWithTextContent("closed", parentNode, closed ? "true" : "false");
	}

	/**
	 * 儲存。
	 *
	 * @param entity 實體
	 * @param taiwaneseYear 年度
	 * @param name 計畫名稱
	 * @param vendorId 受輔導廠商(外鍵)
	 * @param amount 補助金額
	 * @param condition 業者現況
	 * @param items 輔導項目
	 * @param performance 輔導效益
	 * @param multipartFile 相關照片
	 * @param closed 案件狀態
	 * @param parentNode 父層元素&#60;form/&#62;
	 * @return 錯誤訊息
	 */
	public String save(Counseling entity, Short taiwaneseYear, String name, Short vendorId, Integer amount, String condition, String items, String performance, MultipartFile multipartFile, boolean closed, Element parentNode) {
		String errorMessage = null;

		/*
		 年度
		 */
		try {
			if (taiwaneseYear < 1 || taiwaneseYear > Utils.DEFAULT_CALENDAR.get(Calendar.YEAR) - 1911) {
				errorMessage = "「年度」超出可被允許的範圍！";
			}
		} catch (NullPointerException nullPointerException) {
			errorMessage = "「年度」為必填！";
		} catch (Exception ignore) {
			errorMessage = "輸入的「年度」出現不明的錯誤！";
		}
		Utils.createElementWithTextContent("taiwaneseYear", parentNode, taiwaneseYear == null ? "" : taiwaneseYear.toString());

		/*
		 計畫名稱
		 */
		try {
			name = name.trim();
			if (name.isEmpty()) {
				name = null;
				throw new NullPointerException();
			}
		} catch (NullPointerException nullPointerException) {
			errorMessage = "「計畫名稱」為必填！";
		} catch (Exception ignore) {
			errorMessage = "輸入的「計畫名稱」出現不明的錯誤！";
		}
		Utils.createElementWithTextContent("name", parentNode, name == null ? "" : name);

		/*
		 受輔導廠商
		 */
		Vendor vendor = null;
		try {
			vendor = vendorRepository.findOne(vendorId);
			if (vendor == null) {
				throw new NullPointerException();
			}
		} catch (NullPointerException nullPointerException) {
			errorMessage = "「受輔導廠商」為必選！";
		} catch (Exception ignore) {
			errorMessage = "選擇的「受輔導廠商」出現不明的錯誤！";
		}
		Element vendorElement = Utils.createElement("vendor", parentNode);
		for (IndustrialDistrict iD : industrialDistrictRepository.findAll()) {
			Element optgroupElement = Utils.createElementWithAttribute("optgroup", vendorElement, "label", iD.getName());
			for (Vendor v : vendorRepository.findByIndustrialDistrict(iD)) {
				Element optionElement = Utils.createElementWithTextContentAndAttribute("option", optgroupElement, v.getName(), "value", v.getId().toString());
				if (Objects.equals(v, vendor)) {
					optionElement.setAttribute("selected", null);
				}
			}
			if (!optgroupElement.hasChildNodes()) {
				vendorElement.removeChild(optgroupElement);
			}
		}
		Element optgroupElement = Utils.createElementWithAttribute("optgroup", vendorElement, "label", "其它不在工業園區者");
		for (Vendor v : vendorRepository.findByIndustrialDistrictNull()) {
			Element optionElement = Utils.createElementWithTextContentAndAttribute("option", optgroupElement, v.getName(), "value", v.getId().toString());
			if (Objects.equals(v, vendor)) {
				optionElement.setAttribute("selected", null);
			}
		}
		if (!optgroupElement.hasChildNodes()) {
			vendorElement.removeChild(optgroupElement);
		}

		/*
		 補助金額
		 */
		try {
			if (amount < 0) {
				errorMessage = "「補助金額」超出可被允許的範圍！";
			}
		} catch (NullPointerException nullPointerException) {
			errorMessage = "「補助金額」為必填！";
		} catch (Exception ignore) {
			errorMessage = "輸入的「補助金額」出現不明的錯誤！";
		}
		Utils.createElementWithTextContent("amount", parentNode, amount == null ? "" : amount.toString());

		/*
		 業者現況
		 */
		try {
			condition = condition.trim();
			if (condition.isEmpty()) {
				condition = null;
				throw new NullPointerException();
			}
		} catch (NullPointerException nullPointerException) {
			errorMessage = "「業者現況」為必填！";
		} catch (Exception ignore) {
			errorMessage = "輸入的「業者現況」出現不明的錯誤！";
		}
		Utils.createElementWithTextContent("condition", parentNode, condition == null ? "" : condition);

		/*
		 輔導項目
		 */
		try {
			items = items.trim();
			if (items.isEmpty()) {
				items = null;
				throw new NullPointerException();
			}
		} catch (NullPointerException nullPointerException) {
			errorMessage = "「輔導項目」為必填！";
		} catch (Exception ignore) {
			errorMessage = "輸入的「輔導項目」出現不明的錯誤！";
		}
		Utils.createElementWithTextContent("items", parentNode, items == null ? "" : items);

		/*
		 輔導效益
		 */
		try {
			performance = performance.trim();
			if (performance.isEmpty()) {
				performance = null;
				throw new NullPointerException();
			}
		} catch (NullPointerException nullPointerException) {
			errorMessage = "「輔導效益」為必填！";
		} catch (Exception ignore) {
			errorMessage = "輸入的「輔導效益」出現不明的錯誤！";
		}
		Utils.createElementWithTextContent("performance", parentNode, performance == null ? "" : performance);

		/*
		 相關照片
		 */
		byte[] photo = null;
		try {
			photo = multipartFile.getBytes();
			if (photo.length == 0) {
				photo = null;
			}
		} catch (Exception ignore) {
			errorMessage = "選擇的「相關照片」出現不明的錯誤！";
		}

		/*
		 案件狀態
		 */
		Utils.createElementWithTextContent("closed", parentNode, closed ? "true" : "false");

		if (errorMessage == null) {
			entity.setTaiwaneseYear(taiwaneseYear);
			entity.setName(name);
			entity.setVendor(vendor);
			entity.setAmount(amount);
			entity.setCondition(condition);
			entity.setItems(items);
			entity.setPerformance(performance);
			if (photo != null) {
				entity.setPhoto(photo);
			}
			entity.setClosed(closed);
			counselingRepository.saveAndFlush(entity);
			return null;
		}
		return errorMessage;
	}
}
