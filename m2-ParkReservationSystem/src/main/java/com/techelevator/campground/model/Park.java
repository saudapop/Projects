package com.techelevator.campground.model;

import java.sql.Date;
import java.util.List;

public class Park {
	
	private int parkId;
	private String name;
	private String location;
	private Date establishDate;
	private int area;
	private int visitors;
	private String description;
	
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
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Date getEstablishDate() {
		return establishDate;
	}
	public void setEstablishDate(Date date) {
		this.establishDate = date;
	}
	public int getArea() {
		return area;
	}
	public void setArea(int area) {
		this.area = area;
	}
	public int getVisitors() {
		return visitors;
	}
	public void setVisitors(int visitors) {
		this.visitors = visitors;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public static int getParkInformation(String choice, Object[] parkNames, List<Park> parks) {


		int parkId = 0;
		for (int i = 0; i < parkNames.length; i++) {

			if (choice.equals(parks.get(i).getName())) {
				System.out.println();
				System.out.println(parks.get(i).getName() + " National Park");
				System.out.println("Location: " + parks.get(i).getLocation());
				System.out.println("Established: " + parks.get(i).getEstablishDate());
				System.out.println("Area: " + parks.get(i).getArea() + " sq km");
				System.out.println("Number of Visitors: " + parks.get(i).getVisitors());
				System.out.println();
				CutString.splitString(parks.get(i).getDescription(), 75); 
				parkId = parks.get(i).getParkId();
			} 
		}
		return parkId;
	}
}
