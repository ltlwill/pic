package com.hengzhiyi.it.pic.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import com.hengzhiyi.it.pic.base.BaseController;
import com.hengzhiyi.it.pic.common.Constants;
import com.hengzhiyi.it.pic.services.IImgSearchService;
import com.hengzhiyi.it.pic.tools.FileHelper;
import com.hengzhiyi.it.pic.vo.ImageVO;
import com.hengzhiyi.it.pic.vo.PagedVO;

/**
 * 图片搜索控制器
 * 
 * @author liutianlong
 *
 */
@Controller
@RequestMapping("/search")
public class ImgSearchController extends BaseController
{
	private final static Logger logger = Logger
			.getLogger(ImgSearchController.class);

	private final static String TARGET_PAGE = "/pages/search/search.jsp";
	private final static String ACTIVE_MENU_ID = "img-search";

	@Autowired
	private IImgSearchService imgSearchService;

	@RequestMapping("/toSearch")
	public String toImgSearchPage(WebRequest request)
	{
		// 设置要渲染的目标页面名称
		request.setAttribute(Constants.TARGET_PAGE_NAME_FLAG, TARGET_PAGE,
				WebRequest.SCOPE_REQUEST);
		// 设置当前正在使用的菜单ID
		request.setAttribute(Constants.ACTIVE_MENU_ID_FLAG, ACTIVE_MENU_ID,
				WebRequest.SCOPE_REQUEST);
		return "home/index";
	}

	@RequestMapping("/doSearch")
	@ResponseBody
	public PagedVO<List<ImageVO>> imgSearch(HttpServletRequest request,
			PagedVO<?> params)
	{
		PagedVO<List<ImageVO>> result = null;
		try
		{
			result = imgSearchService.findImages(request, params);
			// 设置响应状态为成功
			result.setResStatus(PagedVO.PagedResponseStatus.SUCCESS);
		} catch (Exception e)
		{
			logger.error("-------search image error-------,cause:", e);
			// 设置响应状态为失败
			result = new PagedVO<List<ImageVO>>(
					PagedVO.PagedResponseStatus.ERROR);
		}
		return result;
	}

	@RequestMapping("/download")
	public ResponseEntity<byte[]> downloadImage(
			@RequestParam(required = false, defaultValue = "") String path)
	{
		try
		{
			logger.debug("------Begin download image,the relative path is :"
					+ path + "------");
			File image = imgSearchService.getImageByRelativePath(path);
			return createDownloadResponse(image);
		} catch (Exception e)
		{
			logger.error("------Download image error------,cause:", e);
		}
		return new ResponseEntity<byte[]>(HttpStatus.EXPECTATION_FAILED);
	}

	@RequestMapping("/batchDownload")
	public ResponseEntity<byte[]> batchDownloadImages(
			HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "") String ids)
	{
		File zipFile = null;
		try
		{
			logger.debug("------Begin batch download images,the ids are :"
					+ ids + "------");
			// 查询图片
			List<File> files = imgSearchService.getImageFilesByIds(ids);
			if (files != null && !files.isEmpty())
			{
				// 生成路径
				String zipPath = getZipPath(request, files);
				// 压缩
				compress(files, zipPath);
				zipFile = new File(zipPath);

				return createDownloadResponse(zipFile);
			}
		} catch (Exception e)
		{
			logger.error("------Download image error------,cause:", e);
		} finally
		{
			if (zipFile != null)
			{
				try
				{
					// 删除临时文件
					// FileUtils.forceDelete(zipFile);
					FileHelper.cycleDeleteFile(zipFile);
				} catch (Exception e)
				{
					logger.error("------Force delete file error------,cause:",
							e);
				}
			}
		}
		return new ResponseEntity<byte[]>(HttpStatus.EXPECTATION_FAILED);
	}

	@RequestMapping("/deleteImages")
	@ResponseBody
	public String deleteImages(String ids)
	{
		String flag = "";
		try
		{
			imgSearchService.deleteImagesByIds(ids);
			flag = "ok";
		} catch (Exception e)
		{
			logger.error("------Delete image error------,cause:", e);
			flag = "fail";
		}
		return flag;
	}

	private ResponseEntity<byte[]> createDownloadResponse(File file)
	{
		ResponseEntity<byte[]> res = null;
		try
		{
			HttpHeaders headers = new HttpHeaders();
			headers.setContentDispositionFormData("attachment", new String(file
					.getName().getBytes("GBK"), "ISO8859-1"));// 解决tomcat服务器中中文文件名乱码问题
			// headers.setContentDispositionFormData("attachment",
			// file.getName()); // tomcat服务器中中文文件名会出现乱码
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			res = new ResponseEntity<byte[]>(
					FileUtils.readFileToByteArray(file), headers, HttpStatus.OK);// 若这里状态使用CREATE,则在IE浏览器中下载会有问题（IE不支持201状态码）,所以改为OK(200)即可
		} catch (Exception e)
		{
			logger.error(
					"------Create donwload responseEntity error------,cause:",
					e);
			res = new ResponseEntity<byte[]>(HttpStatus.EXPECTATION_FAILED);
		}
		return res;
	}

	/**
	 * 将所有文件压缩成一个压缩包文件
	 * 
	 * @param files
	 * @return
	 */
	private void compress(List<File> files, String zipPath)
	{
		ZipOutputStream zos = null;
		FileInputStream is = null;
		try
		{
			ZipEntry entry = null;
			zos = new ZipOutputStream(new FileOutputStream(zipPath));
			byte[] buff = new byte[1024];
			for (File file : files)
			{
				is = new FileInputStream(file);
				try
				{
					entry = new ZipEntry(file.getName());
					zos.putNextEntry(entry);
					int len = 0;
					// 希尔邵压缩包
					while ((len = is.read(buff)) != -1)
					{
						zos.write(buff, 0, len);
					}
				} catch (Exception e)
				{
					logger.error(
							"------compress error,file name is:"
									+ file.getName() + "------,cause:", e);
					throw new RuntimeException(e);
				} finally
				{
					zos.closeEntry();
					is.close();
				}
			}
		} catch (Exception e)
		{
			logger.error("------comress error-------,cause:", e);
			throw new RuntimeException(e);
		} finally
		{
			if (is != null)
			{
				try
				{
					is.close();
				} catch (Exception e)
				{
					logger.error("------Inputstream close error-----,cause:", e);
				}
			}
			if (zos != null)
			{
				try
				{
					zos.flush();
					zos.close();
				} catch (Exception e)
				{
					logger.error(
							"------ZipOutputStream close error-----,cause:", e);
				}
			}
		}
	}

	private String getZipPath(HttpServletRequest request, List<File> files)
	{
		String name = files.get(0).getName();
		String tempPath = request.getServletContext().getRealPath(
				"downloadTemp");
		String zipPath = "";
		File file = null;
		try
		{
			zipPath = tempPath + File.separator + System.nanoTime()
					+ File.separator + name + "(等" + files.size() + "个文件)"
					+ ".zip";
			file = new File(zipPath);
		} catch (Exception e)
		{
			logger.error("------error,------cause:", e);
			throw new RuntimeException(e);
		}
		if (!file.getParentFile().exists())
		{
			file.getParentFile().mkdirs();
		}
		return zipPath;
	}
}
