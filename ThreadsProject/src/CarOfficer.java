
public class CarOfficer implements Runnable {

	private int ID;
	private int salary;
	private InformationSystem informationSystem;
	private ReadyRideLine readyRideLine;
	private int extraSalary;
	private UpgradedServiceCall currentCall;

	// a constructor for the class
	public CarOfficer(int ID, InformationSystem informationSystem, ReadyRideLine readyRideLine) {
		this.ID = ID;
		this.salary = 0;
		this.informationSystem = informationSystem;
		this.readyRideLine = readyRideLine;
		this.extraSalary = 0;
	}

	@Override
	// implementing Runnable 
	public void run() {
		// don't run if the day is over
		while (!informationSystem.getIsDayOver()) {

			// 50% to prepare a Taxi and 50% for a delivery vehicle
			double randomValue = Math.random();
			chooseWhichVehicle(randomValue);

			// getting the extra fee
			this.extraSalary = currentCall.getFee();
			// if the day is over then break
			if (informationSystem.getIsDayOver()) {
				break;
			}
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
			readyRideLine.insert(readyRide);
			this.payCarOfficer();
		}
	}

	// a method the pays the officer and add the payment to the information system variable
	public void payCarOfficer() {
		double temp = 5 + (this.extraSalary/2);
		this.salary +=  5 + (temp);
		informationSystem.addSalaryToCarOfficer(temp);
	}

	// a method that chooses which vehicle the officer should choose, it start with a random choice and then it prevent a situation that one of the line is empty
	public synchronized void chooseWhichVehicle(double randomNum) {
		if (randomNum < 0.5) {
			if (informationSystem.getLineForDelivery() == 0 && informationSystem.getLineForTaxi() > 0) {
				informationSystem.addToLineForDelivery();
				currentCall = informationSystem.extractFirstFromDelivery();
				informationSystem.subtractFromLineForDelivery();
			} else {
				informationSystem.addToLineForTaxi();
				currentCall = informationSystem.extractFirstFromTaxi();
				informationSystem.subtractFromLineForTaxi();
			}
		} else {
			if (informationSystem.getLineForTaxi() == 0 && informationSystem.getLineForDelivery() > 0) {
				informationSystem.addToLineForTaxi();
				currentCall = informationSystem.extractFirstFromTaxi();
				informationSystem.subtractFromLineForTaxi();
			} else {
				informationSystem.addToLineForDelivery();
				currentCall = informationSystem.extractFirstFromDelivery();
				informationSystem.subtractFromLineForDelivery();
			}
		}
	}
}