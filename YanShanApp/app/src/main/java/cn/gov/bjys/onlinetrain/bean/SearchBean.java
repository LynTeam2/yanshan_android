package cn.gov.bjys.onlinetrain.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;



public class SearchBean implements MultiItemEntity {

    public final static int KESHI = 0;//课时
    public final static int LAW = 1;//具体法规




    private String item;
    private String type;

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int getItemType() {
        if("law".equals(this.getType())){
            return LAW;
        }else{
            return KESHI;
        }
    }

    public static class SearchParentBean {
        List<SearchBean> items;

        public List<SearchBean> getItems() {
            return items;
        }

        public void setItems(List<SearchBean> items) {
            this.items = items;
        }
    }
}
