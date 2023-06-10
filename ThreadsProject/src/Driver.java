import java.util.Random;

public class Driver implements Runnable{

	private int ID;
	private ReadyRideLine readyRideLine;
	private InformationSystem informationSystem;
	private VehicleList vehicleList;
	private Manager manager;
	private char license;
	private int salary;
	private int rating;
	private double totalDistanceDrove;
	private double totalDrivingProfit;
	private int extraSalary;

	// a constructor for the class
	public Driver(int ID, char license, ReadyRideLine readyRideLine, InformationSystem informationSystem, Manager manager, VehicleList vehicleList) {
		this.ID = ID;
		this.license = license;
		this.readyRideLine = readyRideLine;
		this.informationSystem = informationSystem;
		this.vehicleList = vehicleList;
		this.manager = manager;
		this.salary = 0;
		this.rating = 0;
		this.totalDistanceDrove = 0;
		this.totalDrivingProfit = 0;
		this.extraSalary = 0;
	}


	@Override
	// implementing Runnable 
	public void run() {
		// run as long as the day not over
		while (!informationSystem.getIsDayOver()) {
			ReadyRide currentRide = null;
			// checks that vehicle type fit the driver 
			if ((readyRideLine.getFirst().getServiceType().equals("Delivery") && this.license == 'A') || (readyRideLine.getFirst().getServiceType().equals("Taxi") && this.license == 'B')) {
				// if the day is over then break
				if (informationSystem.getIsDayOver()) {
					break;
				}
				currentRide = readyRideLine.extractFirst();
				this.extraSalary = currentRide.getExtraSalary();
				// sleep for the driving time
				try {
					Thread.sleep((long) currentRide.getVehicle().calculateDrivingTime(currentRide.getDistance()));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				// find the costumer
				for (int i = 0; i < informationSystem.getCustomerList().size(); i++) {
					// add rating, make the customer pay and calculates driving profit, break after you found the customer
					if (currentRide.getCustomerID() == informationSystem.getCustomerList().get(i).getID()) {
						this.addRating(informationSystem.getCustomerList().get(i).giveRating());
						informationSystem.getCustomerList().get(i).pay(currentRide.getVehicle().calculateDrivingTime(currentRide.getDistance()));
						this.drivingProfit(informationSystem.getCustomerList().get(i), currentRide.getDistance(), currentRide.getVehicle());
						break;
					}
				}
				this.updateDistanceDrover(currentRide.getDistance());
				// count this drive in the right variable (taxi or delivery)
				if (currentRide.getVehicle().getType().equals("Taxi")) {
					informationSystem.addTaxiMission();
				}
				else {
					informationSystem.addDeliveryMission();

				}
				// count this driver in the right variable (tel aviv or jerusalem)
				if (currentRide.getServiceArea().equals("Tel Aviv")) {
					informationSystem.addMissionInTelAviv();
				}
				if (currentRide.getServiceArea().equals("Jerusalem")) {
					informationSystem.addMissionInJerusalem();
				}
				// return the vehicle to the list
				vehicleList.addToVehicleList(currentRide.getVehicle());
				// pay the driver
				this.payDriver();
				// mark this missions as complete
				manager.missionComplete();
				// check if the day is over
				manager.isDayOver();
				// sleep
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	// a method to add rating to the driver
	public void addRating(int rating) {
		this.rating += rating;
	}

	// a method to pay the driver
	public void payDriver() {
		double tempPayment = calculatePayment() + (this.extraSalary/2);
		this.salary += tempPayment;
		informationSystem.addSalaryToDriver(tempPayment);
	}

	// a method for updating the distance drover
	public void updateDistanceDrover(double distance) {
		this.totalDistanceDrove += distance;
	}

	// a method that calculates the driving profit
	public double drivingProfit(Customer c, double time, Vehicle v) {
		Random random = new Random();
		double P = 0.5 + random.nextDouble() * 0.5;
		// calling the customer pay method
		c.pay(time);
		this.totalDrivingProfit += (c.getLatestExpense() + c.getRatingToDriver()) - (time*P);
		return (c.getLatestExpense() + c.getRatingToDriver()) - (time*P);
	}

	// a method that calculates the payment
	public double calculatePayment() {
		return this.totalDrivingProfit + 2*totalDistanceDrove;
	}
}