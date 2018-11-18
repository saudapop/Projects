package com.techelevator.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProductFileReader {

	List<String> productList = new ArrayList<String>();
	public List<String> Read(String fileName) throws IOException {
		
		File file = new File(fileName);
		
		try (Scanner in = new Scanner(file)){
			
			while (in.hasNextLine()){
				String line = in.nextLine();
				line = line + "|5";
				productList.add(line);
			}
		}
		return productList;
	}
	

}
