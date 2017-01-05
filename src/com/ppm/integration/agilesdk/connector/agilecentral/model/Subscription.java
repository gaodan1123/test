package com.ppm.integration.agilesdk.connector.agilecentral.model;

import com.ppm.integration.agilesdk.connector.agilecentral.model.Entity;

import net.sf.json.JSONObject;

public class Subscription extends Entity {

	public Subscription(JSONObject jsonObject) {
		super(jsonObject);
	}

	public String getWorkspacesRef() {
		return this.jsonObject.getJSONObject("Workspaces").getString("_ref");
	}
}
