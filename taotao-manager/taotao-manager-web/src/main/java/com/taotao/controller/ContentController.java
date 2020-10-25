package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;
import com.taotao.service.ContentService;

/**
 * 内容分类列表管理
 * @author Admin
 *
 */
@Controller
@RequestMapping("/content")
public class ContentController {

	@Autowired
	private ContentService contentService;
	
	/**
	 * 查询网站内容管理列表
	 * @param page 页数
	 * @param rows 数据长度
	 * @param categoryId 分类id
	 * @return
	 */
	@RequestMapping("/query/list")
	@ResponseBody
	public EUDataGridResult getContentList(int page, int rows, long categoryId) {
		return contentService.getContentList(page, rows, categoryId);
	}
	
	/**
	 * 添加网站内容管理列表
	 * @param content
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	public TaotaoResult saveContentList(TbContent content) {
		contentService.insertContent(content);
		return TaotaoResult.ok();
	}
	
	/**
	 * 更新网站内容管理列表
	 * @param content
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public TaotaoResult updateContentList(TbContent content) {
		contentService.updateContent(content);
		return TaotaoResult.ok();
	}
	
	/**
	 * 删除网站内容管理列表
	 * @param content
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public TaotaoResult deleteContentList(long ids) {
		contentService.deleteContent(ids);
		return TaotaoResult.ok();
	}
}
