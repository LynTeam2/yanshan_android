package cn.gov.bjys.onlinetrain.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/2/4 0004.
 */
public class HomeAnJianList {
    private List<HomeAnJianBean> content;

    public List<HomeAnJianBean> getContent() {
        return content;
    }

    public void setContent(List<HomeAnJianBean> content) {
        this.content = content;
    }

    public static class Parent{
        HomeAnJianList newsList;

        public HomeAnJianList getNewsList() {
            return newsList;
        }

        public void setNewsList(HomeAnJianList newsList) {
            this.newsList = newsList;
        }
    }

}
