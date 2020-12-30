package com.du.management.newBean;

import android.support.annotation.Nullable;

import java.io.Serializable;

public class JczbNew implements Serializable {
    public long jczbId;
    public String jczbName;
    public boolean checked;

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setJczbId(long jczbId) {
        this.jczbId = jczbId;
    }

    public void setJczbName(String jczbName) {
        this.jczbName = jczbName;
    }

    public long getJczbId() {
        return jczbId;
    }

    public String getJczbName() {
        return jczbName;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return ((JczbNew) obj).getJczbId() == getJczbId();
    }
}
