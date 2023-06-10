import java.util.Random;

public class Taxi extends Vehicle  {

	// a constructor for the class
	public Taxi() {
		this.type = "Taxi";
	}

	// a method that calculates the driving time
	public double calculateDrivingTime(double distance) {
		Random random = new Random();
		double min = 0.5;
		double max = 0.7;
		int decimalPlaces = 3;
		double range = max - min;
		double scaledValue = random.nextDouble() * range + min;
		double P = Math.round(scaledValue * Math.pow(10, decimalPlaces)) / Math.pow(10, decimalPlaces);
		return (distance / (100 * P)) + 2;  
	}

}