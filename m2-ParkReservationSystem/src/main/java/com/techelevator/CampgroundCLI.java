package com.techelevator;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

import com.techelevator.campground.model.Campground;
import com.techelevator.campground.model.CutString;
import com.techelevator.campground.model.MonthConverter;
import com.techelevator.campground.model.Park;
import com.techelevator.campground.model.Site;
import com.techelevator.campground.model.jdbc.JDBCCampgroundDAO;
import com.techelevator.campground.model.jdbc.JDBCParkDAO;
import com.techelevator.campground.model.jdbc.JDBCReservationDAO;
import com.techelevator.campground.model.jdbc.JDBCSiteDAO;
import com.techelevator.campground.view.Menu;

public class CampgroundCLI {
	private static final String MAIN_MENU_VIEW_PARKS = "View Parks";
	private static final String MAIN_MENU_EXIT = "Exit";
	private static final String[] MAIN_MENU_OPTIONS = new String[] { MAIN_MENU_VIEW_PARKS, MAIN_MENU_EXIT };

	private static final String MENU_VIEW_CAMPGROUNDS = "View Campgrounds";
	private static final String MENU_SEARCH_FOR_RESERVATION = "Search for Reservation";
	private static final String MENU_RETURN_TO_PREVIOUS_SCREEN = "Return to Main Menu";
	private static final String[] PARK_MENU_OPTIONS = new String[] { MENU_VIEW_CAMPGROUNDS, MENU_SEARCH_FOR_RESERVATION,
			MENU_RETURN_TO_PREVIOUS_SCREEN };

	private static final String MENU_RESERVATION_SEARCH = "Seach for Available Reservation";
	private static final String MENU_RESERVATION_RETURN = "Return to Main Menu";
	private static final String[] CAMPGROUND_MENU_OPTIONS = new String[] { MENU_RESERVATION_SEARCH,
			MENU_RESERVATION_RETURN };
	
	private static final String MENU_SITES_ASK_ALTERNATE_DATES = "Enter Alternate Dates";
	private static final String MENU_SITES_CHOOSE_NEW_CAMPGROUND = "Choose New Campground";
	private static final String[] SITES_MENU_OPTIONS = new String[] {MENU_SITES_ASK_ALTERNATE_DATES, MENU_SITES_CHOOSE_NEW_CAMPGROUND};

	private Menu menu;
	private JDBCParkDAO parkDAO;
	private JDBCCampgroundDAO campgroundDAO;
	private JDBCSiteDAO siteDAO;
	private JDBCReservationDAO reservationDAO;
	List<Park> parks;
	List<Campground> campgrounds; 

	List<Site> sites; 
	Object[] parkNames;
	Object[] campNames;
	int campgroundOptionNumber;
	int siteOptionFromUser;
	LocalDate arrivalDate;
	LocalDate departureDate;

	public static void main(String[] args) {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/campground");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");

