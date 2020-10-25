package com.taotao.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.IDUtils;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemDescExample;
import com.taotao.pojo.TbItemExample;
import com.taotao.pojo.TbItemExample.Criteria;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private TbItemMapper itemMapper;
    
    @Autowired 
    private TbItemDescMapper itemDescMapper;
    
    @Autowired
    private TbItemParamItemMapper itemParamItemMapper;

    @Override
    public TbItem getItemById(long itemId) {
//        TbItem item = itemMapper.selectByPrimaryKey(itemId);

        //添加查询条件
        TbItemExample example = new TbItemExample();
        Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(itemId);
        List<TbItem> list = itemMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            TbItem item = list.get(0);
            return item;
        }
        return null;
    }

	@Override
	public EUDataGridResult getItemList(int page, int rows) {
		//查询商品列表
		TbItemExample example = new TbItemExample();
		//分页处理
		PageHelper.startPage(page, rows);
		List<TbItem> list = itemMapper.selectByExample(example);
		//创建一个返回值对象
		EUDataGridResult result = new EUDataGridResult();
		result.setRows(list);
		//记录总条数
		PageInfo<TbItem> pageInfo = new PageInfo<TbItem>(list);
		result.setTotal(pageInfo.getTotal());
		return result;
	}

	@Override
	public TaotaoResult creatItem(TbItem item, String desc, String itemParam) throws Exception {
		//item补全
		//生成商品ID
		Long itemId = IDUtils.genItemId();
		item.setId(itemId);
		//商品状态，1-正常，2-下架，3-删除
		item.setStatus((byte)1);
		item.setCreated(new Date());
		item.setUpdated(new Date());
		//插入数据库
		itemMapper.insert(item);
		//添加商品描述信息
		TaotaoResult result = insertItemDesc(itemId, desc);
		if (result.getStatus() != 200) {
			throw new Exception();
		}
		//添加规格参数
		result = insertItemParamItem(itemId, itemParam);
		if (result.getStatus() != 200) {
			throw new Exception();
		}
		return TaotaoResult.ok();
	}
	
	/**
	 * 添加商品描述
	 * @param itemId
	 * @param desc
	 * @return
	 */
	private TaotaoResult insertItemDesc(Long itemId, String desc) {
		TbItemDesc itemDesc = new TbItemDesc();
		itemDesc.setItemId(itemId);
		itemDesc.setItemDesc(desc);
		itemDesc.setCreated(new Date());
		itemDesc.setUpdated(new Date());
		itemDescMapper.insert(itemDesc);
		return TaotaoResult.ok();
	}
	
	/**
	 * 添加规格参数
	 * @param itemId
	 * @param itemParam
	 * @return
	 */
	private TaotaoResult insertItemParamItem(Long itemId, String itemParam) {
		//创建一个
		TbItemParamItem itemParamItem = new TbItemParamItem();
		itemParamItem.setItemId(itemId);
		itemParamItem.setParamData(itemParam);
		itemParamItem.setCreated(new Date());
		itemParamItem.setUpdated(new Date());
		itemParamItemMapper.insert(itemParamItem);
		return TaotaoResult.ok();
	}

	/**
	 * 删除商品
	 */
	@Override
	public TaotaoResult deleteItem(long id) {
		itemMapper.deleteByPrimaryKey(id);
		return TaotaoResult.ok();
	}

	/**
	 * 切换商品状态
	 */
	@Override
	public TaotaoResult changeItemStatus(long id, byte status) {
		TbItem tbItem = itemMapper.selectByPrimaryKey(id);
		tbItem.setStatus(status);
		
		itemMapper.updateByPrimaryKey(tbItem);
		return TaotaoResult.ok();
	}

	/**
	 * 查询商品描述
	 */
	@Override
	public TbItemDesc getItemDesc(long id) {
		TbItemDescExample example = new TbItemDescExample();
		TbItemDescExample.Criteria criteria = example.createCriteria();
		criteria.andItemIdEqualTo(id);
		List<TbItemDesc> list = itemDescMapper.selectByExampleWithBLOBs(example);
		if(list != null && !list.isEmpty()) {
			TbItemDesc tbItemDesc = list.get(0);
			return tbItemDesc;
		}
		return null;
	}
	
}
