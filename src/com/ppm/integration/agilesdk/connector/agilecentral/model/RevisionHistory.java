package com.ppm.integration.agilesdk.connector.agilecentral.model;

import com.ppm.integration.agilesdk.connector.agilecentral.model.Entity;

import net.sf.json.JSONObject;

public class RevisionHistory extends Entity {
	
	public RevisionHistory(JSONObject jsonObject) {
		super(jsonObject);
	}
	
	public String getRevisionsRef() {
		return this.jsonObject.getJSONObject("Revisions").getString("_ref");
	}

}
