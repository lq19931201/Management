package com.du.management.newBean;

import java.util.List;

public class Jcxm {
    public long jcxmId;
    public String jcxmName;
    public String deleted;
    public String parentId;
    public String xulieId;
    public String createdTime;
    public String updatedTime;
    public String createdBy;
    public String updatedBy;
    public List<Jcnr> jcnrlist;

    public long getJcxmId() {
        return jcxmId;
    }

    public void setJcxmId(long jcxmId) {
        this.jcxmId = jcxmId;
    }

    public String getJcxmName() {
        return jcxmName;
    }

    public void setJcxmName(String jcxmName) {
        this.jcxmName = jcxmName;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getXulieId() {
        return xulieId;
    }

    public void setXulieId(String xulieId) {
        this.xulieId = xulieId;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(String updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public List<Jcnr> getJcnrlist() {
        return jcnrlist;
    }

    public void setJcnrlist(List<Jcnr> jcnrlist) {
        this.jcnrlist = jcnrlist;
    }
}
