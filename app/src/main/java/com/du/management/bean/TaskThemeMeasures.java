package com.du.management.bean;

import java.io.Serializable;

public class TaskThemeMeasures implements Serializable{
    private Long taskMeasuresId;

    private Long measuresId;

    private String name;

    private Long themeId;

    private Integer sort;

    /**
     * @return task_measures_id
     */
    public Long getTaskMeasuresId() {
        return taskMeasuresId;
    }

    /**
     * @param taskMeasuresId
     */
    public void setTaskMeasuresId(Long taskMeasuresId) {
        this.taskMeasuresId = taskMeasuresId;
    }

    /**
     * @return measures_id
     */
    public Long getMeasuresId() {
        return measuresId;
    }

    /**
     * @param measuresId
     */
    public void setMeasuresId(Long measuresId) {
        this.measuresId = measuresId;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return theme_id
     */
    public Long getThemeId() {
        return themeId;
    }

    /**
     * @param themeId
     */
    public void setThemeId(Long themeId) {
        this.themeId = themeId;
    }

    /**
     * @return sort
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * @param sort
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }
}