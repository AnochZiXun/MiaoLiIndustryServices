package tw.org.itri.mii.service;

import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Element;
import tw.org.itri.mii.Utils;
import tw.org.itri.mii.entity.District;
import tw.org.itri.mii.entity.Institution;
import tw.org.itri.mii.repository.DistrictRepository;
import tw.org.itri.mii.repository.InstitutionRepository;
import tw.org.itri.mii.repository.VendorRepository;

/**
 * 「單位」服務層
 *
 * @author	P-C Lin (a.k.a 高科技黑手)
 */
@Service
public class InstitutionService {

	@Autowired
	DistrictRepository districtRepository;

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
	public void load(Institution entity, Element parentNode) {
		District district = entity.getDistrict();//行政區
		Element districtElement = Utils.createElement("district", parentNode);
		for (District d : districtRepository.findAll()) {
			Element optionElement = Utils.createElementWithTextContentAndAttribute("option", districtElement, d.getName(), "value", d.getId().toString());
			if (Objects.equals(d, district)) {
				optionElement.setAttribute("selected", null);
			}
		}

		String name = entity.getName();//單位名稱
		Utils.createElementWithTextContent("name", parentNode, name == null ? "" : name);
	}

	/**
	 * 儲存。
	 *
	 * @param entity 實體
	 * @param districtId 行政區(外鍵)
	 * @param name 單位名稱
	 * @param parentNode 父層元素&#60;form/&#62;
	 * @return 錯誤訊息
	 */
	public String save(Institution entity, Short districtId, String name, Element parentNode) {
		String errorMessage = null;

		/*
		 行政區
		 */
		District district = null;
		try {
			if (districtId != null) {
				district = districtRepository.findOne(districtId);
			}
		} catch (Exception ignore) {
			errorMessage = "選擇的「行政區」出現不明的錯誤！";
		}
		Element districtElement = Utils.createElement("district", parentNode);
		for (District d : districtRepository.findAll()) {
			Element optionElement = Utils.createElementWithTextContentAndAttribute("option", districtElement, d.getName(), "value", d.getId().toString());
			if (Objects.equals(d, district)) {
				optionElement.setAttribute("selected", null);
			}
		}

		/*
		 單位名稱
		 */
		try {
			name = name.trim();
			if (name.isEmpty()) {
				name = null;
				throw new NullPointerException();
			}

			Short id = entity.getId();
			if (id == null) {
				if (institutionRepository.countByName(name) > 0) {
					errorMessage = "不得有重複的「單位名稱」！";
				}
			} else if (institutionRepository.countByNameAndIdNot(name, id) > 0) {
				errorMessage = "不得有重複的「單位名稱」！";
			}
		} catch (NullPointerException nullPointerException) {
			errorMessage = "「單位名稱」為必填！";
		} catch (Exception ignore) {
			errorMessage = "輸入的「單位名稱」出現不明的錯誤！";
		}
		Utils.createElementWithTextContent("name", parentNode, name == null ? "" : name);

		if (errorMessage == null) {
			entity.setDistrict(district);
			entity.setName(name);
			institutionRepository.saveAndFlush(entity);
			return null;
		}
		return errorMessage;
	}
}
