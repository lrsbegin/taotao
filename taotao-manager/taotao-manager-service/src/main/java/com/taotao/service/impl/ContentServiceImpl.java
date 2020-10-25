package com.taotao.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.pojo.TbContentExample.Criteria;
import com.taotao.service.ContentService;

/**
 * 内容管理
 * @author Admin
 *
 */
@Service
public class ContentServiceImpl implements ContentService {
	
	@Autowired
	private TbContentMapper contentMapper;
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${REST_CONTENT_CACHE_URL}")
	private String REST_CONTENT_CACHE_URL;

	@Override
	public EUDataGridResult getContentList(int page, int rows, long categoryId) {
		
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(categoryId);
		List<TbContent> list = contentMapper.selectByExample(example);
		//分页处理
		PageHelper.startPage(page, rows);
		//创建一个返回值对象
		EUDataGridResult result = new EUDataGridResult();
		result.setRows(list);
		PageInfo<TbContent> pageInfo = new PageInfo<TbContent>();
		result.setTotal(pageInfo.getTotal());
		return result;
	}

	@Override
	public TaotaoResult insertContent(TbContent content) {
		//补全pojo的内容
		content.setCreated(new Date());
		content.setUpdated(new Date());
		contentMapper.insert(content);
		//添加缓存同步逻辑
		try {
			HttpClientUtil.doGet(REST_BASE_URL + REST_CONTENT_CACHE_URL + content.getCategoryId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult updateContent(TbContent content) {
		content.setUpdated(new Date());
		contentMapper.updateByPrimaryKey(content);
		//添加缓存同步逻辑
		try {
			HttpClientUtil.doGet(REST_BASE_URL + REST_CONTENT_CACHE_URL + content.getCategoryId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult deleteContent(long ids) {
		contentMapper.deleteByPrimaryKey(ids);
		//添加缓存同步逻辑
//		try {
//			HttpClientUtil.doGet(REST_BASE_URL + REST_CONTENT_CACHE_URL + content.getCategoryId());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		return TaotaoResult.ok();
	}

}
