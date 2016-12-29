package com.hp.ppm.integration.rally.model;

import java.util.Date;

import net.sf.json.JSONObject;

public class Revision extends Entity {
	
	public Revision(JSONObject jsonObject) {
		super(jsonObject);
	}

	public String getCreatedAt() {
		return check("_CreatedAt") ? jsonObject.getString("_CreatedAt") : null;
	}

	public Date getCreationDate(){
		return convertToDate(check("CreationDate") ? jsonObject.getString("CreationDate") : null);
	}
	
	public String getDescription() {
		return check("Description") ? jsonObject.getString("Description") : null;
	}
	
	public int getRevisionNumber(){
		return check("RevisionNumber") ? jsonObject.getInt("RevisionNumber") : 0;
	}
	
	public String getRevisionHistoryUUID(){
		JSONObject iteration = this.jsonObject.getJSONObject("RevisionHistory");
		if (!iteration.isNullObject()) {
			return iteration.getString("_refObjectUUID");
		}
		return null;
	}
	
}
