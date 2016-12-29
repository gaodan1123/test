package com.hp.ppm.integration.rally;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.wink.client.ClientConfig;
import org.apache.wink.client.ClientResponse;
import org.apache.wink.client.Resource;
import org.apache.wink.client.RestClient;
import org.apache.wink.client.handlers.BasicAuthSecurityHandler;

import com.hp.ppm.integration.rally.model.HierarchicalRequirement;
import com.hp.ppm.integration.rally.model.Iteration;
import com.hp.ppm.integration.rally.model.Revision;
import com.hp.ppm.integration.rally.model.RevisionHistory;

import edu.emory.mathcs.backport.java.util.Collections;

public class getIterationByRelease {

	private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	//thread safe
	protected synchronized static String convertDate(Date date){
		return dateFormat.format(date);
	}
	public static RestHelper helper;
	public static void main(String[] args) throws ParseException{
//		Config config = new Config();
//		config.setProxy("", "");
//		config.setBasicAuthorization("dan.gao3@hpe.com", "Hanyan@223");
//		final RallyClient rallyClient = new RallyClient("https://rally1.rallydev.com",config);
//		
		Date date = new Date("Fri Dec 26 11:11:53 CST 2016");

		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);

		System.out.println(convertDate(calendar.getTime()));
//
//		List<Iteration> iterations = rallyClient.getIterations("78022378668");
//		for(Iteration iteration : iterations){
//			List<HierarchicalRequirement> hierarchicalRequirements = iteration.getHierarchicalRequirement();
//			for(HierarchicalRequirement hierarchicalRequirement : hierarchicalRequirements){
//				TimesheetItem item = new TimesheetItem();
//				//date
//				String currentDate = date.toString();		
//				item.setDate(currentDate);
//				
//				//expected
//				double expectedhours = hierarchicalRequirement.getTaskEstimateTotal();
//				item.setTaskExpected(expectedhours + "");
//				
//				//done
//				RevisionHistory revisionHistory = rallyClient.getRevisionHistory(hierarchicalRequirement.getRevisionHistoryRef());
//				List<Revision> revisions = rallyClient.getRevisions(revisionHistory.getId());
//				
//				Pattern pattern = Pattern.compile("\\d+(\\.\\d+)?");
//				
//				Calendar calendar = new GregorianCalendar();
//				calendar.setTime(date);
//				//move to next day
//				calendar.add(Calendar.DAY_OF_MONTH, 1);
//
//				//remaining hours
//				double remainingHoursSum = 0;
//				
//				for(Revision revision : revisions){
//					
//					Date revisionDate = revision.getCreationDate();
//					if(revisionDate.getTime() <= date.getTime() || revisionDate.getTime() >= calendar.getTime().getTime()){	
//						continue;
//					}
//					
//					//remaining hours
//					double remainingHours = 0;
//					if(revision.getDescription().contains("TASK REMAINING TOTAL")){
//						List<String> remainingHoursBuffer = new ArrayList<String>(2);
//						int len = revision.getDescription().split(",").length;
//						for(int i = 0; i < len; i++){
//							String description = revision.getDescription().split(",")[i];
//							if(description.contains("TASK REMAINING TOTAL")){
//								
//								System.out.println("description--"+description);
//								Matcher matcher = pattern.matcher(description); 
//								while(matcher.find()){
//									remainingHoursBuffer.add(matcher.group());
//								}
//								if(remainingHoursBuffer.size() == 2){
//									remainingHours = Float.parseFloat(remainingHoursBuffer.get(1)) - Float.parseFloat(remainingHoursBuffer.get(0));
//								}
//								continue;
//							}							
//																			
//						}
//					}
//					//sum
//					if(remainingHours < 0){						
//						remainingHoursSum = remainingHoursSum - remainingHours;
//					}
//				}
//				item.setTaskDone(remainingHoursSum + "");
//				System.out.println(item.toString());
				
//				System.out.println(hierarchicalRequirement.getId() + "--" + hierarchicalRequirement.getRevisionHistoryRef());
//				
//				RevisionHistory revisionHistory = rallyClient.getRevisionHistory(hierarchicalRequirement.getRevisionHistoryRef());
//
//				List<Revision> revisions = rallyClient.getRevisions(revisionHistory.getId());				
//				System.out.println("revisions.size--"+revisions.size());
////				HashMap<Date, HashMap<int, String>> estimateHm = new HashMap<Date, HashMap<int, String>>();
//				HashMap<Date, String> remainingHm = new HashMap<Date, String>();
//				HashMap<Date, String> estimateHm = new HashMap<Date, String>();
//				for(Revision revision : revisions){
////					if(revision.getCreationDate().getTime() >= date.getTime()){
////						continue;
////					}
//					if(revision.getDescription().contains("TASK REMAINING TOTAL") || revision.getDescription().contains("TASK ESTIMATE TOTAL")){					
//						
//						int len = revision.getDescription().split(",").length;
//						for(int i = 0; i < len; i++){
//							String description = revision.getDescription().split(",")[i];
//							if(description.contains("TASK REMAINING TOTAL")){
//								System.out.println(revision.getRevisionNumber() + "" +revision.getCreationDate() +"--" + description);
////								List<float> list = new ArrayList<double>();
//								//match "float"
//								Pattern pattern = Pattern.compile("\\d+(\\.\\d+)?");
//
//								Matcher matcher = pattern.matcher(description); 
//								while(matcher.find()){
////									System.out.println("**" + Float.parseFloat(matcher.group()));
//									remainingHm.put(revision.getCreationDate(), matcher.group());
//								}
//							}
//							if(description.contains("TASK ESTIMATE TOTAL")){
//								System.out.println(revision.getRevisionNumber() + "" +revision.getCreationDate() +"--" + description);
//								//match "float"
//								Pattern pattern = Pattern.compile("\\d+(\\.\\d+)?");
//
//								Matcher matcher = pattern.matcher(description); 
//								while(matcher.find()){
////									System.out.println("**" + Float.parseFloat(matcher.group()));
//									estimateHm.put(revision.getCreationDate(), matcher.group());
//								}
//							}
//						}
//						
//					}
//				}
//				for(Date key : estimateHm.keySet()){  
//		            System.out.println("estimate"  + key+"="+estimateHm.get(key));  
//		        }
//				for(Date key : remainingHm.keySet()){  
//		            System.out.println("remaining" + key+"="+remainingHm.get(key));  
//		        }
//				
//			}
//		}
		
	}
	public static int getDaysDiffNumber(Date startDate, Date endDate) {
		Calendar start = new GregorianCalendar();
		start.setTime(startDate);

		Calendar end = new GregorianCalendar();
		end.setTime(endDate);
		//move to last millsecond
		end.set(Calendar.HOUR_OF_DAY,23);
		end.set(Calendar.MINUTE,59);
		end.set(Calendar.SECOND,59);
		end.set(Calendar.MILLISECOND,999);

		Calendar dayDiff =  Calendar.getInstance();
		dayDiff.setTime(startDate);
		int diffNumber  = 0;
		while (dayDiff.before(end)) {
			diffNumber ++;
			dayDiff.add(Calendar.DAY_OF_MONTH, 1);
		}
		return diffNumber;
	}
}
