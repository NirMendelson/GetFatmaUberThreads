
public class Scheduler implements Runnable{

	private SchedulerLine schedulerLine;
	private InformationSystem informationSystem;
	private int ID;
	private String serviceArea;
	private int salary;
	private VehicleList vehicleList;

	// a constructor for the class
	public Scheduler(int ID, String serviceArea, SchedulerLine schedulerLine, InformationSystem informationSystem, VehicleList vehicleList) {
		this.ID = ID;
		this.serviceArea = serviceArea;
		this.schedulerLine = schedulerLine;
		this.informationSystem = informationSystem; 
		this.vehicleList = vehicleList;
		this.salary = 0;

	}

	@Override
	// implementing Runnable 
	public void run() {
		// keep running until the day is over
		while (!informationSystem.getIsDayOver()) {
			ServiceCall currentServiceCall = schedulerLine.extractFirst();
			// if the service call area doesn't match the scheduler's area then return the service call in the schedulers line
			if (!currentServiceCall.getServiceArea().equals(this.serviceArea)) {
				schedulerLine.insert(currentServiceCall);
			}
			else {
				// if the day is over then break
				if (informationSystem.getIsDayOver()) {
					break;
				}

				Vehicle currentVehicle = vehicleList.extractVehicle(0);
				// if the vehicle type doesn't match the service call type then return the vehicle to the vehicle list and choose a different vehicle
				while(!currentVehicle.getType().equals(currentServiceCall.getServiceType())) {
					vehicleList.addToVehicleList(currentVehicle);
					currentVehicle = vehicleList.extractVehicle(0);
				}	

				// create an upgraded service call
				UpgradedServiceCall upgradedServiceCall = new UpgradedServiceCall(currentServiceCall.getID(), currentServiceCall.getCustomerID(), currentServiceCall.getServiceType(), currentServiceCall.getServiceArea(), currentServiceCall.getDistance(), currentVehicle, 0);
				// add the service call to the right list
				if (currentVehicle.getType().equals("Taxi")) {
					informationSystem.addTaxiServiceCall(upgradedServiceCall);
				} else {
					informationSystem.addDeliveryServiceCall(upgradedServiceCall);
				}
				// if the day is over break
				if (informationSystem.getIsDayOver()) {
					break;
				}

				// calculates the sleeping time
				double sleepingTime = ((currentVehicle.calculateDrivingTime(currentServiceCall.getDistance()) / 4) * 100);
				System.out.println("New service call (ID: " + currentServiceCall.getCustomerID() + ") arrived and data inserted to database");
				// sleep
				try {
					Thread.sleep((long) sleepingTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				// pay the scheduler
				this.payScheduler(sleepingTime);
			}
		}
	}

	// a method the pays the calculates how much to pay the scheduler and add this value to the information system
	public void payScheduler(double sleepingTime) {
		double temp = sleepingTime/1000;
		this.salary += temp * 3;
		informationSystem.addSalaryToScheduler(temp * 3);
	}
}