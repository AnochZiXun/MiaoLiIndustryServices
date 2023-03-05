package tw.org.itri.mii.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.Element;
import tw.org.itri.mii.Utils;
import tw.org.itri.mii.entity.*;
import tw.org.itri.mii.repository.*;

/**
 * 「手風琴」服務層
 *
 * @author	P-C Lin (a.k.a 高科技黑手)
 */
@org.springframework.stereotype.Service
public class AccordionService {

	@Autowired
	AccordionRepository accordionRepository;

	@Autowired
	RouteRepository routeRepository;

	@Autowired
	Services services;

	/**
	 * 載入。
	 *
	 * @param entity 實體
	 * @param parentNode 父層元素&#60;form/&#62;
	 */
	public void load(Accordion entity, Element parentNode) {
		String name = entity.getName();//手風琴名稱
		Utils.createElementWithTextContent("name", parentNode, name == null ? "" : name);

		if (entity.getId() != null && routeRepository.countByAccordion(entity) > 0) {
			Element listElement = Utils.createElementWithAttribute("list", parentNode.getParentNode(), "id", entity.getId().toString());

			for (Route route : routeRepository.findByAccordionOrderByOrdinal(entity)) {
				Element rowElement = Utils.createElementWithAttribute("row", listElement, "id", route.getId().toString());//列(帶主鍵)
				Utils.createElementWithTextContent("label", rowElement, route.getLabel());//顯示名稱
				Utils.createElementWithTextContent("hyperlink", rowElement, route.getHyperlink());//連結
				Utils.createElementWithTextContent("ordinal", rowElement, Short.toString(route.getOrdinal()));//排序
			}
		}
	}

	/**
	 * 儲存。
	 *
	 * @param entity 實體
	 * @param name 手風琴名稱
	 * @param parentNode 父層元素&#60;form/&#62;
	 * @return 錯誤訊息
	 */
	public String save(Accordion entity, String name, Element parentNode) {
		String errorMessage = null;

		/*
		 手風琴名稱
		 */
		try {
			name = name.trim();
			if (name.isEmpty()) {
				name = null;
				throw new NullPointerException();
			}
			if (entity.getId() == null) {
				if (accordionRepository.countByName(name) > 0) {
					errorMessage = "重複的「手風琴名稱」！";
				}
			} else if (accordionRepository.countByNameAndIdNot(name, entity.getId()) > 0) {
				errorMessage = "重複的「手風琴名稱」！";
			}
		} catch (NullPointerException nullPointerException) {
			errorMessage = "「手風琴名稱」為必填！";
		} catch (Exception ignore) {
			errorMessage = "輸入的「手風琴名稱」出現不明的錯誤！";
		}
		Utils.createElementWithTextContent("name", parentNode, name == null ? "" : name);

		if (errorMessage == null) {
			entity.setName(name);
			if (entity.getId() == null) {
				entity.setOrdinal((short) (accordionRepository.count() + 1));
			}
			accordionRepository.saveAndFlush(entity);
			return null;
		}
		return errorMessage;
	}
}
