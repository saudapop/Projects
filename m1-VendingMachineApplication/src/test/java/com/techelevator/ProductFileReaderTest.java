package com.techelevator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.techelevator.model.ProductFileReader;

public class ProductFileReaderTest {

	ProductFileReader reader  = new ProductFileReader();
	String fileName = "vendingmachine.csv";
	File file = new File(fileName);
    List<String> testList = new ArrayList<String>();
    List<String> productList;
    
    @Before
    public void setup() throws IOException {
    	
    	testList.add("A1|CHIPS|Potato Crisps|3.05|5");
    	testList.add("A2|CHIPS|Stackers|1.75|5");
    	testList.add("A3|CHIPS|Grain Waves|2.75|5");
    	testList.add("A4|CHIPS|Cloud Popcorn|3.65|5");
    	testList.add("B1|CANDY|Moonpie|1.80|5");
    	testList.add("B2|CANDY|Cowtales|1.50|5");
    	testList.add("B3|CANDY|Wonka Bar|1.50|5");
    	testList.add("B4|CANDY|Crunchie|1.75|5");
    	testList.add("C1|DRINK|Cola|1.25|5");
    	testList.add("C2|DRINK|Dr. Salt|1.50|5");
    	testList.add("C3|DRINK|Mountain Melter|1.50|5");
    	testList.add("C4|DRINK|Heavy|1.50|5");
    	testList.add("D1|GUM|U-Chews|0.85|5");
    	testList.add("D2|GUM|Little League Chew|0.95|5");
    	testList.add("D3|GUM|Chiclets|0.75|5");
    	testList.add("D4|GUM|Triplemint|0.75|5");
    	productList = reader.Read(fileName);
    }
    @Test
    public void test_read_file() throws IOException {
    	
    	Assert.assertEquals(testList, productList);
    	Assert.assertEquals(testList.get(0).length(), productList.get(0).length());
    }
    @Test
    public void test_read_file_size() throws IOException {
    
    	Assert.assertEquals(testList.size(), productList.size());
    }
    
    @Test
    public void test_read_line_length() throws IOException {
    	
    	Assert.assertEquals(testList.get(8).length(), productList.get(8).length());
    }
    
    @Test
    public void test_read_file_hashcode() throws IOException {
    	
    	Assert.assertEquals(testList.hashCode(), productList.hashCode());

    }
}
