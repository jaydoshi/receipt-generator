package receipt;

import java.util.ArrayList;
import java.util.HashSet;

public class Util {

	
	// a fun ASCII title
	public static void printTitle()
	{
		System.out.println("   ___              _      __    _____                      __          \n" + 
				"  / _ \\___ _______ (_)__  / /_  / ___/__ ___  ___ _______ _/ /____  ____\n" + 
				" / , _/ -_) __/ -_) / _ \\/ __/ / (_ / -_) _ \\/ -_) __/ _ `/ __/ _ \\/ __/\n" + 
				"/_/|_|\\__/\\__/\\__/_/ .__/\\__/  \\___/\\__/_//_/\\__/_/  \\_,_/\\__/\\___/_/   \n" + 
				"                  /_/                                                   ");
		System.out.println("Welcome to Receipt Generator!");
	}
	
	// opening options for streaming
	public static void printInputOptions()
	{
		System.out.println("Do you have a premade shopping cart file?");
		System.out.println("1) Yes");
		System.out.println("2) No");
	}
	
	// choose exemption file source
	public static void printCategorizationFileOptions()
	{
		System.out.println("How would you like to configure the exempted items list?");
		System.out.println("1) Default file");
		System.out.println("2) Provide custom file");
	}
	
	// main menu options
	public static void printOptions()
	{
		System.out.print('\n');
		System.out.println("1) Add item");
		System.out.println("2) Remove item");
		System.out.println("3) Clear cart");
		System.out.println("4) Print receipt");
		System.out.println("5) Display shopping cart");
		System.out.println("6) Display shopping cart stats");
		System.out.println("7) Quit and print receipt");
		System.out.print('\n');
	}
	
	public static void printExitOptions()
	{
		System.out.println("Before exiting, would you like to save your receipt?");
		System.out.println("1) Yes");
		System.out.println("2) No");
	}
	
	public static Item parse(String line) throws CustomException
	{
		int index = 0;
		int amount = 0;
		double originalPrice = 0;
		boolean isImported = false;
		boolean isExempted = false;
		
		line = line.toLowerCase();
		
		ArrayList<String> itemType = new ArrayList<String>(); // to find key types
		ArrayList<String> productHold = new ArrayList<String>(); // for product name

		// find amount of item
		
		boolean amountFound = false;
		String amountString = "";
		int count = 0;
		int endAmountIndex = 0;
		while(amountFound == false)
		{
			if(count >= line.length())
			{
				// catches 1 word entries with no other info
				// e.g user input "apple" has no info
				throw new CustomException("Error: Line item was not detailed enough");
			}
			char ch = line.charAt(count);
			if(Character.isWhitespace(ch) == true)
			{
				if(amountString != "")
				{
					try {
						
						amount = Integer.parseInt(amountString);
						
					} catch(NumberFormatException nfe) {
						System.out.println("Error: Could not parse into integer amount");
						throw new CustomException("Error: Malformed amount, please try again");
					}
					
					endAmountIndex = count;
					amountFound = true;
				}
			}
			else
			{
				amountString += ch;
			}
			count++;
		}
		
		
		String typeWord = "";
		
		// find all type words
		// go through the entire product description
		
		boolean foundAtDivider = false;
		for(int i = endAmountIndex; i < line.length(); i++)
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
						foundAtDivider = true;
						break;
					}
					
					typeWord.trim();
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
		
		if(foundAtDivider == false)
		{
			throw new CustomException("Error: \"at\" was not found, please include \"at\" ");
		}
		
		// full product name is itemName (including "imported")
		
		if(productHold.isEmpty())
		{
			throw new CustomException("Error: No product name was given");
		}
		
		String itemName = productHold.get(0); // exception earlier prevents this being null
		for(int i = 1; i < productHold.size(); i++)
		{
			itemName += " " + productHold.get(i);
		}
		itemName.trim();
		
		// ---
		
		
		// price

		String priceCollection = "";
		for(int i = index; i < line.length(); i++)
		{
			char p = line.charAt(i);
			priceCollection += p;
		}
		priceCollection.trim();
		try {
			originalPrice = Double.parseDouble(priceCollection);
		} catch(NullPointerException npe) {
			System.out.println("Error: No price was found");
			throw new CustomException("Error: Price not found, please try again");
		} catch(NumberFormatException nfe) {
			System.out.println("Error: Could not parse into double price");
			throw new CustomException("Error: Malformed price, please try again");
		}
				
		// ---
		
		
		// search words for key types (only imported at the moment)
		
		for(int i = 0; i < itemType.size(); i++)
		{
			String hold = itemType.get(i);
			
			if(hold.equals("imported"))
			{
				isImported = true;
			}
		}
		
		// ---
		
		
		// Quick O(1) lookup on the HashSet to see if the product is exempted
		// cut out the key words before searching set (the exemption set doesn't need to be twice
		// as big just to have the prefix imported- in front of everything)
		
		HashSet<String> exemptionTemp = Exemption.getExemptionSet();
		String searchName = itemName;
		
		if(isImported == true)
		{
			searchName = searchName.replace("imported", "");
			searchName = searchName.trim();
		}
		
		if(exemptionTemp.contains(searchName))
		{
			isExempted = true;
		}
		
		//---
		
		// build and return
		
		Item parseItem = new Item(amount, itemName, isImported, isExempted, originalPrice);
		
		return parseItem;
		
	}

}
