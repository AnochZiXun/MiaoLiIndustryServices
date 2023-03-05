package tw.org.itri.mii.controller;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.mail.internet.InternetAddress;
import javax.servlet.http.*;
import javax.xml.transform.dom.DOMSource;
import org.apache.commons.io.IOUtils;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.validator.routines.EmailValidator;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.w3c.dom.*;
import tw.org.itri.mii.Utils;
import tw.org.itri.mii.entity.*;
import tw.org.itri.mii.repository.*;
import tw.org.itri.mii.service.Services;

/**
 * 「根」控制器
 *
 * @author	P-C Lin (a.k.a 高科技黑手)
 */
@org.springframework.stereotype.Controller
@RequestMapping("/")
public class RootController {

	@Autowired
	private AllyRepository allyRepository;

	@Autowired
	private CounselingRepository counselingRepository;

	@Autowired
	private DownloadRepository downloadRepository;

	@Autowired
	private EventRepository eventRepository;

	@Autowired
	private IndividualRepository individualRepository;

	@Autowired
	private NewsRepository newsRepository;

	@Autowired
	private ReachRepository reachRepository;

	@Autowired
	private javax.servlet.ServletContext context;

	@Autowired
	private Services services;

	/**
	 * 首頁。
	 *
	 * @return 網頁
	 */
	@RequestMapping(value = "/", produces = "text/html;charset=UTF-8", method = RequestMethod.GET)
	private ModelAndView welcome() throws Exception {
		Document document = Utils.newDocument();
		Element documentElement = Utils.createElementWithAttribute("document", document, "contextPath", context.getContextPath());
		documentElement.setAttribute("jQueryVersion", Utils.JQUERY_VERSION);

		Utils.createElement("header", documentElement);
		services.buildFrontEndAside(document);
		services.buildFrontEndCounter(document);

		Element eventsElement = Utils.createElement("events", documentElement);
		for (Event event : eventRepository.findTop4Displayable()) {
			Utils.createElementWithTextContentAndAttribute("event", eventsElement, event.getTitle(), "id", event.getId().toString());
		}

		Element newsesElement = Utils.createElement("newses", documentElement);
		for (News news : newsRepository.findTop10Displayable()) {
			Utils.createElementWithTextContentAndAttribute("news", newsesElement, news.getTitle(), "id", news.getId().toString());
		}

		ModelAndView modelAndView = new ModelAndView("welcome");
		modelAndView.getModelMap().addAttribute(new DOMSource(document));
		return modelAndView;
	}

	/**
	 * 網站導覽。
	 *
	 * @return 網頁
	 */
	@RequestMapping(value = "/sitemap.asp", produces = "text/html;charset=UTF-8", method = RequestMethod.GET)
	private ModelAndView sitemap() throws Exception {
		Document document = Utils.newDocument();
		Element documentElement = Utils.createElementWithAttribute("document", document, "contextPath", context.getContextPath());
		documentElement.setAttribute("jQueryVersion", Utils.JQUERY_VERSION);

		Utils.createElement("header", documentElement);
		services.buildFrontEndAside(document);
		services.buildFrontEndCounter(document);

		ModelAndView modelAndView = new ModelAndView("sitemap");
		modelAndView.getModelMap().addAttribute(new DOMSource(document));
		return modelAndView;
	}

	/**
	 * 登入。
	 *
	 * @param request 請求
	 * @param session 階段
	 * @return 如已登入顯示後臺首頁、反之則顯示登入頁面
	 */
	@RequestMapping(value = "/DengRu.asp", produces = "text/html;charset=UTF-8", method = RequestMethod.GET)
	private ModelAndView dengRu(HttpServletRequest request, HttpSession session) throws Exception {
		Short myId = services.whoTheHeckAmI(session);
		if (myId != null) {
			return new ModelAndView("redirect:/HouTai.asp");//已登入則導向到後臺首頁
		}

		/*
		 未登入
		 */
		Document document = Utils.newDocument();
		Element documentElement = Utils.createElementWithAttribute("document", document, "contextPath", context.getContextPath());
		documentElement.setAttribute("jQuery", Utils.JQUERY_VERSION);

		Utils.createElement("header", documentElement);
		services.buildFrontEndAside(document);
		services.buildFrontEndCounter(document);

		Utils.createElementWithAttribute("form", documentElement, "requestURI", request.getRequestURI());

		ModelAndView modelAndView = new ModelAndView("DengRu");
		modelAndView.getModelMap().addAttribute(new DOMSource(document));
		return modelAndView;
	}

	/**
	 * 登入。
	 *
	 * @param email 電子郵件(帳號)
	 * @param shadow 密碼
	 * @param request 請求
	 * @param session 階段
	 * @return 如已登入或登入成功則顯示後臺首頁、反之顯示登入頁面
	 */
	@RequestMapping(value = "/DengRu.asp", produces = "text/html;charset=UTF-8", method = RequestMethod.POST)
	@SuppressWarnings({"UnusedAssignment", "null"})
	private ModelAndView dengRu(@RequestParam(defaultValue = "", value = "email") String email, @RequestParam(defaultValue = "", value = "shadow") String shadow, HttpServletRequest request, HttpSession session) throws Exception {
		Short myId = services.whoTheHeckAmI(session);
		if (myId != null) {
			return new ModelAndView("redirect:/HouTai.asp");//已登入則導向到後臺首頁
		}

		/*
		 登入了
		 */
		String errorMessage = null;

		//帳號
		try {
			email = email.trim().toLowerCase();
			if (email.isEmpty()) {
				throw new NullPointerException();
			}
			new InternetAddress(email).validate();
		} catch (Exception ignore) {
			errorMessage = "錯誤的電子郵件(帳號)！";
		}
		//密碼
		if (shadow.isEmpty()) {
			errorMessage = "密碼不得為空！";
		}
		shadow = Utils.md5(shadow);

		Individual me = individualRepository.findOneByEmailAndShadow(email, shadow);
		if (me == null) {
			errorMessage = "帳號或密碼錯誤，請重新登入！";
		} else {
			/*
			 成功登入
			 */
			session.setAttribute("me", me.getId());
			return new ModelAndView("redirect:/HouTai.asp");//導向到後臺首頁
		}

		Document document = Utils.newDocument();
		Element documentElement = Utils.createElementWithAttribute("document", document, "contextPath", context.getContextPath());
		documentElement.setAttribute("jQuery", Utils.JQUERY_VERSION);

		Utils.createElement("header", documentElement);
		services.buildFrontEndAside(document);
		services.buildFrontEndCounter(document);

		Element formElement = Utils.createElementWithAttribute("form", documentElement, "requestURI", request.getRequestURI());
		if (errorMessage != null) {
			Utils.createElementWithTextContent("errorMessage", formElement, errorMessage);
		}
		Utils.createElementWithTextContent("email", formElement, email);

		ModelAndView modelAndView = new ModelAndView("DengRu");
		modelAndView.getModelMap().addAttribute(new DOMSource(document));
		return modelAndView;
	}

	/**
	 * 登出。
	 *
	 * @param session 階段
	 * @return 導向到前臺首頁
	 */
	@RequestMapping(value = "/DengChu.asp", produces = "text/html;charset=UTF-8", method = RequestMethod.GET)
	private ModelAndView dengChu(HttpSession session) throws Exception {
		session.invalidate();

		return new ModelAndView("redirect:/");//導向到首頁
	}

	/**
	 * 計畫簡介。
	 *
	 * @return 網頁
	 */
	@RequestMapping(value = "/introduction.asp", produces = "text/html;charset=UTF-8", method = RequestMethod.GET)
	private ModelAndView introduction() throws Exception {
		Document document = Utils.newDocument();
		Element documentElement = Utils.createElementWithAttribute("document", document, "contextPath", context.getContextPath());
		documentElement.setAttribute("jQueryVersion", Utils.JQUERY_VERSION);

		Utils.createElementWithAttribute("header", documentElement, "on", "計畫簡介");
		services.buildFrontEndAside(document);
		services.buildFrontEndCounter(document);

		Element contentElement = Utils.createElement("content", documentElement);

		Path path = Paths.get(services.getContextRealPath(), "WEB-INF", "htm", "JiHuaJianJie.htm");
		StringBuilder stringBuilder = new StringBuilder();
		for (String line : Files.readAllLines(path, Charset.forName("UTF-8"))) {
			stringBuilder.append(line);
		}
		Utils.createElementWithTextContent("wysiwyg", contentElement, stringBuilder.toString());

		ModelAndView modelAndView = new ModelAndView("introduction");
		modelAndView.getModelMap().addAttribute(new DOMSource(document));
		return modelAndView;
	}

