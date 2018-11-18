package com.techelevator.model;

import java.util.HashMap;
import java.util.Map;

public class Item {

	private String itemName;
	private double itemPrice;
	private String itemKey;
	private int stock;
	private String itemType;
	private int amtSold;


	public Item() {

	}

	public Item(String itemName, String itemType, double itemPrice, int stock) {

		this.itemName = itemName;
		this.itemType = itemType;
		this.itemPrice = itemPrice;
		this.stock = stock;
		this.amtSold = 0;

	}

	public int getAmtSold() {
		return amtSold;
	}

	public void reportItemSold() {
		amtSold += 1;
	}

	public String getItemName() {
		return this.itemName;
	}

	public Double getItemPrice() {

		return this.itemPrice;
	}

	public int getStock() {
		return this.stock;
	}

	public String getItemType() {
		return this.itemType;
	}

	public void updateStock() {
		this.stock -= 1;
	}

}
