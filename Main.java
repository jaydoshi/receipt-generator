package receipt;

import java.util.Scanner;

//-------------
//Jay Doshi
//jaydoshi@usc.edu
//-------------

public class Main {
	
	public static Cart userCart = new Cart();
	public static Exemption e1 = new Exemption();

	public static void main(String[] args) {
		
		// initialize variables
        
		Scanner scan = new Scanner(System.in);
		
		// input stream choice
		Util.printTitle();
		
		int inputChoice = 0;
		boolean validInputChoice = false;
		while(validInputChoice == false)
		{
			Util.printInputOptions();
			inputChoice = scan.nextInt();

			if(inputChoice != 1 && inputChoice != 2)
			{
				System.out.println("Not a valid input choice");
			}
			else
			{
				validInputChoice = true;
			}
		}
		
		
		int categorizeChoice = 0;
		boolean validExemptionChoice = false;
		while(validExemptionChoice == false)
		{
			Util.printCategorizationFileOptions();
			categorizeChoice = scan.nextInt();
			
			if(categorizeChoice != 1 && categorizeChoice != 2)
			{
				System.out.println("Not a valid exemption file choice");
			}
			else
			{
				validExemptionChoice = true;
			}
		}
		
		// list set up
		if(categorizeChoice == 1)
		{
			// default exemption configuration
			e1.exemptionConfig();
		}
		else if(categorizeChoice == 2)
		{
			// custom exemption configuration
			scan.nextLine();
			String fileNameFromUser = "";
			System.out.print("What is the name of the file with exempted items? ");			
			fileNameFromUser = scan.nextLine();
			e1.exemptionConfig(fileNameFromUser);
		}
		
		// if there is a file ready, parse that then proceed
		if(inputChoice == 1)
		{
			// file stream
			if(categorizeChoice != 2)
			{
				scan.nextLine();
			}
			String cartFileFromUser = "";
			System.out.print("What is the name of the file with items to add to cart? ");		
			cartFileFromUser = scan.nextLine();
			userCart.fillCartFromFile(cartFileFromUser);
		}

		
		// console stream
		int userChoice = 0;
		while(userChoice != 5)
		{
			Util.printOptions();
			System.out.print("Next: ");
			userChoice = scan.nextInt();
			scan.nextLine(); // clear the buffer
				
			if(userChoice > 5 || userChoice < 1)
			{
				System.out.println("That is not a valid option");
			}
			else if(userChoice == 1)
			{
				// add item
				boolean successAdd = false;
				while(successAdd == false)
				{
					System.out.print("Type item amount, type, and price: ");
					String itemToAdd = scan.nextLine();
					itemToAdd = itemToAdd.trim();
					System.out.println(itemToAdd);
					
					try {
						
						Item inputItem = Util.parse(itemToAdd);
						userCart.addItem(inputItem);
						successAdd = true;
						
					} catch (CustomException ce) {
						System.out.println(ce.getMessage());
					}
				}
			}
			else if(userChoice == 2)
			{
				// remove item
				if(userCart.getSize() == 0)
				{
					System.out.println("No items to remove");
				}
				else
				{
					userCart.removeItem(scan);
				}
			}
			else if(userChoice == 3)
			{
				// print receipt
				boolean validSave = false;
				int saveChoice = 0;
				while(validSave == false)
				{
					System.out.println("Where would you like to save the receipt to? ");
					System.out.println("1) Default save file, receipt.txt");
					System.out.println("2) Custom save file");
					saveChoice = scan.nextInt();
					
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
					userCart.saveReceiptToFile();
				}
				else if(saveChoice == 2)
				{
					scan.nextLine();
					System.out.print("What is the name of the file to save to? ");		
					String saveFileName = scan.nextLine();
					userCart.saveReceiptToFile(saveFileName);
				}
				
			}
			else if(userChoice == 4)
			{
				// display shopping cart
				userCart.displayShoppingCart();
			}
		}
		
		System.out.println("Goodbye!");
		
		scan.close();		
	}
}
