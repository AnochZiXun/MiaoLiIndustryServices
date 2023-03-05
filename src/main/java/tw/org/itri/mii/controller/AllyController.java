package tw.org.itri.mii.controller;

import java.util.Date;
import javax.imageio.ImageIO;
import javax.servlet.http.*;
import javax.xml.transform.dom.DOMSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.w3c.dom.*;
import tw.org.itri.mii.Utils;
import tw.org.itri.mii.entity.*;
import tw.org.itri.mii.repository.*;
import tw.org.itri.mii.service.*;

/**
 * 「友站連結」控制器
 *
 * @author	P-C Lin (a.k.a 高科技黑手)
 */
@org.springframework.stereotype.Controller
@RequestMapping("/YouZhanLianJie")
public class AllyController {

	@Autowired
	IndividualRepository individualRepository;

	@Autowired
	AllyRepository allyRepository;

	@Autowired
	Services services;

	@Autowired
	AllyService allyService;

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
		org.springframework.data.domain.Pageable pageRequest = new org.springframework.data.domain.PageRequest(number, size, org.springframework.data.domain.Sort.Direction.ASC, "id");
		org.springframework.data.domain.Page<Ally> pageOfEntities;
		if (queryString == null) {
			pageOfEntities = allyRepository.findAll(pageRequest);
		} else {
			pageOfEntities = allyRepository.findAll(tw.org.itri.mii.specification.AllySpecs.titleSpec(queryString), pageRequest);
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
		for (Ally entity : pageOfEntities.getContent()) {
			Element rowElement = Utils.createElementWithAttribute("row", contentElement, "id", entity.getId().toString());//列(帶主鍵)
			Utils.createElementWithTextContent("title", rowElement, entity.getTitle());//標題
			Utils.createElementWithTextContent("fqdn", rowElement, entity.getFqdn());//參考網址
			Utils.createElementWithTextContent("since", rowElement, Utils.formatDate(entity.getSince()));//上架日期
			Utils.createElementWithTextContent("until", rowElement, Utils.formatDate(entity.getUntil()));//下架日期
			Utils.createElementWithTextContent("censored", rowElement, Boolean.toString(entity.isCensored()));//發佈審查
		}

		ModelAndView modelAndView = new ModelAndView("ally/default");
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
		documentElement.setAttribute("title", "後臺 &#187; 友站連結 &#187; 新增");

		services.whereTheHellAmI(document, request, session);

		Element formElement = Utils.createElementWithAttribute("form", documentElement, "action", request.getRequestURI());
		formElement.setAttribute("legend", "新增友站連結");
		allyService.load(new Ally(), formElement);

		ModelAndView modelAndView = new ModelAndView("ally/form");
		modelAndView.getModelMap().addAttribute(new DOMSource(document));
		return modelAndView;
	}

