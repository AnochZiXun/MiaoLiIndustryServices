package tw.org.itri.mii.service;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Element;
import tw.org.itri.mii.Utils;
import tw.org.itri.mii.entity.Ally;
import tw.org.itri.mii.repository.AllyRepository;

/**
 * 「友站連結」服務層
 *
 * @author	P-C Lin (a.k.a 高科技黑手)
 */
@Service
public class AllyService {

	@Autowired
	AllyRepository allyRepository;

	/**
	 * 載入。
	 *
	 * @param entity 實體
	 * @param parentNode 父層元素&#60;form/&#62;
	 */
	public void load(Ally entity, Element parentNode) {
		Short id = entity.getId();//主鍵
		if (id != null) {
			Utils.createElementWithTextContent("id", parentNode, id.toString());
		}

		String title = entity.getTitle();//標題
		Utils.createElementWithTextContent("title", parentNode, title == null ? "" : title);

		String phone = entity.getPhone();//聯絡電話
		Utils.createElementWithTextContent("phone", parentNode, phone == null ? "" : phone);

		String fqdn = entity.getFqdn();//參考網址
		Utils.createElementWithTextContent("fqdn", parentNode, fqdn == null ? "" : fqdn);

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
	 * @param phone 聯絡電話
	 * @param multipartFile 縮圖
	 * @param fqdn 內容
	 * @param since 上架日期
	 * @param until 下架日期
	 * @param censored 發佈審查
	 * @param parentNode 父層元素&#60;form/&#62;
	 * @return 錯誤訊息
	 */
	@SuppressWarnings("UseSpecificCatch")
	public String save(Ally entity, String title, String phone, MultipartFile multipartFile, String fqdn, Date since, Date until, boolean censored, Element parentNode) {
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
		 聯絡電話
		 */
		try {
			phone = phone.trim();
			if (phone.isEmpty()) {
				phone = null;
			}
		} catch (Exception ignore) {
			errorMessage = "輸入的「聯絡電話」出現不明的錯誤！";
		}
		Utils.createElementWithTextContent("phone", parentNode, phone == null ? "" : phone);

		/*
		 縮圖
		 */
		byte[] image = null;
		try {
			image = multipartFile.getBytes();
			if (image.length == 0) {
				image = null;
				throw new NullPointerException();
			}
		} catch (NullPointerException nullPointerException) {
			if (entity.getId() == null) {
				errorMessage = "「縮圖」為必選！";
			}
		} catch (Exception ignore) {
			errorMessage = "選擇的「縮圖」出現不明的錯誤！";
		}

		/*
		 參考網址
		 */
		try {
			fqdn = fqdn.trim();
			if (fqdn.isEmpty()) {
				fqdn = null;
				throw new NullPointerException();
			}
		} catch (NullPointerException nullPointerException) {
			errorMessage = "「參考網址」為必填！";
		} catch (Exception ignore) {
			errorMessage = "輸入的「參考網址」出現不明的錯誤！";
		}
		Utils.createElementWithTextContent("fqdn", parentNode, fqdn == null ? "" : fqdn);

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

		if (errorMessage == null) {
			entity.setTitle(title);
			entity.setPhone(phone);
			if (image != null) {
				entity.setImage(image);
			}
			entity.setFqdn(fqdn);
			entity.setSince(since);
			entity.setUntil(until);
			entity.setCensored(censored);
			allyRepository.saveAndFlush(entity);
			return null;
		}
		return errorMessage;
	}
}
