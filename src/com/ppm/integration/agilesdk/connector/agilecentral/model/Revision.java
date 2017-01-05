package com.ppm.integration.agilesdk.connector.agilecentral.model;

import java.util.Date;

import com.ppm.integration.agilesdk.connector.agilecentral.model.Entity;

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
