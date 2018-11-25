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
		
		// if there is a premade cart, parse that then proceed
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
		while(userChoice != 7)
		{
			Util.printOptions();
			System.out.print("Next: ");
			userChoice = scan.nextInt();
			scan.nextLine(); // clear the buffer
				
			if(userChoice > 7 || userChoice < 1)
			{
				System.out.println("That is not a valid option");
			}
			else if(userChoice == 1)
			{
				// add item
				userCart.addChoice(scan);
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
				// clear cart
				userCart.removeAllItems(scan);
				
			}
			else if(userChoice == 4)
			{
				// print receipt
				userCart.saveChoice(scan);
				
			}
			else if(userChoice == 5)
			{
				// display shopping cart
				userCart.displayShoppingCart();
			}
			else if(userChoice == 6)
			{
				// display stats
				userCart.displayShoppingCartStats();
			}
		}
		
		int exitChoice = 0;
		boolean validExitChoice = false;
		while(validExitChoice == false)
		{
			Util.printExitOptions();
			exitChoice = scan.nextInt();

			if(exitChoice != 1 && exitChoice != 2)
			{
				System.out.println("Not a valid input choice");
			}
			else
			{
				validExitChoice = true;
			}
		}
		
		if(exitChoice == 1)
		{
			userCart.saveChoice(scan);
		}


		System.out.println("Goodbye!");
		
		scan.close();		
	}
}