		CampgroundCLI application = new CampgroundCLI(dataSource);
		application.run();
	}

	private void printHeading(String headingText) {
		System.out.println("\n" + headingText);
		for (int i = 0; i < headingText.length(); i++) {
			System.out.print("-");
		}
		System.out.println();
	}

	public CampgroundCLI(DataSource datasource) {
		this.menu = new Menu(System.in, System.out);
		parkDAO = new JDBCParkDAO(datasource);
		campgroundDAO = new JDBCCampgroundDAO(datasource);
		siteDAO = new JDBCSiteDAO(datasource);
		reservationDAO = new JDBCReservationDAO(datasource);
	}

	public void run() {

		displayApplicationBanner();
		
		while (true) {
			printHeading("Welcome! Please make a selection!");

			String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);
			if (choice.equals(MAIN_MENU_VIEW_PARKS)) {
				int parkId = handleParks();
				
				while (true) {
					String choice2 = (String) menu.getChoiceFromOptions(PARK_MENU_OPTIONS);
					if (choice2.equals(MENU_VIEW_CAMPGROUNDS)) {
						handleCampgrounds(parkId);

						String choice3 = (String) menu.getChoiceFromOptions(CAMPGROUND_MENU_OPTIONS);

						if (choice3.equals(MENU_RESERVATION_SEARCH)) {
							handleSearchReservation();
							
							if (campgroundOptionNumber == 0) {
								break;
							}
							
							if (isSitesAvailable()) {
								handleBookReservation(arrivalDate, departureDate);
								break;
							
							}

							while (!isSitesAvailable()) {
								System.out.println("No Sites Available. Please Choose an Option");
								String choice4 = (String) menu.getChoiceFromOptions(SITES_MENU_OPTIONS);
								if (choice4.equals(MENU_SITES_ASK_ALTERNATE_DATES)) {
									handleSearchReservation();
								} else if (choice4.equals(MENU_SITES_CHOOSE_NEW_CAMPGROUND)) {
									break;
								} 
							}
							
						} else if (choice3.equals(MENU_RETURN_TO_PREVIOUS_SCREEN)) {
							break;
						}

					} else if (choice2.equals(MENU_RETURN_TO_PREVIOUS_SCREEN)) {
						break;
					}

				}
			} else if (choice.equals(MAIN_MENU_EXIT)) {
				System.exit(0);
			}
		}
	}

	private boolean isSitesAvailable() {
		return sites.size() >= 1;
	}

	private int handleParks() {
		printHeading("Select a Park for Futher Details");
		parks = parkDAO.getAllParks(); // list of parks
		parkNames = new Object[parks.size()]; // list of park names using size of parks list to create size of array

		getParkNames();
		String parkChoice = (String) menu.getChoiceFromOptions(parkNames); // prints the list of parks to the user and prompts a park choice
		
		int parkId = Park.getParkInformation(parkChoice, parkNames, parks); // uses the choice and prints the park info and gets the parkID.

		return parkId;
	}

	private void getParkNames() {
		for (int i = 0; i < parkNames.length; i++) { // loop through the list of parks and assign the name to the empty objects
			parkNames[i] = parks.get(i).getName(); // of the parkNames array based on index
		}
	}



	private void handleCampgrounds(int parkId) {
		printHeading(parks.get(parkId - 1).getName() + " Park Campgrounds"); // get parks name and concatenate title
		
		campgrounds = campgroundDAO.getAllCampgrounds(parkId); // list of campgrounds
		Campground.getCampgroundInformation(campgrounds);
	}


	private void handleSearchReservation() {
		
		System.out.println("Which campground?(enter 0 to cancel) ::::");
		while (campgroundOptionNumber < 1) {
		campgroundOptionNumber = menu.getCampgroundOptionFromUser(campgrounds); 
		if (campgroundOptionNumber ==0) {
			break;
		}
		System.out.println("What is the arrival date?(YYYY-MM-DD)");
		arrivalDate = menu.getDateFromUser();
		
		System.out.println("What is the departure date?(YYYY-MM-DD)");
		departureDate = menu.getDateFromUser();
		
		printSitesHeading();
		showAvailableSitesToUser(campgroundOptionNumber, arrivalDate, departureDate);
		}

	}
	
	/*
	 * start site extracted methods
	 */
	
	private void showAvailableSitesToUser(int campgroundOptionNumber, LocalDate arrivalDate, LocalDate departureDate) {
		int siteOptionCounter = 1;
		sites = getSitesFromCampground(campgroundOptionNumber, arrivalDate, departureDate);
		
		for (Site site : sites) {
			System.out.println("#" + siteOptionCounter++ + "\t\t" + site.getSiteNumber() + "\t\t"
					+ site.getMaxOccupancy() + "\t\t\t" + site.isAccesible() + "\t\t\t" + site.getMaxRvLength() + "\t\t"
					+ site.isUtilities() + "\t\t\t" + "$" + ChronoUnit.DAYS.between(arrivalDate, departureDate)
							* campgrounds.get(campgroundOptionNumber - 1).getDailyFee());
		}
	}
	
	private List<Site> getSitesFromCampground(int campgroundOptionNumber, LocalDate arrivalDate, LocalDate departureDate) {
		return siteDAO.getAvailableSites(campgrounds.get(campgroundOptionNumber - 1).getCampgroundId(), arrivalDate, departureDate);
	}
	
	private void printSitesHeading() {
		System.out.println("OPTION       " + "\t" + "Site No." + "\t" + "Max Occup." + "\t" + "Wheelchair Accessible?"
											+ "\t         " + "Max RV Length" + "\t\t" + "Utility" + "\t\t\t" + "Cost");
		System.out.println(
				"--------------------------------------------------------------------------------------------------------------------------------------------");
	}
	/*
	 * end site extracted methods
	 */
	
	private void handleBookReservation(LocalDate arrivalDate, LocalDate departureDate) {
		
		System.out.println("Which site should be reserved?(Enter 0 to cancel) ::::");
		
	
		siteOptionFromUser = menu.getSiteOptionFromUser(sites);
		
		
		System.out.println();
		System.out.println("What name should the reservation be made under?");
		String bookersName = menu.getUserInput();
		
		reservationDAO.bookReservation(sites.get(siteOptionFromUser - 1).getSiteId(), bookersName, arrivalDate, departureDate);
		System.out.println("Your reservation has been made and the confirmation id is " + reservationDAO.getReservationIdByName(bookersName));
		
	}
	
	private void displayApplicationBanner() {
		System.out.println("         #        #######  #######   #####        #####   #######       #####      #     #     #  ######   ###  #     #   #####   ###  ###  ###"); 
		System.out.println("	 #        #           #     #     #      #     #  #     #      #     #    # #    ##   ##  #     #   #   ##    #  #     #  ###  ###  ### ");
		System.out.println("	 #        #           #     #            #        #     #      #         #   #   # # # #  #     #   #   # #   #  #        ###  ###  ### ");
		System.out.println("	 #        #####       #      #####       #  ####  #     #      #        #     #  #  #  #  ######    #   #  #  #  #  ####   #    #    #  ");
		System.out.println("	 #        #           #           #      #     #  #     #      #        #######  #     #  #         #   #   # #  #     #                ");
		System.out.println("	 #        #           #     #     #      #     #  #     #      #     #  #     #  #     #  #         #   #    ##  #     #  ###  ###  ### ");
		System.out.println("	 #######  #######     #      #####        #####   #######       #####   #     #  #     #  #        ###  #     #   #####   ###  ###  ### ");
		
		                                                                                                                                        
	}
	
}
