package com.techelevator.campground.model.jdbc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.campground.model.Site;
import com.techelevator.campground.model.SiteDAO;

public class JDBCSiteDAO implements SiteDAO {
	
	private JdbcTemplate jdbcTemplate;
	
	public JDBCSiteDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Site> getAvailableSites(int campgroundId, LocalDate arrivalDate, LocalDate departureDate) {
		
		List<Site> sites = new ArrayList<Site>();
		
		String sql = "SELECT distinct * FROM site\n" + 
					"JOIN campground ON site.campground_id = campground.campground_id\n" + 
					"WHERE site.campground_id = ?\n" + 
					"AND site_id NOT IN\n" + 
					"(SELECT site.site_id FROM site\n" + 
					"JOIN reservation ON reservation.site_id = site.site_id\n" + 
					"WHERE (? >= reservation.from_date AND ? <= reservation.to_date) OR (? >= reservation.from_date AND ? <= reservation.to_date)) \n" + 
					"ORDER BY daily_fee\n" + 
					"LIMIT 5";
		
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, campgroundId, arrivalDate, arrivalDate, departureDate, departureDate);
		
		while (result.next()) {
			Site site = mapRowToSite(result);
				sites.add(site);
		}
		return sites;
	}
	
	private Site mapRowToSite(SqlRowSet result) {
		
		Site site = new Site();
		
		site.setSiteId(result.getInt("site_id"));
		site.setCampgroundId(result.getInt("campground_id"));
		site.setSiteNumber(result.getInt("site_number"));
		site.setMaxOccupancy(result.getInt("max_occupancy"));
		site.setAccesible(result.getBoolean("accessible"));
		site.setMaxRvLength(result.getInt("max_rv_length"));
		site.setUtilities(result.getBoolean("utilities"));
		return site;
		
	}
	

}
