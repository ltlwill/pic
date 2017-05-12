package com.hengzhiyi.it.pic.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.ehcache.Element;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hengzhiyi.it.pic.common.Constants;
import com.hengzhiyi.it.pic.dao.IAccountDao;
import com.hengzhiyi.it.pic.dao.ISysSettingsDao;
import com.hengzhiyi.it.pic.dao.IUserAccountDao;
import com.hengzhiyi.it.pic.exception.BusinessException;
import com.hengzhiyi.it.pic.tools.EhCacheManager;
import com.hengzhiyi.it.pic.vo.AccountVO;
import com.hengzhiyi.it.pic.vo.PagedVO;
import com.hengzhiyi.it.pic.vo.SysSettingsVO;
import com.hengzhiyi.it.pic.vo.User;
import com.hengzhiyi.it.pic.vo.UserAccountVO;

@Service
public class SysService implements ISysService
{
	private final static String SETTING_CACHE_NAME = "system_setting_cache";

	@Autowired
	private IAccountDao accountDao;

	@Autowired
	private IUserAccountDao userAccountDao;

	@Autowired
	private ISysSettingsDao sysSettingsDao;

	@Override
	public void editAccount(AccountVO vo) throws BusinessException
	{
		if (StringUtils.isBlank(vo.getId())) // 新建
		{
			List<AccountVO> accounts = accountDao.getAccounts(new AccountVO(vo
					.getName(), vo.getPlatform()));
			if (accounts != null && !accounts.isEmpty())
			{
				throw new BusinessException("创建账号失败，账号已存在",
						Constants.ErrorCode.USER_EXISTS);
			}
			vo.setCreateTime(new Date());
			accountDao.addAccount(vo);
		} else
		// 修改
		{
			accountDao.editAccount(vo);
		}

	}

	@Override
	public PagedVO<List<AccountVO>> getAccountList(PagedVO<?> params)
			throws BusinessException
	{
		// 获取当前页数据
		List<AccountVO> vos = accountDao.getAccountList(params);
		// 获取总记录数
		int total = accountDao.getAccountListCount(params);
		PagedVO<List<AccountVO>> result = new PagedVO<List<AccountVO>>();
		// 设置数据
		result.setRows(vos);
		result.setCurrPage(params.getCurrPage());
		result.setPageSize(params.getPageSize());
		result.setTotal(total);
		return result;
	}

	@Override
	public AccountVO getAccountById(String id) throws BusinessException
	{
		return accountDao.getAccontById(id);
	}

	@Override
	public void delAccountByIds(List<String> ids) throws BusinessException
	{
		if (ids == null || ids.isEmpty())
		{
			throw new BusinessException("删除账号失败，参数为空");
		}
		accountDao.delAccountByIds(ids);
	}

	@Override
	public List<Map<String, Object>> getBindAccountsInfo(String userId)
			throws BusinessException
	{
		if (StringUtils.isBlank(userId))
		{
			throw new BusinessException("参数为空");
		}
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		// 获取当前用户绑定的账号id集合
		List<String> bindIds = userAccountDao.getAccountIdsByUserId(userId);
		bindIds = (bindIds == null) ? new ArrayList<String>() : bindIds;
		// 获取所有账号的信息
		Map<String, List<AccountVO>> map = getAllAccounts4Group();
		Set<Map.Entry<String, List<AccountVO>>> set = map.entrySet();
		Iterator<Map.Entry<String, List<AccountVO>>> iterator = set.iterator();
		Map.Entry<String, List<AccountVO>> item = null;
		List<AccountVO> values = null;
		Map<String, Object> o = null;
		while (iterator.hasNext())
		{
			item = iterator.next();
			values = item.getValue();
			if (values != null && !values.isEmpty())
			{
				for (AccountVO vo : values)
				{
					// 设置为选中
					if (bindIds.contains(vo.getId()))
					{
						vo.setChecked(true);
					}
				}
			}
			o = new HashMap<String, Object>();
			o.put("name", item.getKey());
			o.put("children", values);
			result.add(o);
		}
		return result;
	}

	@Override
	public List<AccountVO> getAllAccounts() throws BusinessException
	{
		return accountDao.getAllAccounts();
	}

	@Override
	public Map<String, List<AccountVO>> getAllAccounts4Group()
			throws BusinessException
	{
		Map<String, List<AccountVO>> map = new LinkedHashMap<String, List<AccountVO>>();
		List<AccountVO> accounts = accountDao.getAllAccounts();
		if (accounts != null && !accounts.isEmpty())
		{
			for (AccountVO vo : accounts)
			{
				if (map.get(vo.getPlatform()) == null)
				{
					map.put(vo.getPlatform(), new ArrayList<AccountVO>());
				}
				map.get(vo.getPlatform()).add(vo);
			}
		}
		return map;
	}

