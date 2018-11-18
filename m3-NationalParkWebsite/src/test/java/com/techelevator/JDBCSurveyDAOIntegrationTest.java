package com.techelevator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.techelevator.npgeek.jdbc.JDBCSurveyDAO;
import com.techelevator.npgeek.model.Survey;

public class JDBCSurveyDAOIntegrationTest extends DAOIntegrationTest {
 
	
private JDBCSurveyDAO surveyDao;
	
	private JdbcTemplate jdbcTemplate;
	
	@Before
	public void setup() {
		surveyDao = new JDBCSurveyDAO(getDataSource());
		jdbcTemplate = new JdbcTemplate(getDataSource());
		 
	}
	
	@Test
	public void save_survey_test() {
		
		Map<String, Integer> surveysTest;
		
		int sizeBefore = surveyDao.getAllSurveys().size();
		surveyDao.saveSurvey("ABC", "AB@BC.DE", "OH", "bad");
		
		surveysTest = surveyDao.getAllSurveys();
		
		Assert.assertEquals(sizeBefore + 1 ,surveysTest.size());
		
	}
	
	@Test
	public void get_all_surveys_test() {
		
		surveyDao.saveSurvey("ABC", "AB@BC.DE", "OH", "bad");
		
		int surveysTestSize = surveyDao.getAllSurveys().size();
		
		Assert.assertTrue(surveysTestSize > 0);
	}
	
}
