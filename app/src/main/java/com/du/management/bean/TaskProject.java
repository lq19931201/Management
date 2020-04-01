package com.du.management.bean;

import java.io.Serializable;
import java.util.Date;

public class TaskProject implements Serializable{
    private Long taskProjectId;

    /**
     * 项目名称
     */
    private String name;

    private String sort;

    private Boolean deleteFlag;

    private Date createTime;

    /**
     * @return task_project_id
     */
    public Long getTaskProjectId() {
        return taskProjectId;
    }

    /**
     * @param taskProjectId
     */
    public void setTaskProjectId(Long taskProjectId) {
        this.taskProjectId = taskProjectId;
    }

    /**
     * 获取项目名称
     *
     * @return name - 项目名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置项目名称
     *
     * @param name 项目名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return sort
     */
    public String getSort() {
        return sort;
    }

    /**
     * @param sort
     */
    public void setSort(String sort) {
        this.sort = sort;
    }

    /**
     * @return delete_flag
     */
    public Boolean getDeleteFlag() {
        return deleteFlag;
    }

    /**
     * @param deleteFlag
     */
    public void setDeleteFlag(Boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}