package com.du.management.newBean;

import java.io.Serializable;

public class jczcJianchajieguo implements Serializable {
    public long jcjgId;
    public long jcjgJcxmid;
    public long userId;
    public long jcjgJczbid;
    public String createdTime;
    public String updatetime;
    public String jianchaqingkuang;
    public String zhenggaijianyi;
    public String options;
    public int isHege;

    public void setIsHege(int isHege) {
        this.isHege = isHege;
    }

    public int getIsHege() {
        return isHege;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public String getOptions() {
        return options;
    }

    public void setZhenggaijianyi(String zhenggaijianyi) {
        this.zhenggaijianyi = zhenggaijianyi;
    }

    public String getZhenggaijianyi() {
        return zhenggaijianyi;
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

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getJcjgJczbid() {
        return jcjgJczbid;
    }

    public void setJcjgJczbid(long jcjgJczbid) {
        this.jcjgJczbid = jcjgJczbid;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public String getJianchaqingkuang() {
        return jianchaqingkuang;
    }

    public void setJianchaqingkuang(String jianchaqingkuang) {
        this.jianchaqingkuang = jianchaqingkuang;
    }
}
