
public class Manager implements Runnable{

	private ManagerLine managerLine;
	private InformationSystem informationSystem;
	private int numOfMissions;
	private int numOfCompleteMissions;
	private boolean isDayOver;
	private VehicleList vehicleList;

	public Manager(ManagerLine managerLine, int numOfMissions, InformationSystem informationSystem, VehicleList vehicleList) {
		this.managerLine = managerLine;
		this.informationSystem = informationSystem;
		this.numOfMissions = numOfMissions;
		this.vehicleList = vehicleList;
		this.numOfCompleteMissions = 0;
		this.isDayOver = false;
	}

	@Override
	public void run() {
		while (!informationSystem.getIsDayOver()) {
			Request currentRequest = managerLine.extractFirst();
			System.out.println("manager working of request: " + currentRequest.getID());
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (informationSystem.getIsDayOver()) {
				break;
			}

			System.out.println("vehicle list size: " + vehicleList.getVehicleList().size());
			Vehicle currentVehicle = vehicleList.extractVehicle(0);
			System.out.println("current vehicle is " + currentVehicle.getType());
			System.out.println("current request is " + currentRequest.getType());
			System.out.println("manager a");
			if (!currentVehicle.getType().equals(currentRequest.getType())) {
				System.out.println("manager b");
				vehicleList.addToVehicleList(currentVehicle);
				System.out.println("manager c");
				managerLine.insertToStart(currentRequest);
			}	
			else {
				System.out.println("manager d");
				ServiceCall currentCall = new ServiceCall(currentRequest.getID(), currentRequest.getType(), currentRequest.getArea(), currentRequest.getDistance());
				UpgradedServiceCall upgradedServiceCall = new UpgradedServiceCall(currentCall.getID(), currentCall.getCustomerID(), currentCall.getServiceType(), currentCall.getServiceArea(), currentCall.getDistance() ,currentVehicle, 20);
				if (currentVehicle.getType().equals("Taxi")) {
					informationSystem.addTaxiServiceCall(upgradedServiceCall);
				} else {
					informationSystem.addDeliveryServiceCall(upgradedServiceCall);
				}
				currentRequest.closeRequest();
				System.out.println("New special Service Call (ID: " + currentRequest.getID() + ") Arrived");
				System.out.println("manager end");
			}

			//			for (int i = 0; i < vehicleList.getVehicleList().size(); i++) {
			//				if (vehicleList.getVehicleList().get(i).getType().equals(currentRequest.getType())) {
			//					Vehicle currentVehicle = vehicleList.extractVehicle(i);
			//					ServiceCall currentCall = new ServiceCall(currentRequest.getID(), currentRequest.getType(), currentRequest.getArea(), currentRequest.getDistance());
			//					UpgradedServiceCall upgradedServiceCall = new UpgradedServiceCall(currentCall.getID(), currentCall.getCustomerID(), currentCall.getServiceType(), currentCall.getServiceArea(), currentCall.getDistance() ,currentVehicle, 20);
			//					if (currentVehicle.getType().equals("Taxi")) {
			//						informationSystem.addTaxiServiceCall(upgradedServiceCall);
			//					} else {
			//						informationSystem.addDeliveryServiceCall(upgradedServiceCall);
			//					}
			//					currentRequest.closeRequest();
			//					System.out.println("New special Service Call (ID: " + currentRequest.getID() + ") Arrived");
			//					System.out.println("manager end");
			//					break;
			//				}
			//			}
		}
	}

	public void missionComplete() {
		this.numOfCompleteMissions++;		
	}

	public int getNumOfCompletedMissions() {
		return this.numOfCompleteMissions;
	}

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