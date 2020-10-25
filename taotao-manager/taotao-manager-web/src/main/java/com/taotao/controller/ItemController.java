package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.service.ItemService;


@Controller
@RequestMapping("/item")
public class ItemController {

	@Autowired
    private ItemService itemService;

	/**
	 * 根据id查询商品
	 * @param itemId
	 * @return
	 */
    @RequestMapping("/{itemId}")
    @ResponseBody
    public TbItem getItemById(@PathVariable Long itemId) {
        TbItem tbItem = itemService.getItemById(itemId);
        return tbItem;
    }
	
    /**
     * 查询商品列表并分页
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public EUDataGridResult getItemList(Integer page, Integer rows) {
        EUDataGridResult result = itemService.getItemList(page, rows);
        return result;
    }
    
    /**
     * 添加商品
     * @param item
     * @param desc
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    private TaotaoResult creatItem(TbItem item, String desc, String itemParams) throws Exception {
    	TaotaoResult result = itemService.creatItem(item, desc, itemParams);
    	return result;
	}
    
    /**
     * 编辑商品
     * @param ids
     * @return
     */
    @RequestMapping(value = "/query/desc/{id}")
    @ResponseBody
    private TaotaoResult editItem(@PathVariable long id) {
    	TbItemDesc itemDesc = itemService.getItemDesc(id);
    	return TaotaoResult.ok(itemDesc);
	}
    
    /**
     * 移除商品
     * @param ids
     * @return
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    private TaotaoResult removeItem(long ids) {
    	itemService.deleteItem(ids);
    	return TaotaoResult.ok();
	}
    
    /**
     * 下架商品
     * @param ids
     * @return
     */
    @RequestMapping(value = "/instock")
    @ResponseBody
    private TaotaoResult instockItem(long ids) {
    	itemService.changeItemStatus(ids, (byte)2);
    	return TaotaoResult.ok();
	}
    
    /**
     * 上架商品
     * @param ids
     * @return
     */
    @RequestMapping(value = "/reshelf")
    @ResponseBody
    private TaotaoResult reshelfItem(long ids) {
    	itemService.changeItemStatus(ids, (byte)1);
    	return TaotaoResult.ok();
	}
}
