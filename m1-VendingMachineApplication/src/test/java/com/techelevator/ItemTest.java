package com.techelevator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.techelevator.model.Inventory;
import com.techelevator.model.Logger;
import com.techelevator.model.ProductFileReader;
import com.techelevator.model.Purchase;

public class ItemTest {

	Purchase purchase;
	Inventory inventory;
	Logger log;
	File filelog = new File("log.txt");
	ProductFileReader reader  = new ProductFileReader();
	String fileName = "vendingmachine.csv";
	File file = new File(fileName);
	java.util.List<String> productList = new ArrayList<String>();
	
	@Before
	public void setup() throws IOException {
	      productList = reader.Read(fileName);
	      inventory = new Inventory();
	      inventory.stockItems(productList);
	      purchase = new Purchase(inventory);
	}
	
	@Test
	public void test_purchased_item_updates_stock() throws IOException {
		
		purchase.feedMoney(10.00); // Act
		purchase.purchaseItem("C1");
		Assert.assertEquals(4, inventory.getItem("C1").getStock());// Assert
	}
}
