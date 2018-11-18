package com.techelevator.campground.model;

import java.util.List;

public class Campground {
	
	private int campgroundId;
	private int parkId;
	private String name;
	private int openFromMM;
	private int openToMM;
	private double dailyFee;
	public int getCampgroundId() {
		return campgroundId;
	}
	public void setCampgroundId(int campgroundId) {
		this.campgroundId = campgroundId;
	}
	public int getParkId() {
		return parkId;
	}
	public void setParkId(int parkId) {
		this.parkId = parkId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getOpenFromMM() {
		return openFromMM;
	}
	public void setOpenFromMM(int openFromMM) {
		this.openFromMM = openFromMM;
	}
	public int getOpenToMM() {
		return openToMM;
	}
	public void setOpenToMM(int openToMM) {
		this.openToMM = openToMM;
	}
	public double getDailyFee() {
		return dailyFee;
	}
	public void setDailyFee(double dailyFee) {
		this.dailyFee = dailyFee;
	}
	
	public static void getCampgroundInformation(List<Campground> campgrounds) {
		int campgroundOptionNumber = 1;
		System.out.println("OPTION       Name             Open               Close               Daily Fee");
		System.out.println("-------------------------------------------------------------------------");
		for (Campground campground : campgrounds) {
			System.out.println("#" + campgroundOptionNumber++ + "\t" + campground.getName() + "\t"
					+ MonthConverter.convertMonth(campground.getOpenFromMM()) + "\t"
					+ MonthConverter.convertMonth(campground.getOpenToMM()) + "\t" + "$" + campground.getDailyFee());
		}
	}
}
