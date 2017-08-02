package com.ycl.framework.db.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DatabaseField;

/**
 * 课程Bean （课时/视频）
 */
public class VideoBean extends DBEntity implements Parcelable {
    @DatabaseField(generatedId = true)
    protected int loacl_id;
    @DatabaseField(columnName = "video_local_key")
    protected long id;
    @DatabaseField
    protected long video_id;  //课程视频ID
    @DatabaseField
    protected long author_id; //讲师ID
    @DatabaseField
    protected long user_id; //用户id 查询资料
    @DatabaseField
    protected String author_name; //讲师名称
    @DatabaseField
    protected String author_cover_path; //讲师封面路径，头像
    @DatabaseField
    protected String job_title;  //讲师职称
    @DatabaseField
    protected String video_name;  //课程视频名称
    @DatabaseField
    protected long video_hits;// 课程视频点击量(观看量)
    @DatabaseField
    protected String cover_path;  //视频图片
    @DatabaseField
    protected String video_intro;  //视频简介
    @DatabaseField
    protected String video_length;  //视频时长
    @DatabaseField
    protected int video_size;  //视频大小(KB)
    @DatabaseField
    protected int video_level;  //视频等级   视频等级（video_level） 0=R1 1=R2 2=R3 3=R4 4=R5
    @DatabaseField
    protected String file_id;  //视频腾讯云文件ID
    @DatabaseField
    protected long create_time; //视频创建时间（毫秒）
    @DatabaseField
    protected long course_id; //课程ID

    @DatabaseField
    protected int author_type; //讲师类别 1=名人、2=名师
    @DatabaseField
    protected int video_price; //视频价格(财豆金额)
    @DatabaseField
    protected String video_path; //视频播放路径
    @DatabaseField
    protected String course_name; //课程名称
    @DatabaseField
    protected String category_1; //课程一级类别
    @DatabaseField
    protected String category_2; //课程二级类别

    @DatabaseField
    protected String watchDeadline;//上次观看时间
    @DatabaseField
    protected String person_sign;//讲师个性签名

    @DatabaseField
    protected int is_collected;//当前用户是否收藏了当前视频，0:否 1:是。 在用户登录的时候，该字段才有效。
    @DatabaseField
    protected int is_buy;//当前用户是否购买了当前视频，0:否 1:是。
    @DatabaseField
    protected int is_evaluated;//当前用户是否评价，0:否 1:是。在用户登录的时候，该字段才有效

    @DatabaseField
    protected String product_ids;//计费的产品ID字符串，多个产品ID之间以英文逗号间隔

    public int getIs_buy() {
        return is_buy;
    }

    public void setIs_buy(int is_buy) {
        this.is_buy = is_buy;
    }

    public int getLoacl_id() {
        return loacl_id;
    }

    public void setLoacl_id(int loacl_id) {
        this.loacl_id = loacl_id;
    }

    public long getId() {
        if (id <= 0) {
            this.id = video_id;
            return this.id;
        }
        return id;
    }


    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getVideo_id() {
        return video_id;
    }

    public void setVideo_id(long video_id) {
        this.video_id = video_id;
    }

