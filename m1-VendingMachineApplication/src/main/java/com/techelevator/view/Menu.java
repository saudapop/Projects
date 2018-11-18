package com.techelevator.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.techelevator.model.Inventory;
import com.techelevator.model.Item;
import com.techelevator.model.ProductFileReader;
import com.techelevator.model.Purchase;

public class Menu {

	private PrintWriter out;
	private Scanner in;
	Purchase purchase;
	Inventory inventory = new Inventory();

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

	public Object getChoiceFromUserInput(Object[] options) {
		Object choice = null;
		String userInput = in.nextLine();
		try {
			int selectedOption = Integer.valueOf(userInput);
			if (selectedOption > 0 && selectedOption <= options.length) {
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

	public void displayMenuOptions(Object[] options) {
		out.println();
		for (int i = 0; i < options.length; i++) {
			int optionNum = i + 1;
			out.println(optionNum + ") " + options[i]);
		}
		out.print("\nPlease choose an option >>> ");
		out.flush();
	}

	List<String> productList;

	public void DisplayProductMenu() throws IOException {
		ProductFileReader fileReader = new ProductFileReader();
		productList = fileReader.Read("vendingmachine.csv");

		for (String line : productList) {

			System.out.println(line + " In Stock.");

		}

		inventory.stockItems(productList);
		purchase = new Purchase(inventory);
	}

	public void DisplayPurchaseMenu() throws IOException {

		while (true) {

			System.out.println("(1) Feed Money");
			System.out.println("(2) Select Product");
			System.out.println("(3) Finish Transaction");
			System.out.format("Current Money Provided: " + "%.2f", +purchase.getCurrentMoney());
			System.out.println();

			int purchaseChoice = 0;
			try {

				purchaseChoice = in.nextInt();

			} catch (Exception e) {
				System.out.println("Enter Valid Selection");
				in.nextLine();
			}

			switch (purchaseChoice) {
			case 1:

				double moneyIn = 0.0;
				System.out.println("Enter money ($1 / $2 / $5 / $10)");
				try {
					moneyIn = in.nextDouble();
				} catch (InputMismatchException e) {
					System.out.println("The amount entered is not valid! please enter valid amount ($1 / $2 / $5 / $10)");
							
					in.next();
					continue;
				}
				if (moneyIn != 1.0 && moneyIn != 2.0 && moneyIn != 5.0 && moneyIn != 10) {
					System.out.println("Can't accept that amount! please enter dollar amount ($1 / $2 / $5 / $10)");
					in.nextDouble();
					continue;
				}
				purchase.feedMoney(moneyIn);
				continue;

			case 2:

				in.nextLine();
				if (purchase.getCurrentMoney() > 0) {
					System.out.println("make your selection");
					String productKey = in.nextLine();

					if (!(inventory.getItemMap().containsKey(productKey))) {
						System.out.println("Sorry, Invalid product code");
						continue;
					}
					if (inventory.getItem(productKey).getItemPrice() > purchase.getCurrentMoney()) {
						System.out.println("Please Enter More Money!!");
						continue;
					}
					if (inventory.getItem(productKey).getStock() == 0) {
						System.out.println("product SOLD OUT!");
						continue;
					}
					purchase.purchaseItem(productKey);

					continue;

				}
			case 3:
				purchase.endTransaction();
				purchase.changeCalculator();

				if (purchase.isChipsSound()) {
					System.out.println("Crunch Crunch, Yum!");
				}
				if (purchase.isCandySound()) {
					System.out.println("Munch Munch, Yum!");
				}
				if (purchase.isDrinkSound()) {
					System.out.println("Glug Glug, Yum!");
				}
				if (purchase.isGumSound()) {
					System.out.println("Chew Chew, Yum!");
				}

				purchase.soundReset();
				
			}
			break;
		}
	}
}
