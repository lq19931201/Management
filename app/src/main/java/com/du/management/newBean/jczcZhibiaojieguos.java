package com.du.management.newBean;

import java.io.Serializable;

public class jczcZhibiaojieguos implements Serializable {
    private long zbjgId;
    private String jianchaqingkuang;

    public void setJianchaqingkuang(String jianchaqingkuang) {
        this.jianchaqingkuang = jianchaqingkuang;
    }

    public void setZbjgId(long zbjgId) {
        this.zbjgId = zbjgId;
    }

    public long getZbjgId() {
        return zbjgId;
    }

    public String getJianchaqingkuang() {
        return jianchaqingkuang;
    }
}

