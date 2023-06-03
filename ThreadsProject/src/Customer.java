import java.util.Random;

public class Customer {

	private int ID;
	private int ratingToDriver;
	private double latestExpense;
	private double allExpenses;
	
	public Customer(int ID) {
		this.ID = ID;
	}
	
	public int getID() {
		return this.ID;
	}
	
	public int giveRating() {
		Random random = new Random();
		this.ratingToDriver = random.nextInt(5) + 1;
		return this.ratingToDriver;
	}
	
	public int getRatingToDriver() {
		return this.ratingToDriver;
	}
	
	public double getLatestExpense() {
		return this.latestExpense;
	}
	
	public double getAllExpense() {
		return this.allExpenses;
	}
	
	
	public double pay(double time) {
		this.latestExpense = (2*time);
		this.allExpenses = this.allExpenses + this.latestExpense;
		return this.latestExpense;
	}
}
