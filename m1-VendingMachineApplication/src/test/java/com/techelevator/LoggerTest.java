package com.techelevator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.util.reflection.FieldReader;
import org.junit.Assert;

public class LoggerTest {

	Purchase purchase;
	Inventory inventory;
	Logger log;
	ProductFileReader reader = new ProductFileReader();
	File fileLog = new File("log.txt"); 
	File reportTest = new File("reportTest.txt");
	Scanner in;
	java.util.List<String> productList = new ArrayList<String>();
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"); 
	LocalDateTime now = LocalDateTime.now();
	
	@Before
	public void setup() throws IOException {
      
      inventory = new Inventory();
      productList = reader.Read("vendingmachine.csv");
      inventory.stockItems(productList);
      purchase = new Purchase(inventory);
      log = new Logger(inventory);
      in = new Scanner(fileLog);
      purchase.totalSales = 0;
	}
	
	@Test
	public void test_log_feed_money_has_correct_Money_provided() throws IOException {
		purchase.feedMoney(10.00); // Ac
		Assert.assertEquals(10.00, purchase.getCurrentMoney(),0);
	
}
	@Test
	public void test_log_purchase_has_correct_balance_after_purchase() throws IOException {
		purchase.feedMoney(10.00); // Act
		purchase.purchaseItem("C1");
		Assert.assertEquals(8.75, purchase.getBalanceAfterPurchase(),0);//Assert
	}
	
	@Test
	public void test_log_end_transaction_logs_correct_change_after_end() throws IOException {
		purchase.feedMoney(10.00); // Act
		purchase.purchaseItem("C1");
		purchase.endTransaction();
		Assert.assertEquals(8.75, purchase.getChange(),0);//Assert
	}
	@Test
	public void test_report_has_correct_amt_sold() throws IOException {
		purchase.feedMoney(10.00);
		purchase.purchaseItem("A1");
		purchase.purchaseItem("A1");
		Assert.assertEquals(2, inventory.getItem("A1").getAmtSold(), 0);
	}
	@Test 
	public void test_report_totalSales_correct_amt() throws IOException {
		
		purchase.feedMoney(10.00);
		purchase.purchaseItem("A1");
		purchase.purchaseItem("C1");
		purchase.purchaseItem("B1");
		Assert.assertEquals(6.10,purchase.getTotalSales(), 0);
	}
	
}
