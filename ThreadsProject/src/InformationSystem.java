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

	// a constructor for the class
	public InformationSystem() {
		this.customersList = new ArrayList<Customer>();
		this.deliveryServiceCall = new ArrayList<UpgradedServiceCall>();
		this.taxiServiceCall = new ArrayList<UpgradedServiceCall>();
		this.isDayOver = false;
		this.inLineForTaxi = 0;
		this.inLineForDelivery = 0;
		// creating the customers
		for (int i = 100; i < 190; i++) {
			Customer customer = new Customer(i);
			customersList.add(customer);
		}
	}

	// a setter for day is over
	public void setIsDayOver(boolean isOver) {
		this.isDayOver = isOver;
	}

	// a getter for day is over 
	public boolean getIsDayOver() {
		return this.isDayOver;
	}

	// a synchronized method that checks if the customer list contains a customer
	public synchronized boolean containsCustomer(int ID) {
		for (int i = 0; i < customersList.size(); i++) {
			if (customersList.get(i).getID() == ID) {
				return true;
			}
		}
		return false;
	}

	// a synchronized method that add a customer to the customer list
	public synchronized void addCustomer(int ID) {
		Customer customer = new Customer(ID);
		customersList.add(customer);
	}

	// a getter for customer list
	public ArrayList<Customer> getCustomerList() {
		return this.customersList;
	}

	// a getter for taxi service call size
	public synchronized int getTaxiServiceCallSize() {
		return this.taxiServiceCall.size();
	}

	// a getter for delivery service call size
	public synchronized int getDeliveryServiceCallSize() {
		return this.deliveryServiceCall.size();
	}

	// a getter for line for taxi
	public synchronized int getLineForTaxi() {
		return this.inLineForTaxi;
	}

	// a getter for line for delivery
	public synchronized int getLineForDelivery() {
		return this.inLineForDelivery;
	}

	// a method that increase the in line for taxi and return this value
	public synchronized int addToLineForTaxi() {
		this.inLineForTaxi++;
		return this.inLineForTaxi;

	}

	// a method that increase the in line for delivery and return this value
	public synchronized int addToLineForDelivery() {
		this.inLineForDelivery++;
		return this.inLineForDelivery;
	}

	// a method that decrease the in line for taxi and return this value
	public synchronized int subtractFromLineForTaxi() {
		this.inLineForTaxi--;
		return this.inLineForTaxi;
	}

	// a method that decrease the in line for delivery and return this value
	public synchronized int subtractFromLineForDelivery() {
		this.inLineForDelivery--;
		return this.inLineForDelivery;
	}

	// a synchronized method that adds a upgraded service call to the taxi service call and notify the waiting threads
	public synchronized void addTaxiServiceCall(UpgradedServiceCall upgradedServiceCall) {
		this.taxiServiceCall.add(upgradedServiceCall);
		this.notifyAll();

	}

	// a synchronized method that extract the first item from taxi service call, if the list is empty then the thread waits
	public synchronized UpgradedServiceCall extractFirstFromTaxi() {
		while (taxiServiceCall.isEmpty()) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
		return taxiServiceCall.remove(0);
	}

	// a synchronized method that add an upgraded service call to the delivery service call list and notify all the waiting threads
	public synchronized void addDeliveryServiceCall(UpgradedServiceCall upgradedServiceCall) {
		this.deliveryServiceCall.add(upgradedServiceCall);
		this.notifyAll();

	}

	// synchronized method that extract the first item from delivery service call, it the list is empty, the threads wait
	public synchronized UpgradedServiceCall extractFirstFromDelivery() {
		while (deliveryServiceCall.isEmpty()) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
		return deliveryServiceCall.remove(0);
	}

	// a method that gets an input and adds it to the total drivers payment
	public void addSalaryToDriver(double payment) {
		totalDriversPayment += payment;
	}

	// a method that gets an input and adds it to the total car officers payment
	public void addSalaryToCarOfficer(double payment) {
		totalCarOfficersPayment += payment;
	}

	// a method that gets an input and adds it to the total schedulers payment
	public void addSalaryToScheduler(double payment) {
		totalSchedulersPayment += payment;
	}

	// a method that gets an input and adds it to the total clerks payment
	public synchronized void addSalaryToClerk(double payment) {
		totalClerksPayment += payment;
	}

	// increase the num of delivery missions completed
	public synchronized void addDeliveryMission() {
		numOfDeliveryMissions++;
	}

	// increase the num of taxi missions completed
	public synchronized void addTaxiMission() {
		numOfTaxiMissions++;
	}

	// increase the num of missions in Tel Aviv completed
	public synchronized void addMissionInTelAviv() {
		numOfMissionsInTelAviv++;
	}

	// increase the num of missions in Jerusalem completed
	public synchronized void addMissionInJerusalem() {
		numOfMissionsInJerusalem++;
	} 

	// a getter for total schedulers payment
	public double getTotalSchedulersPayment() {
		return this.totalSchedulersPayment;
	}

	// a getter for total car officers payment
	public double getTotalCarOfficersPayment() {
		return this.totalCarOfficersPayment;
	}

	// a getter for total drivers payment
	public double getTotalDriverPayment() {
		return this.totalDriversPayment;
	}

	// a getter for total clerks payment
	public double getTotalClerksPayment() {
		return this.totalClerksPayment;
	}

	// a method that calculates the average payment for worker and returns this value
	public double getAveragePayment() {
		return ((this.totalClerksPayment + this.totalCarOfficersPayment + this.totalDriversPayment + this.totalSchedulersPayment)/(8+this.numOfDrivers));
	}

	// a getter for num of taxi missions
	public int getNumOfTaxiMissions() {
		return this.numOfTaxiMissions;
	}

	// a getter for num of delivery missions
	public int getNumOfDeliveryMissions() {
		return this.numOfDeliveryMissions;
	}

	// a method that checks where most missions where are returns the answer
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

	// a setter for num of drivers
	public void setNumOfDrivers(int drivers) {
		this.numOfDrivers = drivers;
	}

	// a setter for car officers working time
	public void setCarOfficersWorkingTime(double time) {
		this.carOfficersWorkingTime = time;
	}

	// a getter for car officers working time
	public double getCarOfficersWorkingTime() {
		return this.carOfficersWorkingTime;
	}

}