package com.taotao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 页面跳转controller
 * @author Admin
 *
 */
@Controller
public class PageController {

	/*
	 * 打开首页
	 */
	@RequestMapping("/")
	public String showIndex() {
		return "index";
	}
	
	/*
	 * 跳转功能页面
	 */
	@RequestMapping("/{page}")
	public String showPage(@PathVariable String page) {
		return page;
	}
	
}
