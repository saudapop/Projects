package com.techelevator.campground.model;

import java.time.LocalDate;


public interface ReservationDAO {
	

	public  void bookReservation(int siteId, String bookersName, LocalDate arrivalDate, LocalDate departureDate);

	
}
