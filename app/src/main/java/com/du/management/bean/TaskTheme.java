package com.du.management.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TaskTheme implements Serializable {

    public boolean isAdd;

    /**
     * 4级
     */
    private Long taskThemeId;

    private Long taskId;
    /**
     * 检查指标
     */
    private String name;

    /**
     * 检查方法
     */
    private String method;

    private Long themeId;

    private Long createTime;

    private Long updateTime;

    private Long imageId;

    private Long fileId;

    private String situation;

    private boolean accord;

    private List<String> methodList = new ArrayList<>();

    private List<TaskTheme> list;

    public List<String> getMethodList() {
        return methodList;
    }

    public void setMethodList(List<String> methodList) {
        this.methodList = methodList;
    }

    public Long getTaskThemeId() {
        return taskThemeId;
    }

    public void setTaskThemeId(Long taskThemeId) {
        this.taskThemeId = taskThemeId;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Long getThemeId() {
        return themeId;
    }

    public void setThemeId(Long themeId) {
        this.themeId = themeId;
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

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public String getSituation() {
        return situation;
    }

    public void setSituation(String situation) {
        this.situation = situation;
    }

    public boolean isAccord() {
        return accord;
    }

    public void setAccord(boolean accord) {
        this.accord = accord;
    }

    public List<TaskTheme> getList() {
        return list;
    }

    public void setList(List<TaskTheme> list) {
        this.list = list;
    }

    public boolean isAdd() {
        return isAdd;
    }

    public void setAdd(boolean add) {
        isAdd = add;
    }
}