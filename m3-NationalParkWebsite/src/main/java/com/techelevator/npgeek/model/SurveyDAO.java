package com.techelevator.npgeek.model;

import java.util.Map;

public interface SurveyDAO {

	public Map<String,Integer> getAllSurveys();
	
	public void saveSurvey(String parkcode, String emailaddress, String state, String activitylevel);
}
