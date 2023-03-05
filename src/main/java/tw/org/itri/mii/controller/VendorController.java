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
 * 「廠商」控制器
 *
 * @author	P-C Lin (a.k.a 高科技黑手)
 */
@org.springframework.stereotype.Controller
@RequestMapping("/ChangShang")
public class VendorController {

	@Autowired
	IndividualRepository individualRepository;

	@Autowired
	VendorRepository vendorRepository;

	@Autowired
	Services services;

	@Autowired
	VendorService vendorService;

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
				throw new NullPointerException();
			}
			searchElement.setAttribute("queryString", queryString);
		} catch (Exception ignore) {
		}
		org.springframework.data.domain.Pageable pageRequest = new org.springframework.data.domain.PageRequest(number, size, org.springframework.data.domain.Sort.Direction.DESC, "id", "name");
		org.springframework.data.domain.Page<Vendor> pageOfEntities;
		if (queryString == null) {
			pageOfEntities = vendorRepository.findAll(pageRequest);
		} else {
			pageOfEntities = vendorRepository.findAll(tw.org.itri.mii.specification.VendorSpecs.nameSpec(queryString), pageRequest);
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
		for (Vendor entity : pageOfEntities.getContent()) {
			tw.org.itri.mii.entity.IndustrialDistrict industrialDistrict = entity.getIndustrialDistrict();
			String contactEmail = entity.getContactEmail();

			Element rowElement = Utils.createElementWithAttribute("row", contentElement, "id", entity.getId().toString());//列(帶主鍵)
			Utils.createElementWithTextContent("name", rowElement, entity.getName());//廠商名稱
			Utils.createElementWithTextContent("industrialDistrict", rowElement, industrialDistrict == null ? "" : industrialDistrict.getName());//工業區
			Utils.createElementWithTextContent("contactEmail", rowElement, contactEmail == null ? "" : contactEmail);//聯絡人電子郵件
		}

		ModelAndView modelAndView = new ModelAndView("vendor/default");
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
		documentElement.setAttribute("title", "後臺 &#187; 廠商 &#187; 新增");

		services.whereTheHellAmI(document, request, session);

		Element formElement = Utils.createElementWithAttribute("form", documentElement, "action", request.getRequestURI());
		formElement.setAttribute("legend", "新增廠商");
		vendorService.load(new Vendor(), formElement);

		ModelAndView modelAndView = new ModelAndView("vendor/form");
		modelAndView.getModelMap().addAttribute(new DOMSource(document));
		return modelAndView;

	}

	/**
	 * 新增。
	 *
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
	 * @param request 請求
	 * @param response 回應
	 * @param session 階段
	 * @return 成功導向至列表頁、失敗回新增頁
	 */
	@RequestMapping(value = "/add.asp", produces = "text/html;charset=UTF-8", method = RequestMethod.POST)
	private ModelAndView create(@RequestParam String name, @RequestParam String unifiedBusinessNumber, @RequestParam String address, @RequestParam(required = false, value = "industrialDistrict") Short industrialDistrictId, @RequestParam Short employeeCount, @RequestParam(required = false) String contactTitle, @RequestParam String contactName, @RequestParam String contactNumber, @RequestParam String contactEmail, @RequestParam(required = false) String contactFax, @RequestParam(required = false) Integer capital, @RequestParam(required = false) Integer turnover, @RequestParam String business, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
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
		documentElement.setAttribute("title", "後臺 &#187; 廠商 &#187; 新增");

		services.whereTheHellAmI(document, request, session);

		Element formElement = Utils.createElementWithAttribute("form", documentElement, "action", request.getRequestURI());
		formElement.setAttribute("legend", "新增廠商");
		String errorMessage = vendorService.save(new Vendor(), name, unifiedBusinessNumber, address, industrialDistrictId, employeeCount, contactTitle, contactName, contactNumber, contactEmail, contactFax, capital, turnover, business, formElement);
		if (errorMessage != null) {
			formElement.setAttribute("errorMessage", errorMessage);
			ModelAndView modelAndView = new ModelAndView("vendor/form");
			modelAndView.getModelMap().addAttribute(new DOMSource(document));
			return modelAndView;
		}
		return new ModelAndView("redirect:/ChangShang/");
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

		Vendor entity = vendorRepository.findOne(id);
		if (entity == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}

		/*
		 登入了
		 */
		Document document = Utils.newDocument();
		Element documentElement = Utils.createElementWithAttribute("document", document, "contextPath", request.getContextPath());
		documentElement.setAttribute("title", "後臺 &#187; 廠商 &#187; 修改");

		services.whereTheHellAmI(document, request, session);

		Element formElement = Utils.createElementWithAttribute("form", documentElement, "action", request.getRequestURI());
		formElement.setAttribute("legend", "修改廠商");
		vendorService.load(entity, formElement);

		ModelAndView modelAndView = new ModelAndView("vendor/form");
		modelAndView.getModelMap().addAttribute(new DOMSource(document));
		return modelAndView;
	}

	/**
	 * 修改。
	 *
	 * @param id 主鍵
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
	 * @param request 請求
	 * @param response 回應
	 * @param session 階段
	 * @return 成功導向至列表頁、失敗回讀取頁
	 */
	@RequestMapping(value = "/{id:[\\d]+}.asp", produces = "text/html;charset=UTF-8", method = RequestMethod.POST)
	private ModelAndView update(@PathVariable Short id, @RequestParam String name, @RequestParam String unifiedBusinessNumber, @RequestParam String address, @RequestParam(required = false, value = "industrialDistrict") Short industrialDistrictId, @RequestParam Short employeeCount, @RequestParam(required = false) String contactTitle, @RequestParam String contactName, @RequestParam String contactNumber, @RequestParam String contactEmail, @RequestParam(required = false) String contactFax, @RequestParam(required = false) Integer capital, @RequestParam(required = false) Integer turnover, @RequestParam String business, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
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

		Vendor entity = vendorRepository.findOne(id);
		if (entity == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}

		/*
		 登入了
		 */
		Document document = Utils.newDocument();
		Element documentElement = Utils.createElementWithAttribute("document", document, "contextPath", request.getContextPath());
		documentElement.setAttribute("title", "後臺 &#187; 廠商 &#187; 修改");

		services.whereTheHellAmI(document, request, session);

		Element formElement = Utils.createElementWithAttribute("form", documentElement, "action", request.getRequestURI());
		formElement.setAttribute("legend", "修改廠商");
		String errorMessage = vendorService.save(entity, name, unifiedBusinessNumber, address, industrialDistrictId, employeeCount, contactTitle, contactName, contactNumber, contactEmail, contactFax, capital, turnover, business, formElement);
		if (errorMessage != null) {
			formElement.setAttribute("errorMessage", errorMessage);
			ModelAndView modelAndView = new ModelAndView("vendor/form");
			modelAndView.getModelMap().addAttribute(new DOMSource(document));
			return modelAndView;
		}
		return new ModelAndView("redirect:/ChangShang/");
	}

	/**
	 * 密碼。
	 *
	 * @param id 主鍵
	 * @param request 請求
	 * @param response 回應
	 * @param session 階段
	 * @return 網頁
	 */
	@RequestMapping(value = "/{id:[\\d]+}/shadow.asp", produces = "text/html;charset=UTF-8", method = RequestMethod.GET)
	private ModelAndView shadow(@PathVariable Short id, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
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

		Vendor vendor = vendorRepository.findOne(id);
		if (vendor == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}

		/*
		 登入了
		 */
		Document document = Utils.newDocument();
		Element documentElement = Utils.createElementWithAttribute("document", document, "contextPath", request.getContextPath());

		services.whereTheHellAmI(document, request, session);

		Element formElement = Utils.createElementWithAttribute("form", documentElement, "action", request.getRequestURI());
		formElement.setAttribute("legend", "修改密碼");

		Utils.createElement("shadow", formElement);

		ModelAndView modelAndView = new ModelAndView("vendor/shadow");
		modelAndView.getModelMap().addAttribute(new DOMSource(document));
		return modelAndView;
	}

	/**
	 * 密碼。
	 *
	 * @param id 主鍵
	 * @param shadow 密碼
	 * @param request 請求
	 * @param response 回應
	 * @param session 階段
	 * @return 成功導向至列表頁、失敗回修改密碼頁
	 */
	@RequestMapping(value = "/{id:[\\d]+}/shadow.asp", produces = "text/html;charset=UTF-8", method = RequestMethod.POST)
	@SuppressWarnings("UnusedAssignment")
	private ModelAndView shadow(@PathVariable Short id, @RequestParam(defaultValue = "", value = "shadow") String shadow, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
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

		Vendor entity = vendorRepository.findOne(id);
		if (entity == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}

		/*
		 登入了
		 */
		Document document = Utils.newDocument();
		Element documentElement = Utils.createElementWithAttribute("document", document, "contextPath", request.getContextPath());

		services.whereTheHellAmI(document, request, session);

		Element formElement = Utils.createElementWithAttribute("form", documentElement, "action", request.getRequestURI());
		formElement.setAttribute("legend", "修改密碼");

		Utils.createElement("shadow", formElement);

		String errorMessage = null;
		if (shadow.isEmpty()) {
			errorMessage = "密碼不得為空！";
		}
		try {
			shadow = Utils.md5(shadow);
		} catch (Exception ignore) {
			errorMessage = "密碼加密時發生不明的錯誤！";
		}
		if (errorMessage == null) {
			entity.setShadow(shadow);
			vendorRepository.saveAndFlush(entity);
		} else {
			formElement.setAttribute("errorMessage", errorMessage);
			ModelAndView modelAndView = new ModelAndView("vendor/shadow");
			modelAndView.getModelMap().addAttribute(new DOMSource(document));
			return modelAndView;
		}

		return new ModelAndView("redirect:/ChangShang/");
	}
}
