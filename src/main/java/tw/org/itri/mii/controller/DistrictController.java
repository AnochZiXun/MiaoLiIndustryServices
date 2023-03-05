package tw.org.itri.mii.controller;

import javax.servlet.http.*;
import javax.xml.transform.dom.DOMSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.w3c.dom.*;
import tw.org.itri.mii.Utils;
import tw.org.itri.mii.entity.*;
import tw.org.itri.mii.repository.*;
import tw.org.itri.mii.service.*;

/**
 * 「行政區劃」控制器
 *
 * @author	P-C Lin (a.k.a 高科技黑手)
 */
@org.springframework.stereotype.Controller
@RequestMapping("/XingZhengQu")
public class DistrictController {

	@Autowired
	IndividualRepository individualRepository;

	@Autowired
	DistrictRepository districtRepository;

	@Autowired
	Services services;

	@Autowired
	DistrictService districtService;

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
		String queryString = null;//搜尋字串
		try {
			queryString = request.getParameter("queryString").trim();
			if (queryString.isEmpty()) {
				queryString = null;
				throw new NullPointerException();
			}
			searchElement.setAttribute("queryString", queryString);
		} catch (Exception ignore) {
		}
		org.springframework.data.domain.Pageable pageRequest = new org.springframework.data.domain.PageRequest(number, size, org.springframework.data.domain.Sort.Direction.DESC, "id", "name");
		org.springframework.data.domain.Page<District> pageOfEntities;
		if (queryString == null) {
			pageOfEntities = districtRepository.findAll(pageRequest);
		} else {
			pageOfEntities = districtRepository.findAll(tw.org.itri.mii.specification.DistrictSpecs.nameSpec(queryString), pageRequest);
		}
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
		for (District entity : pageOfEntities.getContent()) {
			District upperLevel = entity.getUpperLevel();

			Element rowElement = Utils.createElementWithAttribute("row", contentElement, "id", entity.getId().toString());//列(帶主鍵)
			Utils.createElementWithTextContent("upperLevel", rowElement, upperLevel == null ? "" : upperLevel.getName());//行政區
			Utils.createElementWithTextContent("name", rowElement, entity.getName());//工業區名稱
		}

		ModelAndView modelAndView = new ModelAndView("district/default");
		modelAndView.getModelMap().addAttribute(new DOMSource(document));
		return modelAndView;
	}

	/**
	 * 新增。
	 *
	 * @param request 請求
	 * @param response 回應
	 * @param session 階段
	 * @return 網頁
	 */
	@RequestMapping(value = "/add.asp", produces = "text/html;charset=UTF-8", method = RequestMethod.GET)
	private ModelAndView create(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
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
		documentElement.setAttribute("title", "後臺 &#187; 行政區 &#187; 新增");

		services.whereTheHellAmI(document, request, session);

		Element formElement = Utils.createElementWithAttribute("form", documentElement, "action", request.getRequestURI());
		formElement.setAttribute("legend", "新增行政區");
		districtService.load(new District(), formElement);

		ModelAndView modelAndView = new ModelAndView("district/form");
		modelAndView.getModelMap().addAttribute(new DOMSource(document));
		return modelAndView;

	}

	/**
	 * 新增。
	 *
	 * @param upperLevelId 上層行政區(外鍵)
	 * @param name 行政區名稱
	 * @param request 請求
	 * @param response 回應
	 * @param session 階段
	 * @return 成功導向至列表頁、失敗回新增頁
	 */
	@RequestMapping(value = "/add.asp", produces = "text/html;charset=UTF-8", method = RequestMethod.POST)
	private ModelAndView create(@RequestParam(required = false, value = "upperLevel") Short upperLevelId, @RequestParam String name, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
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
		documentElement.setAttribute("title", "後臺 &#187; 行政區 &#187; 新增");

		services.whereTheHellAmI(document, request, session);

		Element formElement = Utils.createElementWithAttribute("form", documentElement, "action", request.getRequestURI());
		formElement.setAttribute("legend", "新增行政區");
		String errorMessage = districtService.save(new District(), upperLevelId, name, formElement);
		if (errorMessage != null) {
			formElement.setAttribute("errorMessage", errorMessage);
			ModelAndView modelAndView = new ModelAndView("district/form");
			modelAndView.getModelMap().addAttribute(new DOMSource(document));
			return modelAndView;
		}
		return new ModelAndView("redirect:/XingZhengQu/");
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
	private ModelAndView read(@PathVariable Short id, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
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

		District entity = districtRepository.findOne(id);
		if (entity == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}

		/*
		 登入了
		 */
		Document document = Utils.newDocument();
		Element documentElement = Utils.createElementWithAttribute("document", document, "contextPath", request.getContextPath());
		documentElement.setAttribute("title", "後臺 &#187; 行政區 &#187; 修改");

		services.whereTheHellAmI(document, request, session);

		Element formElement = Utils.createElementWithAttribute("form", documentElement, "action", request.getRequestURI());
		formElement.setAttribute("legend", "修改行政區");
		districtService.load(entity, formElement);

		ModelAndView modelAndView = new ModelAndView("district/form");
		modelAndView.getModelMap().addAttribute(new DOMSource(document));
		return modelAndView;
	}

	/**
	 * 修改。
	 *
	 * @param id 主鍵
	 * @param upperLevelId 行政區(外鍵)
	 * @param name 行政區名稱
	 * @param request 請求
	 * @param response 回應
	 * @param session 階段
	 * @return 成功導向至列表頁、失敗回讀取頁
	 */
	@RequestMapping(value = "/{id:[\\d]+}.asp", produces = "text/html;charset=UTF-8", method = RequestMethod.POST)
	private ModelAndView update(@PathVariable Short id, @RequestParam(required = false, value = "upperLevel") Short upperLevelId, @RequestParam String name, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
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

		District entity = districtRepository.findOne(id);
		if (entity == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}

		/*
		 登入了
		 */
		Document document = Utils.newDocument();
		Element documentElement = Utils.createElementWithAttribute("document", document, "contextPath", request.getContextPath());
		documentElement.setAttribute("title", "後臺 &#187; 行政區 &#187; 修改");

		services.whereTheHellAmI(document, request, session);

		Element formElement = Utils.createElementWithAttribute("form", documentElement, "action", request.getRequestURI());
		formElement.setAttribute("legend", "修改行政區");
		String errorMessage = districtService.save(entity, upperLevelId, name, formElement);
		if (errorMessage != null) {
			formElement.setAttribute("errorMessage", errorMessage);
			ModelAndView modelAndView = new ModelAndView("district/form");
			modelAndView.getModelMap().addAttribute(new DOMSource(document));
			return modelAndView;
		}
		return new ModelAndView("redirect:/XingZhengQu/");
	}
}
