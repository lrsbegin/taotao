package com.taotao.rest.solrJ;

import java.util.HashMap;
import java.util.Map;

import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.MapSolrParams;
import org.apache.solr.common.params.SolrParams;
import org.junit.Test;

public class SolrJTest {

	@Test
	public void addDocument() throws Exception {
		final String solrUrl  = "http://192.168.137.133:8080/solr";
		//创建连接
        HttpSolrClient solrClient = new HttpSolrClient.Builder(solrUrl)
                .withConnectionTimeout(10000)
                .withSocketTimeout(60000)
                .build();
		//创建一个文本对象
		SolrInputDocument document = new SolrInputDocument();
        // 第一个参数：域的名称，域的名称必须是在schema.xml中定义的
        // 第二个参数：域的值
        // 注意：id的域不能少
//        document.addField("id", "c0001");
//        document.addField("item_title", "使用solrJ添加的文档");
//        document.addField("item_desc", "商品描述");
//        document.addField("item_sell_point", "商品卖点");
		document.setField("id", "c0001");
        document.setField("item_title", "使用solrJ添加的文档");
        document.setField("item_desc", "商品描述");
        document.setField("item_sell_point", "商品卖点");
		//把文本对象写入索引库中
        solrClient.add("mySolrCore", document);
		//提交
        solrClient.commit("mySolrCore");
	}
	
	@Test
	public void delDocument() throws Exception {
		final String solrUrl  = "http://192.168.137.133:8080/solr";
		//创建连接
        HttpSolrClient solrClient = new HttpSolrClient.Builder(solrUrl)
                .withConnectionTimeout(10000)
                .withSocketTimeout(60000)
                .build();
        solrClient.deleteById("mySolrCore", "160121256967168");
        solrClient.commit("mySolrCore");
	}
	
	@Test
    public void testQuery() throws Exception {
		final String solrUrl  = "http://192.168.137.133:8080/solr";
		//创建连接
        HttpSolrClient solrClient = new HttpSolrClient.Builder(solrUrl)
                .withConnectionTimeout(10000)
                .withSocketTimeout(60000)
                .build();
        // 定义查询条件
        Map<String, String> params = new HashMap<String, String>();
        params.put("q", "item_title:手机");
        SolrParams mapSolrParams = new MapSolrParams(params);
        //执行查询 第一个参数是collection，就是我们在solr中创建的core
        QueryResponse response = solrClient.query("mySolrCore", mapSolrParams);
        // 获取结果集
        SolrDocumentList results = response.getResults();
        for (SolrDocument result : results) {
            // SolrDocument 数据结构为Map
        	Object object = result.get("item_price");
            System.out.println(result.get("item_price"));
        }
    }
}
