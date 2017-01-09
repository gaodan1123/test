package com.ppm.integration.agilesdk.connector.agilecentral.model;

import net.sf.json.JSONObject;

public class TimeEntryItem extends Entity {

    public TimeEntryItem(JSONObject jsonObject) {
        super(jsonObject);
    }

    public String getTaskUUID() {
        JSONObject iteration = this.jsonObject.getJSONObject("Task");
        if (!iteration.isNullObject()) {
            return iteration.getString("_refObjectUUID");
        }
        return null;
    }

}
