package receipt;

import java.util.ArrayList;

public class Search {
	
	public static boolean binarySearch(ArrayList<String> arrayID, int left, int right, String x) 
	{
		if(right >= left)
		{
			int mid = left + (right - left) / 2;
			
			if(arrayID.get(mid).equals(x))
			{
				return true;
			}
			
			if(arrayID.get(mid).compareTo(x) > 0)
			{
				return binarySearch(arrayID, left, mid-1, x); 
			}
			
			return binarySearch(arrayID, mid+1, right, x); 
		}
		
		return false;
	}
}
