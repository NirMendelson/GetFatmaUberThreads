
public abstract class Vehicle {

	public boolean isAvailable;
	protected String type;

	public Vehicle() {
		this.isAvailable = true;
	}
	
	// Calculates the driving time for the given distance, for the motorcycle, taxi, and premium taxi classes
	public abstract double calculateDrivingTime(double distance);
	
	public String getType() {
		return this.type;
	}
}
