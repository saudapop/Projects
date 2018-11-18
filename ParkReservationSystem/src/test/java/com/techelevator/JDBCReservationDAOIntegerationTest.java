package com.techelevator;

import java.time.LocalDate;

import javax.sql.DataSource;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.campground.model.jdbc.JDBCReservationDAO;



public class JDBCReservationDAOIntegerationTest extends DAOIntegrationTest {



	JDBCReservationDAO reservationDAO = new JDBCReservationDAO(getDataSource());
	JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());
	
	
	@Test
	public void test_return_reservationId() {
		
		SqlRowSet nextIdResult = jdbcTemplate.queryForRowSet(" SELECT nextval('reservation_reservation_id_seq') ");
		nextIdResult.next(); 
		int nextReservationId = (int) nextIdResult.getLong(1);
		
		String sql =  "INSERT INTO reservation VALUES (DEFAULT, 566, 'Integration Test', '2018-01-01', '2018-01-05' , CURRENT_DATE ) ";
		
		jdbcTemplate.update(sql);
		
		int actual = reservationDAO.getReservationIdByName("Integration Test");
		
		Assert.assertEquals("Reservation Id: ", nextReservationId +1, actual);
	}
	@Test
	public void test_book_reservation() {
		
		reservationDAO.bookReservation(566, "Tucker", LocalDate.of(2018, 05, 01), LocalDate.of(2018, 05, 05));
		
		String sql = "SELECT name FROM reservation WHERE name = 'Tucker'";
		
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
		result.next();
		String bookersName = result.getString(1);
		
		Assert.assertTrue(bookersName.equals("Tucker"));
		
	}
	
	
}
