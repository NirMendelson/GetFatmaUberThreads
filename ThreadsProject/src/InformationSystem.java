import java.util.ArrayList;
import java.util.Vector;

public class InformationSystem {
	private ArrayList<Customer> customersList;
	private ArrayList<UpgradedServiceCall> deliveryServiceCall;
	private ArrayList<UpgradedServiceCall> taxiServiceCall; 
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
	private int inLineForTaxi;
	private int inLineForDelivery;

	
	public InformationSystem() {
		this.customersList = new ArrayList<Customer>();
		this.deliveryServiceCall = new ArrayList<UpgradedServiceCall>();
		this.taxiServiceCall = new ArrayList<UpgradedServiceCall>();
		this.isDayOver = false;
		this.inLineForTaxi = 0;
		this.inLineForDelivery = 0;
	    for (int i = 100; i < 190; i++) {
	    	Customer customer = new Customer(i);
	        customersList.add(customer);
	    }
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
	
	public synchronized void addCustomer(int ID) {
		Customer customer = new Customer(ID);
		customersList.add(customer);
	}
	
	
	public ArrayList<Customer> getCustomerList() {
		return this.customersList;
	}
	
	public synchronized int getTaxiServiceCallSize() {
		return this.taxiServiceCall.size();
	}
	
	public synchronized int getDeliveryServiceCallSize() {
		return this.deliveryServiceCall.size();
	}
	
	public synchronized int getLineForTaxi() {
		return this.inLineForTaxi;
	}
	
	public synchronized int getLineForDelivery() {
		return this.inLineForDelivery;
	}
	
	public synchronized int addToLineForTaxi() {
		this.inLineForTaxi++;
		return this.inLineForTaxi;

	}
	
	public synchronized int addToLineForDelivery() {
		this.inLineForDelivery++;
		return this.inLineForDelivery;
	}
	
	public synchronized int subtractFromLineForTaxi() {
		this.inLineForTaxi--;
		return this.inLineForTaxi;
	}
	
	public synchronized int subtractFromLineForDelivery() {
		this.inLineForDelivery--;
		return this.inLineForDelivery;
	}
	
	
	
	public synchronized void addTaxiServiceCall(UpgradedServiceCall upgradedServiceCall) {
		this.taxiServiceCall.add(upgradedServiceCall);
		this.notifyAll();
//        System.out.println("Notify all car officers in the taxi");

	}
    

    public synchronized UpgradedServiceCall extractFirstFromTaxi() {
    	while (taxiServiceCall.isEmpty()) {
            try {
//                System.out.println("Car Officer is waiting for taxi");
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
        }
//    	System.out.println("Car Officer stopped waiting for taxi");

    	return taxiServiceCall.remove(0);
    }
    
    public synchronized void addDeliveryServiceCall(UpgradedServiceCall upgradedServiceCall) {
		this.deliveryServiceCall.add(upgradedServiceCall);
		this.notifyAll();
//        System.out.println("Notify all car officers in the delivery");

	}

    public synchronized UpgradedServiceCall extractFirstFromDelivery() {
    	while (deliveryServiceCall.isEmpty()) {
            try {
//                System.out.println("Car Officer is waiting for delivery");
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
        }
//    	System.out.println("Car Officer stopped waiting for delivey");
    	return deliveryServiceCall.remove(0);
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
	
	public synchronized void addSalaryToClerk(double payment) {
		totalClerksPayment += payment;
	}
	
	public synchronized void addDeliveryMission() {
		numOfDeliveryMissions++;
	}
	
	public synchronized void addTaxiMission() {
		numOfTaxiMissions++;
	}
	
	public synchronized void addMissionInTelAviv() {
		numOfMissionsInTelAviv++;
	}
	
	public synchronized void addMissionInJerusalem() {
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