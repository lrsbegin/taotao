package com.taotao.service;

import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItemParam;

public interface ItemParamService {

	TaotaoResult getItemParamByCid(long cid);
	
	TaotaoResult insertItemparam(TbItemParam itemParam);

	EUDataGridResult getItemParamList(Integer page, Integer rows);
	
	TaotaoResult deleteItemparam(long id);
}