	/**
	 * 最新消息(列表)。
	 *
	 * @return 網頁
	 */
	@RequestMapping(value = "/newses.asp", produces = "text/html;charset=UTF-8", method = RequestMethod.GET)
	private ModelAndView newses(@RequestParam(value = "p", defaultValue = "0") Integer page) throws Exception {
		Document document = Utils.newDocument();
		Element documentElement = Utils.createElementWithAttribute("document", document, "contextPath", context.getContextPath());
		documentElement.setAttribute("jQueryVersion", Utils.JQUERY_VERSION);

		Utils.createElementWithAttribute("header", documentElement, "on", "最新消息");
		services.buildFrontEndAside(document);
		services.buildFrontEndCounter(document);

		Pageable pageRequest = new PageRequest(page, 10);
		Page<News> pageOfEntities = newsRepository.findDisplayable(pageRequest);
		Integer totalPages = pageOfEntities.getTotalPages();
		Long totalElements = pageOfEntities.getTotalElements();
		Element paginationElement = Utils.createElement("pagination", documentElement);
		if (pageOfEntities.hasPrevious()) {
			paginationElement.setAttribute("previous", Integer.toString(page - 1));
		}
		paginationElement.setAttribute("page", page.toString());
		for (Integer i = 0; i < totalPages; i++) {
			Element optionElement = Utils.createElementWithTextContentAndAttribute("option", paginationElement, Integer.toString(i + 1), "value", i.toString());
			if (page.equals(i)) {
				optionElement.setAttribute("selected", null);
			}
		}
		paginationElement.setAttribute("totalPages", totalPages.toString());
		paginationElement.setAttribute("totalElements", totalElements.toString());
		if (pageOfEntities.hasNext()) {
			paginationElement.setAttribute("next", Integer.toString(page + 1));
		}

		Element contentElement = Utils.createElement("content", documentElement);
		for (News news : pageOfEntities.getContent()) {
			Date since = news.getSince();

			Element rowElement = Utils.createElementWithAttribute("row", contentElement, "id", news.getId().toString());
			Utils.createElementWithTextContent("since", rowElement, Utils.formatDate(since));//上架日期
			Utils.createElementWithTextContent("title", rowElement, news.getTitle());//標題
		}

		ModelAndView modelAndView = new ModelAndView("newses");
		modelAndView.getModelMap().addAttribute(new DOMSource(document));
		return modelAndView;
	}

	/**
	 * 最新消息(單項)。
	 *
	 * @param id 主鍵
	 * @return 網頁
	 */
	@RequestMapping(value = "/news/{id:[\\d]+}/", produces = "text/html;charset=UTF-8", method = RequestMethod.GET)
	private ModelAndView news(@PathVariable Short id, HttpServletResponse response) throws Exception {
		News news = newsRepository.findOne(id);
		if (news == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}

		Document document = Utils.newDocument();
		Element documentElement = Utils.createElementWithAttribute("document", document, "contextPath", context.getContextPath());
		documentElement.setAttribute("jQueryVersion", Utils.JQUERY_VERSION);

		Utils.createElement("header", documentElement);
		services.buildFrontEndAside(document);
		services.buildFrontEndCounter(document);

		Utils.createElementWithTextContentAndAttribute("content", documentElement, news.getContent(), "title", news.getTitle());

		ModelAndView modelAndView = new ModelAndView("news");
		modelAndView.getModelMap().addAttribute(new DOMSource(document));
		return modelAndView;
	}

	/**
	 * 活動照片(列表)。
	 *
	 * @return 網頁
	 */
	@RequestMapping(value = "/events.asp", produces = "text/html;charset=UTF-8", method = RequestMethod.GET)
	private ModelAndView events(@RequestParam(value = "p", defaultValue = "0") Integer page) throws Exception {
		Document document = Utils.newDocument();
		Element documentElement = Utils.createElementWithAttribute("document", document, "contextPath", context.getContextPath());
		documentElement.setAttribute("jQueryVersion", Utils.JQUERY_VERSION);

		Utils.createElementWithAttribute("header", documentElement, "on", "活動照片");
		services.buildFrontEndAside(document);
		services.buildFrontEndCounter(document);

		Pageable pageRequest = new PageRequest(page, 10);
		Page<Event> pageOfEntities = eventRepository.findDisplayable(pageRequest);
		Integer totalPages = pageOfEntities.getTotalPages();
		Long totalElements = pageOfEntities.getTotalElements();
		Element paginationElement = Utils.createElement("pagination", documentElement);
		if (pageOfEntities.hasPrevious()) {
			paginationElement.setAttribute("previous", Integer.toString(page - 1));
		}
		paginationElement.setAttribute("page", page.toString());
		for (Integer i = 0; i < totalPages; i++) {
			Element optionElement = Utils.createElementWithTextContentAndAttribute("option", paginationElement, Integer.toString(i + 1), "value", i.toString());
			if (page.equals(i)) {
				optionElement.setAttribute("selected", null);
			}
		}
		paginationElement.setAttribute("totalPages", totalPages.toString());
		paginationElement.setAttribute("totalElements", totalElements.toString());
		if (pageOfEntities.hasNext()) {
			paginationElement.setAttribute("next", Integer.toString(page + 1));
		}

		Integer sort = 1;
		Element contentElement = Utils.createElement("content", documentElement);
		for (Event event : pageOfEntities.getContent()) {
			Date since = event.getSince();

			Element rowElement = Utils.createElementWithAttribute("row", contentElement, "id", event.getId().toString());
			Utils.createElementWithTextContent("since", rowElement, Utils.formatDate(since));//上架日期
			Utils.createElementWithTextContent("title", rowElement, event.getTitle());//標題
			Utils.createElementWithTextContent("sort", rowElement, sort.toString());//排序
			sort++;
		}

		ModelAndView modelAndView = new ModelAndView("events");
		modelAndView.getModelMap().addAttribute(new DOMSource(document));
		return modelAndView;
	}

	/**
	 * 活動照片(單項)。
	 *
	 * @param id 主鍵
	 * @return 網頁
	 */
	@RequestMapping(value = "/event/{id:[\\d]+}/", produces = "text/html;charset=UTF-8", method = RequestMethod.GET)
	private ModelAndView event(@PathVariable Short id, HttpServletResponse response) throws Exception {
		Event event = eventRepository.findOne(id);
		if (event == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}

		Document document = Utils.newDocument();
		Element documentElement = Utils.createElementWithAttribute("document", document, "contextPath", context.getContextPath());
		documentElement.setAttribute("jQueryVersion", Utils.JQUERY_VERSION);

		Utils.createElement("header", documentElement);
		services.buildFrontEndAside(document);
		services.buildFrontEndCounter(document);

		Utils.createElementWithTextContentAndAttribute("content", documentElement, event.getContent(), "title", event.getTitle());

		ModelAndView modelAndView = new ModelAndView("event");
		modelAndView.getModelMap().addAttribute(new DOMSource(document));
		return modelAndView;
	}

	/**
	 * 活動照片的列表圖片。
	 *
	 * @param id 主鍵
	 * @param response 回應
	 */
	@RequestMapping(value = "/event/{id:[\\d]+}.png", method = RequestMethod.GET)
	private void photo(@PathVariable Short id, HttpServletResponse response) throws Exception {
		Event event = eventRepository.findOne(id);
		if (event == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);//找不到
			return;
		}

