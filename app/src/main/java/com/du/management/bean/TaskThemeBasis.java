package com.du.management.bean;

import java.io.Serializable;

public class TaskThemeBasis implements Serializable{
    private Long taskBasisId;

    private Long basisId;

    private Long rulesId;

    private Long themeId;

    private Integer sort;

    private String name;

    private String ruleName;

    private String text;

    public Long getTaskBasisId() {
        return taskBasisId;
    }

    public void setTaskBasisId(Long taskBasisId) {
        this.taskBasisId = taskBasisId;
    }

    public Long getBasisId() {
        return basisId;
    }

    public void setBasisId(Long basisId) {
        this.basisId = basisId;
    }

    public Long getRulesId() {
        return rulesId;
    }

    public void setRulesId(Long rulesId) {
        this.rulesId = rulesId;
    }

    public Long getThemeId() {
        return themeId;
    }

    public void setThemeId(Long themeId) {
        this.themeId = themeId;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}