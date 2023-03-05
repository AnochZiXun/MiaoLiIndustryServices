package tw.org.itri.mii.service;

import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.Element;
import tw.org.itri.mii.Utils;
import tw.org.itri.mii.entity.*;
import tw.org.itri.mii.repository.*;

/**
 * 「群組」服務層
 *
 * @author	P-C Lin (a.k.a 高科技黑手)
 */
@org.springframework.stereotype.Service
public class RouteService {

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
	public void load(Route entity, Element parentNode) {
		Accordion accordion = entity.getAccordion();//手風琴
		Element accordionElement = Utils.createElement("accordion", parentNode);
		for (Accordion a : accordionRepository.findAll()) {
			Element optionElement = Utils.createElementWithTextContentAndAttribute("option", accordionElement, a.getName(), "value", a.getId().toString());
			if (Objects.equals(a, accordion)) {
				optionElement.setAttribute("selected", null);
			}
		}

		String label = entity.getLabel();//顯示名稱
		Utils.createElementWithTextContent("label", parentNode, label == null ? "" : label);

		String hyperlink = entity.getHyperlink();//連結
		Utils.createElementWithTextContent("hyperlink", parentNode, hyperlink == null ? "" : hyperlink);
	}

	/**
	 * 儲存。
	 *
	 * @param entity 實體
	 * @param accordionId 手風琴(外鍵)
	 * @param label 顯示名稱
	 * @param hyperlink 連結
	 * @param parentNode 父層元素&#60;form/&#62;
	 * @return 錯誤訊息
	 */
	public String save(Route entity, Short accordionId, String label, String hyperlink, Element parentNode) {
		String errorMessage = null;

		/*
		 手風琴
		 */
		Accordion accordion = null;
		try {
			accordion = accordionRepository.findOne(accordionId);
			if (accordion == null) {
				throw new NullPointerException();
			}
		} catch (NullPointerException nullPointerException) {
			errorMessage = "「手風琴」為必選！";
		} catch (Exception ignore) {
			errorMessage = "選擇的「手風琴」出現不明的錯誤！";
		}
		Element accordionElement = Utils.createElement("accordion", parentNode);
		for (Accordion a : accordionRepository.findAll()) {
			Element optionElement = Utils.createElementWithTextContentAndAttribute("option", accordionElement, a.getName(), "value", a.getId().toString());
			if (Objects.equals(a, accordion)) {
				optionElement.setAttribute("selected", null);
			}
		}

		/*
		 路由名稱
		 */
		try {
			label = label.trim();
			if (label.isEmpty()) {
				label = null;
				throw new NullPointerException();
			}
			if (entity.getId() == null) {
				if (accordion != null && routeRepository.countByAccordionAndLabel(accordion, label) > 0) {
					errorMessage = "重複的「路由名稱」！";
				}
			} else if (routeRepository.countByAccordionAndLabelAndIdNot(accordion, label, entity.getId()) > 0) {
				errorMessage = "重複的「路由名稱」！";
			}
		} catch (NullPointerException nullPointerException) {
			errorMessage = "「路由名稱」為必填！";
		} catch (Exception ignore) {
			errorMessage = "輸入的「路由名稱」出現不明的錯誤！";
		}
		Utils.createElementWithTextContent("label", parentNode, label == null ? "" : label);

		/*
		 連結
		 */
		try {
			hyperlink = hyperlink.trim();
			if (hyperlink.isEmpty()) {
				hyperlink = null;
				throw new NullPointerException();
			}
			if (entity.getId() == null) {
				if (routeRepository.countByHyperlink(hyperlink) > 0) {
					errorMessage = "重複的「連結」！";
				}
			} else if (routeRepository.countByHyperlinkAndIdNot(hyperlink, entity.getId()) > 0) {
				errorMessage = "重複的「連結」！";
			}
		} catch (NullPointerException nullPointerException) {
			errorMessage = "「連結」為必填！";
		} catch (Exception ignore) {
			errorMessage = "輸入的「連結」出現不明的錯誤！";
		}
		Utils.createElementWithTextContent("hyperlink", parentNode, hyperlink == null ? "" : hyperlink);

		if (errorMessage == null) {
			entity.setAccordion(accordion);
			entity.setLabel(label);
			entity.setHyperlink(hyperlink);
			if (entity.getId() == null) {
				entity.setOrdinal((short) (routeRepository.countByAccordion(accordion) + 1));
			}
			routeRepository.saveAndFlush(entity);
			return null;
		}
		return errorMessage;
	}
}
