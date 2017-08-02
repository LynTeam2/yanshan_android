package com.ycl.framework.db.entity;

import android.text.TextUtils;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by dodozhou on 2017/3/16.
 */
@DatabaseTable(tableName = "local_user_detail_info")
public class UserDetailBean extends DBEntity {

    @DatabaseField(generatedId = true)
    private long dbId; //数据库自增长id
    @DatabaseField
    private long user_id;
    @DatabaseField
    private String login_id;
    @DatabaseField
    private String user_im_id;
    @DatabaseField
    private String realname;
    @DatabaseField
    private String nickname = "";
    @DatabaseField
    private String password;
    @DatabaseField
    private int user_type_id;
    @DatabaseField
    private int user_level;
    @DatabaseField
    private int gender;//性别 0女;1男
    @DatabaseField
    private String avatar_path = ""; //头像路径
    @DatabaseField
    private String type;  //讲师类型 1=名人，2=名师
    @DatabaseField
    private String job_title;
    @DatabaseField
    private String introduction;
    @DatabaseField
    private String cover_path; //讲师海报路径
    @DatabaseField
    private String qualify;
    @DatabaseField
    private String create_time;  //创建时间 yyy/MM/dd


    @DatabaseField
    private float balance;//用户账号余额  单位财豆

    @DatabaseField
    private long score;//用户积分余额

    @DatabaseField
    private int is_concerned;  //当前用户是否关注了当前讲师，0:否 1:是。在用户登录的时候，该字段才有效。
    @DatabaseField
    private int author_id;  //讲师ID
    @DatabaseField
    private int fans_count;  //讲师粉丝数量
    @DatabaseField
    private String level_name;//普通用户级别名称
    @DatabaseField
    private String level_icon;//普通用户级别图标
    @DatabaseField
    private String author_level_name;//主播级别名称
    @DatabaseField
    private String author_level_icon;//主播级别图标
    @DatabaseField
    private String next_author_level_name;//下一级主播等级名称
    @DatabaseField
    private String next_author_level_icon;//下一级主播等级图标
    @DatabaseField
    private int difference_amount;//主播等级财豆差额

    //更新资料所需字段
    @DatabaseField
    private String mobile;//手机号
    @DatabaseField
    private long qq;
    @DatabaseField
    private String email;
    @DatabaseField
    private int age;//年龄
    @DatabaseField
    private String location;//地区
    @DatabaseField
    private int monthly_income;//月收入 '月收入（元RMB）：1=3k以下；2=3k~5k；3=5k~7k；4=7k~9k；5=9k~10k；6=10k~15k；7=15k~20k；8=20k~25k；9=25k以上' ,
    @DatabaseField
    private int education;//：1=博士；2=硕士；3=本科；4=专科；5=中学；6=小学；7=其他。'

    @DatabaseField
    private String job;//职业

    //评测系统
    @DatabaseField
    private int risk_assess_score;//：风险评测结果分数'
    @DatabaseField
    private int skill_level;//：当前技能等级 ,'
    @DatabaseField
    private int skill_score;//：当前技能评分,'
    @DatabaseField
    private int invest_type;//  0=保守型（C1）、1=相对保守型（C2）、2=稳健型（C3）、3=相对积极型（C4）、4=积极型（C5）
    @DatabaseField
    private String risk_assess_time;//提交测评时间

    private String product_ids;//问答计费产品，多个产品之间用,分割

