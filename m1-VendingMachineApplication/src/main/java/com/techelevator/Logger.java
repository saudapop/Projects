package com.techelevator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {

	Inventory inventory;
	Purchase purchase;
	Item item = new Item();
	File file = new File("log.txt");
	File report = new File("report.txt");
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
	LocalDateTime now = LocalDateTime.now();
	DecimalFormat df = new DecimalFormat("#.##");

	public Logger(Inventory logger) {
		inventory = logger;
	}

	public void logFedMoney(double money, double currentBalance) throws IOException {

		try (PrintWriter out = new PrintWriter(new FileWriter(file, true))) {

			out.println();
			out.printf(dtf.format(now) + "  " + "FEED MONEY:" + "\t\t" + money + "\t" + "$"
					+ Double.parseDouble(df.format(currentBalance)));
			out.println();

		}
	}

	public void logPurchase(String itemKey, double currentMoney, double balanceAfterPurchase) throws IOException {

		try (PrintWriter out = new PrintWriter(new FileWriter(file, true))) {
			out.println();
			out.printf(dtf.format(now) + "  " + "%-10s%-10s  ",  inventory.getItem(itemKey).getItemName(), "  " +  itemKey.toString()
					+ "\t\t" + "$" + Double.parseDouble(df.format(currentMoney)) + "\t" + "$"
					+ Double.parseDouble(df.format(balanceAfterPurchase)));
			out.println();
		}

	}

	public void logEndTransaction(double change, double currentMoney) throws IOException {

		try (PrintWriter out = new PrintWriter(new FileWriter(file, true))) {
			out.println();
			out.printf(dtf.format(now) + "  " + "GIVE CHANGE:" + "\t\t" + "$" + Double.parseDouble(df.format(change))
					+ "\t" + df.format(currentMoney)).toString();
			out.println();
		}
	}

	public void reportStockChange(double totalSales) throws IOException {

		try (PrintWriter out = new PrintWriter(new FileWriter(report, false))) {
			out.println();
			for (String itemKey : inventory.getItemMap().keySet()) {
				out.write(inventory.getItem(itemKey).getItemName() + "|" + inventory.getItem(itemKey).getAmtSold());
				out.println();
			}
			out.println();
			out.write("**TOTAL SALES** " + "$" + df.format(totalSales));
		} 
	}
}