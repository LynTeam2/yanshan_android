package com.ycl.framework.system;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


public class APNEntity {
    private String name;
    private String apn;
    private int id;
    private String proxy;
    private String port;
    private String user;
    private String password;
    private String type;
    private String mcc;
    private String mnc;
    private String mmsProxy;
    private String mmsPort;

    public APNEntity() {
        this((String) null, (String) null, -1, (String) null, (String) null, (String) null, (String) null, (String) null, (String) null, (String) null, (String) null, (String) null);
    }

    public APNEntity(String name, String apn, int id, String proxy, String port, String user, String password, String type, String mcc, String mnc, String mmsProxy, String mmsPort) {
        this.name = name;
        this.apn = apn;
        this.id = id;
        this.proxy = proxy;
        this.port = port;
        this.user = user;
        this.password = password;
        this.type = type;
        this.mcc = mcc;
        this.mnc = mnc;
        this.mmsProxy = mmsProxy;
        this.mmsPort = mmsPort;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getApn() {
        return this.apn;
    }

    public void setApn(String apn) {
        this.apn = apn;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProxy() {
        return this.proxy;
    }

    public void setProxy(String proxy) {
        this.proxy = proxy;
    }

    public String getPort() {
        return this.port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getUser() {
        return this.user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMcc() {
        return this.mcc;
    }

    public void setMcc(String mcc) {
        this.mcc = mcc;
    }

    public String getMnc() {
        return this.mnc;
    }

    public void setMnc(String mnc) {
        this.mnc = mnc;
    }

    public String getMmsProxy() {
        return this.mmsProxy;
    }

    public void setMmsProxy(String mmsProxy) {
        this.mmsProxy = mmsProxy;
    }

    public String getMmsPort() {
        return this.mmsPort;
    }

    public void setMmsPort(String mmsPort) {
        this.mmsPort = mmsPort;
    }
}
