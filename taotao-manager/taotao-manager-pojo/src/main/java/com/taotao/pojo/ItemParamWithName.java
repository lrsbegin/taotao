package com.taotao.pojo;

public class ItemParamWithName extends TbItemParam {

    private String itemCatName;

    public ItemParamWithName(TbItemParam itemParam, String itemCatName) {
        this.itemCatName = itemCatName;
        this.setUpdated(itemParam.getUpdated());
        this.setCreated(itemParam.getCreated());
        this.setId(itemParam.getId());
        this.setItemCatId(itemParam.getItemCatId());
        this.setParamData(itemParam.getParamData());
    }

    public String getItemCatName() {
        return itemCatName;
    }

    public void setItemCatName(String itemCatName) {
        this.itemCatName = itemCatName;
    }
}
