
public class Manager implements Runnable{

	private ManagerLine managerLine;
	private InformationSystem informationSystem;
	private int numOfMissions;
	private int numOfCompleteMissions;
	private boolean isDayOver;

	public Manager(ManagerLine managerLine, int numOfMissions, InformationSystem informationSystem) {
		this.managerLine = managerLine;
		this.informationSystem = informationSystem;
		this.numOfMissions = numOfMissions;
		this.numOfCompleteMissions = 0;
		this.isDayOver = false;
	}

	@Override
	public void run() {
		while (!this.isDayOver) {
			synchronized (managerLine) {
			Request currentRequest = managerLine.getFirst();
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (informationSystem.getIsDayOver()) {
				break;
			}
			for (int i = 0; i < informationSystem.getVehicleList().size(); i++) {
				if (informationSystem.getVehicleList().get(i).getType().equals(currentRequest.getType())) {
					Vehicle currentVehicle = informationSystem.getVehicleList().remove(i);
					ServiceCall currentCall = new ServiceCall(currentRequest.getID(), currentRequest.getType(), currentRequest.getArea(), currentRequest.getDistance());
					UpgradedServiceCall upgradedServiceCall = new UpgradedServiceCall(currentCall.getID(), currentCall.getCustomerID(), currentCall.getServiceType(), currentCall.getServiceArea(), currentCall.getDistance() ,currentVehicle, 20);
					if (currentVehicle.getType().equals("Taxi")) {
						informationSystem.addTaxiServiceCall(upgradedServiceCall);
					} else {
						informationSystem.addDeliveryServiceCall(upgradedServiceCall);
					}
					currentRequest.closeRequest();
					System.out.println("New special Service Call (ID: " + currentRequest.getID() + ") Arrived");
					managerLine.extractFirst();
					System.out.println("manager end");
					break;
				}
			}
			}
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
