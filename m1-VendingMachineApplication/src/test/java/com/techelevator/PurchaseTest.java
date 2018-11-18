package com.techelevator;

import java.awt.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.techelevator.model.Inventory;
import com.techelevator.model.Logger;
import com.techelevator.model.ProductFileReader;
import com.techelevator.model.Purchase;

public class PurchaseTest {

	Purchase purchase;
	Inventory inventory;
	Logger log;
	File filelog = new File("log.txt");
	ProductFileReader reader = new ProductFileReader();
	String fileName = "vendingmachine.csv";
	File file = new File(fileName);
	java.util.List<String> productList = new ArrayList<String>();

	@Before
	public void setup() throws IOException {

		log = new Logger(new Inventory());
		inventory = new Inventory();
		// in = new Scanner(file);
		productList = reader.Read(fileName);
		inventory.stockItems(productList);
		purchase = new Purchase(inventory);
	}

	@Test
	public void test_feed_money_updates_current_Money_provided() throws IOException {
		purchase.feedMoney(10.00); // Act
		Assert.assertEquals(10.00, purchase.getCurrentMoney(), 0);
		purchase.feedMoney(5.00);
		Assert.assertEquals(15.00, purchase.getCurrentMoney(), 0);// Assert
		Assert.assertEquals(0.0, purchase.getChange(), 0);
	}

	@Test
	public void test_purchase_updates_current_Money_provided() throws IOException {
		purchase.feedMoney(10.00); // Act
		purchase.purchaseItem("C1");
		Assert.assertEquals(8.75, purchase.getCurrentMoney(), 0);
	}

	@Test
	public void test_purchase_updates_stock() throws IOException {
		purchase.feedMoney(10.00); // Act
		purchase.purchaseItem("C1");
		Assert.assertEquals(4, inventory.getItem("C1").getStock());
	}

	@Test
	public void test_end_transaction_returns_correct_change() throws IOException {
		purchase.feedMoney(10.00); // Act
		purchase.purchaseItem("C1");
		purchase.purchaseItem("A1");
		purchase.endTransaction();
		Assert.assertEquals(5.70, purchase.getChange(), 0);
	}

	public void test_end_transaction_updates_money_provided_to_zero() throws IOException {
		purchase.feedMoney(10.00); // Act
		purchase.purchaseItem("C1");
		purchase.purchaseItem("A1");
		purchase.endTransaction();
		Assert.assertEquals(0.0, purchase.getCurrentMoney(), 0);
	}

	@Test
	public void test_end_transaction_returns_correct_drink_sound() throws IOException {
		purchase.feedMoney(10.00); // Act
		purchase.purchaseItem("C1");
		purchase.endTransaction();
		Assert.assertEquals(true, purchase.isDrinkSound());
	}

	@Test
	public void test_end_transaction_returns_correct_chips_sound() throws IOException {
		purchase.feedMoney(10.00); // Act
		purchase.purchaseItem("A1");
		purchase.endTransaction();
		Assert.assertEquals(true, purchase.isChipsSound());
	}

	@Test
	public void test_end_transaction_returns_correct_gum_sound() throws IOException {
		purchase.feedMoney(10.00); // Act
		purchase.purchaseItem("D4");
		purchase.endTransaction();
		Assert.assertEquals(true, purchase.isGumSound());
	}

	@Test
	public void test_end_transaction_returns_correct_candy_sound() throws IOException {
		purchase.feedMoney(10.00); // Act
		purchase.purchaseItem("B3");
		purchase.endTransaction();
		Assert.assertEquals(true, purchase.isCandySound());
	}

	@Test 
	public void test_change_calculator() throws IOException {
		
		purchase.feedMoney(10.00);
		purchase.purchaseItem("B2");
		purchase.purchaseItem("B2");
		purchase.purchaseItem("D1");
		purchase.endTransaction();
		String change = purchase.changeCalculator();
		Assert.assertEquals("Quarters: 24 Dimes: 1 Nickels: 1",change);
	}
	@Test
	public void sound_reset_test() throws IOException {
		purchase.feedMoney(10.00);
		purchase.purchaseItem("B2");
		purchase.purchaseItem("A2");
		purchase.purchaseItem("C1");
		purchase.purchaseItem("D1");
		purchase.soundReset();
		Assert.assertEquals(false, purchase.isCandySound());
		Assert.assertEquals(false, purchase.isGumSound());
		Assert.assertEquals(false, purchase.isChipsSound());
		Assert.assertEquals(false, purchase.isDrinkSound());
	}
}
