package com.techelevator;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.techelevator.campground.model.Campground;
import com.techelevator.campground.model.Park;
import com.techelevator.campground.model.jdbc.JDBCCampgroundDAO;

public class JDBCCampgroundDAOIntegrationTest extends DAOIntegrationTest {

	
	JDBCCampgroundDAO campgroundDAO = new JDBCCampgroundDAO(getDataSource());
	JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());
	
	@Test
	public void test_get_all_campgrounds() {
		
		List<Campground> campgroundsBefore = new ArrayList<Campground>();
		campgroundsBefore = campgroundDAO.getAllCampgrounds(2);
		
		String sql = "INSERT INTO campground (park_id, name, open_from_mm, open_to_mm, daily_fee)"
				+ "VALUES (2, 'Test Campground', 01, 12, 50)";
		
		jdbcTemplate.update(sql);
		
		List<Campground> campgroundsAfter = new ArrayList<Campground>();
		
		campgroundsAfter = campgroundDAO.getAllCampgrounds(2);
		
		Assert.assertTrue(campgroundsBefore.size() < campgroundsAfter.size());
	}
}
