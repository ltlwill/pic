package com.hengzhiyi.it.pic.appcontroller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.hengzhiyi.it.pic.base.BaseController;
import com.hengzhiyi.it.pic.common.Constants;
import com.hengzhiyi.it.pic.exception.BusinessException;
import com.hengzhiyi.it.pic.services.IImgUploadService;
import com.hengzhiyi.it.pic.vo.User;
import com.mysql.fabric.xmlrpc.base.Array;

/**
 * APP图片上传控制器
 * 
 * @author liutianlong
 *
 */
@Controller
@RequestMapping("/app/upload")
public class AppImgUploadController extends BaseController
{

	@Autowired
	private IImgUploadService ImgUploadService;

	/**
	 * 上传单个图片
	 * 
	 * @param request
	 * @param files
	 */
	@RequestMapping("/uploadImage")
	public ResponseEntity<Object> uploadImage(WebRequest request,
			HttpServletResponse response, MultipartFile file, User user,
			String accountId)
	{
		ResponseEntity<Object> responseEntity = null;
		try
		{
			logger.debug("------App upload file success,file name is:"
					+ (file == null ? "" : file.getOriginalFilename())
					+ "-------");
			// 文件上传
			ImgUploadService.uploadImage(request, file, user, accountId);
			responseEntity = new ResponseEntity<Object>(HttpStatus.OK);
		} catch (Exception e)
		{
			logger.error("------App upload file fail,file name is :"
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

	@RequestMapping("/uploadImages")
	public ResponseEntity<Object> uploadImages(WebRequest request,
			HttpServletRequest request2, HttpServletResponse response,
			User user, String accountId)
	{
		List<MultipartFile> files = null;
		try
		{
			files = convertToMultipartFile(request2);
		} catch (Exception e)
		{
			return new ResponseEntity<Object>(e,HttpStatus.EXPECTATION_FAILED); 
		}
		Map<String,Object> map = null;
		if (files != null && !files.isEmpty())
		{
			map = new HashMap<String, Object>();
			List<String> successList = new ArrayList<String>(); // 上传成功的文件名称
			List<String> failList = new ArrayList<String>();    // 上传失败的文件名称
			for (MultipartFile file : files)
			{
				try
				{
					// 文件上传
					ImgUploadService.uploadImage(request, file, user, accountId);
					successList.add(file.getOriginalFilename());
//					successList.add(file.getOriginalFilename().replaceAll("\\.", "_"));
				}catch(Exception e)
				{
					failList.add(file.getOriginalFilename());
//					failList.add(file.getOriginalFilename().replaceAll("\\.", "_"));
					logger.error("App 上传文件失败，文件名：" + file.getOriginalFilename(),e);
				}
			}
			map.put("success", successList);
			map.put("fail", failList);
		}
		return new ResponseEntity<Object>(map,HttpStatus.OK);
	}
	
	@SuppressWarnings("unused")
	private String convertName2Key(String name)
	{
		if(StringUtils.isEmpty(name))
		{
			return name;
		}
		String _name = name.substring(0, name.lastIndexOf(".")),
				extension = name.substring(name.lastIndexOf(".") + 1);
		return _name + "_" + extension;
	}

	private List<MultipartFile> convertToMultipartFile(
			HttpServletRequest request)
	{
		CommonsMultipartResolver resolver = new CommonsMultipartResolver(
				request.getServletContext());
		List<MultipartFile> files = null;
		if (resolver.isMultipart(request))
		{
			files = new ArrayList<MultipartFile>();
			MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) request;
			Iterator<String> names = mRequest.getFileNames();
			while (names.hasNext())
			{
				files.add(mRequest.getFile(names.next()));
			}
		}
		return files;
	}
}
