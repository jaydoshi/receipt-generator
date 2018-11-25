package receipt;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class Cart {
    
	double cartTotal;
	double salesTaxesOfCart;
	public ArrayList<Item> shoppingCart = new ArrayList<Item>(); 
	
	public Cart()
	{
		this.cartTotal = 0.0;
		this.salesTaxesOfCart = 0.0;
	}
	
	// manages from main
	public void addChoice(Scanner scan1)
	{
		boolean successAdd = false;
		while(successAdd == false)
		{
			System.out.print("Type item amount, type, and price: ");
			String itemToAdd = scan1.nextLine();
			itemToAdd = itemToAdd.trim();
			
			try {
				
				Item inputItem = Util.parse(itemToAdd);
				this.addItem(inputItem);
				successAdd = true;
				System.out.println("Successfully added item to cart");
				
			} catch (CustomException ce) {
				System.out.println(ce.getMessage());
			}
		}
	}
	
	// adds a new item to the shopping cart
	public void addItem(Item parseItem)
	{
		// if an identical product with the same full name & price exists, don't add new object
		// instead just bump up the amount of the item
		
		boolean doesExistAlready = false;
		for(int i = 0; i < this.shoppingCart.size(); i++)
		{
			String name1 = this.shoppingCart.get(i).getFullProductName();
			double price1 = this.shoppingCart.get(i).getOriginalPrice();
			String name2 = parseItem.getFullProductName();
			double price2 = parseItem.getOriginalPrice();
			
			if(name1.equals(name2) && price1 == price2)
			{
				doesExistAlready = true;
				this.shoppingCart.get(i).increaseAmount(parseItem.getAmount());
				parseItem = null;
			}
		}
		
		if(doesExistAlready == false)
		{
			shoppingCart.add(parseItem);
		}
	}
	
	// remove an item from the shopping cart
	public void removeItem(Scanner rscan)
	{
		boolean isChosen = false;
		int removalChoice = 0;
		while(isChosen == false)
		{
			this.displayShoppingCartWithNumbers();
			System.out.print("Which of these would you like to remove?: ");
			removalChoice = rscan.nextInt();
			if(removalChoice > this.shoppingCart.size() || removalChoice < 1)
			{
				System.out.println("Error: Invalid choice, please choose again");
			}
			else
			{
				isChosen = true;
			}
		}
		
		Item temp = shoppingCart.get(removalChoice-1);
		
		boolean validAmount = false;
		int amountToRemove = -1;
		while(validAmount == false)
		{
			System.out.print('\n');
			System.out.println("You currently have "+temp.getAmount()+" of "+temp.getFullProductName());
			System.out.println("You may input between 0 and "+temp.getAmount());
			System.out.print("What amount of "+temp.getFullProductName()+" do you want to remove? ");
			amountToRemove = rscan.nextInt();
			if(amountToRemove < 0 || amountToRemove > temp.getAmount())
			{
				System.out.println("Error: Invalid amount, please choose again");
			}
			else
			{
				validAmount = true;
			}
		}
		
		System.out.println("Removing "+amountToRemove+" of "+temp.getAmount()+" of your "+temp.getFullProductName());
		
		if(amountToRemove == temp.getAmount())
		{
			this.shoppingCart.remove(removalChoice-1);
		}
		else
		{
			temp.decreaseAmount(amountToRemove);
		}
	}
	
	// clear the whole cart
	public void removeAllItems(Scanner rscan)
	{
		boolean isChosen = false;
		int rChoice = 0;
		while(isChosen == false)
		{
			// double check
			System.out.println("Are you sure you want to clear your cart?");
			System.out.println("1) Yes");
			System.out.println("2) No");
			rChoice = rscan.nextInt();
			if(rChoice != 1 && rChoice != 2)
			{
				System.out.println("Error: Invalid choice, please choose again");
			}
			else
			{
				isChosen = true;
			}
		}
		
		if(rChoice == 1)
		{
			shoppingCart.clear();
			System.out.println("Your cart is now empty");
		}
		else
		{
			System.out.println("Your cart is unchanged");
		}
	}
	
	// fill cart
	public void fillCartFromFile(String fileName)
	{
		System.out.println("File chosen: "+fileName);
		System.out.print('\n');
		File file = new File(fileName).getAbsoluteFile();
		try {

	        Scanner iscan = new Scanner(file);
	        int lineNumberOfFile = 1;
	        while(iscan.hasNextLine()) 
	        {
	            String lineID = iscan.nextLine();
	            
	            try {
	            	Item hold = Util.parse(lineID);
	            	this.addItem(hold);
	            } catch (CustomException ce) {
					System.out.println(ce.getMessage());
					System.out.println("Line "+lineNumberOfFile+" was skipped because it was malformed");
	            }
	            
	            lineNumberOfFile++;
	        }
	        iscan.close();
	    } 
	    catch (FileNotFoundException e) {
	        System.out.println("Error: User chosen file not found, your cart is empty");
	    }
	}
	
	// gets cart size, aka the number of different things NOT total amount (see below for that method)
	public int getSize()
	{
		return this.shoppingCart.size();
	}
	
	
	// gets total number of items in the cart
	public int getTotalNumberOfItems()
	{
		int count = 0;
		for(int i = 0; i < this.shoppingCart.size(); i++)
		{
			count += shoppingCart.get(i).getAmount();
		}
		return count;
	}
	
	// display some miscellaneous stats about the cart
	public void displayShoppingCartStats()
	{
        System.out.println("Your cart:");
        if(this.getSize() == 0)
        {
        	System.out.println("You have no items");
        	return;
        }
        
        if(this.getSize() == 1)
        {
            System.out.println("You have 1 unique item");
        }
        else if(this.getSize() > 1)
        {
            System.out.println("You have "+this.getSize()+" unique items");
        }
        
        int count = this.getTotalNumberOfItems();
        
        if(count == 1)
        {
            System.out.println("You have 1 total item");
        }
        else
        {
            System.out.println("You have "+this.getTotalNumberOfItems()+" total items");
        }
	}
	
	// gets cart total cost
	public double getCartTotal()
	{
		return this.cartTotal;
	}
	
	
	// gets sales tax only of cart
	public double getTaxTotal()
	{
		return this.salesTaxesOfCart;
	}
	
	
	// sums the total prices of all items in the cart
	// also sums the total sales tax of all items
	public void sumCartTotal()
	{
		double totalHold = 0.0;
		double totalWithoutTax = 0.0;
		
		// for each item in the cart, add up the costs
		for(int i = 0; i < this.shoppingCart.size(); i++)
		{
			// cost
			int multiplier = this.shoppingCart.get(i).getAmount();
			double hold = (double)multiplier * this.shoppingCart.get(i).getTaxedPrice();
			totalWithoutTax += (double)multiplier * this.shoppingCart.get(i).getOriginalPrice();
			double costOfItem = Math.round(hold*100.0)/100.0;
			totalHold += costOfItem;
		}

		this.cartTotal = Math.round(totalHold*100.0)/100.0;
		this.salesTaxesOfCart = Math.round((this.cartTotal - totalWithoutTax)*100.0)/100.0;
	}

	
	// display the current items in the shopping cart, sales tax, and total
	public void displayShoppingCart()
	{
		this.sumCartTotal();
		System.out.print('\n');
		for(int i = 0; i < this.shoppingCart.size(); i++)
		{
			System.out.println(this.shoppingCart.get(i));
		}
	    DecimalFormat decim = new DecimalFormat("0.00");

		System.out.println("Sales Taxes "+decim.format(this.salesTaxesOfCart));
		System.out.println("Total "+decim.format(this.cartTotal));
	}
	
	
	// display the current items in the shopping cart, sales tax, and total
	// includes numbering for user selection
	public void displayShoppingCartWithNumbers()
	{
		this.sumCartTotal();
		System.out.print('\n');
		for(int i = 0; i < this.shoppingCart.size(); i++)
		{
			System.out.println(i+1+") "+this.shoppingCart.get(i));
		}
	    DecimalFormat decim = new DecimalFormat("0.00");

		System.out.println("Sales Taxes "+decim.format(this.salesTaxesOfCart));
		System.out.println("Total "+decim.format(this.cartTotal));
		System.out.print('\n');
	}
	
	// manages from main for saving
	public void saveChoice(Scanner scan1)
	{
		boolean validSave = false;
		int saveChoice = 0;
		while(validSave == false)
		{
			System.out.println("Where would you like to save the receipt to? ");
			System.out.println("1) Default save file, receipt.txt");
			System.out.println("2) Custom save file");
			saveChoice = scan1.nextInt();
			
			if(saveChoice == 1 || saveChoice == 2)
			{
				validSave = true;
			}
			else
			{
				System.out.println("That is not a valid option");
			}
		}
		
		if(saveChoice == 1)
		{
			this.saveReceiptToFile();
		}
		else if(saveChoice == 2)
		{
			scan1.nextLine();
			System.out.print("What is the name of the file to save to? ");		
			String saveFileName = scan1.nextLine();
			this.saveReceiptToFile(saveFileName);
		}
	}
	
	// default save
	public void saveReceiptToFile()
	{
		System.out.println("Saving to default file, receipt.txt");
		System.out.print('\n');
		String fileName = "receipt.txt";
		File file = new File(fileName).getAbsoluteFile();
	    try {
	    	
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			
			// last summation before saving
			this.sumCartTotal();
			for(int i = 0; i < this.shoppingCart.size(); i++)
			{
				writer.write(this.shoppingCart.get(i).toString()+'\n');
			}
		    DecimalFormat decim = new DecimalFormat("0.00");

			writer.write("Sales Taxes: "+decim.format(this.salesTaxesOfCart)+'\n');
			writer.write("Total: "+decim.format(this.cartTotal));
			writer.close();
		} catch (IOException e) {
			System.out.println("Error: Could not locate default file");
		}
	    
		System.out.println("Finished saving receipt");
	}

	
	// overload to save at a custom file
	public void saveReceiptToFile(String saveFileName)
	{
		System.out.println("Saving to custom file, "+saveFileName);
		File file = new File(saveFileName).getAbsoluteFile();
	    try {
	    	
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			
			// last summation before saving
			this.sumCartTotal();
			for(int i = 0; i < this.shoppingCart.size(); i++)
			{
				writer.write(this.shoppingCart.get(i).toString()+'\n');
			}
		    DecimalFormat decim = new DecimalFormat("0.00");

			writer.write("Sales Taxes: "+decim.format(this.salesTaxesOfCart)+'\n');
			writer.write("Total: "+decim.format(this.cartTotal));
			writer.close();
		} catch (IOException e) {
			System.out.println("Error: Could not locate user receipt file");
		}
	    
		System.out.println("Finished saving receipt");
	}
}
