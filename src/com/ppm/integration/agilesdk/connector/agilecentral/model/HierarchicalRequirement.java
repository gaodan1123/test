package com.ppm.integration.agilesdk.connector.agilecentral.model;

import net.sf.json.JSONObject;

import com.ppm.integration.agilesdk.provider.UserProvider;

public class HierarchicalRequirement extends Backlog {

    public HierarchicalRequirement(JSONObject jsonObject, UserProvider userProvider) {
        super(jsonObject, userProvider);
    }
	
}
