import java.text.DecimalFormat;

public class Item {
	
	String itemName;
	boolean isImported;
	boolean isExempted;
	double originalPrice;
	double taxedPrice;
	double taxRateOfItem;
	int amount;
	
	public Item(int amount, String itemName, boolean isImported, boolean isExempted, double originalPrice)
	{
		this.amount = amount;
		this.itemName = itemName;
		this.isImported = isImported;
		this.isExempted = isExempted;
		this.originalPrice = originalPrice;
		this.taxRateOfItem = 0.0;
		
		if(this.isExempted != true)
		{
			this.taxRateOfItem += 0.1;
		}
		
		if(this.isImported == true)
		{
			this.taxRateOfItem += 0.05;
		}
		
		double num = this.originalPrice * this.taxRateOfItem;
		num = Math.ceil(num * 20) / 20;
		this.taxedPrice = num + originalPrice;
	}
	
	@Override
	public String toString() 
	{
	    DecimalFormat decim = new DecimalFormat("0.00");
		String hold = this.amount+" "+this.itemName+": "+decim.format(this.taxedPrice);
		return hold;
	}
	
	public double getOriginalPrice()
	{
		return originalPrice;
	}
	
	public double getTaxedPrice()
	{
		return taxedPrice;
	}
	
	public int getAmount()
	{
		return amount;
	}
	
	public String getFullProductName()
	{
		return itemName;
	}
	
	public void increaseAmount(int num)
	{
		this.amount = this.getAmount() + num;
	}
	
	public void decreaseAmount(int num)
	{
		this.amount = this.getAmount() - num;
	}
	
	
}