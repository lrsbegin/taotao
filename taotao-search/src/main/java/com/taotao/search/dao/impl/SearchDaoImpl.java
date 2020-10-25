package com.taotao.search.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.taotao.search.dao.SearchDao;
import com.taotao.search.pojo.Item;
import com.taotao.search.pojo.SearchResult;

@Repository
public class SearchDaoImpl implements SearchDao {

	@Autowired
	private SolrClient solrClient;
	
	@Override
	public SearchResult search(SolrQuery query) throws Exception {
		//返回值对象
		SearchResult result = new SearchResult();
		//根据查询条件查询索引库
		QueryResponse response = solrClient.query("mySolrCore", query);
		//取查询结果
		SolrDocumentList solrDocumentList = response.getResults();
		//查询总记录数
		result.setRecordCount(solrDocumentList.getNumFound());
		//商品列表
		List<Item> items = new ArrayList<Item>();
		//取高亮显示
		Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
		//取商品列表
		for (SolrDocument solrDocument : solrDocumentList) {
			//创建一个商品对象
			Item item = new Item();
			item.setId((String) solrDocument.get("id"));
			//取高亮显示的结果
			List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
			String title = "";
			if(list != null && list.size() > 0) {
				title = list.get(0);
			}else {
				title = (String) solrDocument.get("item_title");
			}
			item.setTitle(title);
			item.setSellPoint((String) solrDocument.get("item_sell_point"));
			Object item_price = solrDocument.get("item_price");
			item.setPrice(Long.parseLong(item_price.toString()));
			item.setImage((String) solrDocument.get("item_image"));
			item.setCategoryName((String) solrDocument.get("item_category"));
			items.add(item);
		}
		result.setItems(items);
		return result;
	}

}
