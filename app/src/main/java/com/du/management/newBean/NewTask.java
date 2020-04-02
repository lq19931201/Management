package com.du.management.newBean;

import java.util.List;

public class NewTask {

    public long renwuId;

    public String renwuname;

    public String renwuneirong;

    public String renwuleixing;

    public String createdtime;

    public long finished;

    public List<NewContent> jczcJianchashishis;

    public void setJczcJianchashishis(List<NewContent> jczcJianchashishis) {
        this.jczcJianchashishis = jczcJianchashishis;
    }

    public List<NewContent> getJczcJianchashishis() {
        return jczcJianchashishis;
    }

    public long getRenwuId() {
        return renwuId;
    }

    public void setRenwuId(long renwuId) {
        this.renwuId = renwuId;
    }

    public String getRenwuname() {
        return renwuname;
    }

    public void setRenwuname(String renwuname) {
        this.renwuname = renwuname;
    }

    public String getRenwuneirong() {
        return renwuneirong;
    }

    public void setRenwuneirong(String renwuneirong) {
        this.renwuneirong = renwuneirong;
    }

    public String getRenwuleixing() {
        return renwuleixing;
    }

    public void setRenwuleixing(String renwuleixing) {
        this.renwuleixing = renwuleixing;
    }

    public String getCreatedtime() {
        return createdtime;
    }

    public void setCreatedtime(String createdtime) {
        this.createdtime = createdtime;
    }

    public long getFinished() {
        return finished;
    }

    public void setFinished(long finished) {
        this.finished = finished;
    }
}

