package com.du.management.bean;

import java.io.Serializable;

public class TaskThemeElements implements Serializable{
    private Long taskElementsId;

    private Long elementsId;

    private String name;

    private Long themeId;

    private Integer sort;

    /**
     * @return task_elements_id
     */
    public Long getTaskElementsId() {
        return taskElementsId;
    }

    /**
     * @param taskElementsId
     */
    public void setTaskElementsId(Long taskElementsId) {
        this.taskElementsId = taskElementsId;
    }

    /**
     * @return elements_id
     */
    public Long getElementsId() {
        return elementsId;
    }

    /**
     * @param elementsId
     */
    public void setElementsId(Long elementsId) {
        this.elementsId = elementsId;
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