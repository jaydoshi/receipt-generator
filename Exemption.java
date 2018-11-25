package receipt;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;

public class Exemption {

	public static HashSet<String> exemptionSet = new HashSet<String>();
	
	public Exemption()
	{
		
	}
	
	public static HashSet<String> getExemptionSet()
	{
		return exemptionSet;
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
	            if(lineID != null && !lineID.isEmpty())
	            {
		            lineID = lineID.trim();
		            lineID = lineID.toLowerCase();
		            exemptionSet.add(lineID);
	            }
	        }
	        fscan.close();
	    } 
	    catch (FileNotFoundException e) {
	        System.out.println("Error: Default file not found, the exemption list is empty");
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
	            if(lineID != null && !lineID.isEmpty())
	            {
		            lineID = lineID.trim();
		            lineID = lineID.toLowerCase();
		            exemptionSet.add(lineID);
	            }
	        }
	        fscan.close();
	    } 
	    catch (FileNotFoundException e) {
	        System.out.println("Error: User chosen file not found, using default exemptions");
	        exemptionConfig();
	    }
	}
}
