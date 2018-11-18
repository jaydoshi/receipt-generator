import java.util.ArrayList;

public class Cart {
    
    
	double cartTotal;
	double salesTaxesOfCart;
	public ArrayList<Item> shoppingCart = new ArrayList<Item>(); 
	
	public Cart()
	{
		this.cartTotal = 0.0;
		this.salesTaxesOfCart = 0.0;
	}
	
	public void addItem(Item parseItem)
	{
		shoppingCart.add(parseItem);
	}
	
	public void removeItem()
	{
		this.displayShoppingCartWithNumbers();
		System.out.print("Which of these would you like to remove?: ");
		
	}
	
	public double getCartTotal()
	{
		return this.cartTotal;
	}
	
	public double getTaxTotal()
	{
		return this.salesTaxesOfCart;
	}
	
	public void sumCartTotal()
	{
		double totalHold = 0.0;
		
		// for each item in the cart, add up the costs
		for(int i = 0; i < this.shoppingCart.size(); i++)
		{
			int multiplier = this.shoppingCart.get(i).getAmount();
			double costOfItem = multiplier * this.shoppingCart.get(i).getTaxedPrice();
			System.out.println("cost "+costOfItem);
			totalHold += costOfItem;
		}

		this.cartTotal = totalHold;
	}
	
	public void displayShoppingCart()
	{
		this.sumCartTotal();
		for(int i = 0; i < this.shoppingCart.size(); i++)
		{
			System.out.println(this.shoppingCart.get(i));
		}
		System.out.println("Sales taxes "+this.salesTaxesOfCart);
		System.out.println("Total "+this.cartTotal);
	}
	
	public void displayShoppingCartWithNumbers()
	{
		this.sumCartTotal();
		for(int i = 0; i < this.shoppingCart.size(); i++)
		{
			System.out.println(i+1+") "+this.shoppingCart.get(i));
		}
		System.out.println("Sales taxes "+this.salesTaxesOfCart);
		System.out.println("Total "+this.cartTotal);
	}

}
