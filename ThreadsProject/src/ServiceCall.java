
public class ServiceCall implements Comparable<ServiceCall>{
	
	private static int nextServiceID = 1;
	private int serviceID;
	private int customerID;
	private String serviceType;
	private String serviceArea;
	private double distance;
	
	public ServiceCall(int customerID, String serviceType, String serviceArea, double distance) {
		this.serviceID = nextServiceID;
		this.customerID = customerID;
		this.serviceType = serviceType;
		this.serviceArea = serviceArea;
		this.distance = distance;
		
		// Increment the nextServiceID for the next service call
		nextServiceID++;
	}
	
	public int getID() {
		return this.serviceID;
	}
	
	public int getCustomerID() {
		return this.customerID;
	}
	
	public double getDistance() {
		return this.distance;
	}
	
	public String getServiceArea() {
		return this.serviceArea;
	}
	
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