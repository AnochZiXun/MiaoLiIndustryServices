package tw.org.itri.mii.service;

import javax.servlet.http.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.*;
import tw.org.itri.mii.Utils;
import tw.org.itri.mii.entity.*;
import tw.org.itri.mii.repository.*;

/**
 * 「共用」服務層
 *
 * @author	P-C Lin (a.k.a 高科技黑手)
 */
@Service
public class Services {

	@Autowired
	AccessLogValveRepository accessLogValveRepository;

	@Autowired
	AccordionRepository accordionRepository;

	@Autowired
	AllyRepository allyRepository;

	@Autowired
	IndividualRepository individualRepository;

	@Autowired
	PrivilegeRepository privilegeRepository;

	@Autowired
	RouteRepository routeRepository;

	@Autowired
	javax.servlet.ServletContext context;

	/**
	 * @return 此 context 的實體路徑
	 */
	public String getContextRealPath() {
		return context.getRealPath(context.getContextPath());
	}

	/**
	 * @param document 文件
	 * @return 前臺左側邊欄計數器
	 */
	public Element buildFrontEndCounter(Document document) {
		Element documentElement = document.getDocumentElement();

		Element counterElement = Utils.createElement("counter", documentElement);
		Long counts = accessLogValveRepository.countByQuery("/");
		for (char c : counts.toString().toCharArray()) {
			Utils.createElementWithTextContent("digit", counterElement, String.valueOf(c));
		}

		return counterElement;
	}

	/**
	 * @param document 文件
	 * @return 前臺左側邊欄連結們
	 */
	public Element buildFrontEndAside(Document document) {
		Element documentElement = document.getDocumentElement();

		Element asideElement = Utils.createElement("aside", documentElement);
		for (Ally ally : allyRepository.findTop15Displayable()) {
			Element anchorElement = Utils.createElementWithTextContentAndAttribute("anchor", asideElement, ally.getFqdn(), "id", ally.getId().toString());
			anchorElement.setAttribute("title", ally.getTitle());
		}

		return asideElement;
	}

	/**
	 * @param request 請求
	 * @param session 階段
	 * @return 權限
	 */
	public Privilege whatTheFuckIsMyPrivilege(HttpServletRequest request, HttpSession session) {
		Short myId = whoTheHeckAmI(session);
		if (myId == null) {
			return null;
		}
		Individual me = individualRepository.findOne(myId);
		if (me == null) {
			return null;
		}
		Clan clan = me.getClan();
		if (clan == null) {
			return null;
		}

		String requestURI = request.getRequestURI().replaceAll("^".concat(context.getContextPath()), "");//去掉 contextPath 之後的 requestURI

		Privilege privilege = null;
		for (Route route : routeRepository.findAll()) {
			String hyperlink = route.getHyperlink();
			if (requestURI.matches("^".concat(hyperlink.replaceAll("/", "\\\\/.*")))) {
				privilege = privilegeRepository.findOneByRouteAndClan(route, clan);
				break;
			}
		}

		return privilege;
	}

	/**
	 * @param document 文件
	 * @param request 請求
	 * @param session 階段
	 * @return 左側邊欄選單
	 */
	@SuppressWarnings("null")
	public Element whereTheHellAmI(Document document, HttpServletRequest request, HttpSession session) {
		Element documentElement = document.getDocumentElement();
		documentElement.setAttribute("contextPath", request.getContextPath());
		documentElement.setAttribute("jQuery", Utils.JQUERY_VERSION);
		documentElement.setAttribute("jQueryUI", Utils.JQUERY_UI_VERSION);

		/*
		 左側邊欄選單的容器及登入人員的群組、姓名
		 */
		Element asideElement = Utils.createElement("aside", documentElement);
		Short myId = whoTheHeckAmI(session);
		if (myId == null) {
			return asideElement;
		}
		Individual me = individualRepository.findOne(myId);
		if (me == null) {
			return asideElement;
		}
		Clan clan = me.getClan();
		documentElement.setAttribute("myName", me.getName());

		String requestURI = request.getRequestURI().replaceAll("^".concat(context.getContextPath()), "");//去掉 contextPath 之後的 requestURI

		for (Accordion accordion : accordionRepository.findAll(new org.springframework.data.domain.Sort("ordinal"))) {
			/*
			 每個手風琴
			 */
			Element accordionElement = Utils.createElementWithAttribute("collapsed", asideElement, "id", accordion.getId().toString());
			accordionElement.setAttribute("name", accordion.getName());

			for (Route route : routeRepository.findByAccordionOrderByOrdinal(accordion)) {
				String hyperlink = route.getHyperlink();
				/*
				 每個手風琴下的路由
				 */
				if (privilegeRepository.findOneByRouteAndClan(route, clan) != null) {
					/*
					 有權限的話
					 */
					Element anchorElement = Utils.createElementWithTextContentAndAttribute("anchor", accordionElement, route.getLabel(), "ordinal", Short.toString(route.getOrdinal()));
					anchorElement.setAttribute("href", hyperlink);

					if (requestURI.matches("^".concat(hyperlink.replaceAll("/", "\\\\/.*")))) {
						/*
						 目前開啟的手風琴
						 */
						document.renameNode(accordionElement, accordionElement.getNamespaceURI(), "expanded");

						if (requestURI.matches("^".concat(hyperlink.replaceAll("/", "\\\\/")))) {
							/*
							 目前開啟的連結
							 */
							anchorElement.removeAttribute("href");
						}
					}
				}
			}

			if (!accordionElement.hasChildNodes()) {
				/*
				 空的手風琴乾脆就拿掉
				 */
				asideElement.removeChild(accordionElement);
			}
		}

		return asideElement;
	}

	/**
	 * @param session 階段
	 * @return 登入的人員主鍵或空值。
	 */
	public Short whoTheHeckAmI(HttpSession session) {
		Short myId = null;

		if (session.isNew()) {
			return myId;
		}

		try {
			myId = (Short) session.getAttribute("me");
		} catch (Exception exception) {
			return myId;
		}

		if (myId == null) {
			return myId;
		}

		Individual someone = individualRepository.findOne(myId);
		return someone == null || someone.getId() == null ? null : myId;
	}
}
