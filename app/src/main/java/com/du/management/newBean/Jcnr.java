package com.du.management.newBean;

import java.io.Serializable;
import java.util.List;

public class Jcnr implements Serializable {
    public long xiangmuId;
    public String jcxmName;
    public String id;
    public String createdBy;
    public String createdTime;
    public String updatedBy;
    public String updatedTime;
    public String jcnrId;
    public String jcnrName;
    public String deleted;
    public String parentId;
    public String xulieId;
    public List<Jcnrfj> jcnrfjlist;

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

    public String getJcnrId() {
        return jcnrId;
    }

    public void setJcnrId(String jcnrId) {
        this.jcnrId = jcnrId;
    }

    public String getJcnrName() {
        return jcnrName;
    }

    public void setJcnrName(String jcnrName) {
        this.jcnrName = jcnrName;
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

    public List<Jcnrfj> getJcnrfjlist() {
        return jcnrfjlist;
    }

    public void setJcnrfjlist(List<Jcnrfj> jcnrfjlist) {
        this.jcnrfjlist = jcnrfjlist;
    }

    public long getXiangmuId() {
        return xiangmuId;
    }

    public void setXiangmuId(long xiangmuId) {
        this.xiangmuId = xiangmuId;
    }

    public String getJcxmName() {
        return jcxmName;
    }

    public void setJcxmName(String jcxmName) {
        this.jcxmName = jcxmName;
    }
}
