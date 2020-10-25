package com.taotao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.service.ContentCategoryService;

/**
 * 内容分类管理
 * @author Admin
 *
 */
@Controller
@RequestMapping("/content/category")
public class ContentCategoryController {

	@Autowired
	private ContentCategoryService contentCategoryService;
	
	/**
	 * 查询内容分类
	 * @param parentId
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public List<EUTreeNode> getContentCatList(@RequestParam(value = "id", defaultValue = "0")Long parentId){
		List<EUTreeNode> list = contentCategoryService.getCategoryList(parentId);
		return list;
	}
	
	/**
	 * 添加节点
	 * @param parentId
	 * @param name
	 * @return
	 */
	@RequestMapping("/create")
	@ResponseBody
	public TaotaoResult createContentCategory(Long parentId, String name){
		TaotaoResult result = contentCategoryService.insertContentCategory(parentId, name);
		return result;
	}
	
	/**
	 * 删除节点
	 * @param parentId
	 * @param name
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public TaotaoResult deleteContentCategory(Long id){
		TaotaoResult result = contentCategoryService.deleteContentCategory(id);
		return result;
	}
	
	/**
	 * 重命名节点
	 * @param parentId
	 * @param name
	 * @return
	 */
	@RequestMapping("/update")
	@ResponseBody
	public TaotaoResult updateContentCategory(Long id, String name){
		TaotaoResult result = contentCategoryService.updateContentCategory(id, name);
		return result;
	}
}