	/**
	 * 新增。
	 *
	 * @param title 標題
	 * @param phone 聯絡電話
	 * @param multipartFile 縮圖
	 * @param fqdn 參考網址
	 * @param since 上架日期
	 * @param until 下架日期
	 * @param censored 發佈審查
	 * @param request 請求
	 * @param response 回應
	 * @param session 階段
	 * @return 成功導向至列表頁、失敗回新增頁
	 */
	@RequestMapping(value = "/add.asp", produces = "text/html;charset=UTF-8", method = RequestMethod.POST)
	private ModelAndView create(@RequestParam(required = false) String title, @RequestParam(defaultValue = "") String phone, @RequestParam(required = false, value = "image") MultipartFile multipartFile, @RequestParam(required = false) String fqdn, @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(defaultValue = "") Date since, @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(defaultValue = "") Date until, @RequestParam boolean censored, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
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
		documentElement.setAttribute("title", "後臺 &#187; 友站連結 &#187; 新增");

		services.whereTheHellAmI(document, request, session);

		Element formElement = Utils.createElementWithAttribute("form", documentElement, "action", request.getRequestURI());
		formElement.setAttribute("legend", "新增友站連結");
		String errorMessage = allyService.save(new Ally(), title, phone, multipartFile, fqdn, since, until, censored, formElement);
		if (errorMessage != null) {
			formElement.setAttribute("errorMessage", errorMessage);
			ModelAndView modelAndView = new ModelAndView("ally/form");
			modelAndView.getModelMap().addAttribute(new DOMSource(document));
			return modelAndView;
		}
		return new ModelAndView("redirect:/YouZhanLianJie/");
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

		Ally entity = allyRepository.findOne(id);
		if (entity == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}

		/*
		 登入了
		 */
		Document document = Utils.newDocument();
		Element documentElement = Utils.createElementWithAttribute("document", document, "contextPath", request.getContextPath());
		documentElement.setAttribute("title", "後臺 &#187; 友站連結 &#187; 修改");

		services.whereTheHellAmI(document, request, session);

		Element formElement = Utils.createElementWithAttribute("form", documentElement, "action", request.getRequestURI());
		formElement.setAttribute("legend", "修改友站連結");
		allyService.load(entity, formElement);

		ModelAndView modelAndView = new ModelAndView("ally/form");
		modelAndView.getModelMap().addAttribute(new DOMSource(document));
		return modelAndView;
	}

	/**
	 * 修改。
	 *
	 * @param id 主鍵
	 * @param title 標題
	 * @param phone 聯絡電話
	 * @param multipartFile 縮圖
	 * @param fqdn 參考網址
	 * @param since 上架日期
	 * @param until 下架日期
	 * @param censored 發佈審查
	 * @param request 請求
	 * @param response 回應
	 * @param session 階段
	 * @return 成功導向至列表頁、失敗回讀取頁
	 */
	@RequestMapping(value = "/{id:[\\d]+}.asp", produces = "text/html;charset=UTF-8", method = RequestMethod.POST)
	private ModelAndView update(@PathVariable Short id, @RequestParam(required = false) String title, @RequestParam(defaultValue = "") String phone, @RequestParam(required = false, value = "image") MultipartFile multipartFile, @RequestParam(required = false) String fqdn, @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(defaultValue = "") Date since, @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(defaultValue = "") Date until, @RequestParam boolean censored, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
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

		Ally entity = allyRepository.findOne(id);
		if (entity == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}

		/*
		 登入了
		 */
		Document document = Utils.newDocument();
		Element documentElement = Utils.createElementWithAttribute("document", document, "contextPath", request.getContextPath());
		documentElement.setAttribute("title", "後臺 &#187; 友站連結 &#187; 修改");

		services.whereTheHellAmI(document, request, session);

		Element formElement = Utils.createElementWithAttribute("form", documentElement, "action", request.getRequestURI());
		formElement.setAttribute("legend", "修改友站連結");
		String errorMessage = allyService.save(entity, title, phone, multipartFile, fqdn, since, until, censored, formElement);
		if (errorMessage != null) {
			formElement.setAttribute("errorMessage", errorMessage);
			ModelAndView modelAndView = new ModelAndView("ally/form");
			modelAndView.getModelMap().addAttribute(new DOMSource(document));
			return modelAndView;
		}
		return new ModelAndView("redirect:/YouZhanLianJie/");
	}

	/**
	 * 縮圖。
	 *
	 * @param id 主鍵
	 * @param response 回應
	 */
	@RequestMapping(value = "/{id:[\\d]+}.png", method = RequestMethod.GET)
	private void image(@PathVariable Short id, HttpServletResponse response) throws Exception {
		Ally ally = allyRepository.findOne(id);
		if (ally == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		byte[] image = ally.getImage();
		if (image.length <= 0) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		ImageIO.write(ImageIO.read(new java.io.ByteArrayInputStream(image)), "PNG", response.getOutputStream());
	}
}
