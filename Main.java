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
		Util.printInputOptions();
		int inputChoice = scan.nextInt();
		
		Util.printCategorizationFileOptions();
		int categorizeChoice = scan.nextInt();
		
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
		if(inputChoice == 2)
		{
			// file
			if(categorizeChoice != 2)
			{
				scan.nextLine();
			}
			String cartFileFromUser = "";
			System.out.print("What is the name of the file with items to add to cart? ");		
			cartFileFromUser = scan.nextLine();
			Util.fillCartFromFile(cartFileFromUser, userCart);
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
				System.out.print("Type item amount, type, and price: ");
				String itemToAdd = scan.nextLine();
				itemToAdd = itemToAdd.trim();
				System.out.println(itemToAdd);
				Item inputItem = Util.parse(itemToAdd);
				userCart.addItem(inputItem);
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
				userCart.saveReceiptToFile();
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
