
public class ServiceCall implements Comparable<ServiceCall>{

	private static int nextServiceID = 1;
	private int serviceID;
	private int customerID;
	private String serviceType;
	private String serviceArea;
	private double distance;

	// a constructor for the class
	public ServiceCall(int customerID, String serviceType, String serviceArea, double distance) {
		this.serviceID = nextServiceID;
		this.customerID = customerID;
		this.serviceType = serviceType;
		this.serviceArea = serviceArea;
		this.distance = distance;

		// Increment the nextServiceID for the next service call
		nextServiceID++;
	}

	// a getter for ID
	public int getID() {
		return this.serviceID;
	}

	// a getter for customer ID
	public int getCustomerID() {
		return this.customerID;
	}

	// a getter for distance 
	public double getDistance() {
		return this.distance;
	}

	// a getter for service area
	public String getServiceArea() {
		return this.serviceArea;
	}

	// a getter for service type
	public String getServiceType() {
		return this.serviceType;
	}

	// Uses Comparable
	public int compareTo(ServiceCall other) {
		if (this.getDistance() > other.getDistance()) {
			return 1;
		}
		else if (other.getDistance() > this.getDistance()) {
			return -1;
		} 
		else {
			return 0;
		}
	}
}