package tw.org.itri.mii.service;

import java.io.*;
import java.nio.file.*;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Element;
import tw.org.itri.mii.Utils;
import tw.org.itri.mii.entity.Download;
import tw.org.itri.mii.repository.DownloadRepository;

/**
 * 「下載專區」服務層
 *
 * @author	P-C Lin (a.k.a 高科技黑手)
 */
@Service
public class DownloadService {

	@Autowired
	DownloadRepository downloadRepository;

	@Autowired
	Services services;

	/**
	 * 載入。
	 *
	 * @param entity 實體
	 * @param parentNode 父層元素&#60;form/&#62;
	 */
	public void load(Download entity, Element parentNode) {
		Short id = entity.getId();//主鍵
		if (id != null) {
			Utils.createElementWithTextContent("id", parentNode, id.toString());
		}

		String filename = entity.getFilename();//原始檔名
		Utils.createElementWithTextContent("filename", parentNode, filename == null ? "" : filename);

		String contentType = entity.getContentType();//檔案類型
		Utils.createElementWithTextContent("contentType", parentNode, contentType == null ? "" : contentType);

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
	 * @param multipartFile 檔案
	 * @param since 上架日期
	 * @param until 下架日期
	 * @param censored 發佈審查
	 * @param parentNode 父層元素&#60;form/&#62;
	 * @return 錯誤訊息
	 */
	@SuppressWarnings({"Convert2Diamond", "ConvertToTryWithResources", "MismatchedQueryAndUpdateOfCollection", "UseSpecificCatch"})
	public String save(Download entity, MultipartFile multipartFile, Date since, Date until, boolean censored, Element parentNode) throws Exception {
		String errorMessage = null;

		/*
		 主鍵
		 */
		Short id = entity.getId();
		if (id != null) {
			Utils.createElementWithTextContent("id", parentNode, id.toString());
		}

		/*
		 檔案
		 */
		byte[] content = null;
		try {
			content = multipartFile.getBytes();
			if (content.length == 0) {
				content = null;
				throw new NullPointerException();
			}
//			ByteArrayOutputStream fileOutputStream = new ByteArrayOutputStream();
//
//			ZipArchiveEntry zipArchiveEntry = new ZipArchiveEntry(multipartFile.getOriginalFilename());
//			zipArchiveEntry.setSize(multipartFile.getSize());
//
//			ZipArchiveOutputStream zipArchiveOutputStream = new ZipArchiveOutputStream(fileOutputStream);
//			zipArchiveOutputStream.putArchiveEntry(zipArchiveEntry);
//			zipArchiveOutputStream.write(content);
//			zipArchiveOutputStream.closeArchiveEntry();
//
//			content = fileOutputStream.toByteArray();
//			fileOutputStream.close();
		} catch (NullPointerException nullPointerException) {
			if (id == null) {
				errorMessage = "新增時「檔案」為必選！";
			}
		} catch (Exception ignore) {
			errorMessage = "選擇的「檔案」出現不明的錯誤！";
		}

		/*
		 原始檔名
		 */
		String filename = null;
		if (content != null) {
			filename = multipartFile.getOriginalFilename();
			if (id == null) {
				if (downloadRepository.countByFilename(filename) > 0) {
					errorMessage = "已存在相同的檔案名稱！";
				}
			}
		} else if (id != null) {
			filename = entity.getFilename();
			if (downloadRepository.countByFilenameAndIdNot(filename, id) > 0) {
				errorMessage = "已存在相同的檔案名稱！";
			}
		}
		Utils.createElementWithTextContent("filename", parentNode, filename);

		/*
		 檔案類型
		 */
		String contentType = null;
		if (content != null) {
			contentType = multipartFile.getContentType();
		} else if (id != null) {
			contentType = entity.getContentType();
		}
		Utils.createElementWithTextContent("contentType", parentNode, contentType);

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
			entity.setFilename(filename);
			if (content != null) {
				InputStream inputStream = new ByteArrayInputStream(content);

				Path path = Paths.get(services.getContextRealPath(), "downloads", filename);
				File file = path.toFile();
				file.createNewFile();

				FileOutputStream fileOutputStream = new FileOutputStream(file);
				int i;
				while ((i = inputStream.read()) != -1) {
					fileOutputStream.write(i);
				}
				fileOutputStream.close();

				inputStream.close();

				entity.setContentType(contentType);
			}
			entity.setSince(since);
			entity.setUntil(until);
			entity.setCensored(censored);
			downloadRepository.saveAndFlush(entity);
			return null;
		}
		return errorMessage;
	}
}
