package com.techelevator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class Inventory {

	Item item;
	Map<String, Item> itemMap = new HashMap<String, Item>();

	public void stockItems(List<String> productList) {

		for (String product : productList) {
			String key = product.substring(0, 2);
			String[] temp = product.substring(3).split(Pattern.quote("|"));
			itemMap.put(key, new Item(temp[1], temp[0], Double.parseDouble(temp[2]), Integer.parseInt(temp[3])));

		}

	}

	public Item getItem(String itemKey) {
		return itemMap.get(itemKey);
	}

	public Map<String, Item> getItemMap() {
		return itemMap;
	}

}
