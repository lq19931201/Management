package com.du.management.newBean;

import java.io.Serializable;
import java.util.List;

public class Jcnrfj implements Serializable {
    public String id;
    public String createdBy;
    public String createdTime;
    public String updatedBy;
    public String updatedTime;
    public String nextXulieId;
    public String jcnrfjId;
    public String jcnrfjName;
    public String deleted;
    public String parentId;
    public String xulieId;
    public List<Jczb> jczblist;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(String updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getNextXulieId() {
        return nextXulieId;
    }

    public void setNextXulieId(String nextXulieId) {
        this.nextXulieId = nextXulieId;
    }

    public String getJcnrfjId() {
        return jcnrfjId;
    }

    public void setJcnrfjId(String jcnrfjId) {
        this.jcnrfjId = jcnrfjId;
    }

    public String getJcnrfjName() {
        return jcnrfjName;
    }

    public void setJcnrfjName(String jcnrfjName) {
        this.jcnrfjName = jcnrfjName;
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

    public List<Jczb> getJczblist() {
        return jczblist;
    }

    public void setJczblist(List<Jczb> jczblist) {
        this.jczblist = jczblist;
    }
}
