package com.hengzhiyi.it.pic.wscontroller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hengzhiyi.it.pic.base.BaseController;
import com.hengzhiyi.it.pic.services.IImgSearchService;
import com.hengzhiyi.it.pic.vo.ImageVO;
import com.hengzhiyi.it.pic.vo.PagedVO;

/**
 * 对外开放的图片查询服务控制器
 * 
 * @author liutianlong
 *
 */
@Controller
@RequestMapping("/ws/search")
public class WsImgSearchController extends BaseController
{
	private final static Logger logger = Logger
			.getLogger(WsImgSearchController.class);
	@Autowired
	private IImgSearchService imgSearchService;

	@RequestMapping(value = "/doSearch", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	@ResponseBody
	public List<String> getImagesUrlBySku(HttpServletRequest request,
			@RequestParam(required = true, defaultValue = "") String sku)
			throws Exception
	{
		List<String> result = null;
		try
		{
			result = imgSearchService.getImagesUrlBySku(request, sku);
		} catch (Exception e)
		{
			logger.error("对外开放的service:/ws/search/doSearch,在请求时出错了,原因：", e);
			throw e;
		}
		return result;
	}

	@RequestMapping(value = "/getImagesBySku", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	@ResponseBody
	public List<ImageVO> getImagesBySku(HttpServletRequest request,
			@RequestParam(required = true, defaultValue = "") String sku)
			throws Exception
	{
		List<ImageVO> result = null;
		try
		{
			result = imgSearchService.getImagesBySku(request, sku);
		} catch (Exception e)
		{
			logger.error("对外开放的service:/ws/search/getImagesBySku,在请求时出错了,原因：",
					e);
			throw e;
		}
		return result;
	}

	/**
	 * 分页根据SKU查询图片信息
	 * 
	 * @param request
	 * @param sku
	 * @param params
	 *            分页信息：currPage(当前页),pageSize(每页多少条)
	 * @return 
	 *         PagedVo:rows(获取的数据),currPage(当前页),pageSize(每页多少条),total(总记录数),pages
	 *         (总页数),resStatus(查询状态:200成功,100失败)
	 */
	@RequestMapping(value = "/getPagedImagesBySku", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	@ResponseBody
	public PagedVO<List<ImageVO>> getPagedImagesBySku(
			HttpServletRequest request,
			@RequestParam(required = true, defaultValue = "") String sku,
			PagedVO<?> params)
	{
		PagedVO<List<ImageVO>> result = null;
		try
		{
			params.setKeyWord(sku);
			result = imgSearchService.findImages(request, params);
			result.setResStatus(PagedVO.PagedResponseStatus.SUCCESS);
		} catch (Exception e)
		{
			throw new RuntimeException(e);
		}
		return result;
	}
}
