package com.techelevator.campground.model;

public class MonthConverter {

	
	public static String convertMonth (int month) {
		String convertedMonth = "";
		
		switch (month) {
		
		case 01: 
			convertedMonth = "January";
		case 02:
			convertedMonth = "February";
			break;
		case 03:
			convertedMonth = "March";
			break;
		case 04:
			convertedMonth = "April";
			break;
		case 05:
			convertedMonth = "May";
			break;
		case 06:
			convertedMonth = "June";
			break;
		case 07:
			convertedMonth = "July";
			break;
		case 8:
			convertedMonth = "August";
			break;
		case 9:
			convertedMonth = "September";
			break;
		case 10:
			convertedMonth = "October";
			break;
		case 11:
			convertedMonth = "November";
			break;
		case 12:
			convertedMonth = "December";
			break;
			
		}
		return convertedMonth;
	}
}
