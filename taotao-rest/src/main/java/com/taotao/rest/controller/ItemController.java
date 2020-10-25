package com.taotao.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.rest.service.ItemService;

/**
 * 商品信息controller
 * @author Admin
 *
 */
@Controller
@RequestMapping("/item")
public class ItemController {

	@Autowired
	private ItemService itemService;
	
	@RequestMapping("/info/{itemId}")
	@ResponseBody
	public TaotaoResult getItemBaseInfo(@PathVariable long itemId) {
		return itemService.getItemBaseInfo(itemId);
	}
	
	@RequestMapping("/desc/{itemId}")
	@ResponseBody
	public TaotaoResult getItemDescInfo(@PathVariable long itemId) {
		return itemService.getItemDescInfo(itemId);
	}
	
	@RequestMapping("/params/{itemId}")
	@ResponseBody
	public TaotaoResult getItemParamsInfo(@PathVariable long itemId) {
		return itemService.getItemParamsInfo(itemId);
	}
}
