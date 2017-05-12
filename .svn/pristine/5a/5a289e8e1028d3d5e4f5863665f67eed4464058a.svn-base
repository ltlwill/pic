package com.hengzhiyi.it.pic.services;

import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import com.hengzhiyi.it.pic.exception.BusinessException;
import com.hengzhiyi.it.pic.vo.User;

/**
 * 图片上传服务接口
 * 
 * @author liutianlong
 *
 */
public interface IImgUploadService
{

	/**
	 * 上传图片(单个)
	 * 
	 * @param request
	 * @param file
	 * @param user
	 * @param accountId
	 */
	void uploadImage(WebRequest request, MultipartFile file, User user,String accountId)
			throws BusinessException;

	/**
	 * 上传图片(多个)
	 * @param request
	 * @param files
	 * @param user
	 * @throws Exception
	 */
	void uploadImages(WebRequest request, MultipartFile[] files, User user)
			throws Exception;
}
