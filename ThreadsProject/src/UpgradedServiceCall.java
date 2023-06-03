
public class UpgradedServiceCall {

	private int serviceID;
	private int customerID;
	private String serviceType;
	private String serviceArea;
	private double distance;
	private Vehicle vehicle;
	private int fee;

	public UpgradedServiceCall(int serviceID, int customerID, String serviceType, String serviceArea, double distance, Vehicle vehicle, int fee) {
		this.serviceID = serviceID;
		this.customerID = customerID;
		this.serviceType = serviceType;
		this.serviceArea = serviceArea;
		this.vehicle = vehicle;
		this.fee = fee;
	}
	
	public int getID() {
		return this.serviceID;
	}
	
	public int getFee() {
		return this.fee;
	}
	
	public int getCustomerID() {
		return this.customerID;
	}
	public String getServiceType() {
		return this.serviceType;
	}
	
	public String getServiceArea() {
		return this.serviceArea;
	}
	
	public double getDistance() {
		return this.distance;
	}
	
	public Vehicle getVehicle() {
		return this.vehicle;
	}
	
}
