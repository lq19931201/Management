package com.du.management.newBean;

import java.util.List;

public class Jczb {
    public int jcqkPosition;
    public boolean isAdd;
    public String nextXulieId;
    public long jczbId;
    public String jczbName;
    public String jczbdeleted;
    public String jczbparentId;
    public String jczbxulieId;
    public String jczbcreatedTime;
    public String jczbupdatedTime;
    public String jczbcreatedBy;
    public String jczbupdatedBy;
    public String zhenggaicuoshi;
    public String shishifenleifirst;
    public String shishifenleisecond;
    public List<Jianchayiju> jianchayiju;
    public jczcJianchajieguo jczcJianchajieguo = new jczcJianchajieguo();
    public JczcZhibiaoMoban jczcZhibiaoMobanBean;
    public List<jczcZhibiaojieguos> jczcZhibiaojieguos;

    public void setJczcZhibiaojieguos(List<com.du.management.newBean.jczcZhibiaojieguos> jczcZhibiaojieguos) {
        this.jczcZhibiaojieguos = jczcZhibiaojieguos;
    }

    public List<com.du.management.newBean.jczcZhibiaojieguos> getJczcZhibiaojieguos() {
        return jczcZhibiaojieguos;
    }

    public String getNextXulieId() {
        return nextXulieId;
    }

    public void setNextXulieId(String nextXulieId) {
        this.nextXulieId = nextXulieId;
    }

    public long getJczbId() {
        return jczbId;
    }

    public void setJczbId(long jczbId) {
        this.jczbId = jczbId;
    }

    public String getJczbName() {
        return jczbName;
    }

    public void setJczbName(String jczbName) {
        this.jczbName = jczbName;
    }

    public String getJczbdeleted() {
        return jczbdeleted;
    }

    public void setJczbdeleted(String jczbdeleted) {
        this.jczbdeleted = jczbdeleted;
    }

    public String getJczbparentId() {
        return jczbparentId;
    }

    public void setJczbparentId(String jczbparentId) {
        this.jczbparentId = jczbparentId;
    }

    public String getJczbxulieId() {
        return jczbxulieId;
    }

    public void setJczbxulieId(String jczbxulieId) {
        this.jczbxulieId = jczbxulieId;
    }

    public String getJczbcreatedTime() {
        return jczbcreatedTime;
    }

    public void setJczbcreatedTime(String jczbcreatedTime) {
        this.jczbcreatedTime = jczbcreatedTime;
    }

    public String getJczbupdatedTime() {
        return jczbupdatedTime;
    }

    public void setJczbupdatedTime(String jczbupdatedTime) {
        this.jczbupdatedTime = jczbupdatedTime;
    }

    public String getJczbcreatedBy() {
        return jczbcreatedBy;
    }

    public void setJczbcreatedBy(String jczbcreatedBy) {
        this.jczbcreatedBy = jczbcreatedBy;
    }

    public String getJczbupdatedBy() {
        return jczbupdatedBy;
    }

    public void setJczbupdatedBy(String jczbupdatedBy) {
        this.jczbupdatedBy = jczbupdatedBy;
    }

    public String getZhenggaicuoshi() {
        return zhenggaicuoshi;
    }

    public void setZhenggaicuoshi(String zhenggaicuoshi) {
        this.zhenggaicuoshi = zhenggaicuoshi;
    }

    public String getShishifenleifirst() {
        return shishifenleifirst;
    }

    public void setShishifenleifirst(String shishifenleifirst) {
        this.shishifenleifirst = shishifenleifirst;
    }

    public String getShishifenleisecond() {
        return shishifenleisecond;
    }

    public void setShishifenleisecond(String shishifenleisecond) {
        this.shishifenleisecond = shishifenleisecond;
    }

    public JczcZhibiaoMoban getJczcZhibiaoMobanBean() {
        return jczcZhibiaoMobanBean;
    }

    public void setJczcZhibiaoMobanBean(JczcZhibiaoMoban jczcZhibiaoMobanBean) {
        this.jczcZhibiaoMobanBean = jczcZhibiaoMobanBean;
    }

    public boolean isAdd() {
        return isAdd;
    }

    public void setAdd(boolean add) {
        isAdd = add;
    }

    public void setJczcJianchajieguo(com.du.management.newBean.jczcJianchajieguo jczcJianchajieguo) {
        this.jczcJianchajieguo = jczcJianchajieguo;
    }

    public com.du.management.newBean.jczcJianchajieguo getJczcJianchajieguo() {
        return jczcJianchajieguo;
    }

    public void setJcqkPosition(int jcqkPosition) {
        this.jcqkPosition = jcqkPosition;
    }

    public int getJcqkPosition() {
        return jcqkPosition;
    }

    public void setJianchayiju(List<Jianchayiju> jianchayiju) {
        this.jianchayiju = jianchayiju;
    }

    public List<Jianchayiju> getJianchayiju() {
        return jianchayiju;
    }
}
