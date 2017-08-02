package com.ycl.framework.db.entity;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.ycl.framework.utils.util.FastJSONParser;

import java.util.List;

/**
 * 用户详情  Created by joeYu on 16/12/8.
 */

@DatabaseTable(tableName = "local_user_info")
public class UserInfoBean extends DBEntity {
    @DatabaseField(generatedId = true)
    private long dbId; //数据库自增长id

    @DatabaseField
    private long id;

    @DatabaseField
    private long createTime;

    @DatabaseField
    private long cpdateTime;

    @DatabaseField
    private String Remark;

    @DatabaseField
    private String LoginID;

    @DatabaseField
    private String Password;

    @DatabaseField
    private String realName;

    @DatabaseField
    private String nickName;

    @DatabaseField
    private String avatarPath;

    @DatabaseField
    private String mobile;

    @DatabaseField
    private String lastLoginIp;


    @DatabaseField
    private Long lastLoginTime;

    @DatabaseField
    private Long birthday;

    @DatabaseField
    private String location;

    @DatabaseField
    private String os;

    @DatabaseField
    private String weibo;

    @DatabaseField
    private String weixin;

    @DatabaseField
    private String qq;

    @DatabaseField
    private String email;

    @DatabaseField
    private int education;

    @DatabaseField
    private int monthlyIncome;

    @DatabaseField
    private int gender;

    @DatabaseField
    private int age;

    @DatabaseField
    private String token;

    @DatabaseField
    private float balance;


    private String userImId; // 用户Im账户ID

    public long getDbId() {
        return dbId;
    }

    public void setDbId(long dbId) {
        this.dbId = dbId;
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

    public long getCpdateTime() {
        return cpdateTime;
    }

    public void setCpdateTime(long cpdateTime) {
        this.cpdateTime = cpdateTime;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getLoginID() {
        return LoginID;
    }

    public void setLoginID(String loginID) {
        LoginID = loginID;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public Long getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Long lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Long getBirthday() {
        return birthday;
    }

    public void setBirthday(Long birthday) {
        this.birthday = birthday;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getWeibo() {
        return weibo;
    }

    public void setWeibo(String weibo) {
        this.weibo = weibo;
    }

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getEducation() {
        return education;
    }

    public void setEducation(int education) {
        this.education = education;
    }

    public int getMonthlyIncome() {
        return monthlyIncome;
    }

    public void setMonthlyIncome(int monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public String getUserImId() {
        return userImId;
    }

    public void setUserImId(String userImId) {
        this.userImId = userImId;
    }
}
