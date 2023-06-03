import java.util.ArrayList;
import java.util.Vector;

public class InformationSystem {
	private ArrayList<Customer> customersList;
	private ArrayList<Vehicle> vehicleList;
	private ArrayList<UpgradedServiceCall> DeliveryServiceCall;
	private ArrayList<UpgradedServiceCall> TaxiServiceCall; 
	private double totalSchedulersPayment;
	private double totalCarOfficersPayment;
	private double totalDriversPayment;
	private double totalClerksPayment;
	private int numOfDeliveryMissions;
	private int numOfTaxiMissions;
	private int numOfMissionsInTelAviv;
	private int numOfMissionsInJerusalem;
	private int numOfDrivers;
	private boolean isDayOver;
	private double carOfficersWorkingTime;

	
	public InformationSystem() {
		this.customersList = new ArrayList<Customer>();
		this.vehicleList = new ArrayList<Vehicle>();
		this.DeliveryServiceCall = new ArrayList<UpgradedServiceCall>();
		this.TaxiServiceCall = new ArrayList<UpgradedServiceCall>();
		this.isDayOver = false;
	    for (int i = 100; i < 190; i++) {
	    	Customer customer = new Customer(i);
	        customersList.add(customer);
	    }
	    for (int i = 0; i < 5; i++) {
	    	Motorcycle motorcycle = new Motorcycle();
	    	Taxi taxi = new Taxi();
	    	vehicleList.add(motorcycle);
	    	vehicleList.add(taxi);
	    }
	}
	
	
	public synchronized ArrayList<UpgradedServiceCall> getDeliveryList() {
		return this.DeliveryServiceCall;
	}
	
	public void setIsDayOver(boolean isOver) {
		this.isDayOver = isOver;
	}
	
	public boolean getIsDayOver() {
		return this.isDayOver;
	}
	
	public synchronized boolean containsCustomer(int ID) {
        for (int i = 0; i < customersList.size(); i++) {
        	if (customersList.get(i).getID() == ID) {
        		return true;
        	}
        }
		return false;
    }
	
	public synchronized void addToVehicleList(Vehicle vehicle) {
		this.vehicleList.add(vehicle);
		this.notifyAll();
	}
    
    
    public synchronized UpgradedServiceCall extractFirstFromTaxi() {
    	while (TaxiServiceCall.isEmpty()) {
    		try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
    	}
        return TaxiServiceCall.remove(0);
    }
    
    public synchronized UpgradedServiceCall extractFirstFromDelivery() {
    	while (DeliveryServiceCall.isEmpty()) {
    		try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
    	}
        return DeliveryServiceCall.remove(0);
    }
    
    
	
	public synchronized void addCustomer(int ID) {
		Customer customer = new Customer(ID);
		customersList.add(customer);
	}
	
	public synchronized void addDeliveryServiceCall(UpgradedServiceCall upgradedServiceCall) {
		this.DeliveryServiceCall.add(upgradedServiceCall);
		this.notifyAll();
	}
	
	public synchronized void addTaxiServiceCall(UpgradedServiceCall upgradedServiceCall) {
		this.TaxiServiceCall.add(upgradedServiceCall);
		this.notifyAll();
	}
	
	public ArrayList<Customer> getCustomerList() {
		return this.customersList;
	}
	
	public ArrayList<Vehicle> getVehicleList() {
		return this.vehicleList;
	}
	
	public synchronized Vehicle extractVehicle(int i) {
		while (vehicleList.isEmpty()) {
    		try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
    	}
		return this.vehicleList.remove(i);
	}
	
	public void removeVehicle(Vehicle vehicle) {
		this.vehicleList.remove(vehicle);
	}
	
	public void addSalaryToDriver(double payment) {
		totalDriversPayment += payment;
	}
	
	public void addSalaryToCarOfficer(double payment) {
		totalCarOfficersPayment += payment;
	}
	
	public void addSalaryToScheduler(double payment) {
		totalSchedulersPayment += payment;
	}
	
	public void addSalaryToClerk(double payment) {
		totalClerksPayment += payment;
	}
	
	public void addDeliveryMission() {
		numOfDeliveryMissions++;
	}
	
	public void addTaxiMission() {
		numOfTaxiMissions++;
	}
	
	public void addMissionInTelAviv() {
		numOfMissionsInTelAviv++;
	}
	
	public void addMissionInJerusalem() {
		numOfMissionsInJerusalem++;
	} 
	
	public double getTotalSchedulersPayment() {
		return this.totalSchedulersPayment;
	}
	
	public double getTotalCarOfficersPayment() {
		return this.totalCarOfficersPayment;
	}
	
	public double getTotalDriverPayment() {
		return this.totalDriversPayment;
	}
	
	public double getTotalClerksPayment() {
		return this.totalClerksPayment;
	}
	
	// need to change according to number of drivers
	public double getAveragePayment() {
		return ((this.totalClerksPayment + this.totalCarOfficersPayment + this.totalDriversPayment + this.totalSchedulersPayment)/(8+this.numOfDrivers));
	}
	
	public int getNumOfTaxiMissions() {
		return this.numOfTaxiMissions;
	}
	
	public int getNumOfDeliveryMissions() {
		return this.numOfDeliveryMissions;
	}
	
	public String getMostPopularArea() {
		if (this.numOfMissionsInJerusalem > this.numOfMissionsInTelAviv) {
			return "Jerusalem";
		}
		if (this.numOfMissionsInJerusalem < this.numOfMissionsInTelAviv) {
			return "Tel Aviv";
		}
		else {
			return "Jerusalem and Tel Aviv are equally popular.";
		}
	}
	
	public void setNumOfDrivers(int drivers) {
		this.numOfDrivers = drivers;
	}
	
	public void setCarOfficersWorkingTime(double time) {
		this.carOfficersWorkingTime = time;
	}
	
	public double getCarOfficersWorkingTime() {
		return this.carOfficersWorkingTime;
	}
	

	
	



	
	
}
