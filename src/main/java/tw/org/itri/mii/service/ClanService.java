package tw.org.itri.mii.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Element;
import tw.org.itri.mii.Utils;
import tw.org.itri.mii.entity.*;
import tw.org.itri.mii.repository.*;

/**
 * 「群組」服務層
 *
 * @author	P-C Lin (a.k.a 高科技黑手)
 */
@Service
public class ClanService {

	@Autowired
	AccordionRepository accordionRepository;

	@Autowired
	ClanRepository clanRepository;

	@Autowired
	PrivilegeRepository privilegeRepository;

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
	public void load(Clan entity, Element parentNode) {
		String name = entity.getName();//群組名稱
		Utils.createElementWithTextContent("name", parentNode, name == null ? "" : name);

		if (entity.getId() != null) {
			Element listElement = Utils.createElementWithAttribute("list", parentNode.getParentNode(), "id", entity.getId().toString());

			for (Accordion accordion : accordionRepository.findAll(new org.springframework.data.domain.Sort(org.springframework.data.domain.Sort.Direction.ASC, "ordinal"))) {
				Element tableElement = Utils.createElementWithAttribute("table", listElement, "id", accordion.getId().toString());//表(帶主鍵)
				tableElement.setAttribute("name", accordion.getName());//手風琴名稱

				for (Route route : routeRepository.findByAccordionOrderByOrdinal(accordion)) {
					Element rowElement = Utils.createElementWithCDATASectionAndAttribute("row", tableElement, route.getLabel(), "id", route.getId().toString());//列(帶主鍵)
					Utils.createElementWithTextContent("label", rowElement, route.getLabel());//顯示名稱
					Utils.createElementWithTextContent("hyperlink", rowElement, route.getHyperlink());//連結
					Utils.createElementWithTextContent("ordinal", rowElement, Short.toString(route.getOrdinal()));//排序
					Utils.createElementWithTextContent("status", rowElement, privilegeRepository.findOneByRouteAndClan(route, entity) == null ? "&#xF05E;" : "&#xF14A;");//狀態
				}
			}
		}
	}

	/**
	 * 儲存。
	 *
	 * @param entity 實體
	 * @param name 群組名稱
	 * @param parentNode 父層元素&#60;form/&#62;
	 * @return 錯誤訊息
	 */
	public String save(Clan entity, String name, Element parentNode) {
		String errorMessage = null;

		/*
		 群組名稱
		 */
		try {
			name = name.trim();
			if (name.isEmpty()) {
				name = null;
				throw new NullPointerException();
			}
			if (entity.getId() == null) {
				if (clanRepository.countByName(name) > 0) {
					errorMessage = "重複的「群組名稱」！";
				}
			} else if (clanRepository.countByNameAndIdNot(name, entity.getId()) > 0) {
				errorMessage = "重複的「群組名稱」！";
			}
		} catch (NullPointerException nullPointerException) {
			errorMessage = "「群組名稱」為必填！";
		} catch (Exception ignore) {
			errorMessage = "輸入的「群組名稱」出現不明的錯誤！";
		}
		Utils.createElementWithTextContent("name", parentNode, name == null ? "" : name);

		if (errorMessage == null) {
			entity.setName(name);
			if (entity.getId() == null) {
				entity.setOrdinal((short) (clanRepository.count() + 1));
			}
			clanRepository.saveAndFlush(entity);
			return null;
		}
		return errorMessage;
	}
}
