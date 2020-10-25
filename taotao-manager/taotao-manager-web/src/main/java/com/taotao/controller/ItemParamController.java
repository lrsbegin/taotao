package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItemParam;
import com.taotao.service.ItemParamService;

/**
 * 商品规格参数模板管理
 * @author Admin
 *
 */
@Controller
@RequestMapping("/item/param")
public class ItemParamController {

	@Autowired
	private ItemParamService itemParamService;
	
	/**
	 * 根据cid查询商品规格参数模板
	 * @param itemCatId
	 * @return
	 */
	@RequestMapping("/query/itemcatid/{itemCatId}")
	@ResponseBody
	public TaotaoResult getItemParamByCid(@PathVariable Long itemCatId) {
		TaotaoResult result = itemParamService.getItemParamByCid(itemCatId);
		return result;
	}
	
	/**
	 * 添加商品规格参数模板
	 * @param cid
	 * @param paramData
	 * @return
	 */
	@RequestMapping("/save/{cid}")
	@ResponseBody
	public TaotaoResult insertItemParam(@PathVariable Long cid, String paramData) {
		//创建pojo对象
		TbItemParam itemParam = new TbItemParam();
		itemParam.setItemCatId(cid);
		itemParam.setParamData(paramData);
		itemParamService.insertItemparam(itemParam);
		return null;
	}
	
	/**
	 * 展示商品规格参数模板
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/list")
    @ResponseBody
    public EUDataGridResult getItemList(Integer page, Integer rows) {
		return itemParamService.getItemParamList(page, rows);
	}

	/**
	 * 删除商品规格参数模板
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public TaotaoResult deleteItemparam(long ids) {
		itemParamService.deleteItemparam(ids);
		return TaotaoResult.ok();
	}
}
