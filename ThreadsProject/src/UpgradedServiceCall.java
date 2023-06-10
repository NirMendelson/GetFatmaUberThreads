
public class UpgradedServiceCall {

	private int serviceID;
	private int customerID;
	private String serviceType;
	private String serviceArea;
	private double distance;
	private Vehicle vehicle;
	private int fee;

	// a constructor for the class
	public UpgradedServiceCall(int serviceID, int customerID, String serviceType, String serviceArea, double distance, Vehicle vehicle, int fee) {
		this.serviceID = serviceID;
		this.customerID = customerID;
		this.serviceType = serviceType;
		this.serviceArea = serviceArea;
		this.vehicle = vehicle;
		this.fee = fee;
	}

	// a getter for ID
	public int getID() {
		return this.serviceID;
	}

	// a getter for fee
	public int getFee() {
		return this.fee;
	}

	// a getter for customer ID
	public int getCustomerID() {
		return this.customerID;
	}

	// a getter for service type
	public String getServiceType() {
		return this.serviceType;
	}

	// a getter for service area
	public String getServiceArea() {
		return this.serviceArea;
	}

	// a getter for distance
	public double getDistance() {
		return this.distance;
	}

	// a getter for vehicle
	public Vehicle getVehicle() {
		return this.vehicle;
	}

}