		byte[] image = event.getImage();
		if (image.length <= 0) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		ImageIO.write(ImageIO.read(new java.io.ByteArrayInputStream(image)), "PNG", response.getOutputStream());
	}

	/**
	 * 活動公告
	 *
	 * @return 網頁
	 */
	@RequestMapping(value = "/application.asp", produces = "text/html;charset=UTF-8", method = RequestMethod.GET)
	private ModelAndView aPPLiCaTioN() throws Exception {
		Document document = Utils.newDocument();
		Element documentElement = Utils.createElementWithAttribute("document", document, "contextPath", context.getContextPath());
		documentElement.setAttribute("jQueryVersion", Utils.JQUERY_VERSION);

		Utils.createElementWithAttribute("header", documentElement, "on", "活動公告");
		services.buildFrontEndAside(document);
		services.buildFrontEndCounter(document);

		ModelAndView modelAndView = new ModelAndView("aPPLiCaTioN6");
		modelAndView.getModelMap().addAttribute(new DOMSource(document));
		return modelAndView;
	}

	/**
	 * 活動公告
	 *
	 * @return 網頁
	 */
	@RequestMapping(value = "/application5.asp", produces = "text/html;charset=UTF-8", method = RequestMethod.GET)
	private ModelAndView aPPLiCaTioN5() throws Exception {
		Document document = Utils.newDocument();
		Element documentElement = Utils.createElementWithAttribute("document", document, "contextPath", context.getContextPath());
		documentElement.setAttribute("jQueryVersion", Utils.JQUERY_VERSION);

		Utils.createElementWithAttribute("header", documentElement, "on", "活動公告");
		services.buildFrontEndAside(document);
		services.buildFrontEndCounter(document);

		ModelAndView modelAndView = new ModelAndView("aPPLiCaTioN5");
		modelAndView.getModelMap().addAttribute(new DOMSource(document));
		return modelAndView;
	}

	/**
	 * 活動公告(第五次)
	 *
	 * @param request 請求
	 * @return 成功回首頁；失敗重來。
	 * @since 2016-06-17
	 */
	@RequestMapping(value = "/application.asp", produces = "text/html;charset=UTF-8", method = RequestMethod.POST)
	private ModelAndView aPPLiCaTioN(@RequestParam(required = false) String organization, @RequestParam(required = false) String number, @RequestParam(required = false) String phone, @RequestParam(required = false) String fax, @RequestParam(required = false) String address, @RequestParam(required = false) String fullname, @RequestParam(required = false) String title, @RequestParam(required = false) String cellular, @RequestParam(required = false) String email, @RequestParam(required = false) String vegetarian, HttpServletRequest request) throws Exception {
		Document document = Utils.newDocument();
		Element documentElement = Utils.createElementWithAttribute("document", document, "contextPath", context.getContextPath());
		documentElement.setAttribute("jQueryVersion", Utils.JQUERY_VERSION);

		Utils.createElementWithAttribute("header", documentElement, "on", "活動公告");
		services.buildFrontEndAside(document);
		services.buildFrontEndCounter(document);

		String errorMessage = null;
		Element formElement = Utils.createElementWithAttribute("form", documentElement, "action", request.getRequestURI());

		/*
		 公司名稱
		 */
		try {
			organization = organization.trim();
			if (organization.isEmpty()) {
				errorMessage = "公司名稱為必填。";
				throw new NullPointerException();
			}
		} catch (Exception ignore) {
			organization = null;
		}
		Utils.createElementWithTextContent("organization", formElement, organization == null ? "" : organization);

		/*
		 統一編號
		 */
		try {
			number = number.trim();
			if (number.isEmpty()) {
				throw new NullPointerException();
			}
		} catch (Exception ignore) {
			number = null;
		}
		Utils.createElementWithTextContent("number", formElement, number == null ? "" : number);

		/*
		 公司電話
		 */
		try {
			phone = phone.trim();
			if (phone.isEmpty()) {
				throw new NullPointerException();
			}
		} catch (Exception ignore) {
			phone = null;
		}
		Utils.createElementWithTextContent("phone", formElement, phone == null ? "" : phone);

		/*
		 公司傳真
		 */
		try {
			fax = fax.trim();
			if (fax.isEmpty()) {
				throw new NullPointerException();
			}
		} catch (Exception ignore) {
			fax = null;
		}
		Utils.createElementWithTextContent("fax", formElement, fax == null ? "" : fax);

		/*
		 公司地址
		 */
		try {
			address = address.trim();
			if (address.isEmpty()) {
				throw new NullPointerException();
			}
		} catch (Exception ignore) {
			address = null;
		}
		Utils.createElementWithTextContent("address", formElement, address == null ? "" : address);

		/*
		 姓名
		 */
		try {
			fullname = fullname.trim();
			if (fullname.isEmpty()) {
				errorMessage = "姓名為必填。";
				throw new NullPointerException();
			}
		} catch (Exception ignore) {
			fullname = null;
		}
		Utils.createElementWithTextContent("fullname", formElement, fullname == null ? "" : fullname);

		/*
		 部門/職稱
		 */
		try {
			title = title.trim();
			if (title.isEmpty()) {
				errorMessage = "部門/職稱為必填。";
				throw new NullPointerException();
			}
		} catch (Exception ignore) {
			title = null;
		}
		Utils.createElementWithTextContent("title", formElement, title == null ? "" : title);

		/*
		 行動電話
		 */
		try {
			cellular = cellular.trim();
			if (cellular.isEmpty()) {
				errorMessage = "行動電話為必填。";
				throw new NullPointerException();
			}
		} catch (Exception ignore) {
			cellular = null;
		}
		Utils.createElementWithTextContent("cellular", formElement, cellular == null ? "" : cellular);

		/*
		 電子郵件
		 */
		try {
			email = email.trim();
			if (email.isEmpty()) {
				errorMessage = "電子郵件為必填。";
				throw new NullPointerException();
			}

			if (!EmailValidator.getInstance(false, false).isValid(email)) {
				errorMessage = "錯誤的電子郵件格式！";
			}
		} catch (Exception ignore) {
			email = null;
		}
		Utils.createElementWithTextContent("email", formElement, email == null ? "" : email);

		/*
		 便當
		 */
		if (errorMessage != null) {
			formElement.setAttribute("errorMessage", errorMessage);

			ModelAndView modelAndView = new ModelAndView("aPPLiCaTioN5");
			modelAndView.getModelMap().addAttribute(new DOMSource(document));
			return modelAndView;
		}

		StringBuilder stringBuilder = new StringBuilder("<TABLE border='1' cellspacing='3' cellpadding='3'>");
		stringBuilder.append("<TR><TH>公司名稱</TH><TD>").append(organization == null ? "" : organization).append("</TD></TR>");
		stringBuilder.append("<TR><TH>統一編號</TH><TD>").append(number == null ? "" : number).append("</TD></TR>");
		stringBuilder.append("<TR><TH>公司電話</TH><TD>").append(phone == null ? "" : phone).append("</TD></TR>");
		stringBuilder.append("<TR><TH>公司傳真</TH><TD>").append(fax == null ? "" : fax).append("</TD></TR>");
		stringBuilder.append("<TR><TH>公司地址</TH><TD>").append(address == null ? "" : address).append("</TD></TR>");
		stringBuilder.append("<TR><TH>姓名</TH><TD>").append(fullname == null ? "" : fullname).append("</TD></TR>");
		stringBuilder.append("<TR><TH>部門/職稱</TH><TD>").append(title == null ? "" : title).append("</TD></TR>");
		stringBuilder.append("<TR><TH>行動電話</TH><TD>").append(cellular == null ? "" : cellular).append("</TD></TR>");
		stringBuilder.append("<TR><TH>電子郵件</TH><TD>").append(email == null ? "" : email).append("</TD></TR>");
		stringBuilder.append("<TR><TH>便當</TH><TD>").append(vegetarian == null ? "" : vegetarian).append("</TD></TR>");
		stringBuilder.append("</TABLE>");

		HtmlEmail htmlEmail = new HtmlEmail();
		htmlEmail.setHostName("smtp.googlemail.com");
		htmlEmail.setSmtpPort(465);
		htmlEmail.setAuthentication("itri037670050@gmail.com", "mii-itri.org");
		htmlEmail.setSSLOnConnect(true);
		htmlEmail.setCharset("UTF-8");
		htmlEmail.setFrom(email, fullname, "UTF-8");
		htmlEmail.addTo("mii@itri.org.tw");
		htmlEmail.addCc(email, fullname, "UTF-8");
		htmlEmail.setSubject("活動報名：苗栗智慧樂活網路行銷學程");
		htmlEmail.setHtmlMsg(stringBuilder.toString());
		htmlEmail.send();

		return new ModelAndView("redirect:".concat(context.getContextPath().concat("/")));
	}

	/**
	 * 活動報名(第一、二、三次)
	 *
	 * @param organization 公司名稱
	 * @param address 公司地址
	 * @param phone 公司電話
	 * @param fax 公司傳真
	 * @param unifiedBusinessNumber 統一編號
	 * @param fullname 姓名
	 * @param department 部門
	 * @param title 職稱
	 * @param cellular 行動電話
	 * @param email 電子郵件
	 * @param vegetarian 便當
	 * @param request 請求
	 * @return 成功回首頁；失敗重來。
	 */
	@RequestMapping(value = "/application123.asp", produces = "text/html;charset=UTF-8", method = RequestMethod.POST)
	private ModelAndView aPPLiCaTioN3(@RequestParam(required = false) String organization, @RequestParam(required = false) String fullname, @RequestParam(required = false) String title, @RequestParam(required = false) String phone, @RequestParam(required = false) String email, @RequestParam boolean vegetarian, HttpServletRequest request) throws Exception {
		Document document = Utils.newDocument();
		Element documentElement = Utils.createElementWithAttribute("document", document, "contextPath", context.getContextPath());
		documentElement.setAttribute("jQueryVersion", Utils.JQUERY_VERSION);

		Utils.createElementWithAttribute("header", documentElement, "on", "活動報名");
		services.buildFrontEndAside(document);
		services.buildFrontEndCounter(document);

		String errorMessage = null;
		Element formElement = Utils.createElementWithAttribute("form", documentElement, "action", request.getRequestURI());

		/*
		 單位名稱
		 */
		try {
			organization = organization.trim();
			if (organization.isEmpty()) {
				throw new NullPointerException();
			}
		} catch (Exception ignore) {
			organization = null;
		}
		Utils.createElementWithTextContent("organization", formElement, organization == null ? "" : organization);

		/*
		 姓名
		 */
		try {
			fullname = fullname.trim();
			if (fullname.isEmpty()) {
				errorMessage = "姓名為必填。";
				throw new NullPointerException();
			}
		} catch (Exception ignore) {
			fullname = null;
		}
		Utils.createElementWithTextContent("fullname", formElement, fullname == null ? "" : fullname);

		/*
		 職稱
		 */
		try {
			title = title.trim();
			if (title.isEmpty()) {
				throw new NullPointerException();
			}
		} catch (Exception ignore) {
			title = null;
		}
		Utils.createElementWithTextContent("title", formElement, title == null ? "" : title);

		/*
		 連絡電話
		 */
		try {
			phone = phone.trim();
			if (phone.isEmpty()) {
				throw new NullPointerException();
			}
		} catch (Exception ignore) {
			phone = null;
		}
		Utils.createElementWithTextContent("phone", formElement, phone == null ? "" : phone);

		/*
		 電子郵件
		 */
		try {
			email = email.trim();
			if (email.isEmpty()) {
				errorMessage = "電子郵件為必填。";
				throw new NullPointerException();
			}

			if (!EmailValidator.getInstance(false, false).isValid(email)) {
				errorMessage = "錯誤的電子郵件格式！";
			}
		} catch (Exception ignore) {
			email = null;
		}
		Utils.createElementWithTextContent("email", formElement, email == null ? "" : email);

		/*
		 便當
		 */
		Utils.createElementWithTextContent("vegetarian", formElement, Boolean.toString(vegetarian));

		if (errorMessage != null) {
			formElement.setAttribute("errorMessage", errorMessage);

			ModelAndView modelAndView = new ModelAndView("aPPLiCaTioN4");
			modelAndView.getModelMap().addAttribute(new DOMSource(document));
			return modelAndView;
		}

		StringBuilder stringBuilder = new StringBuilder("<TABLE border='1' cellspacing='3' cellpadding='3'>");
		stringBuilder.append("<TR><TH>單位名稱</TH><TD>").append(organization == null ? "" : organization).append("</TD></TR>");
		stringBuilder.append("<TR><TH>姓名</TH><TD>").append(fullname == null ? "" : fullname).append("</TD></TR>");
		stringBuilder.append("<TR><TH>職稱</TH><TD>").append(title == null ? "" : title).append("</TD></TR>");
		stringBuilder.append("<TR><TH>連絡電話</TH><TD>").append(phone == null ? "" : phone).append("</TD></TR>");
		stringBuilder.append("<TR><TH>電子郵件</TH><TD>").append(email == null ? "" : email).append("</TD></TR>");
		stringBuilder.append("<TR><TH>便當</TH><TD>").append(vegetarian ? "素食" : "葷食").append("</TD></TR>");
		stringBuilder.append("</TABLE>");

		final String CONSTANT = /*"itri532659@itri.org.tw"*/ "mii@itri.org.tw";
		HtmlEmail htmlEmail = new HtmlEmail();
		htmlEmail.setHostName("smtp.googlemail.com");
		htmlEmail.setSmtpPort(465);
		htmlEmail.setAuthentication("itri037670050@gmail.com", "mii-itri.org");
		htmlEmail.setSSLOnConnect(true);
		htmlEmail.setCharset("UTF-8");
		htmlEmail.setFrom(email, fullname, "UTF-8");
		htmlEmail.addTo(CONSTANT);
		htmlEmail.setSubject("活動報名：研發創新計畫書撰寫技巧與實務應用實戰班 - 計畫書1對1業師輔導");
		htmlEmail.setHtmlMsg(stringBuilder.toString());
		htmlEmail.send();

		return new ModelAndView("redirect:".concat(context.getContextPath().concat("/")));
	}

	/**
	 * 活動公告(第四次)
	 *
	 * @param organization 公司名稱
	 * @param address 公司地址
	 * @param phone 公司電話
	 * @param fax 公司傳真
	 * @param unifiedBusinessNumber 統一編號
	 * @param fullname 姓名
	 * @param department 部門
	 * @param title 職稱
	 * @param cellular 行動電話
	 * @param email 電子郵件
	 * @param request 請求
	 * @return 成功回首頁；失敗重來。
	 * @since 2016-04-07
	 */
	@RequestMapping(value = "/application4.asp", produces = "text/html;charset=UTF-8", method = RequestMethod.POST)
	private ModelAndView aPPLiCaTioN4(@RequestParam(required = false) String organization, @RequestParam(required = false) String fullname, @RequestParam(required = false) String title, @RequestParam(required = false) String phone, @RequestParam(required = false) String email, HttpServletRequest request) throws Exception {
		Document document = Utils.newDocument();
		Element documentElement = Utils.createElementWithAttribute("document", document, "contextPath", context.getContextPath());
		documentElement.setAttribute("jQueryVersion", Utils.JQUERY_VERSION);

		Utils.createElementWithAttribute("header", documentElement, "on", "活動公告");
		services.buildFrontEndAside(document);
		services.buildFrontEndCounter(document);

		String errorMessage = null;
		Element formElement = Utils.createElementWithAttribute("form", documentElement, "action", request.getRequestURI());

		/*
		 單位名稱
		 */
		try {
			organization = organization.trim();
			if (organization.isEmpty()) {
				throw new NullPointerException();
			}
		} catch (Exception ignore) {
			organization = null;
		}
		Utils.createElementWithTextContent("organization", formElement, organization == null ? "" : organization);

		/*
		 姓名
		 */
		try {
			fullname = fullname.trim();
			if (fullname.isEmpty()) {
				errorMessage = "姓名為必填。";
				throw new NullPointerException();
			}
		} catch (Exception ignore) {
			fullname = null;
		}
		Utils.createElementWithTextContent("fullname", formElement, fullname == null ? "" : fullname);

		/*
		 職稱
		 */
		try {
			title = title.trim();
			if (title.isEmpty()) {
				throw new NullPointerException();
			}
		} catch (Exception ignore) {
			title = null;
		}
		Utils.createElementWithTextContent("title", formElement, title == null ? "" : title);

		/*
		 連絡電話
		 */
		try {
			phone = phone.trim();
			if (phone.isEmpty()) {
				throw new NullPointerException();
			}
		} catch (Exception ignore) {
			phone = null;
		}
		Utils.createElementWithTextContent("phone", formElement, phone == null ? "" : phone);

		/*
		 電子郵件
		 */
		try {
			email = email.trim();
			if (email.isEmpty()) {
				errorMessage = "電子郵件為必填。";
				throw new NullPointerException();
			}

			if (!EmailValidator.getInstance(false, false).isValid(email)) {
				errorMessage = "錯誤的電子郵件格式！";
			}
		} catch (Exception ignore) {
			email = null;
		}
		Utils.createElementWithTextContent("email", formElement, email == null ? "" : email);

		if (errorMessage != null) {
			formElement.setAttribute("errorMessage", errorMessage);

			ModelAndView modelAndView = new ModelAndView("aPPLiCaTioN4");
			modelAndView.getModelMap().addAttribute(new DOMSource(document));
			return modelAndView;
		}

		StringBuilder stringBuilder = new StringBuilder("<TABLE border='1' cellspacing='3' cellpadding='3'>");
		stringBuilder.append("<TR><TH>單位名稱</TH><TD>").append(organization == null ? "" : organization).append("</TD></TR>");
		stringBuilder.append("<TR><TH>姓名</TH><TD>").append(fullname == null ? "" : fullname).append("</TD></TR>");
		stringBuilder.append("<TR><TH>職稱</TH><TD>").append(title == null ? "" : title).append("</TD></TR>");
		stringBuilder.append("<TR><TH>連絡電話</TH><TD>").append(phone == null ? "" : phone).append("</TD></TR>");
		stringBuilder.append("<TR><TH>電子郵件</TH><TD>").append(email == null ? "" : email).append("</TD></TR>");
		stringBuilder.append("</TABLE>");

		HtmlEmail htmlEmail = new HtmlEmail();
		htmlEmail.setHostName("smtp.googlemail.com");
		htmlEmail.setSmtpPort(465);
		htmlEmail.setAuthentication("itri037670050@gmail.com", "mii-itri.org");
		htmlEmail.setSSLOnConnect(true);
		htmlEmail.setCharset("UTF-8");
		htmlEmail.setFrom(email, fullname, "UTF-8");
		htmlEmail.addTo("mii@itri.org.tw");
		htmlEmail.setSubject("活動報名：研發創新計畫書撰寫技巧與實務應用實戰班 - 計畫書1對1業師輔導");
		htmlEmail.setHtmlMsg(stringBuilder.toString());
		htmlEmail.send();

		return new ModelAndView("redirect:".concat(context.getContextPath().concat("/")));
	}

	/**
	 * 輔導團隊
	 *
	 * @return 網頁
	 */
	@RequestMapping(value = "/mentors.asp", produces = "text/plain;charset=UTF-8")
	@SuppressWarnings("ConvertToTryWithResources")
	private ModelAndView memtors() throws Exception {
		String string = null;
		CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet("http://mii-itri.org/adminB/mentors.php");
		CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpGet);
		try {
			HttpEntity entity = closeableHttpResponse.getEntity();
			if (entity != null) {
				InputStream inputStream = entity.getContent();
				try {
					string = IOUtils.toString(inputStream);
				} finally {
					IOUtils.closeQuietly(inputStream);
				}
			}
		} finally {
			closeableHttpResponse.close();
		}

		Document document = Utils.newDocument();
		Element documentElement = Utils.createElementWithAttribute("document", document, "contextPath", context.getContextPath());
		documentElement.setAttribute("jQueryVersion", Utils.JQUERY_VERSION);

		Utils.createElement("header", documentElement);
		services.buildFrontEndAside(document);
		services.buildFrontEndCounter(document);

		Element contentElement = Utils.createElement("content", documentElement);
		JSONObject jsonObject = new JSONObject(string.replaceAll("^.*result\"", "{\"result\""));
		JSONArray jsonArray = jsonObject.getJSONArray("result");
		for (int i = 0; i < jsonArray.length(); i++) {
			Element rowElement = Utils.createElement("row", contentElement);
			JSONObject object = (JSONObject) jsonArray.get(i);
			Iterator keys = object.keys();
			while (keys.hasNext()) {
				String key = (String) keys.next(), value = object.isNull(key) ? "" : (String) object.get(key);
				Utils.createElementWithTextContent(key, rowElement, value == null ? "" : value);
			}
		}

		ModelAndView modelAndView = new ModelAndView("mentors");
		modelAndView.getModelMap().addAttribute(new DOMSource(document));
		return modelAndView;
	}

	/**
	 * 輔導流程。
	 *
	 * @return 網頁
	 */
	@RequestMapping(value = "/flows.asp", produces = "text/html;charset=UTF-8", method = RequestMethod.GET)
	private ModelAndView flows() throws Exception {
		Document document = Utils.newDocument();
		Element documentElement = Utils.createElementWithAttribute("document", document, "contextPath", context.getContextPath());
		documentElement.setAttribute("jQueryVersion", Utils.JQUERY_VERSION);

		Utils.createElementWithAttribute("header", documentElement, "on", "輔導流程");
		services.buildFrontEndAside(document);
		services.buildFrontEndCounter(document);

		Element contentElement = Utils.createElement("content", documentElement);

		Path path = Paths.get(services.getContextRealPath(), "WEB-INF", "htm", "FuDaoLiuCheng.htm");
		StringBuilder stringBuilder = new StringBuilder();
		for (String line : Files.readAllLines(path, Charset.forName("UTF-8"))) {
			stringBuilder.append(line);
		}
		Utils.createElementWithTextContent("wysiwyg", contentElement, stringBuilder.toString());

		ModelAndView modelAndView = new ModelAndView("flows");
		modelAndView.getModelMap().addAttribute(new DOMSource(document));
		return modelAndView;
	}

	/**
	 * 下載專區
	 *
	 * @return 網頁
	 */
	@RequestMapping(value = "/downloads.asp", produces = "text/html;charset=UTF-8", method = RequestMethod.GET)
	private ModelAndView downloads() throws Exception {
		Document document = Utils.newDocument();
		Element documentElement = Utils.createElementWithAttribute("document", document, "contextPath", context.getContextPath());
		documentElement.setAttribute("jQueryVersion", Utils.JQUERY_VERSION);

		Utils.createElementWithAttribute("header", documentElement, "on", "下載專區");
		services.buildFrontEndAside(document);
		services.buildFrontEndCounter(document);

		Element contentElement = Utils.createElement("content", documentElement);
		for (Download download : downloadRepository.findDisplayable()) {
			String filename = download.getFilename(), contentType = null;

			Element rowElement = Utils.createElementWithAttribute("row", contentElement, "id", download.getId().toString());
			Utils.createElementWithTextContent("filename", rowElement, download.getFilename());//原始檔名
			if (filename.matches("^.*\\.doc(x?)$")) {
				contentType = "&#xF1C2;";
			}
			if (filename.matches("^.*\\.pdf$")) {
				contentType = "&#xF1C1;";
			}
			if (filename.matches("^.*\\.ppt(x?)$")) {
				contentType = "&#xF1C4;";
			}
			if (filename.matches("^.*\\.xls(x?)$")) {
				contentType = "&#xF1C3;";
			}
			if (filename.matches("^.*\\.(bmp|gif|jpg|jpeg|png)$")) {
				contentType = "&#xF1C5;";
			}
			if (filename.matches("^.*\\.(bz2|gz|rar|tar|zip)$")) {
				contentType = "&#xF1C6;";
			}
			if (contentType == null) {
				contentType = "&#xF016;";
			}
			Utils.createElementWithTextContent("contentType", rowElement, contentType);//檔案類型
		}

		ModelAndView modelAndView = new ModelAndView("downloads");
		modelAndView.getModelMap().addAttribute(new DOMSource(document));
		return modelAndView;
	}

