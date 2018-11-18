package receipt;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Util {

	//public static ArrayList<String> exemptedItemList = new ArrayList<String>(); 
	
	// a fun ASCII title
	public static void printTitle()
	{
		System.out.println("   ___              _      __    _____                      __          \n" + 
				"  / _ \\___ _______ (_)__  / /_  / ___/__ ___  ___ _______ _/ /____  ____\n" + 
				" / , _/ -_) __/ -_) / _ \\/ __/ / (_ / -_) _ \\/ -_) __/ _ `/ __/ _ \\/ __/\n" + 
				"/_/|_|\\__/\\__/\\__/_/ .__/\\__/  \\___/\\__/_//_/\\__/_/  \\_,_/\\__/\\___/_/   \n" + 
				"                  /_/                                                   ");
	}
	
	// opening options for streaming
	public static void printInputOptions()
	{
		System.out.println("Welcome to Receipt Generator!"+'\n'+"How would you like to stream your input?");
		System.out.println("1) From console");
		System.out.println("2) From file");
	}
	
	// choose exemption file source
	public static void printCategorizationFileOptions()
	{
		System.out.println("How would you like to provide the exempted item list?");
		System.out.println("1) Use default file");
		System.out.println("2) Provide text file");
	}
	
	// main menu options
	public static void printOptions()
	{
		System.out.print('\n');
		System.out.println("1) Add item");
		System.out.println("2) Remove item");
		System.out.println("3) Print receipt");
		System.out.println("4) Display shopping cart");
		System.out.println("5) Quit and print receipt");
		System.out.print('\n');
	}
	
	// fill cart
	public static void fillCartFromFile(String fileName, Cart userCart)
	{
		System.out.println("File chosen: "+fileName);
		System.out.print('\n');
		File file = new File(fileName).getAbsoluteFile();
		try {

	        Scanner iscan = new Scanner(file);
	        while(iscan.hasNextLine()) 
	        {
	            String lineID = iscan.nextLine();
	            Item hold = Util.parse(lineID);
	            userCart.addItem(hold);
	        }
	        iscan.close();
	    } 
	    catch (FileNotFoundException e) {
	        System.out.println("Error: User chosen file not found, your cart is empty");
	    }
	}
	
	public static Item parse(String line)
	{
		int index = 0;
		int amount = 0;
		double originalPrice;
		boolean isImported = false;
		boolean isExempted = false;
		
		line = line.toLowerCase();
		ArrayList<String> itemType = new ArrayList<String>();
		ArrayList<String> productHold = new ArrayList<String>();

		// find amount of item
		if(Character.isDigit(line.charAt(0)) == true)
		{
			char a = line.charAt(0);
			amount = Character.getNumericValue(a);
			System.out.println("amount: "+amount);
		}
		
		
		String typeWord = "";
		
		// find all type words
		// go through the entire product description
		for(int i = 1; i < line.length(); i++)
		{
			char ch = line.charAt(i);
			
			if(Character.isWhitespace(ch) == true)
			{
				if(typeWord != "")
				{	
					// "at" indicates the end of the product description
					// next will be the price, so we exit
					if(typeWord.equals("at"))
					{
						index = i;
						break;
					}
					
					typeWord.trim();
					System.out.println("new type: "+typeWord);
					productHold.add(typeWord);
					itemType.add(typeWord);
					typeWord = "";
				}
			}
			else
			{
				typeWord += ch;
			}
		}
		
		// full product name
		
		String itemName = productHold.get(0);
		for(int i = 1; i < productHold.size(); i++)
		{
			itemName += " " + productHold.get(i);
		}
		itemName.trim();
		System.out.println("Full product name: "+itemName);
		
		// ---
		
		
		// price
		
		String priceCollection = "";
		for(int i = index; i < line.length(); i++)
		{
			char p = line.charAt(i);
			priceCollection += p;
		}
		priceCollection.trim();
		originalPrice = Double.parseDouble(priceCollection);
		
		System.out.println("cost per unit: $"+originalPrice);
		
		// ---
		
		
		// search words for key types (only imported at the moment)
		
		for(int i = 0; i < itemType.size(); i++)
		{
			String hold = itemType.get(i);
			
			if(hold.equals("imported"))
			{
				isImported = true;
				System.out.println("this is imported");
			}
		}
		
		// ---
		
		// run a binary search and see if this item is exempted
		// check across a default or given list of exempted items
		// books, food, medical supplies

		ArrayList<String> temp = Exemption.getIDList();
		String searchName = itemName;
		if(isImported == true)
		{
			searchName = searchName.replace("imported", "");
			searchName = searchName.trim();
		}
		System.out.println("search :"+searchName);
		isExempted = Search.binarySearch(temp, 0, temp.size(), searchName); 
		
		if(isExempted == true)
		{
			System.out.println("this is exempted");
		}
		
		//---
		
		Item parseItem = new Item(amount, itemName, isImported, isExempted, originalPrice);
		
		return parseItem;
		
	}

}
