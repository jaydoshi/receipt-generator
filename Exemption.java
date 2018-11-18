package receipt;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Exemption {

	public static ArrayList<String> identificationList = new ArrayList<String>();
	
	public Exemption()
	{
		
	}
	
	// getter for id list
	public static ArrayList<String> getIDList()
	{
		return identificationList; 
	}
	
	// default exemption config 
	public void exemptionConfig()
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
	        System.out.println("Error: Default file not found, the exemption list is empty");
	    }
	    
	    // The list needs to be sorted in order for binary search to work
	    identificationList.sort(null);
	    for(int i = 0; i < identificationList.size(); i++)
		{
			System.out.println(identificationList.get(i));
		}
	}	
	
	// custom exemption
	public void exemptionConfig(String fileName)
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
