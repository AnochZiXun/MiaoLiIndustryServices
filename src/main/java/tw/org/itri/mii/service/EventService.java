package tw.org.itri.mii.service;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Element;
import tw.org.itri.mii.Utils;
import tw.org.itri.mii.entity.Event;
import tw.org.itri.mii.repository.CounselingRepository;
import tw.org.itri.mii.repository.IndustrialDistrictRepository;
import tw.org.itri.mii.repository.EventRepository;
import tw.org.itri.mii.repository.VendorRepository;

/**
 * 「活動花絮」服務層
 *
 * @author	P-C Lin (a.k.a 高科技黑手)
 */
@Service
public class EventService {

	@Autowired
	CounselingRepository counselingRepository;

	@Autowired
	EventRepository eventRepository;

	@Autowired
	IndustrialDistrictRepository industrialDistrictRepository;

	@Autowired
	VendorRepository vendorRepository;

	/**
	 * 載入。
	 *
	 * @param entity 實體
	 * @param parentNode 父層元素&#60;form/&#62;
	 */
	public void load(Event entity, Element parentNode) {
		String title = entity.getTitle();//標題
		Utils.createElementWithTextContent("title", parentNode, title == null ? "" : title);

		String content = entity.getContent();//內容
		Utils.createElementWithTextContent("content", parentNode, content == null ? "" : content);

		Date since = entity.getSince();//上架日期
		Utils.createElementWithTextContent("since", parentNode, since == null ? "" : Utils.formatDate(since));

		Date until = entity.getUntil();//下架日期
		Utils.createElementWithTextContent("until", parentNode, until == null ? "" : Utils.formatDate(until));

		boolean censored = entity.isCensored();//發佈審查
		Utils.createElementWithTextContent("censored", parentNode, Boolean.toString(censored));
	}

	/**
	 * 儲存。
	 *
	 * @param entity 實體
	 * @param title 標題
	 * @param content 內容
	 * @param since 上架日期
	 * @param until 下架日期
	 * @param censored 發佈審查
	 * @param parentNode 父層元素&#60;form/&#62;
	 * @return 錯誤訊息
	 */
	public String save(Event entity, String title, String content, Date since, Date until, boolean censored, MultipartFile multipartFile, Element parentNode) {
		String errorMessage = null;

		/*
		 標題
		 */
		try {
			title = title.trim();
			if (title.isEmpty()) {
				title = null;
				throw new NullPointerException();
			}
		} catch (NullPointerException nullPointerException) {
			errorMessage = "「標題」為必填！";
		} catch (Exception ignore) {
			errorMessage = "輸入的「標題」出現不明的錯誤！";
		}
		Utils.createElementWithTextContent("title", parentNode, title == null ? "" : title);

		/*
		 內容
		 */
		try {
			if (content.isEmpty()) {
				content = null;
				throw new NullPointerException();
			}
		} catch (NullPointerException nullPointerException) {
			errorMessage = "「內容」為必填！";
		} catch (Exception ignore) {
			errorMessage = "輸入的「內容」出現不明的錯誤！";
		}
		Utils.createElementWithTextContent("content", parentNode, content == null ? "" : content);

		/*
		 上架日期
		 */
		if (since == null) {
			errorMessage = "「上架日期」為必填！";
		}
		Utils.createElementWithTextContent("since", parentNode, since == null ? "" : Utils.formatDate(since));

		/*
		 下架日期
		 */
		if (until == null) {
			errorMessage = "「下架日期」為必填！";
		}
		Utils.createElementWithTextContent("until", parentNode, until == null ? "" : Utils.formatDate(until));

		if (since != null && until != null && since.after(until)) {
			errorMessage = "「上架日期」不得遲於「下架日期」！";
		}

		/*
		 發佈審查
		 */
		Utils.createElementWithTextContent("censored", parentNode, Boolean.toString(censored));

		/*
		 列表圖片
		 */
		byte[] image = null;
		try {
			image = multipartFile.getBytes();
			if (image.length == 0) {
				image = null;
				throw new NullPointerException();
			}
		} catch (NullPointerException nullPointerException) {
			errorMessage = "「列表圖片」為必選！";
		} catch (Exception ignore) {
			errorMessage = "選擇的「列表圖片」出現不明的錯誤！";
		}

		if (errorMessage == null) {
			entity.setTitle(title);
			entity.setContent(content);
			entity.setSince(since);
			entity.setUntil(until);
			entity.setCensored(censored);
			if (image != null) {
				entity.setImage(image);
			}
			eventRepository.saveAndFlush(entity);
			return null;
		}
		return errorMessage;
	}
}
