
public class ReadyRide  {

	private int serviceID;
	private int customerID;
	private String serviceType;
	private String serviceArea;
	private double distance;
	private Vehicle vehicle;
	private int extraSalary;

	// a constructor for the class
	public ReadyRide(int serviceID, int customerID, String serviceType, String serviceArea, double distance, Vehicle vehicle, int extraSalary) {
		this.serviceID = serviceID;
		this.customerID = customerID;
		this.serviceType = serviceType;
		this.serviceArea = serviceArea;
		this.vehicle = vehicle;
		this.extraSalary = extraSalary;
	}

	// a getter for service ID
	public int getServiceID() {
		return this.serviceID;
	}

	// a getter for extra salary
	public int getExtraSalary() {
		return this.extraSalary;
	}

	// a getter for customer ID
	public int getCustomerID() {
		return this.customerID;
	}

	// a getter for service area
	public String getServiceArea() {
		return this.serviceArea;
	}

	// a getter for service type
	public String getServiceType() {
		return this.serviceType;
	}

	// a getter for vehicle
	public Vehicle getVehicle() {
		return this.vehicle;
	}

	// a getter for distance
	public double getDistance() {
		return this.distance;
	}
}