package com.techelevator;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

import org.junit.*;

import com.techelevator.view.Menu;

public class InventoryTest {
	
	
	Inventory inventory = new Inventory();
	Menu menu = new Menu(System.in, System.out);
	Purchase purchase = new Purchase(inventory);
	Item expected;
	Item result;
	ProductFileReader reader  = new ProductFileReader();
	String fileName = "vendingmachine.csv";
	File file = new File(fileName);
    List<String> testList = new ArrayList<String>();
    Map<String, Item> mapTest = new HashMap<String, Item>();
	
    @Test
	public void inventory_map_test_type() throws IOException {
    	testList = reader.Read(fileName);//Arrange
		inventory.stockItems(testList);//Act
		Assert.assertEquals("CHIPS", inventory.getItemMap().get("A1").getItemType());//Assert
	}
    @Test
   	public void inventory_map_test_name() throws IOException {
       	testList = reader.Read(fileName);//Arrange
   		inventory.stockItems(testList);//Act
   		Assert.assertEquals("Mountain Melter", inventory.getItemMap().get("C3").getItemName());//Assert
   		Assert.assertEquals("Little League Chew", inventory.getItemMap().get("D2").getItemName());//Assert
    
    }
    
    @Test
   	public void inventory_map_test_price() throws IOException {
       	testList = reader.Read(fileName);//Arrange
   		inventory.stockItems(testList);//Act
   		Assert.assertEquals(3.65, inventory.getItemMap().get("A4").getItemPrice(), 0);//Assert
   		Assert.assertEquals(1.25, inventory.getItemMap().get("C1").getItemPrice(), 0);//Assert
    
    }
    
    @Test
   	public void inventory_map_test_stock_after_purchase() throws IOException {
       	testList = reader.Read(fileName);//Arrange
   		inventory.stockItems(testList);//Act
   		purchase.purchaseItem("A1");//Act
   		Assert.assertEquals(5, inventory.getItemMap().get("C3").getStock());//Assert 		
   		Assert.assertEquals(4, inventory.getItemMap().get("A1").getStock());//Assert
    
    }
    
    @Test
    public void test_get_item_returns_item_object() throws IOException {
    	testList = reader.Read(fileName);//Arrange
    	inventory.stockItems(testList);//Act
    	Item result = inventory.getItem("A1");
    	Assert.assertEquals("Potato Crisps", result.getItemName());//Assert
    }
    
}
