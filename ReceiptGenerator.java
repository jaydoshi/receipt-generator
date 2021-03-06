import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

//-------------
// Jay Doshi
// jaydoshi@usc.edu
// -------------

public class ReceiptGenerator {
	
	public static ArrayList<String> identificationList = new ArrayList<String>(); 
	public static Cart userCart = new Cart();
	

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
			exemptionConfig();
		}
		else if(categorizeChoice == 2)
		{
			// custom exemption configuration
			scan.nextLine();
			String fileNameFromUser = "";
			System.out.print("What is the name of the file with exempted items? ");			
			fileNameFromUser = scan.nextLine();
			exemptionConfig(fileNameFromUser);
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
			fillCartFromFile(cartFileFromUser);
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
		scan.close();
	}
	
	// getter for id list
	public static ArrayList<String> getIDList()
	{
		return identificationList; 
	}
	
	// fill cart
	public static void fillCartFromFile(String fileName)
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
	
	// default exemption config 
	public static void exemptionConfig()
	{
		System.out.println("Using default exemption file");
		System.out.print('\n');
		String fileName = "defaultID.txt";
		File file = new File(fileName).getAbsoluteFile();

	    try {

	        Scanner fscan = new Scanner(file);
	        while(fscan.hasNextLine()) 
	        {
	            String lineID = fscan.nextLine();
	            identificationList.add(lineID);
	        }
	        fscan.close();
	    } 
	    catch (FileNotFoundException e) {
	        e.printStackTrace();
	    }
	    
	    // The list needs to be sorted in order for binary search to work
	    identificationList.sort(null);
	    for(int i = 0; i < identificationList.size(); i++)
		{
			System.out.println(identificationList.get(i));
		}
	}
	
	// custom exemption
	public static void exemptionConfig(String fileName)
	{
		System.out.println("File chosen: "+fileName);
		System.out.print('\n');
		File file = new File(fileName).getAbsoluteFile();
		try {

			Scanner fscan = new Scanner(file);
	        while(fscan.hasNextLine()) 
	        {
	            String lineID = fscan.nextLine();
	            identificationList.add(lineID);
	        }
	        fscan.close();
	    } 
	    catch (FileNotFoundException e) {
	        System.out.println("Error: User chosen file not found, using default exemptions");
	        exemptionConfig();
	    }
		
		// The list needs to be sorted in order for binary search to work
	    identificationList.sort(null);
	    for(int i = 0; i < identificationList.size(); i++)
		{
			System.out.println(identificationList.get(i));
		}
	}
	
}








