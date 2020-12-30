package com.du.management.newBean;

public class JczcFieldValue {
    public long valueId;
    public String valueName;
    public long pid;

    public void setPid(long pid) {
        this.pid = pid;
    }

    public long getPid() {
        return pid;
    }

    public void setValueId(long valueId) {
        this.valueId = valueId;
    }

    public long getValueId() {
        return valueId;
    }

    public void setValueName(String valueName) {
        this.valueName = valueName;
    }

    public String getValueName() {
        return valueName;
    }
}
