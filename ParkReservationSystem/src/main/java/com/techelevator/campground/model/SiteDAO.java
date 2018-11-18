package com.techelevator.campground.model;


import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface SiteDAO {

	public List<Site>  getAvailableSites(int campgroundId, LocalDate arrivalDate, LocalDate departureDate);
}
