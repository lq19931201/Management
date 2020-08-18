package com.du.management.bean;

import java.io.Serializable;

public class PushBean implements Serializable {
    public String createdTime;

    public int isHege;

    public long jcjgId;

    public long jcjgJcxmid;

    public long jcjgJczbid;

    public long zbjgId;

    public String jianchaqingkuang;

    public String updatetime;

    public long userId;

    public String zhenggaiyijian;

    public String option;

    public void setOption(String option) {
        this.option = option;
    }

    public String getOption() {
        return option;
    }

    public void setZhenggaiyijian(String zhenggaiyijian) {
        this.zhenggaiyijian = zhenggaiyijian;
    }

    public String getZhenggaiyijian() {
        return zhenggaiyijian;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public int getIsHege() {
        return isHege;
    }

    public void setIsHege(int isHege) {
        this.isHege = isHege;
    }

    public long getJcjgId() {
        return jcjgId;
    }

    public void setJcjgId(long jcjgId) {
        this.jcjgId = jcjgId;
    }

    public long getJcjgJcxmid() {
        return jcjgJcxmid;
    }

    public void setJcjgJcxmid(long jcjgJcxmid) {
        this.jcjgJcxmid = jcjgJcxmid;
    }

    public long getJcjgJczbid() {
        return jcjgJczbid;
    }

    public void setJcjgJczbid(long jcjgJczbid) {
        this.jcjgJczbid = jcjgJczbid;
    }

    public String getJianchaqingkuang() {
        return jianchaqingkuang;
    }

    public void setJianchaqingkuang(String jianchaqingkuang) {
        this.jianchaqingkuang = jianchaqingkuang;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setZbjgId(long zbjgId) {
        this.zbjgId = zbjgId;
    }

    public long getZbjgId() {
        return zbjgId;
    }
}
