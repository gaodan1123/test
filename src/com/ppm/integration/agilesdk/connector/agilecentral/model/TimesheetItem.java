package com.ppm.integration.agilesdk.connector.agilecentral.model;

public class TimesheetItem {

    private String date;
    private String taskExpected;
    private String taskDone;

    public String getTaskExpected() {
		return taskExpected;
	}

	public void setTaskExpected(String taskExpected) {
		this.taskExpected = taskExpected;
	}

	public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    
    public String getTaskDone() {
		return taskDone;
	}

	public void setTaskDone(String taskDone) {
		this.taskDone = taskDone;
	}

	public String toString() {
        StringBuffer sb = new StringBuffer();

        sb.append(" date:" + date);
        sb.append(" ,taskExpected:" + taskExpected);
        sb.append(" ,taskDone:" + taskDone);

        return sb.toString();
    }
}
