package com.taotao.portal.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.portal.pojo.ItemInfo;
import com.taotao.portal.service.ItemService;

/**
 * 查询商品信息service
 * @author Admin
 *
 */
@Service
public class ItemServiceImpl implements ItemService {

	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;	//基础url
	@Value("${ITEM_INFO_URL}")
	private String ITEM_INFO_URL;	//商品基本信息url
	@Value("${ITEM_DESC_URL}")
	private String ITEM_DESC_URL;	//商品描述信息url
	@Value("${ITEM_PARAMS_URL}")
	private String ITEM_PARAMS_URL;	//商品规格参数信息url
	
	@Override
	public ItemInfo getItemById(Long itemId) {
		try {
			//调用服务层的服务
			String json = HttpClientUtil.doGet(REST_BASE_URL + ITEM_INFO_URL + itemId);
			if(!StringUtils.isBlank(json)) {
				TaotaoResult result = TaotaoResult.formatToPojo(json, ItemInfo.class);
				if(result.getStatus() == 200) {
					ItemInfo item = (ItemInfo) result.getData();
					return item;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取商品描述
	 */
	@Override
	public String getItemDescById(Long itemId) {
		try {
			//调用服务层的服务
			String json = HttpClientUtil.doGet(REST_BASE_URL + ITEM_DESC_URL + itemId);
			if(!StringUtils.isBlank(json)) {
				TaotaoResult result = TaotaoResult.formatToPojo(json, TbItemDesc.class);
				if(result.getStatus() == 200) {
					TbItemDesc itemDesc = (TbItemDesc) result.getData();
					//取商品描述
					String desc = itemDesc.getItemDesc();
					return desc;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
    public String getItemParam(Long itemId) {
        try {
            String json = HttpClientUtil.doGet(REST_BASE_URL + ITEM_PARAMS_URL + itemId);
            if (!StringUtils.isBlank(json)) {
                TaotaoResult taotaoResult = TaotaoResult.formatToPojo(json, TbItemParamItem.class);
                if (taotaoResult.getStatus() == 200 && taotaoResult.getData() != null) {
//                    TbItemParamItem itemParamItem = null;
//                    itemParamItem = (TbItemParamItem) taotaoResult.getData();
                	TbItemParamItem itemParamItem = (TbItemParamItem) taotaoResult.getData();
                    String paramData = itemParamItem.getParamData();

                    //生成html
                    List<Map> jsonList = JsonUtils.jsonToList(paramData, Map.class);
                    StringBuffer sb = new StringBuffer();
                    sb.append(
                            "<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"1\" class=\"Ptable\">\n");
                    sb.append("    <tbody>\n");
                    for (Map m1 : jsonList) {
                        sb.append("        <tr>\n");
                        sb.append("            <th class=\"tdTitle\" colspan=\"2\">" + m1.get("group") + "</th>\n");
                        sb.append("        </tr>\n");
                        List<Map> list2 = (List<Map>) m1.get("params");
                        for (Map m2 : list2) {
                            sb.append("        <tr>\n");
                            sb.append("            <td class=\"tdTitle\">" + m2.get("k") + "</td>\n");
                            sb.append("            <td>" + m2.get("v") + "</td>\n");
                            sb.append("        </tr>\n");
                        }
                    }
                    sb.append("    </tbody>\n");
                    sb.append("</table>");
                    //返回html片段
                    return sb.toString();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
	}
}
