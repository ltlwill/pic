package com.hengzhiyi.it.pic.services;

import java.io.File;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import com.hengzhiyi.it.pic.common.Constants;
import com.hengzhiyi.it.pic.dao.IImgUploadDao;
import com.hengzhiyi.it.pic.exception.BusinessException;
import com.hengzhiyi.it.pic.tools.ImageCompressHelper;
import com.hengzhiyi.it.pic.vo.ImageVO;
import com.hengzhiyi.it.pic.vo.UploadLogVO;
import com.hengzhiyi.it.pic.vo.User;

/**
 * 图片上传服务实现类
 * 
 * @author liutianlong
 *
 */
@Service
public class ImgUploadService extends SysAbstractService implements
		IImgUploadService
{
	private final static Logger logger = Logger
			.getLogger(ImgUploadService.class);

	@Autowired
	private IImgUploadDao imgUploadDao;

	@Override
	public void uploadImage(WebRequest request, MultipartFile file, User user,
			String accountId) throws BusinessException
	{
		if (file == null)
		{
			logger.error("upload image fail,cause the file is null.");
			throw new RuntimeException();
		}
		String relativePath = file.getOriginalFilename();
		// 服务器文件名称
		String newFilePath = this.getVirtualDir() + File.separator
				+ relativePath;
		// 创建新文件
		File newFile = new File(newFilePath);
		File compressFile = null;
		try
		{
			// 如果文件已存在，则不允许再次保存，抛出异常
			if (newFile.exists())
			{
				throw new BusinessException("-----文件上传失败,文件已存在-----",
						Constants.ErrorCode.FILE_EXISTS);
			}
			// 如果父路径不存在,则创建父路径
			if (!newFile.getParentFile().exists())
			{
				newFile.getParentFile().mkdirs();
			}
			// 保存到新文件
			file.transferTo(newFile);
			// 创建vo
			ImageVO vo = createImageVo(newFile, user, relativePath,accountId);
			// 创建压缩版的图片文件
			createCompressionImage(newFile, vo);
			compressFile = new File(vo.getSmallAbsolutePath());
			// 保存到数据库
			imgUploadDao.saveImage(vo);
			// 创建日志VO
			UploadLogVO log = createUploadLog(vo);
			// 保存上传日志
			imgUploadDao.saveUploadLog(log);
		} catch (BusinessException e)
		{
			logger.error("upload file fail,cause:" + e);
			if (e.getErrorCode() != Constants.ErrorCode.FILE_EXISTS)
			{
				// 失败时需要将新文件删除
				// newFile.delete();
				// 删除源文件和压缩的文件
				deleteFile(newFile, compressFile);
			}
			throw new BusinessException(e, e.getErrorCode());
		} catch (Exception e)
		{
			logger.error("upload file fail,cause:" + e);
			// 失败时需要将新文件删除
			// newFile.delete();
			// 删除源文件和压缩的文件
			deleteFile(newFile, compressFile);
			throw new BusinessException("upload file fail,cause:", e);
		}
	}

	private void deleteFile(File... files)
	{
		if (files == null || files.length == 0)
		{
			return;
		}
		for (File file : files)
		{
			file.delete();
		}
	}

	@Override
	public void uploadImages(WebRequest request, MultipartFile[] files,
			User user) throws Exception
	{
	}

	private ImageVO createImageVo(File file, User user, String relativePath,String accountId)
	{
		ImageVO vo = new ImageVO();
		vo.setName(file.getName());
		vo.setAbsolutePath(file.getAbsolutePath());
		vo.setRelativePath(relativePath);
		vo.setUserId(user.getUserId());
		vo.setUserName(user.getUserName());
		vo.setUploadDate(new Date());
		vo.setExtension(getFileExtension(file.getName()));
		vo.setAccountId(accountId);
		return vo;
	}

	private UploadLogVO createUploadLog(ImageVO img)
	{
		UploadLogVO log = new UploadLogVO();
		log.setFileName(img.getName());
		log.setOperatorId(img.getUserId());
		log.setOperatorName(img.getUserName());
		log.setAbsolutePath(img.getAbsolutePath());
		log.setRelativePath(img.getRelativePath());
		log.setUploadDate(img.getUploadDate());
		return log;
	}

	private String getFileExtension(String fileName)
	{
		if (StringUtils.isEmpty(fileName))
		{
			return null;
		}
		return fileName.substring(fileName.indexOf(".") + 1);
	}

	/**
	 * 创建压缩版的图片
	 * 
	 * @param srcFile
	 * @return
	 * @throws Exception
	 */
	private void createCompressionImage(File srcFile, ImageVO vo)
			throws Exception
	{
		// 压缩图片
		String[] arr = ImageCompressHelper
				.compress(srcFile, this.getVirtualDir(),
						this.getCompressFolder(),
						this.getDefaultCompressWidth(),
						this.getDefaultCompressHeight());
		vo.setSmallAbsolutePath(arr[0]);
		vo.setSmallRelativePath(arr[1]);
	}
}
