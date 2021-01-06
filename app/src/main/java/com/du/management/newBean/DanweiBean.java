package com.du.management.newBean;

import java.util.List;

public class DanweiBean {
    public long xiangmuId;
    public String lianxiren ;
    public String lianxifangshi ;
    public String danweiName ;
    public String shishiCity ;
    public String shishiAddress ;
    public String checkedType ;
    public String creditCode;
    public double locationX ;
    public double locationY ;
    public long unitTypeId;
    public List<UnitInformations> unitInformations;

    public List<UnitInformations> getUnitInformations() {
        return unitInformations;
    }

    public void setUnitInformations(List<UnitInformations> unitInformations) {
        this.unitInformations = unitInformations;
    }

    public void setCreditCode(String creditCode) {
        this.creditCode = creditCode;
    }

    public String getCreditCode() {
        return creditCode;
    }

    public long getXiangmuId() {
        return xiangmuId;
    }

    public void setXiangmuId(long xiangmuId) {
        this.xiangmuId = xiangmuId;
    }

    public String getLianxiren() {
        return lianxiren;
    }

    public void setLianxiren(String lianxiren) {
        this.lianxiren = lianxiren;
    }

    public String getLianxifangshi() {
        return lianxifangshi;
    }

    public void setLianxifangshi(String lianxifangshi) {
        this.lianxifangshi = lianxifangshi;
    }

    public String getDanweiName() {
        return danweiName;
    }

    public void setDanweiName(String danweiName) {
        this.danweiName = danweiName;
    }

    public String getShishiCity() {
        return shishiCity;
    }

    public void setShishiCity(String shishiCity) {
        this.shishiCity = shishiCity;
    }

    public String getShishiAddress() {
        return shishiAddress;
    }

    public void setShishiAddress(String shishiAddress) {
        this.shishiAddress = shishiAddress;
    }

    public String getCheckedType() {
        return checkedType;
    }

    public void setCheckedType(String checkedType) {
        this.checkedType = checkedType;
    }

    public double getLocationX() {
        return locationX;
    }

    public void setLocationX(double locationX) {
        this.locationX = locationX;
    }

    public double getLocationY() {
        return locationY;
    }

    public void setLocationY(double locationY) {
        this.locationY = locationY;
    }

    public long getUnitTypeId() {
        return unitTypeId;
    }

    public void setUnitTypeId(long unitTypeId) {
        this.unitTypeId = unitTypeId;
    }
}
