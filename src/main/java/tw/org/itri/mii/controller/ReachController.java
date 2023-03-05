package tw.org.itri.mii.controller;

import javax.servlet.http.*;
import javax.xml.transform.dom.DOMSource;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.w3c.dom.*;
import tw.org.itri.mii.Utils;
import tw.org.itri.mii.entity.*;
import tw.org.itri.mii.repository.*;
import tw.org.itri.mii.service.*;

/**
 * 「聯絡我們」控制器
 *
 * @author	P-C Lin (a.k.a 高科技黑手)
 */
@org.springframework.stereotype.Controller
@RequestMapping("/LianLuoWoMen")
public class ReachController {

	@Autowired
	IndividualRepository individualRepository;

	@Autowired
	PrivilegeRepository privilegeRepository;

	@Autowired
	ReachRepository reachRepository;

	@Autowired
	javax.servlet.ServletContext context;

	@Autowired
	Services services;

	/**
	 * 列表。
	 *
	 * @param size 每頁幾筆
	 * @param number 第幾頁
	 * @param request 請求
	 * @param response 回應
	 * @param session 階段
	 * @return 網頁
	 */
	@RequestMapping(value = "/", produces = "text/html;charset=UTF-8", method = RequestMethod.GET)
	@SuppressWarnings("UseSpecificCatch")
	private ModelAndView index(@RequestParam(value = "s", defaultValue = "10") Integer size, @RequestParam(value = "p", defaultValue = "0") Integer number, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
		Short myId = services.whoTheHeckAmI(session);
		if (myId == null) {
			return new ModelAndView("redirect:/DengRu.asp");//未登入則導向到登入頁面
		}

		Individual me = individualRepository.findOne(myId);
		if (me == null) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);//未授權
			return null;
		}

		Privilege privilege = services.whatTheFuckIsMyPrivilege(request, session);
		if (privilege == null) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);//被禁止
			return null;
		}

		/*
		 登入了
		 */
		Document document = Utils.newDocument();
		Element documentElement = Utils.createElementWithAttribute("document", document, "contextPath", request.getContextPath());

		services.whereTheHellAmI(document, request, session);

		/*
		 分頁
		 */
		Element searchElement = Utils.createElementWithAttribute("search", documentElement, "action", request.getRequestURI());
		//每頁幾筆
		if (size < 1) {
			size = 10;
		}
		//第幾頁
		if (number < 0) {
			number = 0;
		}
		org.springframework.data.domain.Pageable pageRequest = new org.springframework.data.domain.PageRequest(number, size, org.springframework.data.domain.Sort.Direction.DESC, "id");
		org.springframework.data.domain.Page<Reach> pageOfEntities;
		pageOfEntities = reachRepository.findAll(pageRequest);
		number = pageOfEntities.getNumber();
		Integer totalPages = pageOfEntities.getTotalPages();
		Long totalElements = pageOfEntities.getTotalElements();
		if (pageOfEntities.hasPrevious()) {
			searchElement.setAttribute("previous", Integer.toString(number - 1));
			if (!pageOfEntities.isFirst()) {
				searchElement.setAttribute("first", "0");
			}
		}
		searchElement.setAttribute("size", size.toString());
		searchElement.setAttribute("number", number.toString());
		for (Integer i = 0; i < totalPages; i++) {
			Element optionElement = Utils.createElementWithTextContentAndAttribute("option", searchElement, Integer.toString(i + 1), "value", i.toString());
			if (number.equals(i)) {
				optionElement.setAttribute("selected", null);
			}
		}
		searchElement.setAttribute("totalPages", totalPages.toString());
		searchElement.setAttribute("totalElements", totalElements.toString());
		if (pageOfEntities.hasNext()) {
			searchElement.setAttribute("next", Integer.toString(number + 1));
			if (!pageOfEntities.isLast()) {
				searchElement.setAttribute("last", Integer.toString(totalPages - 1));
			}
		}

		/*
		 列表
		 */
		Element contentElement = Utils.createElement("list", documentElement);
		for (Reach entity : pageOfEntities.getContent()) {
			Element rowElement = Utils.createElementWithAttribute("row", contentElement, "id", entity.getId().toString());//列(帶主鍵)
			Utils.createElementWithTextContent("name", rowElement, entity.getName());//大名
			Utils.createElementWithTextContent("phone", rowElement, entity.getPhone());//聯絡電話
			Utils.createElementWithTextContent("email", rowElement, entity.getEmail());//電子郵件
		}

		ModelAndView modelAndView = new ModelAndView("reach/default");
		modelAndView.getModelMap().addAttribute(new DOMSource(document));
		return modelAndView;
	}

	/**
	 * 讀取。
	 *
	 * @param id 主鍵
	 * @param request 請求
	 * @param response 回應
	 * @param session 階段
	 * @return 網頁
	 */
	@RequestMapping(value = "/{id:[\\d]+}.asp", produces = "text/html;charset=UTF-8", method = RequestMethod.GET)
	private ModelAndView read(@PathVariable Integer id, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
		Short myId = services.whoTheHeckAmI(session);
		if (myId == null) {
			return new ModelAndView("redirect:/DengRu.asp");//未登入則導向到登入頁面
		}

		Individual me = individualRepository.findOne(myId);
		if (me == null) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);//未授權
			return null;
		}

		Privilege privilege = services.whatTheFuckIsMyPrivilege(request, session);
		if (privilege == null) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);//被禁止
			return null;
		}

		Reach entity = reachRepository.findOne(id);
		if (entity == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}

		/*
		 登入了
		 */
		Document document = Utils.newDocument();
		Element documentElement = Utils.createElementWithAttribute("document", document, "contextPath", request.getContextPath());
		documentElement.setAttribute("title", "後臺 &#187; 聯絡我們 &#187; 檢視");

		services.whereTheHellAmI(document, request, session);

		Element formElement = Utils.createElementWithAttribute("form", documentElement, "action", request.getRequestURI());
		formElement.setAttribute("legend", "檢視聯絡我們");

		String company = entity.getCompany();//公司名稱
		Utils.createElementWithTextContent("company", formElement, company == null ? "" : company);

		String name = entity.getName();//大名
		Utils.createElementWithTextContent("name", formElement, name == null ? "" : name);

		String title = entity.getTitle();//職稱
		Utils.createElementWithTextContent("title", formElement, title == null ? "" : title);

		String phone = entity.getPhone();//聯絡電話
		Utils.createElementWithTextContent("phone", formElement, phone == null ? "" : phone);

		String email = entity.getEmail();//電子郵件
		Utils.createElementWithTextContent("email", formElement, email == null ? "" : email);

		String address = entity.getAddress();//地址
		Utils.createElementWithTextContent("address", formElement, address == null ? "" : address);

		String content = entity.getContent();//諮詢或發問的內容
		Utils.createElementWithTextContent("content", formElement, content == null ? "" : content);

		ModelAndView modelAndView = new ModelAndView("reach/form");
		modelAndView.getModelMap().addAttribute(new DOMSource(document));
		return modelAndView;
	}

	@RequestMapping(value = "/{id:[\\d]+}.asp", produces = "text/html;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	private String delete(@PathVariable Integer id, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
		JSONObject jsonObject = new JSONObject();
		Short myId = services.whoTheHeckAmI(session);
		if (myId == null) {
			return jsonObject.put("reason", "未登入！").put("redirect", context.getContextPath().concat("/DengRu.asp")).toString();
		}

		Individual me = individualRepository.findOne(myId);
		if (me == null) {
			return jsonObject.put("reason", "未授權！").put("redirect", context.getContextPath().concat("/DengRu.asp")).toString();
		}

		Privilege privilege = services.whatTheFuckIsMyPrivilege(request, session);
		if (privilege == null) {
			return jsonObject.put("reason", "被禁止！").put("redirect", context.getContextPath().concat("/HouTai.asp")).toString();
		}

		Reach entity = reachRepository.findOne(id);
		if (entity == null) {
			return jsonObject.put("reason", "找不到！").put("redirect", id.toString().concat(".asp")).toString();
		}

		/*
		 登入了
		 */
		reachRepository.delete(entity);
		reachRepository.flush();
		return jsonObject.put("response", true).put("redirect", context.getContextPath().concat("/LianLuoWoMen/")).toString();
	}
}
