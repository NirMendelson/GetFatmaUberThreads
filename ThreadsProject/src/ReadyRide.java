
public class ReadyRide  {

	private int serviceID;
	private int customerID;
	private String serviceType;
	private String serviceArea;
	private double distance;
	private Vehicle vehicle;
	private int extraSalary;
	
	public ReadyRide(int serviceID, int customerID, String serviceType, String serviceArea, double distance, Vehicle vehicle, int extraSalary) {
		this.serviceID = serviceID;
		this.customerID = customerID;
		this.serviceType = serviceType;
		this.serviceArea = serviceArea;
		this.vehicle = vehicle;
		this.extraSalary = extraSalary;
	}
	
	public int getServiceID() {
		return this.serviceID;
	}
	
	public int getExtraSalary() {
		return this.extraSalary;
	}
	
	public int getCustomerID() {
		return this.customerID;
	}
	
	public String getServiceArea() {
		return this.serviceArea;
	}
	
	public String getServiceType() {
		return this.serviceType;
	}
	
	public Vehicle getVehicle() {
		return this.vehicle;
	}
	
	public double getDistance() {
		return this.distance;
	}
}
