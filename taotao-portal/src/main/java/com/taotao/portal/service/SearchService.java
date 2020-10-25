package com.taotao.portal.service;

import com.taotao.portal.pojo.SearchResult;

public interface SearchService {

	SearchResult queryString(String queryString, int page);
}
