package com.taotao.rest.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.JsonUtils;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.pojo.TbItemParamItemExample;
import com.taotao.pojo.TbItemParamItemExample.Criteria;
import com.taotao.rest.dao.JedisClient;
import com.taotao.rest.service.ItemService;

/**
 * 商品信息service
 * @author Admin
 *
 */
@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private TbItemMapper itemMapper;
	@Autowired
	private TbItemDescMapper itemDescMapper;
	@Autowired
	private TbItemParamItemMapper itemParamItemMapper;
	@Autowired 
	private JedisClient jedisClient;
	@Value("${ITEM_REDIS_KEY}")
	private String ITEM_REDIS_KEY;	//商品信息在redis中保存的key
	@Value("${ITEM_REDIS_EXPIRE}")
	private String ITEM_REDIS_EXPIRE;	//商品信息在redis中保存的过期时间
	
	
	/**
	 * 查询商品信息
	 */
	@Override
	public TaotaoResult getItemBaseInfo(long itemId) {
		//该商品redis中商品基本信息对应的key
        String key = ITEM_REDIS_KEY + ":" + itemId + ":base";
		//添加商品缓存逻辑
        try {
        	//从缓存中取商品信息
        	String json = jedisClient.get(key);
        	if(!StringUtils.isBlank(json)) {
        		//把json转换成java对象
        		TbItem item = JsonUtils.jsonToPojo(json, TbItem.class);
        		return TaotaoResult.ok(item);
        	}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//根据商品id查询信息
		TbItem item = itemMapper.selectByPrimaryKey(itemId);
		//把商品信息写入缓存
		try {
			jedisClient.set(key, JsonUtils.objectToJson(item));
			//设置key的有效期
			jedisClient.expire(key, Integer.parseInt(ITEM_REDIS_EXPIRE));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return TaotaoResult.ok(item);
	}

	/**
	 * 查询商品描述
	 */
	@Override
	public TaotaoResult getItemDescInfo(long itemId) {
		//该商品redis中商品基本信息对应的key
        String key = ITEM_REDIS_KEY + ":" + itemId + ":desc";
		//添加商品缓存逻辑
        try {
        	//从缓存中取商品详情
        	String json = jedisClient.get(key);
        	if(!StringUtils.isBlank(json)) {
        		//把json转换成java对象
        		TbItemDesc itemDesc = JsonUtils.jsonToPojo(json, TbItemDesc.class);
        		return TaotaoResult.ok(itemDesc);
        	}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//根据商品id查询详情
        TbItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(itemId);
		//把商品信息写入缓存
		try {
			jedisClient.set(key, JsonUtils.objectToJson(itemDesc));
			//设置key的有效期
			jedisClient.expire(key, Integer.parseInt(ITEM_REDIS_EXPIRE));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return TaotaoResult.ok(itemDesc);
	}

	/**
	 * 查询商品规格参数
	 */
	@Override
	public TaotaoResult getItemParamsInfo(long itemId) {
		//该商品redis中商品基本信息对应的key
        String key = ITEM_REDIS_KEY + ":" + itemId + ":params";
		//添加商品缓存逻辑
        try {
        	//从缓存中取商品规格参数
        	String json = jedisClient.get(key);
        	if(!StringUtils.isBlank(json)) {
        		//把json转换成java对象
        		TbItemParamItem paramItem = JsonUtils.jsonToPojo(json, TbItemParamItem.class);
        		return TaotaoResult.ok(paramItem);
        	}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//根据商品id查询规格信息
        TbItemParamItem itemParamItem = null;
        TbItemParamItemExample example = new TbItemParamItemExample();
        Criteria criteria = example.createCriteria();
        criteria.andItemIdEqualTo(itemId);
        List<TbItemParamItem> list = itemParamItemMapper.selectByExampleWithBLOBs(example);
        if (list != null && list.size() > 0) {
            itemParamItem = list.get(0);
        }
		//把商品规格写入缓存
		try {
			jedisClient.set(key, JsonUtils.objectToJson(itemParamItem));
			//设置key的有效期
			jedisClient.expire(key, Integer.parseInt(ITEM_REDIS_EXPIRE));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return TaotaoResult.ok(itemParamItem);
	}

}
