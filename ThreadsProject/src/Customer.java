import java.util.Random;

public class Customer {
	private int ID;
	private int ratingToDriver;
	private double latestExpense;
	private double allExpenses;

	// a constructor for the class
	public Customer(int ID) {
		this.ID = ID;
	}

	// a getter for ID
	public int getID() {
		return this.ID;
	}

	// a method that decides the rating 
	public int giveRating() {
		Random random = new Random();
		this.ratingToDriver = random.nextInt(5) + 1;
		return this.ratingToDriver;
	}

	// a getter for rating to driver
	public int getRatingToDriver() {
		return this.ratingToDriver;
	}

	// a getter for latest expense
	public double getLatestExpense() {
		return this.latestExpense;
	}

	// a getter for all expenses
	public double getAllExpense() {
		return this.allExpenses;
	}

	// a method that pay
	public double pay(double time) {
		this.latestExpense = (2*time);
		this.allExpenses = this.allExpenses + this.latestExpense;
		return this.latestExpense;
	}
}