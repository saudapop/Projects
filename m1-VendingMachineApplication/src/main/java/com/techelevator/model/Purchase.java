package com.techelevator.model;

import java.io.IOException;

import java.text.DecimalFormat;

public class Purchase {

	private double balanceAfterPurchase;
	public static double totalSales = 0;
	private double currentMoney;
	private double change;
	public boolean chipsSound = false;
	public boolean candySound = false;
	public boolean drinkSound = false;
	public boolean gumSound = false;

	Inventory inventory;
	Item item;
	Logger log;

	DecimalFormat df = new DecimalFormat("#.##");

	public Purchase(Inventory setter) {

		inventory = setter;
		log = new Logger(setter);
		this.currentMoney = 0.0;
		this.balanceAfterPurchase = 0.0;

	}

	public boolean isChipsSound() {
		return chipsSound;
	}

	public boolean isCandySound() {
		return candySound;
	}

	public boolean isDrinkSound() {
		return drinkSound;
	}

	public boolean isGumSound() {
		return gumSound;
	}

	public double getCurrentMoney() {
		return this.currentMoney;
	}

	public double getTotalSales() {
		return totalSales;
	}

	public double getChange() {
		return this.change;
	}

	public double getBalanceAfterPurchase() {
		return balanceAfterPurchase;
	}

	public void feedMoney(double money) throws IOException {

		this.currentMoney += money;
		log.logFedMoney(money, currentMoney);
	}

	public void purchaseItem(String itemKey) throws IOException {

		if (inventory.getItem(itemKey).getItemType().equals("CHIPS")) {
			chipsSound = true;
		}
		if (inventory.getItem(itemKey).getItemType().equals("CANDY")) {
			candySound = true;
		}
		if (inventory.getItem(itemKey).getItemType().equals("GUM")) {
			gumSound = true;
		}
		if (inventory.getItem(itemKey).getItemType().equals("DRINK")) {
			drinkSound = true;
		}

		totalSales += inventory.getItem(itemKey).getItemPrice();
		balanceAfterPurchase = currentMoney - inventory.getItem(itemKey).getItemPrice();
		inventory.getItem(itemKey).updateStock();
		inventory.getItem(itemKey).reportItemSold();
		log.logPurchase(itemKey, currentMoney, balanceAfterPurchase);

		currentMoney -= inventory.getItem(itemKey).getItemPrice();

		log.reportStockChange(totalSales);

	}

	public void endTransaction() throws IOException {
		this.change = this.currentMoney;
		this.currentMoney = 0.0;
		this.balanceAfterPurchase = 0.0;
		log.logEndTransaction(change, currentMoney);
	}

	public String changeCalculator() {
		// amount of each coin//
		int q = 0;
		int d = 0;
		int n = 0;
		double currentChange = 100 * (Double.parseDouble(df.format(this.getChange())));
		while ((currentChange - 25) >= 0) {
			currentChange -= 25;
			q++;
		}
		while ((currentChange - 10) >= 0) {
			currentChange -= 10;
			d++;
		}
		while ((currentChange - 5) >= 0) {
			currentChange -= 5;
			n++;
		}

		System.out.println("Change: ");
		System.out.println("Quarters: " + q);
		System.out.println("Dimes: " + d);
		System.out.println("Nickels: " + n);

		this.change = 0; 
		String changeInString = "Quarters: "+ q + " Dimes: " + d + " Nickels: " + n;
		
		return changeInString;
	}
 
	public void soundReset() {
		this.chipsSound = false;
		this.gumSound = false;
		this.candySound = false;
		this.drinkSound = false;
	}

}