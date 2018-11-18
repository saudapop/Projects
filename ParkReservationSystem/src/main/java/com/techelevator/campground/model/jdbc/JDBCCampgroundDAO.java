package com.techelevator.campground.model.jdbc;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.campground.model.Campground;
import com.techelevator.campground.model.CampgroundDAO;
import com.techelevator.campground.model.Park;

public class JDBCCampgroundDAO implements CampgroundDAO {
	
	
	private JdbcTemplate jdbcTemplate;
	
	public JDBCCampgroundDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Campground> getAllCampgrounds(int parkId) {
		List<Campground> campgrounds = new ArrayList<Campground>();
		
		String sql = "SELECT * FROM campground WHERE park_id = ?";
		
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql,parkId);
		
		while (result.next()) {
			Campground campground = mapRowToCampground(result);
				campgrounds.add(campground);
		}
		return campgrounds;
	}

	private Campground mapRowToCampground(SqlRowSet result) {
		Campground campground = new Campground();
		campground.setCampgroundId(result.getInt("campground_id"));
		campground.setParkId(result.getInt("park_id"));
		campground.setName(result.getString("name"));
		campground.setOpenFromMM(result.getInt("open_from_mm"));
		campground.setOpenToMM(result.getInt("open_to_mm"));
		campground.setDailyFee(result.getDouble("daily_fee"));
		return campground;
	}

}
