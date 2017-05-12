package com.hengzhiyi.it.pic.appcontroller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hengzhiyi.it.pic.base.BaseController;
import com.hengzhiyi.it.pic.services.IImgSearchService;
import com.hengzhiyi.it.pic.vo.ImageVO;
import com.hengzhiyi.it.pic.vo.PagedVO;

/**
 * APP圖片查询控制器
 * 
 * @author liutianlong
 *
 */
@Controller
@RequestMapping("/app/search")
public class AppImgSearchController extends BaseController
{
	@Autowired
	private IImgSearchService imgSearchService;

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
			// result = new PagedVO<List<ImageVO>>(
			// PagedVO.PagedResponseStatus.ERROR);
			throw new RuntimeException(e);
		}
		return result;
	}
	
	@RequestMapping("/deleteImagesByIds")
	@ResponseBody
	public ResponseEntity<Object> deleteImages(String ids)
	{
		ResponseEntity<Object> res = null;
		try
		{
			imgSearchService.deleteImagesByIds(ids);
			res = new ResponseEntity<Object>(HttpStatus.OK);
		} catch (Exception e)
		{
			logger.error("------App delete image error------,cause:", e);
			res = new ResponseEntity<Object>(HttpStatus.EXPECTATION_FAILED);
		}
		return res;
	}
}
