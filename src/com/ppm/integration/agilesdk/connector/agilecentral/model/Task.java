package com.ppm.integration.agilesdk.connector.agilecentral.model;

import net.sf.json.JSONObject;

public class Task extends Entity {

    public Task(JSONObject jsonObject) {
        super(jsonObject);
    }

    public int getTimespentTotal() {
        return check("TimeSpent") ? jsonObject.getInt("TimeSpent") : null;
    }

}