    //资料是否完善
    public boolean getCompleteState() {
        return !TextUtils.isEmpty(mobile) && !TextUtils.isEmpty(realname) && !TextUtils.isEmpty(location) && !TextUtils.isEmpty(job) && age > 0;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public long getDbId() {
        return dbId;
    }

    public void setDbId(long dbId) {
        this.dbId = dbId;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getLogin_id() {
        return login_id;
    }

    public void setLogin_id(String login_id) {
        this.login_id = login_id;
    }

    public String getUser_im_id() {
        return user_im_id;
    }

    public void setUser_im_id(String user_im_id) {
        this.user_im_id = user_im_id;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUser_type_id() {
        return user_type_id;
    }

    public void setUser_type_id(int user_type_id) {
        this.user_type_id = user_type_id;
    }

    public int getUser_level() {
        return user_level;
    }

    public void setUser_level(int user_level) {
        this.user_level = user_level;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getAvatar_path() {
        return avatar_path;
    }

    public void setAvatar_path(String avatar_path) {
        this.avatar_path = avatar_path;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getJob_title() {
        return job_title;
    }

    public void setJob_title(String job_title) {
        this.job_title = job_title;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getCover_path() {
        return cover_path;
    }

    public void setCover_path(String cover_path) {
        this.cover_path = cover_path;
    }

    public String getQualify() {
        return qualify;
    }

    public void setQualify(String qualify) {
        this.qualify = qualify;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public int getIs_concerned() {
        return is_concerned;
    }

    public void setIs_concerned(int is_concerned) {
        this.is_concerned = is_concerned;
    }

    public int getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(int author_id) {
        this.author_id = author_id;
    }

    public int getFans_count() {
        return fans_count;
    }

    public void setFans_count(int fans_count) {
        this.fans_count = fans_count;
    }

    public String getLevel_name() {
        return level_name;
    }

    public void setLevel_name(String level_name) {
        this.level_name = level_name;
    }

    public String getLevel_icon() {
        return level_icon;
    }

    public void setLevel_icon(String level_icon) {
        this.level_icon = level_icon;
    }

    public String getAuthor_level_name() {
        return author_level_name;
    }

    public void setAuthor_level_name(String author_level_name) {
        this.author_level_name = author_level_name;
    }

    public String getAuthor_level_icon() {
        if (TextUtils.isEmpty(author_level_icon))
            return "";
        if (author_level_icon.contains(",")) {
            return author_level_icon.split(",")[1];
        }
        return author_level_icon;
    }

    public void setAuthor_level_icon(String author_level_icon) {
        this.author_level_icon = author_level_icon;
    }

    public String getNext_author_level_name() {
        return next_author_level_name;
    }

    public void setNext_author_level_name(String next_author_level_name) {
        this.next_author_level_name = next_author_level_name;
    }

    public String getNext_author_level_icon() {
        return next_author_level_icon;
    }

    public void setNext_author_level_icon(String next_author_level_icon) {
        this.next_author_level_icon = next_author_level_icon;
    }

    public int getDifference_amount() {
        return difference_amount;
    }

    public void setDifference_amount(int difference_amount) {
        this.difference_amount = difference_amount;
    }

    public long getQq() {
        return qq;
    }

    public void setQq(long qq) {
        this.qq = qq;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getMonthly_income() {
        return monthly_income;
    }

    public void setMonthly_income(int monthly_income) {
        this.monthly_income = monthly_income;
    }

    public int getEducation() {
        return education;
    }

    public void setEducation(int education) {
        this.education = education;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public int getRisk_assess_score() {
        return risk_assess_score;
    }

    public void setRisk_assess_score(int risk_assess_score) {
        this.risk_assess_score = risk_assess_score;
    }

    public int getSkill_level() {
        return skill_level;
    }

    public void setSkill_level(int skill_level) {
        this.skill_level = skill_level;
    }

    public int getSkill_score() {
        return skill_score;
    }

    public void setSkill_score(int skill_score) {
        this.skill_score = skill_score;
    }

    public int getInvest_type() {
        return invest_type;
    }

    public void setInvest_type(int invest_type) {
        this.invest_type = invest_type;
    }


    public String getRisk_assess_time() {
        return risk_assess_time;
    }

    public void setRisk_assess_time(String risk_assess_time) {
        this.risk_assess_time = risk_assess_time;
    }

    public String getProduct_ids() {
        return product_ids;
    }

    public void setProduct_ids(String product_ids) {
        this.product_ids = product_ids;
    }
}
