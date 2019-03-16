package com.crec.shield.entity.common.AreaToLine;

import java.util.List;

public class CommonAreaEntity {

    public String conditionId;
    public String conditionName;
    //public String tag;

    public String getConditionId() {
        return conditionId;
    }

    public void setConditionId(String conditionId) {
        this.conditionId = conditionId;
    }

    public String getConditionName() {
        return conditionName;
    }

    public void setConditionName(String conditionName) {
        this.conditionName = conditionName;
    }

    /*public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }*/

    @Override
    public String toString() {
        return "{ conditionId:" + conditionId + ", conditionName:" + conditionName + "}";
    }
}
