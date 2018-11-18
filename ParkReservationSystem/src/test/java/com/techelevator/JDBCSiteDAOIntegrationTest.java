package com.techelevator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.campground.model.Site;
import com.techelevator.campground.model.jdbc.JDBCReservationDAO;
import com.techelevator.campground.model.jdbc.JDBCSiteDAO;

public class JDBCSiteDAOIntegrationTest extends DAOIntegrationTest {

	JDBCSiteDAO siteDAO = new JDBCSiteDAO(getDataSource());
	JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());
	JDBCReservationDAO reservationDAO = new JDBCReservationDAO(getDataSource());
	List<Site> sitesBefore;
	List<Site> sitesAfter;
	int testCampGroundId;
	LocalDate testArrivalDate;
	LocalDate testDepartureDate;

	@Before
	public void setup() {
		sitesBefore = new ArrayList<Site>();
		sitesAfter = new ArrayList<Site>();
		testCampGroundId = 6;
		testArrivalDate = LocalDate.of(2019, 12, 20);
		testDepartureDate = LocalDate.of(2019, 12, 23);
	}

	@Test
	public void test_get_available_sites() {

		sitesBefore = siteDAO.getAvailableSites(testCampGroundId, testArrivalDate, testDepartureDate);

		String sql = "INSERT INTO site VALUES( DEFAULT, 6, 10, 10, false, 0, false )";

		jdbcTemplate.update(sql);

		sitesAfter = siteDAO.getAvailableSites(testCampGroundId, testArrivalDate, testDepartureDate);

		Assert.assertTrue(sitesBefore.size() < sitesAfter.size());

	}

	@Test
	public void test_get_available_sites_after_booking() {
		sitesBefore = siteDAO.getAvailableSites(testCampGroundId, testArrivalDate, testDepartureDate);
		
		String sql = "INSERT INTO site VALUES( DEFAULT, 6, 10, 10, false, 0, false )";

		jdbcTemplate.update(sql);

		String sql2 = "SELECT site_id FROM site WHERE campground_id = 6 AND site_number = 10";

		SqlRowSet result = jdbcTemplate.queryForRowSet(sql2);

		result.next();
		int testSiteId = result.getInt(1);

		reservationDAO.bookReservation(testSiteId, "Test Reservation", testArrivalDate, testDepartureDate);

		sitesAfter = siteDAO.getAvailableSites(testCampGroundId, testArrivalDate, testDepartureDate);

		Assert.assertTrue((sitesBefore.size() == sitesAfter.size()));
	}
}
