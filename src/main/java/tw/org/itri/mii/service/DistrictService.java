package tw.org.itri.mii.service;

import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Element;
import tw.org.itri.mii.Utils;
import tw.org.itri.mii.entity.District;
import tw.org.itri.mii.repository.CounselingRepository;
import tw.org.itri.mii.repository.DistrictRepository;
import tw.org.itri.mii.repository.IndustrialDistrictRepository;
import tw.org.itri.mii.repository.VendorRepository;

/**
 * 「行政區」服務層
 *
 * @author	P-C Lin (a.k.a 高科技黑手)
 */
@Service
public class DistrictService {

	@Autowired
	CounselingRepository counselingRepository;

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
	public void load(District entity, Element parentNode) {
		District upperLevel = entity.getUpperLevel();//上層行政區
		Element districtElement = Utils.createElement("district", parentNode);
		for (District d : districtRepository.findAll()) {
			Element optionElement = Utils.createElementWithTextContentAndAttribute("option", districtElement, d.getName(), "value", d.getId().toString());
			if (Objects.equals(d, upperLevel)) {
				optionElement.setAttribute("selected", null);
			}
		}

		String name = entity.getName();//行政區名稱
		Utils.createElementWithTextContent("name", parentNode, name == null ? "" : name);
	}

	/**
	 * 儲存。
	 *
	 * @param entity 實體
	 * @param districtId 行政區(外鍵)
	 * @param name 行政區名稱
	 * @param parentNode 父層元素&#60;form/&#62;
	 * @return 錯誤訊息
	 */
	public String save(District entity, Short districtId, String name, Element parentNode) {
		String errorMessage = null;

		/*
		 上層行政區
		 */
		District upperLevel = null;
		try {
			upperLevel = districtRepository.findOne(districtId);
		} catch (Exception ignore) {
		}
		Element districtElement = Utils.createElement("district", parentNode);
		for (District d : districtRepository.findAll()) {
			Element optionElement = Utils.createElementWithTextContentAndAttribute("option", districtElement, d.getName(), "value", d.getId().toString());
			if (Objects.equals(d, upperLevel)) {
				optionElement.setAttribute("selected", null);
			}
		}

		/*
		 行政區名稱
		 */
		try {
			name = name.trim();
			if (name.isEmpty()) {
				name = null;
				throw new NullPointerException();
			}
		} catch (NullPointerException nullPointerException) {
			errorMessage = "「行政區名稱」為必填！";
		} catch (Exception ignore) {
			errorMessage = "輸入的「行政區名稱」出現不明的錯誤！";
		}
		Utils.createElementWithTextContent("name", parentNode, name == null ? "" : name);

		if (upperLevel != null && name != null && industrialDistrictRepository.countByDistrictAndName(upperLevel, name) > 0) {
			errorMessage = "在同一級「行政區」下不得有重複的「行政區名稱」！";
		}

		if (errorMessage == null) {
			entity.setUpperLevel(upperLevel);
			entity.setName(name);
			districtRepository.saveAndFlush(entity);
			return null;
		}
		return errorMessage;
	}
}
