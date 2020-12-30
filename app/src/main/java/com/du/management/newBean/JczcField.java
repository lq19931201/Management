package com.du.management.newBean;

import java.util.List;

public class JczcField {
    public long fieldId;
    public String fieldName;
    public long fieldType;
    public String value;
    public List<JczcFieldValue> jczcFieldValue;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public long getFieldId() {
        return fieldId;
    }

    public void setFieldId(long fieldId) {
        this.fieldId = fieldId;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public long getFieldType() {
        return fieldType;
    }

    public void setFieldType(long fieldType) {
        this.fieldType = fieldType;
    }

    public List<JczcFieldValue> getJczcFieldValue() {
        return jczcFieldValue;
    }

    public void setJczcFieldValue(List<JczcFieldValue> jczcFieldValue) {
        this.jczcFieldValue = jczcFieldValue;
    }
}
