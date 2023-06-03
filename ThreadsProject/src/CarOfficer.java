
public class CarOfficer implements Runnable {

	private int ID;
	private int salary;
	private InformationSystem informationSystem;
	private ReadyRideLine readyRideLine;
	private int extraSalary;


	public CarOfficer(int ID, InformationSystem informationSystem, ReadyRideLine readyRideLine) {
		this.ID = ID;
		this.salary = 0;
		this.informationSystem = informationSystem;
		this.readyRideLine = readyRideLine;
		this.extraSalary = 0;
	}

	@Override
	public void run() {
		while (!informationSystem.getIsDayOver()) {
			synchronized (informationSystem) {
				double randomValue = Math.random();
				UpgradedServiceCall currentCall = null;
				if (randomValue < 0.5) {
					currentCall = informationSystem.extractFirstFromTaxi();
				}
				else {
					currentCall = informationSystem.extractFirstFromDelivery();
				}
				this.extraSalary = currentCall.getFee();
				if (informationSystem.getIsDayOver()) {
					break;
				}
				System.out.println("Car Officer ID: " + this.ID + " started to work on " + currentCall.getCustomerID() );

				try {
					Thread.sleep((long) informationSystem.getCarOfficersWorkingTime());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (informationSystem.getIsDayOver()) {
					break;
				}
				ReadyRide readyRide = new ReadyRide(currentCall.getID(), currentCall.getCustomerID(), currentCall.getServiceType(), currentCall.getServiceArea(), currentCall.getDistance() ,currentCall.getVehicle(), currentCall.getFee()/2);
				System.out.println("Ride " + readyRide.getCustomerID() + " is ready");
				readyRideLine.insert(readyRide);
				this.payCarOfficer();
			}
		}
	}




	public void payCarOfficer() {
		this.salary +=  5 + (this.extraSalary/2);
		informationSystem.addSalaryToCarOfficer(5);
	}



}
