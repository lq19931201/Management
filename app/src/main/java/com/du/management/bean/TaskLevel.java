package com.du.management.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class TaskLevel implements Serializable{
    /**
     * 任务等级
     */
    private Long taskLevelId;

    private Long taskId;

    private Long parentId;

    private String label;

    private Long contentId;

    private Byte deleteFlag;

    private long createTime;

    private long updateTime;

    private List<TaskBody> taskBodyList;

    public Long getTaskLevelId() {
        return taskLevelId;
    }

    public void setTaskLevelId(Long taskLevelId) {
        this.taskLevelId = taskLevelId;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Long getContentId() {
        return contentId;
    }

    public void setContentId(Long contentId) {
        this.contentId = contentId;
    }

    public Byte getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Byte deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public List<TaskBody> getTaskBodyList() {
        return taskBodyList;
    }

    public void setTaskBodyList(List<TaskBody> taskBodyList) {
        this.taskBodyList = taskBodyList;
    }
}