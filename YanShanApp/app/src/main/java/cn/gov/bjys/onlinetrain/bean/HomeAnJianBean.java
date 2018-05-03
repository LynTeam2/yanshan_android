package cn.gov.bjys.onlinetrain.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2018/2/4 0004.
 */
public class HomeAnJianBean implements Parcelable{
    private String content;
    private long id;
    private long createTime;
    private String introduction;
    private String newsTime;
    private String title;//
    private String imagePath;//

    public HomeAnJianBean(){

    }

    protected HomeAnJianBean(Parcel in) {
        content = in.readString();
        id = in.readLong();
        createTime = in.readLong();
        introduction = in.readString();
        newsTime = in.readString();
        title = in.readString();
        imagePath = in.readString();
    }

    public static final Creator<HomeAnJianBean> CREATOR = new Creator<HomeAnJianBean>() {
        @Override
        public HomeAnJianBean createFromParcel(Parcel in) {
            return new HomeAnJianBean(in);
        }

        @Override
        public HomeAnJianBean[] newArray(int size) {
            return new HomeAnJianBean[size];
        }
    };

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
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

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(content);
        dest.writeLong(id);
        dest.writeLong(createTime);
        dest.writeString(introduction);
        dest.writeString(newsTime);
        dest.writeString(title);
        dest.writeString(imagePath);
    }
}
