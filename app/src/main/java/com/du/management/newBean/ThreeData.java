package com.du.management.newBean;

import java.util.List;

public class ThreeData {
    public String sslyId;
    public String sslyName;
    public String deleted;
    public String createdTime;
    public String updatedTime;
    public String createdBy;
    public String updatedBy;
    public List<Jcxm> jcxmlist;

    public String getSslyId() {
        return sslyId;
    }

    public void setSslyId(String sslyId) {
        this.sslyId = sslyId;
    }

    public String getSslyName() {
        return sslyName;
    }

    public void setSslyName(String sslyName) {
        this.sslyName = sslyName;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
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

    public List<Jcxm> getJcxmlist() {
        return jcxmlist;
    }

    public void setJcxmlist(List<Jcxm> jcxmlist) {
        this.jcxmlist = jcxmlist;
    }
}
