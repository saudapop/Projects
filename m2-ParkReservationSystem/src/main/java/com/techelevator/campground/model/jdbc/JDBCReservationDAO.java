package com.techelevator.campground.model.jdbc;

import java.time.LocalDate;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.campground.model.Reservation;
import com.techelevator.campground.model.ReservationDAO;

public class JDBCReservationDAO implements ReservationDAO {
	
	private JdbcTemplate jdbcTemplate;
	
	public JDBCReservationDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public void bookReservation(int siteId, String bookersName, LocalDate arrivalDate, LocalDate departureDate) {
		
		String sql = "INSERT INTO reservation VALUES (DEFAULT, ?, ?, ?, ? , CURRENT_DATE ) ";
		
		jdbcTemplate.update(sql, siteId, bookersName, arrivalDate, departureDate );
		
	}
	
	public int getReservationIdByName(String name) {
		int reservationId = 0;
		
		String sql = "SELECT reservation_id  FROM reservation \n" + 
					 "WHERE name = ?";
		
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, name);
		result.next();
		reservationId = result.getInt(1);
		
		return reservationId;
		
	}
	
	private Reservation mapRowToReservation(SqlRowSet result) {
		Reservation reservation;
		reservation = new Reservation();
		
		reservation.setReservationId(result.getInt("reservation_id"));
		reservation.setSiteId(result.getInt("site_id"));
		reservation.setName(result.getString("name"));
		reservation.setFromDate(result.getDate("from_date").toLocalDate());
		reservation.setToDate(result.getDate("to_date").toLocalDate());
		reservation.setCreateDate(result.getDate("create_date").toLocalDate());
		
		return reservation;
	}

}
