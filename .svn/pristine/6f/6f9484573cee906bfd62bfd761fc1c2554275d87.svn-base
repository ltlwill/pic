package com.hengzhiyi.it.pic.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import com.hengzhiyi.it.pic.vo.User;

@Controller
@RequestMapping("/test")
public class HelloWorldController
{
	private final static Logger logger = Logger.getLogger(HelloWorldController.class);

	@RequestMapping("/toTest")
	public String toTestPage(WebRequest request)
	{
		logger.debug("------invoke toTestPage method.------");
		return "test/test";
	}
	
	@RequestMapping("testJsonp")
	@ResponseBody
	public User test2()
	{
		System.out.println("----------testJsonp----------");
		User u = new User();
		u.setUserName("test");
		return u;
	}

}




