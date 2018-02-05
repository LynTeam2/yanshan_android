package cn.gov.bjys.onlinetrain.bean;

/**
 * Created by Administrator on 2018/2/5 0005.
 */
public class AnJianDetailBean {

    private String content;//": "鏂伴椈鍐呭",
        private long    createTime;//": 1517668606000,
        private long    id;//": 1,
    private String   introduction;//": "鏂伴椈绠€浠�",
    private String   newsTime;//": "2018-02-03",
    private String   title;//": "鏂伴椈鏍囬"

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getNewsTime() {
        return newsTime;
    }

    public void setNewsTime(String newsTime) {
        this.newsTime = newsTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static class  DetailParent{
        AnJianDetailBean news;

        public AnJianDetailBean getNews() {
            return news;
        }

        public void setNews(AnJianDetailBean news) {
            this.news = news;
        }
    }
}
