package com.ppm.integration.agilesdk.connector.agilecentral.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import com.hp.ppm.integration.pm.IExternalTask;
import com.ppm.integration.agilesdk.connector.agilecentral.model.Entity;
import com.ppm.integration.agilesdk.connector.agilecentral.model.HierarchicalRequirement;

public class Iteration extends Entity {

	private final List<IExternalTask> hierarchicalRequirements = new ArrayList<IExternalTask>();
	private final List<HierarchicalRequirement> hierarchicalRequirement = new ArrayList<HierarchicalRequirement>();
	
	public Iteration(JSONObject jsonObject) {
		super(jsonObject);
	}

	public void addHierarchicalRequirement(HierarchicalRequirement hierarchicalRequirement) {	
		if (hierarchicalRequirement.getIterationUUID() != null && this.getUUID().equals(hierarchicalRequirement.getIterationUUID())){
			hierarchicalRequirement.setIteration(this);
			this.hierarchicalRequirement.add(hierarchicalRequirement);
			this.hierarchicalRequirements.add(hierarchicalRequirement);			
		}
	}

	@Override
	public Date getScheduleStart() {
		return convertToDate(check("StartDate") ? jsonObject.getString("StartDate") : null);
	}

	@Override
	public Date getScheduleFinish() {
		return convertToDate(check("EndDate") ? jsonObject.getString("EndDate") : null);
	}

	@Override
	public TaskStatus getStatus() {
		String status = (check("State") ? jsonObject.getString("State") : null);
		IExternalTask.TaskStatus result = IExternalTask.TaskStatus.UNKNOWN;
		switch (status){
		case "Planning":
			result = IExternalTask.TaskStatus.IN_PLANNING;
			break;
		case "Committed":
			result = IExternalTask.TaskStatus.READY;
			break;
		case "Accepted":
			result = IExternalTask.TaskStatus.COMPLETED;
			break;
		}
		return result;
	}

	@Override
	public List<IExternalTask> getChildren() {
		return hierarchicalRequirements;		
	}

	public String getTaskRemainingTotal(){
		return check("TaskRemainingTotal") ? jsonObject.getString("TaskRemainingTotal") : null;
	}

	public String getTaskEstimateTotal() {
		return check("TaskEstimateTotal") ? jsonObject.getString("TaskEstimateTotal") : null;
	}
	
	public String getTaskActualTotal(){
		return check("TaskActualTotal") ? jsonObject.getString("TaskActualTotal") : null;
	}
	
	public List<HierarchicalRequirement> getHierarchicalRequirement(){
		return hierarchicalRequirement;
	}

}