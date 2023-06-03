
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
		// don't run if the day is over
		while (!informationSystem.getIsDayOver()) {
//			synchronized (informationSystem) {

				// 50% to prepare a Taxi and 50% for a delivery vehicle
				double randomValue = Math.random();
				UpgradedServiceCall currentCall = null;
				if (randomValue < 0.5) {
					currentCall = informationSystem.extractFirstFromTaxi();
				}
				else {
					currentCall = informationSystem.extractFirstFromDelivery();
				}
				// getting the extra fee
				this.extraSalary = currentCall.getFee();
				// if the day is over then break
				if (informationSystem.getIsDayOver()) {
					break;
				}
				System.out.println("Car Officer ID: " + this.ID + " started to work on " + currentCall.getCustomerID() );
				
				// sleep for the time that was decided in the GUI
				try {
					Thread.sleep((long) informationSystem.getCarOfficersWorkingTime());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (informationSystem.getIsDayOver()) {
					break;
				}
				// create a ride and insert it to the ready ride line
				ReadyRide readyRide = new ReadyRide(currentCall.getID(), currentCall.getCustomerID(), currentCall.getServiceType(), currentCall.getServiceArea(), currentCall.getDistance() ,currentCall.getVehicle(), currentCall.getFee()/2);
				System.out.println("Ride " + readyRide.getCustomerID() + " is ready");
				readyRideLine.insert(readyRide);
				this.payCarOfficer();
			}
		}
//	}




	public void payCarOfficer() {
		this.salary +=  5 + (this.extraSalary/2);
		informationSystem.addSalaryToCarOfficer(5);
	}



}