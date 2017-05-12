package com.hengzhiyi.it.pic.initalizer;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

/**
 * 系统初始化类
 * 
 * @author liutianlong
 *
 */
public class SysInitalizer implements WebBindingInitializer
{
	
	private Logger logger = Logger.getLogger(SysInitalizer.class);

	private final static String DATE_FMT_PATTERN = "yyyy-MM-dd HH:mm:ss";

	@Override
	public void initBinder(WebDataBinder webdatabinder, WebRequest webrequest)
	{
		logger.info("------invoke sysInitalizer...------");
		DateFormat df = new SimpleDateFormat(DATE_FMT_PATTERN);
		df.setLenient(false);
		webdatabinder.registerCustomEditor(Date.class, new CustomDateEditor(df,
				false));
	}

}
