package tw.org.itri.mii.controller;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.transform.dom.DOMSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import tw.org.itri.mii.Utils;
import tw.org.itri.mii.entity.Individual;
import tw.org.itri.mii.entity.Privilege;
import tw.org.itri.mii.repository.IndividualRepository;
import tw.org.itri.mii.service.Services;

/**
 * 「CKEditor」控制器
 *
 * @author	P-C Lin (a.k.a 高科技黑手)
 */
@Controller
public class CKEditorController {

	@Autowired
	IndividualRepository individualRepository;

	@Autowired
	Services services;

	/**
	 * 計畫簡介。
	 *
	 * @param request 請求
	 * @param response 回應
	 * @param session 階段
	 * @return 網頁
	 */
	@RequestMapping(value = "/JiHuaJianJie/", produces = "text/html;charset=UTF-8", method = RequestMethod.GET)
	private ModelAndView jiHuaJianJie(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
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
		documentElement.setAttribute("myName", me.getName());

		services.whereTheHellAmI(document, request, session);

		Element formElement = Utils.createElementWithAttribute("form", documentElement, "action", request.getRequestURI());
		formElement.setAttribute("legend", "修改計畫簡介");

		Path path = Paths.get(services.getContextRealPath(), "WEB-INF", "htm", "JiHuaJianJie.htm");
		StringBuilder stringBuilder = new StringBuilder();
		for (String line : Files.readAllLines(path, Charset.forName("UTF-8"))) {
			stringBuilder.append(line);
		}
		Utils.createElementWithTextContent("wysiwyg", formElement, stringBuilder.toString());

		ModelAndView modelAndView = new ModelAndView("CKEditor/JiHuaJianJie");
		modelAndView.getModelMap().addAttribute(new DOMSource(document));
		return modelAndView;
	}

	/**
	 * 計畫簡介。
	 *
	 * @param wysiwyg 內容
	 * @param request 請求
	 * @param session 階段
	 * @return 網頁
	 */
	@RequestMapping(value = "/JiHuaJianJie/", produces = "text/html;charset=UTF-8", method = RequestMethod.POST)
	@SuppressWarnings("UnusedAssignment")
	private ModelAndView jiHuaJianJie(@RequestParam(defaultValue = "", value = "wysiwyg") String wysiwyg, HttpServletRequest request, HttpSession session) throws Exception {
		Short myId = services.whoTheHeckAmI(session);
		if (myId == null) {
			return new ModelAndView("redirect:/DengRu.asp");//未登入則導向到登入頁面
		}

		/*
		 登入了
		 */
		String errorMessage = null;

		try (BufferedWriter bufferedWriter = Files.newBufferedWriter(Paths.get(services.getContextRealPath(), "WEB-INF", "htm", "JiHuaJianJie.htm"), Charset.forName("UTF-8"), StandardOpenOption.WRITE)) {
			bufferedWriter.write(wysiwyg, 0, wysiwyg.length());
			return new ModelAndView("redirect:/JiHuaJianJie/");//導回到「修改計畫簡介」頁面
		} catch (IOException ioException) {
			errorMessage = ioException.getLocalizedMessage();
		}

		Document document = Utils.newDocument();
		Element documentElement = Utils.createElementWithAttribute("document", document, "contextPath", request.getContextPath());

		services.whereTheHellAmI(document, request, session);

		Element formElement = Utils.createElementWithAttribute("form", documentElement, "action", request.getRequestURI());
		formElement.setAttribute("legend", "修改計畫簡介");
		if (errorMessage != null) {
			Utils.createElementWithTextContent("errorMessage", formElement, errorMessage);
		}

		Utils.createElementWithTextContent("wysiwyg", formElement, wysiwyg);

		ModelAndView modelAndView = new ModelAndView("CKEditor/JiHuaJianJie");
		modelAndView.getModelMap().addAttribute(new DOMSource(document));
		return modelAndView;
	}

	/**
	 * 輔導流程。
	 *
	 * @param request 請求
	 * @param response 回應
	 * @param session 階段
	 * @return 網頁
	 */
	@RequestMapping(value = "/FuDaoLiuCheng/", produces = "text/html;charset=UTF-8", method = RequestMethod.GET)
	private ModelAndView fuDaoLiuCheng(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
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
		documentElement.setAttribute("myName", me.getName());

		services.whereTheHellAmI(document, request, session);

		Element formElement = Utils.createElementWithAttribute("form", documentElement, "action", request.getRequestURI());
		formElement.setAttribute("legend", "修改輔導流程");

		Path path = Paths.get(services.getContextRealPath(), "WEB-INF", "htm", "FuDaoLiuCheng.htm");
		StringBuilder stringBuilder = new StringBuilder();
		for (String line : Files.readAllLines(path, Charset.forName("UTF-8"))) {
			stringBuilder.append(line);
		}
		Utils.createElementWithTextContent("wysiwyg", formElement, stringBuilder.toString());

		ModelAndView modelAndView = new ModelAndView("CKEditor/FuDaoLiuCheng");
		modelAndView.getModelMap().addAttribute(new DOMSource(document));
		return modelAndView;
	}

	/**
	 * 輔導流程。
	 *
	 * @param wysiwyg 內容
	 * @param request 請求
	 * @param session 階段
	 * @return 網頁
	 */
	@RequestMapping(value = "/FuDaoLiuCheng/", produces = "text/html;charset=UTF-8", method = RequestMethod.POST)
	@SuppressWarnings("UnusedAssignment")
	private ModelAndView fuDaoLiuCheng(@RequestParam(defaultValue = "", value = "wysiwyg") String wysiwyg, HttpServletRequest request, HttpSession session) throws Exception {
		Short myId = services.whoTheHeckAmI(session);
		if (myId == null) {
			return new ModelAndView("redirect:/DengRu.asp");//未登入則導向到登入頁面
		}

		/*
		 登入了
		 */
		String errorMessage = null;

		try (BufferedWriter bufferedWriter = Files.newBufferedWriter(Paths.get(services.getContextRealPath(), "WEB-INF", "htm", "FuDaoLiuCheng.htm"), Charset.forName("UTF-8"), StandardOpenOption.WRITE)) {
			bufferedWriter.write(wysiwyg, 0, wysiwyg.length());
			return new ModelAndView("redirect:/FuDaoLiuCheng/");//導回到「修改計畫簡介」頁面
		} catch (IOException ioException) {
			errorMessage = ioException.getLocalizedMessage();
		}

		Document document = Utils.newDocument();
		Element documentElement = Utils.createElementWithAttribute("document", document, "contextPath", request.getContextPath());

		services.whereTheHellAmI(document, request, session);

		Element formElement = Utils.createElementWithAttribute("form", documentElement, "action", request.getRequestURI());
		formElement.setAttribute("legend", "修改輔導流程");
		if (errorMessage != null) {
			Utils.createElementWithTextContent("errorMessage", formElement, errorMessage);
		}

		Utils.createElementWithTextContent("wysiwyg", formElement, wysiwyg);

		ModelAndView modelAndView = new ModelAndView("CKEditor/FuDaoLiuCheng");
		modelAndView.getModelMap().addAttribute(new DOMSource(document));
		return modelAndView;
	}
}
