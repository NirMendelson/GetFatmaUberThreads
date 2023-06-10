
public class Scheduler implements Runnable{

	private SchedulerLine schedulerLine;
	private InformationSystem informationSystem;
	private int ID;
	private String serviceArea;
	private int salary;
	private VehicleList vehicleList;

	public Scheduler(int ID, String serviceArea, SchedulerLine schedulerLine, InformationSystem informationSystem, VehicleList vehicleList) {
		this.ID = ID;
		this.serviceArea = serviceArea;
		this.schedulerLine = schedulerLine;
		this.informationSystem = informationSystem; 
		this.vehicleList = vehicleList;
		this.salary = 0;

	}

	@Override
	public void run() {
		while (!informationSystem.getIsDayOver()) {
			ServiceCall currentServiceCall = schedulerLine.extractFirst();
			if (!currentServiceCall.getServiceArea().equals(this.serviceArea)) {
				schedulerLine.insert(currentServiceCall);
			}
			else {
				if (informationSystem.getIsDayOver()) {
					break;
				}
//				System.out.println("scheduler " + this.ID + " got service Call " + currentServiceCall.getCustomerID());		

				Vehicle currentVehicle = vehicleList.extractVehicle(0);
//				System.out.println("aaa");
				while(!currentVehicle.getType().equals(currentServiceCall.getServiceType())) {
					vehicleList.addToVehicleList(currentVehicle);
					currentVehicle = vehicleList.extractVehicle(0);

				}	

				UpgradedServiceCall upgradedServiceCall = new UpgradedServiceCall(currentServiceCall.getID(), currentServiceCall.getCustomerID(), currentServiceCall.getServiceType(), currentServiceCall.getServiceArea(), currentServiceCall.getDistance(), currentVehicle, 0);
//				System.out.println("ccc");
				if (currentVehicle.getType().equals("Taxi")) {
					informationSystem.addTaxiServiceCall(upgradedServiceCall);
				} else {
					informationSystem.addDeliveryServiceCall(upgradedServiceCall);
				}
				if (informationSystem.getIsDayOver()) {
					break;
				}

				double sleepingTime = ((currentVehicle.calculateDrivingTime(currentServiceCall.getDistance()) / 4) * 100);
				System.out.println("New service call (ID: " + currentServiceCall.getCustomerID() + ") arrived and data inserted to database");
				try {
					Thread.sleep((long) sleepingTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				this.payScheduler(sleepingTime);


			}
		}
	}




	public void payScheduler(double sleepingTime) {
		double temp = sleepingTime/1000;
		this.salary += temp * 3;
		informationSystem.addSalaryToScheduler(temp * 3);
	}
}