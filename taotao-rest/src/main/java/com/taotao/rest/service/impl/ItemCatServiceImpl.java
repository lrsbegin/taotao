package com.taotao.rest.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.utils.JsonUtils;
import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.CatNode;
import com.taotao.pojo.CatResult;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.pojo.TbItemCatExample.Criteria;
import com.taotao.rest.dao.JedisClient;
import com.taotao.rest.service.ItemCatService;

/**
 * 分类服务
 * 
 * @author Admin
 *
 */
@Service
public class ItemCatServiceImpl implements ItemCatService {

	@Autowired
	private TbItemCatMapper itemCatMapper;
	@Autowired
	private JedisClient jedisClient;
	@Value("${INDEX_CATEGORY_CONTENT_REDIS_KEY}")
	private String INDEX_CATEGORY_CONTENT_REDIS_KEY;

	@Override
	public CatResult getItemCatList() {
		CatResult catResult = new CatResult();
		//从缓存中取内容
		try {
			String result = jedisClient.get(INDEX_CATEGORY_CONTENT_REDIS_KEY);
			if(!StringUtils.isBlank(result)) {
				//将字符串转换成list
				List<Object> resultList = JsonUtils.jsonToList(result, Object.class);
				catResult.setData(resultList);
				return catResult;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 从数据库查询分类列表
		catResult.setData(getCatList(0));
		
		//向缓存中添加内容
		try {
			//将list转为字符串
			String cacheString = JsonUtils.objectToJson(getCatList(0));
			jedisClient.set(INDEX_CATEGORY_CONTENT_REDIS_KEY, cacheString);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return catResult;
	}

	/**
	 * 查询分类列表
	 * 
	 * @param parentId
	 * @return
	 */
	private List<?> getCatList(long parentId) {
		// 创建查询条件
		TbItemCatExample example = new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		// 执行查询
		List<TbItemCat> list = itemCatMapper.selectByExample(example);
		// 返回值list
		List resultList = new ArrayList<CatNode>();
		// 向list中添加节点
		int count = 0;
		for (TbItemCat tbItemCat : list) {
			// 判断是否为父节点
			if (tbItemCat.getIsParent()) {
				CatNode CatNode = new CatNode();
				if (parentId == 0) {
					CatNode.setName(
							"<a href='/products/" + tbItemCat.getId() + ".html'>" + tbItemCat.getName() + "</a>");
				} else {
					CatNode.setName(tbItemCat.getName());
				}
				CatNode.setUrl("/products/" + tbItemCat.getId() + ".html");
				CatNode.setItem(getCatList(tbItemCat.getId()));
				resultList.add(CatNode);
				count++;
				// 第一层只取14条记录
				if (parentId == 0 && count >= 14) {
					break;
				}
			} else {
				// 如果是叶子节点
				resultList.add("/products/" + tbItemCat.getId() + ".html|" + tbItemCat.getName());
			}
		}
		return resultList;
	}
	
	
}
