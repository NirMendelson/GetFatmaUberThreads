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
	public void run() {
		while (!informationSystem.getIsDayOver()) {
				ReadyRide currentRide = null;
				if ((readyRideLine.getFirst().getServiceType().equals("Delivery") && this.license == 'A') || (readyRideLine.getFirst().getServiceType().equals("Taxi") && this.license == 'B')) {
					if (informationSystem.getIsDayOver()) {
						break;
					}
					currentRide = readyRideLine.extractFirst();
					this.extraSalary = currentRide.getExtraSalary();
					try {
						Thread.sleep((long) currentRide.getVehicle().calculateDrivingTime(currentRide.getDistance()));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					for (int i = 0; i < informationSystem.getCustomerList().size(); i++) {
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
					vehicleList.addToVehicleList(currentRide.getVehicle());
					this.payDriver();
					manager.missionComplete();
					manager.isDayOver();
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}

	public void addRating(int rating) {
		this.rating += rating;
	}

	public void payDriver() {
		double tempPayment = calculatePayment() + (this.extraSalary/2);
		this.salary += tempPayment;
		informationSystem.addSalaryToDriver(tempPayment);
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