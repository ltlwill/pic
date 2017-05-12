package com.hengzhiyi.it.pic.controller;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import com.hengzhiyi.it.pic.base.BaseController;
import com.hengzhiyi.it.pic.common.Constants;
import com.hengzhiyi.it.pic.exception.BusinessException;
import com.hengzhiyi.it.pic.services.IImgUploadService;
import com.hengzhiyi.it.pic.vo.User;

/**
 * 图片搜索控制器
 * 
 * @author liutianlong
 *
 */
@Controller
@RequestMapping("/upload")
public class ImgUploadController extends BaseController
{
	private final static Logger logger = Logger
			.getLogger(ImgUploadController.class);

	private final static String TARGET_PAGE = "/pages/upload/upload.jsp";
	private final static String ACTIVE_MENU_ID = "img-upload";

	@Autowired
	private IImgUploadService ImgUploadService;

	@RequestMapping("/toUpload")
	public String toImgSearchPage(WebRequest request)
	{
		logger.debug("------to image upload page.------");
		// 设置要渲染的目标页面名称
		request.setAttribute(Constants.TARGET_PAGE_NAME_FLAG, TARGET_PAGE,
				WebRequest.SCOPE_REQUEST);
		// 设置当前正在使用的菜单ID
		request.setAttribute(Constants.ACTIVE_MENU_ID_FLAG, ACTIVE_MENU_ID,
				WebRequest.SCOPE_REQUEST);
		return "home/index";
	}

	/**
	 * 上传单个图片
	 * 
	 * @param request
	 * @param files
	 */
	@RequestMapping("/uploadImage")
	public ResponseEntity<Object> uploadImage(WebRequest request,
			HttpServletResponse response,
			@RequestParam("file") MultipartFile file, User user,
			String accountId)
	{
		ResponseEntity<Object> responseEntity = null;
		try
		{
			logger.debug("------upload file success,file name is:"
					+ (file == null ? "" : file.getOriginalFilename())
					+ "-------");
			// 文件上传
			ImgUploadService.uploadImage(request, file, user, accountId);
			responseEntity = new ResponseEntity<Object>(HttpStatus.OK);
		} catch (Exception e)
		{
			logger.error("------upload file fail,file name is :"
					+ (file == null ? "" : file.getOriginalFilename())
					+ "-------,cause:", e);
			// 如果文件已存在
			if (e instanceof BusinessException
					&& ((BusinessException) e).getErrorCode() == Constants.ErrorCode.FILE_EXISTS)
			{
				responseEntity = new ResponseEntity<Object>(
						((BusinessException) e).getErrorCode(), HttpStatus.OK);
			} else
			{
				responseEntity = new ResponseEntity<Object>(
						HttpStatus.EXPECTATION_FAILED);
			}
		}
		return responseEntity;
	}

	/**
	 * 上传多个图片
	 * 
	 * @param request
	 * @param files
	 */
	@RequestMapping("/uploadImages")
	public ResponseEntity<?> uploadImages(WebRequest request,
			@RequestParam("file") MultipartFile[] files)
	{
		// ResponseEntity<?> responseEntity = null;
		try
		{
			logger.debug("------upload files success,file length:"
					+ (files == null ? 0 : files.length) + "------");
		} catch (Exception e)
		{
			logger.error("------upload files fail ------,cause:", e);
		}
		return null;
	}
}
