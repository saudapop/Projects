package com.techelevator;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.techelevator.campground.model.Park;
import com.techelevator.campground.model.jdbc.JDBCParkDAO;
public class JDBCParkDAOIntegrationTest extends DAOIntegrationTest {

	
	JDBCParkDAO parkDAO = new JDBCParkDAO(getDataSource());
	JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());
	
	@Test
	public void test_get_all_parks() {
		List<Park> parksBefore = new ArrayList<Park>();
		
		parksBefore = parkDAO.getAllParks();
		
		String sql = "INSERT INTO park (name, location, establish_date, area, visitors, description)"
				+ "VALUES ('Test Park', 'Ohio', '2018-01-01', 9000, 1000, 'This is a test')";
		
		jdbcTemplate.update(sql);
		
		List<Park> parksAfter = new ArrayList<Park>();
		
		parksAfter = parkDAO.getAllParks();
		
		Assert.assertTrue(parksBefore.size() < parksAfter.size());
	}
}