    public long getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(long author_id) {
        this.author_id = author_id;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getAuthor_cover_path() {
        return author_cover_path;
    }

    public void setAuthor_cover_path(String author_cover_path) {
        this.author_cover_path = author_cover_path;
    }

    public String getJob_title() {
        return job_title;
    }

    public void setJob_title(String job_title) {
        this.job_title = job_title;
    }

    public String getVideo_name() {
        return video_name;
    }

    public void setVideo_name(String video_name) {
        this.video_name = video_name;
    }

    public long getVideo_hits() {
        return video_hits;
    }

    public void setVideo_hits(long video_hits) {
        this.video_hits = video_hits;
    }

    public String getCover_path() {
        return cover_path;
    }

    public void setCover_path(String cover_path) {
        this.cover_path = cover_path;
    }

    public String getVideo_intro() {
        return video_intro;
    }

    public void setVideo_intro(String video_intro) {
        this.video_intro = video_intro;
    }

    public String getVideo_length() {
        return video_length;
    }

    public void setVideo_length(String video_length) {
        this.video_length = video_length;
    }

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public long getCourse_id() {
        return course_id;
    }

    public void setCourse_id(long course_id) {
        this.course_id = course_id;
    }

    public int getAuthor_type() {
        return author_type;
    }

    public void setAuthor_type(int author_type) {
        this.author_type = author_type;
    }

    public int getVideo_price() {
        return video_price;
    }

    public void setVideo_price(int video_price) {
        this.video_price = video_price;
    }

    public String getVideo_path() {
        return video_path;
    }

    public void setVideo_path(String video_path) {
        this.video_path = video_path;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getCategory_1() {
        return category_1;
    }

    public void setCategory_1(String category_1) {
        this.category_1 = category_1;
    }

    public String getCategory_2() {
        return category_2;
    }

    public void setCategory_2(String category_2) {
        this.category_2 = category_2;
    }

    public String getWatchDeadline() {
        return watchDeadline;
    }

    public void setWatchDeadline(String watchDeadline) {
        this.watchDeadline = watchDeadline;
    }

    public int getIs_collected() {
        return is_collected;
    }

    public void setIs_collected(int is_collected) {
        this.is_collected = is_collected;
    }


    public boolean getBuy() {
        return is_buy == 1;
    }

    public boolean getCollect() {
        return is_collected == 1;
    }


    public int getVideo_size() {
        return video_size;
    }

    public void setVideo_size(int video_size) {
        this.video_size = video_size;
    }

    public String getFile_id() {
        return file_id;
    }

    public void setFile_id(String file_id) {
        this.file_id = file_id;
    }


    public String getProduct_ids() {
        return product_ids;
    }

    public void setProduct_ids(String product_ids) {
        this.product_ids = product_ids;
    }


    public String getPerson_sign() {
        return person_sign;
    }

    public void setPerson_sign(String person_sign) {
        this.person_sign = person_sign;
    }

    public int getIs_evaluated() {
        return is_evaluated;
    }

    public void setIs_evaluated(int is_evaluated) {
        this.is_evaluated = is_evaluated;
    }

    public void setVideo_level(int video_level) {
        this.video_level = video_level;
    }

    public int getVideo_level() {
        return video_level;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.loacl_id);
        dest.writeLong(this.id);
        dest.writeLong(this.video_id);
        dest.writeLong(this.author_id);
        dest.writeLong(this.user_id);
        dest.writeString(this.author_name);
        dest.writeString(this.author_cover_path);
        dest.writeString(this.job_title);
        dest.writeString(this.video_name);
        dest.writeLong(this.video_hits);
        dest.writeString(this.cover_path);
        dest.writeString(this.video_intro);
        dest.writeString(this.video_length);
        dest.writeInt(this.video_size);
        dest.writeInt(this.video_level);
        dest.writeString(this.file_id);
        dest.writeLong(this.create_time);
        dest.writeLong(this.course_id);
        dest.writeInt(this.author_type);
        dest.writeInt(this.video_price);
        dest.writeString(this.video_path);
        dest.writeString(this.course_name);
        dest.writeString(this.category_1);
        dest.writeString(this.category_2);
        dest.writeString(this.watchDeadline);
        dest.writeString(this.person_sign);
        dest.writeInt(this.is_collected);
        dest.writeInt(this.is_buy);
        dest.writeInt(this.is_evaluated);
        dest.writeString(this.product_ids);
    }

    public VideoBean() {
    }

    protected VideoBean(Parcel in) {
        this.loacl_id = in.readInt();
        this.id = in.readLong();
        this.video_id = in.readLong();
        this.author_id = in.readLong();
        this.user_id = in.readLong();
        this.author_name = in.readString();
        this.author_cover_path = in.readString();
        this.job_title = in.readString();
        this.video_name = in.readString();
        this.video_hits = in.readLong();
        this.cover_path = in.readString();
        this.video_intro = in.readString();
        this.video_length = in.readString();
        this.video_size = in.readInt();
        this.video_level = in.readInt();
        this.file_id = in.readString();
        this.create_time = in.readLong();
        this.course_id = in.readLong();
        this.author_type = in.readInt();
        this.video_price = in.readInt();
        this.video_path = in.readString();
        this.course_name = in.readString();
        this.category_1 = in.readString();
        this.category_2 = in.readString();
        this.watchDeadline = in.readString();
        this.person_sign = in.readString();
        this.is_collected = in.readInt();
        this.is_buy = in.readInt();
        this.is_evaluated = in.readInt();
        this.product_ids = in.readString();
    }

    public static final Creator<VideoBean> CREATOR = new Creator<VideoBean>() {
        @Override
        public VideoBean createFromParcel(Parcel source) {
            return new VideoBean(source);
        }

        @Override
        public VideoBean[] newArray(int size) {
            return new VideoBean[size];
        }
    };
}
