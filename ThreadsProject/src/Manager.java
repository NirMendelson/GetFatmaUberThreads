
public class Manager implements Runnable{

	private ManagerLine managerLine;
	private InformationSystem informationSystem;
	private int numOfMissions;
	private int numOfCompleteMissions;
	private boolean isDayOver;
	private VehicleList vehicleList;

	// a constructor for the class
	public Manager(ManagerLine managerLine, int numOfMissions, InformationSystem informationSystem, VehicleList vehicleList) {
		this.managerLine = managerLine;
		this.informationSystem = informationSystem;
		this.numOfMissions = numOfMissions;
		this.vehicleList = vehicleList;
		this.numOfCompleteMissions = 0;
		this.isDayOver = false;
	}

	@Override
	// implementing Runnable 
	public void run() {
		// keep running until the day is over
		while (!informationSystem.getIsDayOver()) {
			Request currentRequest = managerLine.extractFirst();
			// if the day is over then break
			if (informationSystem.getIsDayOver()) {
				break;
			}

			Vehicle currentVehicle = vehicleList.extractVehicle(0);
			// if the vehicle type does not match to the request type then returns this vehicle to the list and return the request to the manager list
			if (!currentVehicle.getType().equals(currentRequest.getType())) {
				vehicleList.addToVehicleList(currentVehicle);
				managerLine.insertToStart(currentRequest);
			}	
			else {
				// sleep 3 seconds
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				// create a service call
				ServiceCall currentCall = new ServiceCall(currentRequest.getID(), currentRequest.getType(), currentRequest.getArea(), currentRequest.getDistance());
				// create an upgraded service call
				UpgradedServiceCall upgradedServiceCall = new UpgradedServiceCall(currentCall.getID(), currentCall.getCustomerID(), currentCall.getServiceType(), currentCall.getServiceArea(), currentCall.getDistance() ,currentVehicle, 20);
				// add the mission to the right counter in the information system
				if (currentVehicle.getType().equals("Taxi")) {
					informationSystem.addTaxiServiceCall(upgradedServiceCall);
				} else {
					informationSystem.addDeliveryServiceCall(upgradedServiceCall);
				}
				// close the request
				currentRequest.closeRequest();
				System.out.println("New special Service Call (ID: " + currentRequest.getID() + ") Arrived");
			}
		}
	}

	// a method that increase num of complete missions
	public synchronized void missionComplete() {
		this.numOfCompleteMissions++;		
	}

	// a getter for num of complete missions
	public int getNumOfCompletedMissions() {
		return this.numOfCompleteMissions;
	}

	// a method that checks if the day is over and if so it updated the information system and prints a few sentences
	public void isDayOver() {
		if (this.numOfMissions == this.numOfCompleteMissions) {
			informationSystem.setIsDayOver(true);
			System.out.println("Day is over");
			System.out.println("Clerks total payment: " + informationSystem.getTotalClerksPayment());
			System.out.println("Schedulers total payment: " + informationSystem.getTotalSchedulersPayment());
			System.out.println("Car Officers total payment: " + informationSystem.getTotalCarOfficersPayment());
			System.out.println("Drivers total payment: " + informationSystem.getTotalDriverPayment());
			System.out.println("Average payment for worker: " + informationSystem.getAveragePayment());
			System.out.println("Num of Delivery missions: " + informationSystem.getNumOfDeliveryMissions());
			System.out.println("Num of Taxi missions: " + informationSystem.getNumOfTaxiMissions());
			System.out.println("Most popular service area: " + informationSystem.getMostPopularArea());
		}
	}
}