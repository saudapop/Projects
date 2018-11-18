package com.techelevator.campground.view;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

import com.techelevator.campground.model.Campground;
import com.techelevator.campground.model.Park;
import com.techelevator.campground.model.Site;

public class Menu {

	private PrintWriter out;
	private Scanner in;

	public Menu(InputStream input, OutputStream output) {
		this.out = new PrintWriter(output);
		this.in = new Scanner(input);
	}

	public Object getChoiceFromOptions(Object[] options) {
		Object choice = null;
		while (choice == null) {
			displayMenuOptions(options);
			choice = getChoiceFromUserInput(options);
		}
		return choice;
	}

	private Object getChoiceFromUserInput(Object[] options) {
		Object choice = null;
		String userInput = in.nextLine();
		try {
			int selectedOption = Integer.valueOf(userInput);
			if (selectedOption <= options.length) {
				choice = options[selectedOption - 1];
			}
		} catch (NumberFormatException e) {
			// eat the exception, an error message will be displayed below since choice will
			// be null
		}
		if (choice == null) {
			out.println("\n*** " + userInput + " is not a valid option ***\n");
		}
		return choice;
	}

	private void displayMenuOptions(Object[] options) {
		out.println();
		for (int i = 0; i < options.length; i++) {
			int optionNum = i + 1;
			out.println(optionNum + ") " + options[i]);
		}
		out.print("\nPlease choose an option >>> ");
		out.flush();
	}

	public int getCampgroundOptionFromUser(List<Campground> campgrounds) {

		int campgroundId = -1;
		do  {
			try {
				campgroundId = Integer.parseInt(in.nextLine()); 
				if (isCampgroundIdInvalid(campgrounds, campgroundId)) {
					showInvalidCampgroundMessage(campgroundId);
					campgroundId = -1;
				}
				
			} 
			catch (NumberFormatException e) {
				showInvalidCampgroundMessage(campgroundId);
				campgroundId = -1;
			}
			
		} while ( isCampgroundIdInvalid(campgrounds, campgroundId));
		
		return campgroundId;
	}
	
	public LocalDate getDateFromUser() {
		LocalDate userDate = null;

			try {
				userDate = LocalDate.parse(in.nextLine());
			} catch (DateTimeParseException e) {
				System.out.println("Invalid input. Please try again using format: YYYY-MM-DD");
				getDateFromUser();
			}
		
		return userDate;

	}

	private boolean isCampgroundIdInvalid(List<Campground> campgrounds, int campgroundId) {
		return campgroundId > campgrounds.size() || campgroundId < 0;
	}

	private void showInvalidCampgroundMessage(int campgroundId) {
		System.out.println("\n*** " + campgroundId + " is not a valid option ***\n"); 
		System.out.println("Please enter a valid campground number!");
	}

	
	public int getSiteOptionFromUser(List<Site> sites) {
		int siteId = 0;
		
		try {
			siteId = Integer.parseInt(in.nextLine());
			if (isSitesInputInvalid(sites, siteId)) {
				showSitesErrorMessage(sites);
			}
			
		}catch (NumberFormatException e) {
			showSitesErrorMessage(sites);
		}
		return siteId;
	}

	private void showSitesErrorMessage(List<Site> sites) {
		System.out.println("Invalid input. Please try again!");
		getSiteOptionFromUser(sites);
	}

	private boolean isSitesInputInvalid(List<Site> sites, int siteId) {
		return siteId > sites.size() || siteId < 1;
	}
	public String getUserInput() {
		
		String userInput = in.nextLine();
		return userInput;
	}
	
	
}
