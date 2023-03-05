package tw.org.itri.mii.controller;

import javax.imageio.ImageIO;
import javax.servlet.http.*;
import javax.xml.transform.dom.DOMSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.w3c.dom.*;
import tw.org.itri.mii.Utils;
import tw.org.itri.mii.entity.*;
import tw.org.itri.mii.repository.*;
import tw.org.itri.mii.service.*;

/**
 * 「輔導案例」控制器
 *
 * @author	P-C Lin (a.k.a 高科技黑手)
 */
@org.springframework.stereotype.Controller
@RequestMapping("/FuDaoAnLi")
public class CounselingController {

	@Autowired
	CounselingRepository counselingRepository;

	@Autowired
	IndividualRepository individualRepository;

	@Autowired
	VendorRepository vendorRepository;

	@Autowired
	Services services;

	@Autowired
	CounselingService counselingService;

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
		Short taiwaneseYear = null;//年度
		try {
			String taiwaneseYearString = request.getParameter("taiwaneseYear").trim();
			if (!taiwaneseYearString.isEmpty()) {
				taiwaneseYear = Short.parseShort(taiwaneseYearString);
				searchElement.setAttribute("taiwaneseYear", taiwaneseYear.toString());
			}
		} catch (Exception ignore) {
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
		Boolean closed = null;//案件狀態
		try {
			String closedString = request.getParameter("closed").trim();
			if (!closedString.isEmpty()) {
				closed = Boolean.parseBoolean(closedString);
				searchElement.setAttribute("closed", Boolean.toString(closed));
			}
		} catch (Exception ignore) {
		}
		org.springframework.data.domain.Pageable pageRequest = new org.springframework.data.domain.PageRequest(number, size, org.springframework.data.domain.Sort.Direction.DESC, "taiwaneseYear", "id");
		org.springframework.data.domain.Page<Counseling> pageOfEntities;
		if (taiwaneseYear == null && queryString == null && closed == null) {
			pageOfEntities = counselingRepository.findAll(pageRequest);
		} else {
			pageOfEntities = counselingRepository.findAll(tw.org.itri.mii.specification.CounselingSpecs.threeSpecs(taiwaneseYear, queryString, closed), pageRequest);
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
		for (Counseling entity : pageOfEntities.getContent()) {
			Vendor vendor = entity.getVendor();
			String vendorAddress = vendor.getAddress();
			String vendorNumber = vendor.getContactNumber();
			Integer amount = entity.getAmount();

			Element rowElement = Utils.createElementWithAttribute("row", contentElement, "id", entity.getId().toString());//列(帶主鍵)
			Utils.createElementWithTextContent("taiwaneseYear", rowElement, entity.getTaiwaneseYear() == null ? "" : entity.getTaiwaneseYear().toString());//年度
			Utils.createElementWithTextContent("name", rowElement, entity.getName());//計畫名稱
			Utils.createElementWithTextContent("vendorName", rowElement, vendor == null ? "" : vendor.getName());//受輔導廠商
			Utils.createElementWithTextContent("vendorAddress", rowElement, vendorAddress == null ? "" : vendorAddress);//廠商地址
			Utils.createElementWithTextContent("vendorNumber", rowElement, vendorNumber == null ? "" : vendorNumber);//廠商電話
			Utils.createElementWithTextContent("amount", rowElement, amount == null ? "" : amount.toString());//補助金額
			Utils.createElementWithTextContent("closed", rowElement, Boolean.toString(entity.isClosed()));//案件狀態
		}

		ModelAndView modelAndView = new ModelAndView("counseling/default");
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
		documentElement.setAttribute("title", "後臺 &#187; 輔導案例 &#187; 新增");

		services.whereTheHellAmI(document, request, session);

		Element formElement = Utils.createElementWithAttribute("form", documentElement, "action", request.getRequestURI());
		formElement.setAttribute("legend", "新增輔導案例");
		counselingService.load(new Counseling(), formElement);

		ModelAndView modelAndView = new ModelAndView("counseling/form");
		modelAndView.getModelMap().addAttribute(new DOMSource(document));
		return modelAndView;

	}

	/**
	 * 新增。
	 *
	 * @param taiwaneseYear 年度
	 * @param name 計畫名稱
	 * @param vendorId 受輔導廠商(外鍵)
	 * @param amount 補助金額
	 * @param condition 業者現況
	 * @param items 輔導項目
	 * @param performance 輔導效益
	 * @param multipartFile 相關照片
	 * @param closed 案件狀態
	 * @param request 請求
	 * @param response 回應
	 * @param session 階段
	 * @return 成功導向至列表頁、失敗回新增頁
	 */
	@RequestMapping(value = "/add.asp", produces = "text/html;charset=UTF-8", method = RequestMethod.POST)
	private ModelAndView create(@RequestParam Short taiwaneseYear, @RequestParam String name, @RequestParam(required = false, value = "vendor") Short vendorId, @RequestParam Integer amount, @RequestParam String condition, @RequestParam String items, @RequestParam String performance, @RequestParam(required = false, value = "photo") MultipartFile multipartFile, @RequestParam boolean closed, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
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
		documentElement.setAttribute("title", "後臺 &#187; 輔導案例 &#187; 新增");

		services.whereTheHellAmI(document, request, session);

		Element formElement = Utils.createElementWithAttribute("form", documentElement, "action", request.getRequestURI());
		formElement.setAttribute("title", "修改輔導案例");
		String errorMessage = counselingService.save(new Counseling(), taiwaneseYear, name, vendorId, amount, condition, items, performance, multipartFile, closed, formElement);
		if (errorMessage != null) {
			formElement.setAttribute("errorMessage", errorMessage);
			ModelAndView modelAndView = new ModelAndView("counseling/form");
			modelAndView.getModelMap().addAttribute(new DOMSource(document));
			return modelAndView;
		}
		return new ModelAndView("redirect:/FuDaoAnLi/");
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

		Counseling entity = counselingRepository.findOne(id);
		if (entity == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}

		/*
		 登入了
		 */
		Document document = Utils.newDocument();
		Element documentElement = Utils.createElementWithAttribute("document", document, "contextPath", request.getContextPath());
		documentElement.setAttribute("title", "後臺 &#187; 輔導案例 &#187; 修改");

		services.whereTheHellAmI(document, request, session);

		Element formElement = Utils.createElementWithAttribute("form", documentElement, "action", request.getRequestURI());
		formElement.setAttribute("title", "修改輔導案例");
		counselingService.load(entity, formElement);

		ModelAndView modelAndView = new ModelAndView("counseling/form");
		modelAndView.getModelMap().addAttribute(new DOMSource(document));
		return modelAndView;
	}

	/**
	 * 修改。
	 *
	 * @param id 主鍵
	 * @param taiwaneseYear 年度
	 * @param name 計畫名稱
	 * @param vendorId 受輔導廠商(外鍵)
	 * @param amount 補助金額
	 * @param condition 業者現況
	 * @param items 輔導項目
	 * @param performance 輔導效益
	 * @param multipartFile 相關照片
	 * @param closed 案件狀態
	 * @param request 請求
	 * @param response 回應
	 * @param session 階段
	 * @return 成功導向至列表頁、失敗回修改頁
	 */
	@RequestMapping(value = "/{id:[\\d]+}.asp", produces = "text/html;charset=UTF-8", method = RequestMethod.POST)
	private ModelAndView update(@PathVariable Short id, @RequestParam Short taiwaneseYear, @RequestParam String name, @RequestParam(required = false, value = "vendor") Short vendorId, @RequestParam Integer amount, @RequestParam String condition, @RequestParam String items, @RequestParam String performance, @RequestParam(required = false, value = "photo") MultipartFile multipartFile, @RequestParam boolean closed, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
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

		Counseling entity = counselingRepository.findOne(id);
		if (entity == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}

		/*
		 登入了
		 */
		Document document = Utils.newDocument();
		Element documentElement = Utils.createElementWithAttribute("document", document, "contextPath", request.getContextPath());
		documentElement.setAttribute("title", "後臺 &#187; 輔導案例 &#187; 修改");

		services.whereTheHellAmI(document, request, session);

		Element formElement = Utils.createElementWithAttribute("form", documentElement, "action", request.getRequestURI());
		formElement.setAttribute("title", "修改輔導案例");
		String errorMessage = counselingService.save(entity, taiwaneseYear, name, vendorId, amount, condition, items, performance, multipartFile, closed, formElement);
		if (errorMessage != null) {
			formElement.setAttribute("errorMessage", errorMessage);
			ModelAndView modelAndView = new ModelAndView("counseling/form");
			modelAndView.getModelMap().addAttribute(new DOMSource(document));
			return modelAndView;
		}
		return new ModelAndView("redirect:/FuDaoAnLi/");
	}

	/**
	 * 相關照片。
	 *
	 * @param id 主鍵
	 * @param request 請求
	 * @param response 回應
	 * @param session 階段
	 */
	@RequestMapping(value = "/{id:[\\d]+}.png", method = RequestMethod.GET)
	private void photo(@PathVariable Short id, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
		Short myId = services.whoTheHeckAmI(session);
		if (myId == null) {
			response.sendRedirect(request.getContextPath().concat("/DengRu.asp"));//未登入則導向到登入頁面
			return;
		}

		Individual me = individualRepository.findOne(myId);
		if (me == null) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);//未授權
			return;
		}

		Privilege privilege = services.whatTheFuckIsMyPrivilege(request, session);
		if (privilege == null) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);//被禁止
			return;
		}

		Counseling counseling = counselingRepository.findOne(id);
		if (counseling == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);//找不到
			return;
		}

		byte[] image = counseling.getPhoto();
		if (image.length <= 0) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		ImageIO.write(ImageIO.read(new java.io.ByteArrayInputStream(image)), "PNG", response.getOutputStream());
	}
}
