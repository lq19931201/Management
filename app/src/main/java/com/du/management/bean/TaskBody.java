package com.du.management.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class TaskBody implements Serializable{
    /**
     * 任务主体 3级
     */
    private Long taskBodyId;

    private Long contentId;

    private String name;

    private Integer sort;

    /**
     * 操作人
     */
    private Long userId;

    private Long createTime;

    private Long updateTime;

    private Long templateId;

    private List<TaskTheme> taskThemeList;

    public Long getTaskBodyId() {
        return taskBodyId;
    }

    public void setTaskBodyId(Long taskBodyId) {
        this.taskBodyId = taskBodyId;
    }

    public Long getContentId() {
        return contentId;
    }

    public void setContentId(Long contentId) {
        this.contentId = contentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public List<TaskTheme> getTaskThemeList() {
        return taskThemeList;
    }

    public void setTaskThemeList(List<TaskTheme> taskThemeList) {
        this.taskThemeList = taskThemeList;
    }
}