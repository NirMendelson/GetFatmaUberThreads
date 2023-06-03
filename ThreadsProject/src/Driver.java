import java.util.Random;

public class Driver implements Runnable{

	private int ID;
	private ReadyRideLine readyRideLine;
	private InformationSystem informationSystem;
	private Manager manager;
	private char license;
	private int salary;
	private int rating;
	private double totalDistanceDrove;
	private double totalDrivingProfit;
	private int extraSalary;

	public Driver(int ID, char license, ReadyRideLine readyRideLine, InformationSystem informationSystem, Manager manager) {
		this.ID = ID;
		this.license = license;
		this.readyRideLine = readyRideLine;
		this.informationSystem = informationSystem;
		this.manager = manager;
		this.salary = 0;
		this.rating = 0;
		this.totalDistanceDrove = 0;
		this.totalDrivingProfit = 0;
		this.extraSalary = 0;
		// Assign other member variables as needed
	}


	@Override
	public void run() {
		while (!informationSystem.getIsDayOver()) {
			synchronized (readyRideLine) {
				ReadyRide currentRide = null;
				if ((readyRideLine.getFirst().getServiceType().equals("Delivery") && this.license == 'A') || (readyRideLine.getFirst().getServiceType().equals("Taxi") && this.license == 'B')) {
					if (informationSystem.getIsDayOver()) {
						break;
					}
					currentRide = readyRideLine.getFirst();
					this.extraSalary = currentRide.getExtraSalary();
					System.out.println("Driver " + this.ID +" started to work on ride " + currentRide.getCustomerID());
					readyRideLine.extractFirst();
					try {
						Thread.sleep((long) currentRide.getVehicle().calculateDrivingTime(currentRide.getDistance()));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					for (int i = 0; i < informationSystem.getCustomerList().size(); i++) {
//						System.out.println("current ride ID: " + currentRide.getCustomerID() + " customer ID: " + informationSystem.getCustomerList().get(i).getID());
						if (currentRide.getCustomerID() == informationSystem.getCustomerList().get(i).getID()) {
							
							this.addRating(informationSystem.getCustomerList().get(i).giveRating());
							informationSystem.getCustomerList().get(i).pay(currentRide.getVehicle().calculateDrivingTime(currentRide.getDistance()));
							this.drivingProfit(informationSystem.getCustomerList().get(i), currentRide.getDistance(), currentRide.getVehicle());
							break;
						}
					}
					this.updateDistanceDrover(currentRide.getDistance());
					if (currentRide.getVehicle().getType().equals("Taxi")) {
						informationSystem.addTaxiMission();
					}
					else {
						informationSystem.addDeliveryMission();
					}

					if (currentRide.getServiceArea().equals("Tel Aviv")) {
						informationSystem.addMissionInTelAviv();
					}
					if (currentRide.getServiceArea().equals("Jerusalem")) {
						informationSystem.addMissionInJerusalem();
					}
					System.out.println("Drive " + currentRide.getCustomerID() + " is over");
					informationSystem.addToVehicleList(currentRide.getVehicle());
					this.payDriver();
					manager.missionComplete();
					System.out.println("num of completed missions: " + manager.getNumOfCompletedMissions());
					manager.isDayOver();
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	public void addRating(int rating) {
		this.rating += rating;
	}

	public void payDriver() {
		this.salary += (5 + this.extraSalary);
		informationSystem.addSalaryToDriver(5);
	}

	public void updateDistanceDrover(double distance) {
		this.totalDistanceDrove += distance;
	}

	// Calculates driving profit
	public double drivingProfit(Customer c, double time, Vehicle v) {
		Random random = new Random();
		double P = 0.5 + random.nextDouble() * 0.5;
		this.totalDrivingProfit += (c.getLatestExpense() + c.getRatingToDriver()) - (time*P);
		return (c.getLatestExpense() + c.getRatingToDriver()) - (time*P);
	}

	public double calculatePayment() {
		return this.totalDrivingProfit + 2*totalDistanceDrove;
	}
}
