import java.util.Random;

public class Motorcycle extends Vehicle{

	private int maxSpeed;
	
	public Motorcycle() {
		this.type = "Delivery";
		this.maxSpeed = 100;
	}

	
	public double calculateDrivingTime(double distance) {
		Random random = new Random();
		double min = 0.6;
		double max = 0.8;
		int decimalPlaces = 3;
		double range = max - min;
		double scaledValue = random.nextDouble() * range + min;
		double P = Math.round(scaledValue * Math.pow(10, decimalPlaces)) / Math.pow(10, decimalPlaces);
		return (distance / (this.maxSpeed * P));
	}
}
