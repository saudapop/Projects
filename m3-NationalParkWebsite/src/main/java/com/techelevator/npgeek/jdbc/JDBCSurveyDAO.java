package com.techelevator.npgeek.jdbc;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.techelevator.npgeek.model.SurveyDAO;

@Component
public class JDBCSurveyDAO implements SurveyDAO{
	
	private JdbcTemplate jdbcTemplate;

	
	@Autowired
	public JDBCSurveyDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override

	public Map<String, Integer> getAllSurveys() {
	
		String sql = "SELECT parkcode, Count(parkcode) FROM survey_result GROUP BY parkcode ORDER BY count DESC, parkcode";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
		Map<String,Integer> favParks  = mapRowToMap(result);
		
		return favParks;
	}

	@Override
	public void saveSurvey(String parkcode, String emailaddress, String state, String activitylevel) {
		
		String sql = "INSERT INTO survey_result VALUES ( default , ?, ?, ?, ? )";	
		jdbcTemplate.update(sql, parkcode, emailaddress, state, activitylevel);
		
	}
	
	private Map<String ,Integer> mapRowToMap(SqlRowSet result) {
		
		Map<String,Integer> favParks = new LinkedHashMap<>();
		while (result.next()) {
		favParks.put(result.getString("parkcode"), result.getInt("count"));
		}
		
		return favParks;
	}
 
}
