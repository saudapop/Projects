package com.techelevator.campground.model.jdbc;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.campground.model.Park;
import com.techelevator.campground.model.ParkDAO;

public class JDBCParkDAO implements ParkDAO {
	
	private JdbcTemplate jdbcTemplate;
	
	public JDBCParkDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Park> getAllParks() {
		List<Park> parks = new ArrayList<Park>();
		String sql = "SELECT * FROM park ORDER BY name ASC";
		
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
		
		while (result.next()) {
			Park park = mapRowToPark(result);
			parks.add(park);
		}
		
		
		return parks;
	}

	private Park mapRowToPark(SqlRowSet result) {
		Park park = new Park();
		park.setParkId(result.getInt("park_id"));
		park.setName(result.getString("name"));
		park.setLocation(result.getString("location"));
		park.setEstablishDate(result.getDate("establish_date"));
		park.setArea(result.getInt("area"));
		park.setVisitors(result.getInt("visitors"));
		park.setDescription(result.getString("description"));
		return park;
	}

}