//	/**
//	 * 檔案。
//	 *
//	 * @param id 主鍵
//	 * @param response 回應
//	 */
//	@RequestMapping(value = "/download.asp", produces = "application/zip", method = RequestMethod.GET)
//	@SuppressWarnings({"Convert2Diamond", "ConvertToTryWithResources"})
//	private ResponseEntity<InputStreamResource> download(@RequestParam Short id, HttpServletResponse response) throws Exception {
//		String classCanonicalName = getClass().getCanonicalName();
//
//		Download download = downloadRepository.findOne(id);
//		if (download == null) {
//			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
//			System.err.println(classCanonicalName.concat("HttpServletResponse.SC_NOT_FOUND"));
//			return null;
//		}
//
//		byte[] content = download.getContent();
//		int length = content.length;
//		if (length <= 0) {
//			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//			System.err.println(classCanonicalName.concat("HttpServletResponse.SC_BAD_REQUEST"));
//			return null;
//		}
//
//		String filename = Long.toString(Utils.DEFAULT_CALENDAR.getTimeInMillis()).concat(".zip");
//		File file = Paths.get(services.getContextRealPath(), filename).toFile();
//		FileOutputStream fileOutputStream = new FileOutputStream(file);
//		fileOutputStream.write(content);
//		fileOutputStream.close();
//
//		HttpHeaders httpHeaders = new HttpHeaders();
//		httpHeaders.setContentDispositionFormData("attachment", filename);
//		httpHeaders.setContentLength(length);
//
//		InputStreamResource inputStreamResource = new InputStreamResource(new FileInputStream(file));
//		return new ResponseEntity<InputStreamResource>(inputStreamResource, httpHeaders, HttpStatus.OK);
//	}
//	private void download(@RequestParam Short id, HttpServletResponse response) throws Exception {
//		String classCanonicalName = getClass().getCanonicalName();
//
//		Download download = downloadRepository.findOne(id);
//		if (download == null) {
//			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
//			System.err.println(classCanonicalName.concat("HttpServletResponse.SC_NOT_FOUND"));
//			return;
//		}
//
//		byte[] content = download.getContent();
//		if (content.length <= 0) {
//			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//			System.err.println(classCanonicalName.concat("HttpServletResponse.SC_BAD_REQUEST"));
//			return;
//		}
//
//		String filename = Long.toString(Utils.DEFAULT_CALENDAR.getTimeInMillis()).concat(".zip");
//		File file = Paths.get(services.getContextRealPath(), filename).toFile();
//		FileOutputStream fileOutputStream = new FileOutputStream(file);
//		fileOutputStream.write(content);
//		fileOutputStream.close();
//
//		response.setHeader("Content-Disposition", "attachment;filename=".concat(filename));
//		//ByteArrayInputStream byteArrayInputStream = new java.io.ByteArrayInputStream(content);
//		//IOUtils.copy(byteArrayInputStream, response.getOutputStream());
//		//byteArrayInputStream.close();
//		//response.flushBuffer();
//		//return IOUtils.toByteArray(byteArrayInputStream);
//		FileInputStream fileInputStream = new FileInputStream(file);
//		IOUtils.copy(fileInputStream, response.getOutputStream());
//		fileInputStream.close();
//		response.flushBuffer();
//	}
//	@RequestMapping(value = "/downloads/{filename}", method = RequestMethod.GET)
//	private void download(@PathVariable String filename, HttpServletResponse response) throws Exception {
//		System.out.println("537");
//		Download download = downloadRepository.findOneByFilename(filename);
//		if (download == null) {
//			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
//			return;
//		}
//
//		InputStream inputStream = new ByteArrayInputStream(download.getContent());
//		IOUtils.copy(inputStream, response.getOutputStream());
//		response.setHeader("Content-Disposition", "attachment;filename=".concat(filename));
//		response.setHeader("Content-Type", download.getContentType());
//		response.flushBuffer();
//	}
//
	/**
	 * 輔導案例(列表)。
	 *
	 * @return 網頁
	 */
	@RequestMapping(value = "/counselings.asp", produces = "text/html;charset=UTF-8", method = RequestMethod.GET)
	private ModelAndView counselings() throws Exception {
		Document document = Utils.newDocument();
		Element documentElement = Utils.createElementWithAttribute("document", document, "contextPath", context.getContextPath());
		documentElement.setAttribute("jQueryVersion", Utils.JQUERY_VERSION);

		Utils.createElementWithAttribute("header", documentElement, "on", "輔導案例");
		services.buildFrontEndAside(document);
		services.buildFrontEndCounter(document);

		Element contentElement = Utils.createElement("content", documentElement);
		for (Counseling counseling : counselingRepository.findAll()) {
			Short taiwaneseYear = counseling.getTaiwaneseYear();

			Element rowElement = Utils.createElementWithAttribute("row", contentElement, "id", counseling.getId().toString());
			Utils.createElementWithTextContent("taiwaneseYear", rowElement, taiwaneseYear == null ? "" : taiwaneseYear.toString());//年度
			Utils.createElementWithTextContent("name", rowElement, counseling.getName());//計畫名稱
			Utils.createElementWithTextContent("vendorName", rowElement, counseling.getVendor().getName());//廠商名稱
			Utils.createElementWithTextContent("closed", rowElement, counseling.isClosed() ? "已結案" : "審核中");//案件狀態
		}

		ModelAndView modelAndView = new ModelAndView("counselings");
		modelAndView.getModelMap().addAttribute(new DOMSource(document));
		return modelAndView;
	}

	/**
	 * 輔導案例(內頁)。
	 *
	 * @return 網頁
	 */
	@RequestMapping(value = "/counseling/{id:[\\d]+}/", produces = "text/html;charset=UTF-8", method = RequestMethod.GET)
	private ModelAndView counseling(@PathVariable Short id, HttpServletResponse response) throws Exception {
		Counseling entity = counselingRepository.findOne(id);
		if (entity == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
		Vendor vendor = entity.getVendor();
		String vendorAddress = vendor.getAddress();
		String districtName = null;
		IndustrialDistrict industrialDistrict = vendor.getIndustrialDistrict();
		if (industrialDistrict != null) {
			districtName = industrialDistrict.getDistrict().getName();
		}
		String vendorContactName = vendor.getContactName(), vendorContactNumber = vendor.getContactNumber();
		//Integer amount = entity.getAmount();
		String condition = entity.getCondition();
		String items = entity.getItems();
		String performance = entity.getPerformance();
		byte[] photo = entity.getPhoto();

		Document document = Utils.newDocument();
		Element documentElement = Utils.createElementWithAttribute("document", document, "contextPath", context.getContextPath());
		documentElement.setAttribute("jQueryVersion", Utils.JQUERY_VERSION);

		Utils.createElementWithAttribute("header", documentElement, "on", "輔導案例");
		services.buildFrontEndAside(document);
		services.buildFrontEndCounter(document);

		Element contentElement = Utils.createElementWithAttribute("content", documentElement, "id", entity.getId().toString());
		Utils.createElementWithTextContent("name", contentElement, entity.getName());//計畫名稱
		Utils.createElementWithTextContent("vendorName", contentElement, vendor.getName());//受輔導公司
		Utils.createElementWithTextContent("districtName", contentElement, districtName == null ? "" : districtName);//行政區名稱
		Utils.createElementWithTextContent("vendorAddress", contentElement, vendorAddress == null ? "" : vendorAddress);//公司住址
		Utils.createElementWithTextContent("vendorContactNumber", contentElement, vendorContactNumber == null ? "" : vendorContactNumber);//聯絡方式
		//Utils.createElementWithTextContent("amount", contentElement, amount == null ? "" : amount.toString());//補助金額
		Utils.createElementWithTextContent("vendorContactName", contentElement, vendorContactName == null ? "" : vendorContactName);//聯絡人
		Utils.createElementWithTextContent("condition", contentElement, condition == null ? "" : condition);//業者現況
		Utils.createElementWithTextContent("items", contentElement, items == null ? "" : items);//輔導項目
		Utils.createElementWithTextContent("performance", contentElement, performance == null ? "" : performance);//輔導效益
		Utils.createElementWithTextContent("photo", contentElement, Boolean.toString(photo != null && photo.length > 0));//相關照片

		ModelAndView modelAndView = new ModelAndView("counseling");
		modelAndView.getModelMap().addAttribute(new DOMSource(document));
		return modelAndView;
	}

	/**
	 * 相關照片。
	 *
	 * @param id 主鍵
	 * @param response 回應
	 */
	@RequestMapping(value = "/counseling/{id:[\\d]+}/photo.png", method = RequestMethod.GET)
	private void counselingPhoto(@PathVariable Short id, HttpServletResponse response) throws Exception {
		Counseling counseling = counselingRepository.findOne(id);
		if (counseling == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		byte[] image = counseling.getPhoto();
		if (image.length <= 0) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		ImageIO.write(ImageIO.read(new java.io.ByteArrayInputStream(image)), "PNG", response.getOutputStream());
	}

	/**
	 * 友站連結。
	 *
	 * @return 網頁
	 */
	@RequestMapping(value = "/links.asp", produces = "text/html;charset=UTF-8", method = RequestMethod.GET)
	private ModelAndView links() throws Exception {
		Document document = Utils.newDocument();
		Element documentElement = Utils.createElementWithAttribute("document", document, "contextPath", context.getContextPath());
		documentElement.setAttribute("jQueryVersion", Utils.JQUERY_VERSION);

		Utils.createElementWithAttribute("header", documentElement, "on", "友站連結");
		services.buildFrontEndAside(document);
		services.buildFrontEndCounter(document);

		Element contentElement = Utils.createElement("content", documentElement);
		for (Ally ally : allyRepository.findDisplayable()) {
			String phone = ally.getPhone();

			Element rowElement = Utils.createElementWithAttribute("row", contentElement, "id", ally.getId().toString());
			Utils.createElementWithTextContent("title", rowElement, ally.getTitle());//標題
			Utils.createElementWithTextContent("fqdn", rowElement, ally.getFqdn());//參考網址
			Utils.createElementWithTextContent("phone", rowElement, phone == null ? "" : phone);//聯絡電話
		}

		ModelAndView modelAndView = new ModelAndView("links");
		modelAndView.getModelMap().addAttribute(new DOMSource(document));
		return modelAndView;
	}

	/**
	 * 左側連結縮圖。
	 *
	 * @param id 主鍵
	 * @param response 回應
	 */
	@RequestMapping(value = "/link/{id:[\\d]+}.png", method = RequestMethod.GET)
	private void linkImage(@PathVariable Short id, HttpServletResponse response) throws Exception {
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

	/**
	 * 聯絡我們。
	 *
	 * @return 網頁
	 */
	@RequestMapping(value = "/contact.asp", produces = "text/html;charset=UTF-8", method = RequestMethod.GET)
	private ModelAndView contact() throws Exception {
		Document document = Utils.newDocument();
		Element documentElement = Utils.createElementWithAttribute("document", document, "contextPath", context.getContextPath());
		documentElement.setAttribute("jQueryVersion", Utils.JQUERY_VERSION);

		Utils.createElementWithAttribute("header", documentElement, "on", "聯絡我們");
		services.buildFrontEndAside(document);
		services.buildFrontEndCounter(document);

		ModelAndView modelAndView = new ModelAndView("contact");
		modelAndView.getModelMap().addAttribute(new DOMSource(document));
		return modelAndView;
	}

	/**
	 * 聯絡我們。
	 *
	 * @param company 公司名稱
	 * @param name 大名
	 * @param title 職稱
	 * @param phone 聯絡電話
	 * @param email 電子郵件
	 * @param address 地址
	 * @param content 諮詢或發問的內容
	 * @param request 請求
	 * @return 成功回首頁；失敗重來。
	 */
	@RequestMapping(value = "/contact.asp", produces = "text/html;charset=UTF-8", method = RequestMethod.POST)
	private ModelAndView contact(@RequestParam(required = false) String company, @RequestParam(required = false) String name, @RequestParam(required = false) String title, @RequestParam(required = false) String phone, @RequestParam(required = false) String email, @RequestParam(required = false) String address, @RequestParam(required = false) String content, HttpServletRequest request) throws Exception {
		Document document = Utils.newDocument();
		Element documentElement = Utils.createElementWithAttribute("document", document, "contextPath", context.getContextPath());
		documentElement.setAttribute("jQueryVersion", Utils.JQUERY_VERSION);

		Utils.createElementWithAttribute("header", documentElement, "on", "聯絡我們");
		services.buildFrontEndAside(document);
		services.buildFrontEndCounter(document);

		String errorMessage = null;
		Element formElement = Utils.createElementWithAttribute("form", documentElement, "action", request.getRequestURI());

		/*
		 公司名稱
		 */
		try {
			company = company.trim();
			if (company.isEmpty()) {
				throw new NullPointerException();
			}
		} catch (Exception ignore) {
			company = null;
		}
		Utils.createElementWithTextContent("company", formElement, company == null ? "" : company);

		/*
		 大名
		 */
		try {
			name = name.trim();
			if (name.isEmpty()) {
				name = null;
				throw new NullPointerException();
			}
		} catch (NullPointerException nullPointerException) {
			errorMessage = "大名不得為空！";
		} catch (Exception exception) {
			errorMessage = "輸入的大名出現不明的錯誤！";
		}
		Utils.createElementWithTextContent("name", formElement, name == null ? "" : name);

		/*
		 職稱
		 */
		try {
			title = title.trim();
			if (title.isEmpty()) {
				throw new NullPointerException();
			}
		} catch (Exception ignore) {
			title = null;
		}
		Utils.createElementWithTextContent("title", formElement, title == null ? "" : title);

		/*
		 聯絡電話
		 */
		try {
			phone = phone.trim();
			if (phone.isEmpty()) {
				throw new NullPointerException();
			}
		} catch (Exception ignore) {
			phone = null;
		}
		Utils.createElementWithTextContent("phone", formElement, phone == null ? "" : phone);

		/*
		 電子郵件
		 */
		try {
			email = email.trim();
			if (email.isEmpty()) {
				throw new NullPointerException();
			}
		} catch (Exception ignore) {
			email = null;
		}
		Utils.createElementWithTextContent("email", formElement, email == null ? "" : email);

		if (phone == null && email == null) {
			errorMessage = "至少需填寫聯絡電話或電子郵件！";
		}

		/*
		 地址
		 */
		try {
			address = address.trim();
			if (address.isEmpty()) {
				throw new NullPointerException();
			}
		} catch (Exception ignore) {
			address = null;
		}
		Utils.createElementWithTextContent("address", formElement, address == null ? "" : address);

		/*
		 內容
		 */
		try {
			content = content.trim();
			if (content.isEmpty()) {
				content = null;
				throw new NullPointerException();
			}
		} catch (NullPointerException nullPointerException) {
			errorMessage = "內容不得為空！";
		} catch (Exception exception) {
			errorMessage = "輸入的內容出現不明的錯誤！";
		}
		Utils.createElementWithTextContent("content", formElement, content == null ? "" : content);

		if (errorMessage != null) {
			formElement.setAttribute("errorMessage", errorMessage);

			ModelAndView modelAndView = new ModelAndView("contact");
			modelAndView.getModelMap().addAttribute(new DOMSource(document));
			return modelAndView;
		}

		Reach reach = new Reach();
		reach.setCompany(company);
		reach.setName(name);
		reach.setTitle(title);
		reach.setPhone(phone);
		reach.setEmail(email);
		reach.setAddress(address);
		reach.setContent(content);
		reachRepository.saveAndFlush(reach);

		StringBuilder stringBuilder = new StringBuilder("<TABLE border='1' cellspacing='3' cellpadding='3'>");
		if (company != null) {
			stringBuilder.append("<TR>");
			stringBuilder.append("<TH>公司名稱</TH>");
			stringBuilder.append("<TD>").append(company).append("</TD>");
			stringBuilder.append("</TR>");
		}
		stringBuilder.append("<TR>");
		stringBuilder.append("<TH>大名</TH>");
		stringBuilder.append("<TD>").append(name).append("</TD>");
		stringBuilder.append("</TR>");
		if (title != null) {
			stringBuilder.append("<TR>");
			stringBuilder.append("<TH>職稱</TH>");
			stringBuilder.append("<TD>").append(title).append("</TD>");
			stringBuilder.append("</TR>");
		}
		if (phone != null) {
			stringBuilder.append("<TR>");
			stringBuilder.append("<TH>聯絡電話</TH>");
			stringBuilder.append("<TD>").append(phone).append("</TD>");
			stringBuilder.append("</TR>");
		}
		if (email != null) {
			stringBuilder.append("<TR>");
			stringBuilder.append("<TH>電子郵件</TH>");
			stringBuilder.append("<TD>").append(email).append("</TD>");
			stringBuilder.append("</TR>");
		}
		if (address != null) {
			stringBuilder.append("<TR>");
			stringBuilder.append("<TH>地址</TH>");
			stringBuilder.append("<TD>").append(address).append("</TD>");
			stringBuilder.append("</TR>");
		}
		stringBuilder.append("<TR>");
		stringBuilder.append("<TH>內容</TH>");
		stringBuilder.append("<TD><PRE>").append(content).append("</PRE></TD>");
		stringBuilder.append("</TR>");
		stringBuilder.append("</TABLE>");

		//final String CONSTANT = "mii@itri.org.tw";
		final String CONSTANT = "itri532659@itri.org.tw​";
		HtmlEmail htmlEmail = new HtmlEmail();
		htmlEmail.setHostName("smtp.googlemail.com");
		htmlEmail.setSmtpPort(465);
		htmlEmail.setAuthentication("itri037670050@gmail.com", "mii-itri.org");
		htmlEmail.setSSLOnConnect(true);
		htmlEmail.setCharset("UTF-8");
		htmlEmail.setFrom(CONSTANT, "苗栗產業創新推動中心", "UTF-8");
		htmlEmail.addTo(CONSTANT);
		htmlEmail.setSubject("苗創服務網有人留言請回應!!");
		htmlEmail.setHtmlMsg(stringBuilder.toString());
		htmlEmail.send();

		return new ModelAndView("redirect:".concat(context.getContextPath().concat("/")));
	}

	/**
	 * 後臺首頁。
	 *
	 * @param request 請求
	 * @param response 回應
	 * @param session 階段
	 * @return 網頁
	 */
	@RequestMapping(value = "/HouTai.asp", produces = "text/html;charset=UTF-8", method = RequestMethod.GET)
	private ModelAndView houTai(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
		Short myId = services.whoTheHeckAmI(session);
		if (myId == null) {
			return new ModelAndView("redirect:/DengRu.asp");//未登入
		}

		Individual me = individualRepository.findOne(myId);
		if (me == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);//找不到
			return null;
		}

		/*
		 登入了
		 */
		Document document = Utils.newDocument();
		Element documentElement = Utils.createElementWithAttribute("document", document, "title", "後臺");
		documentElement.appendChild(services.whereTheHellAmI(document, request, session));
		documentElement.setAttribute("myName", me.getName());

		ModelAndView modelAndView = new ModelAndView("HouTai");
		modelAndView.getModelMap().addAttribute(new DOMSource(document));
		return modelAndView;
	}

	@RequestMapping(value = "/statistics.asp", produces = "text/plain;charset=UTF-8", method = RequestMethod.GET)
	@ResponseBody
	private String statistics() throws Exception {
		System.out.println("user.dir\t" + System.getProperty("user.dir"));
		System.out.println("user.home\t" + System.getProperty("user.home"));
		System.out.println(System.getenv("TEMP"));

		Map<String, String> map = System.getenv();
		for (String envName : map.keySet()) {
			System.out.format("%s=%s%n", envName, map.get(envName));
		}

		DecimalFormat decimalFormat = new DecimalFormat("###,###");
		Runtime runtime = Runtime.getRuntime();

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Total:\t").append(decimalFormat.format(runtime.totalMemory())).append("\n");
		stringBuilder.append("Max:\t").append(decimalFormat.format(runtime.maxMemory())).append("\n");
		stringBuilder.append("Free:\t").append(decimalFormat.format(runtime.freeMemory())).append("\n");
		stringBuilder.append("Shadow:\t").append(Utils.md5("+++")).append("\n");
		return stringBuilder.toString();
	}

	@RequestMapping(value = "/blackMarket.asp", produces = "text/html;charset=UTF-8", method = RequestMethod.GET)
	private ModelAndView blackMarket(HttpServletRequest request) throws Exception {
		Document document = Utils.newDocument();
		Element documentElement = Utils.createElementWithAttribute("document", document, "contextPath", context.getContextPath());
		documentElement.setAttribute("jQueryVersion", Utils.JQUERY_VERSION);

		Utils.createElementWithAttribute("form", documentElement, "action", request.getRequestURI());

		ModelAndView modelAndView = new ModelAndView("blackMarket");
		modelAndView.getModelMap().addAttribute(new DOMSource(document));
		return modelAndView;
	}

	@RequestMapping(value = "/blackMarket.asp", produces = "text/html;charset=UTF-8", method = RequestMethod.POST)
	private ModelAndView blackMarket(@RequestParam Integer lumber, @RequestParam Integer clay, @RequestParam Integer iron, @RequestParam Integer crop, @RequestParam Integer total, HttpServletRequest request) throws Exception {
		Document document = Utils.newDocument();
		Element documentElement = Utils.createElementWithAttribute("document", document, "contextPath", context.getContextPath());
		documentElement.setAttribute("jQueryVersion", Utils.JQUERY_VERSION);

		int subTotal = lumber + clay + iron + crop;

		Element formElement = Utils.createElementWithAttribute("form", documentElement, "action", request.getRequestURI());
		formElement.setAttribute("lumber", lumber.toString());
		formElement.setAttribute("clay", clay.toString());
		formElement.setAttribute("iron", iron.toString());
		formElement.setAttribute("crop", crop.toString());
		formElement.setAttribute("total", total.toString());

		Float floatLumber = lumber.floatValue() / (float) subTotal, floatClay = clay.floatValue() / (float) subTotal, floatIron = iron.floatValue() / (float) subTotal, floatCrop = crop.floatValue() / (float) subTotal;
		Utils.createElementWithTextContent("lumber", formElement, Integer.toString(Math.round(total.floatValue() * floatLumber)));
		Utils.createElementWithTextContent("clay", formElement, Integer.toString(Math.round(total.floatValue() * floatClay)));
		Utils.createElementWithTextContent("iron", formElement, Integer.toString(Math.round(total.floatValue() * floatIron)));
		Utils.createElementWithTextContent("crop", formElement, Integer.toString(Math.round(total.floatValue() * floatCrop)));
		Utils.createElementWithTextContent("total", formElement, total.toString());

		ModelAndView modelAndView = new ModelAndView("blackMarket");
		modelAndView.getModelMap().addAttribute(new DOMSource(document));
		return modelAndView;
	}

	@RequestMapping(value = "/search.asp", produces = "text/html;charset=UTF-8", method = RequestMethod.GET)
	private ModelAndView search() throws Exception {
		Document document = Utils.newDocument();
		Element documentElement = Utils.createElementWithAttribute("document", document, "contextPath", context.getContextPath());
		documentElement.setAttribute("jQueryVersion", Utils.JQUERY_VERSION);

		Utils.createElement("header", documentElement);
		services.buildFrontEndAside(document);
		services.buildFrontEndCounter(document);

		ModelAndView modelAndView = new ModelAndView("search");
		modelAndView.getModelMap().addAttribute(new DOMSource(document));
		return modelAndView;
	}
}