	@Override
	public void doBindAccounts(String userId, List<String> accountIds)
			throws BusinessException
	{
		if (StringUtils.isBlank(userId))
		{
			throw new BusinessException("参数为空");
		}
		// 先删除
		userAccountDao.delByUserId(userId);
		if (accountIds != null && !accountIds.isEmpty())
		{
			// 在保存
			List<UserAccountVO> vos = new ArrayList<UserAccountVO>();
			for (String accountId : accountIds)
			{
				vos.add(new UserAccountVO(userId, accountId));

			}
			// 批量保存
			userAccountDao.addList(vos);
		}
	}

	@Override
	public List<Map<String, Object>> getBindAccountsByList()
			throws BusinessException
	{
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		// 获取所有账号的信息
		Map<String, List<AccountVO>> map = getAllAccounts4Group();
		Set<Map.Entry<String, List<AccountVO>>> set = map.entrySet();
		Iterator<Map.Entry<String, List<AccountVO>>> iterator = set.iterator();
		Map.Entry<String, List<AccountVO>> item = null;
		List<AccountVO> values = null;
		Map<String, Object> o = null;
		while (iterator.hasNext())
		{
			item = iterator.next();
			values = item.getValue();
			o = new HashMap<String, Object>();
			o.put("name", item.getKey());
			o.put("children", values);
			result.add(o);
		}
		return result;
	}

	@Override
	public String sysSetting(String settingJson) throws Exception
	{
		if (StringUtils.isBlank(settingJson))
		{
			throw new RuntimeException("保存系统设置信息失败，参数settingJson="
					+ settingJson);
		}
		List<SysSettingsVO> vos = sysSettingsDao.getAll();
		SysSettingsVO vo = null;
		if (vos != null && !vos.isEmpty())
		{
			vo = vos.get(0);
			vo.setContent(settingJson);
			vo.setUpdateTime(new Date());
			sysSettingsDao.update(vo);
		} else
		{
			vo = new SysSettingsVO(settingJson, new Date());
			sysSettingsDao.add(vo);
		}
		// 更行缓存
		EhCacheManager.put(SETTING_CACHE_NAME, settingJson);
		return settingJson;
	}

	@Override
	public String getSysSetting() throws Exception
	{

		String setting = null;
		Element ele = EhCacheManager.get(SETTING_CACHE_NAME);
		if (ele == null || ele.getValue() == null)
		{
			List<SysSettingsVO> vos = sysSettingsDao.getAll();
			setting = (vos == null || vos.isEmpty()) ? null : vos.get(0)
					.getContent();
		} else
		{
			setting = (String) ele.getValue();
		}
		return setting;
	}

	@Override
	public boolean validateLoginTime(User user) throws Exception
	{
		// 如果登录白名单或者是管理员，则直接通过
		if (user == null
				|| (user != null && Integer.valueOf(
						User.WhiteListType.WHITE_LIST).equals(
						user.getWhiteListType()))
				|| Integer.valueOf(User.UserType.ADMIN).equals(
						user.getUserType()))
		{
			return true;
		}
		Calendar now = Calendar.getInstance();
		int day = now.get(Calendar.DAY_OF_WEEK);
		// 星期六星期天不允许登录
		if (day == Calendar.SATURDAY || day == Calendar.SUNDAY)
		{
			return false;
		}
		// 获取系统设置
		String settings = getSysSetting();
		// 没有设置信息，则直接通过
		if (StringUtils.isBlank(settings))
		{
			return true;
		}
		// 开始验证设置的登录时间段
		JSONArray arr = JSONArray.fromObject(settings);
		JSONObject o = null;
		String startTimeStr = null, endTimeStr = null;
		for (int i = 0; i < arr.size(); i++)
		{
			o = JSONObject.fromObject(arr.getString(i));
			startTimeStr = o.getString("startTime");
			endTimeStr = o.getString("endTime");
			// 如果设置的时间段为空或者时时间段不符合规范，则不验证
			if (StringUtils.isBlank(startTimeStr)
					|| startTimeStr.indexOf(":") < 0
					|| StringUtils.isBlank(endTimeStr)
					|| endTimeStr.indexOf(":") < 0)
			{
				continue;
			}
			String[] startTimeArr = startTimeStr.split(":");
			String[] endTimeArr = endTimeStr.split(":");
			// 开始时间
			Calendar start = Calendar.getInstance();
			start.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH),
					now.get(Calendar.DAY_OF_MONTH),
					Integer.parseInt(startTimeArr[0]),
					Integer.parseInt(startTimeArr[1]));
			// 结束时间
			Calendar end = Calendar.getInstance();
			end.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH),
					now.get(Calendar.DAY_OF_MONTH),
					Integer.parseInt(endTimeArr[0]),
					Integer.parseInt(endTimeArr[1]));
			// 如果当前时间在允许登录的起始时间 且 当前时间在允许登录的结束时间之间，则通过验证
			if (now.after(start) && now.before(end))
			{
				return true;
			}
		}
		return false;
	}

}
