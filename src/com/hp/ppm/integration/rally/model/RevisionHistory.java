package com.hp.ppm.integration.rally.model;

import net.sf.json.JSONObject;

public class RevisionHistory extends Entity {
	
	public RevisionHistory(JSONObject jsonObject) {
		super(jsonObject);
	}
	
	public String getRevisionsRef() {
		return this.jsonObject.getJSONObject("Revisions").getString("_ref");
	}

}
