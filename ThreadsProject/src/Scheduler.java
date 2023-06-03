
public class Scheduler implements Runnable{

	private SchedulerLine schedulerLine;
	private InformationSystem informationSystem;
	private int ID;
	private String serviceArea;
	private int salary;

	public Scheduler(int ID, String serviceArea, SchedulerLine schedulerLine, InformationSystem informationSystem) {
		this.ID = ID;
		this.serviceArea = serviceArea;
		this.schedulerLine = schedulerLine;
		this.informationSystem = informationSystem; 
		this.salary = 0;

	}

	@Override
	public void run() {
		while (!informationSystem.getIsDayOver()) {
			synchronized (schedulerLine) {
				if (schedulerLine.getFirst().getServiceArea().equals(this.serviceArea)) {
					if (informationSystem.getIsDayOver()) {
						break;
					}
					ServiceCall currentServiceCall = schedulerLine.getFirst();
					System.out.println("scheduler " + this.ID + " got service Call " + currentServiceCall.getCustomerID());
//					System.out.println("vehicle list: " + informationSystem.getVehicleList().size());

					for (int i = 0; i < informationSystem.getVehicleList().size(); i++) {
						if (informationSystem.getVehicleList().get(i).getType().equals(currentServiceCall.getServiceType())) {
							
							Vehicle currentVehicle = informationSystem.getVehicleList().remove(i);
							UpgradedServiceCall upgradedServiceCall = new UpgradedServiceCall(currentServiceCall.getID(), currentServiceCall.getCustomerID(), currentServiceCall.getServiceType(), currentServiceCall.getServiceArea(), currentServiceCall.getDistance(), currentVehicle, 0);
							if (currentVehicle.getType().equals("Taxi")) {
								informationSystem.addTaxiServiceCall(upgradedServiceCall);
							} else {
								informationSystem.addDeliveryServiceCall(upgradedServiceCall);
							}
							if (informationSystem.getIsDayOver()) {
								break;
							}
							double sleepingTime = ((currentVehicle.calculateDrivingTime(currentServiceCall.getDistance()) / 4) * 100);
//							System.out.println("chose vehicle: " + currentVehicle.getType());
							System.out.println("New service call (ID: " + currentServiceCall.getCustomerID() + ") arrived and data inserted to database");
							try {
								Thread.sleep((long) sleepingTime);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							break; // Exit the loop after processing one service call
						}
					}
					schedulerLine.extractFirst();
					this.payScheduler();
//					System.out.println("scheduler " + this.ID + " salary is " + this.salary);

				}

			}
		}
	}



	public void payScheduler() {
		this.salary += 3;
		informationSystem.addSalaryToScheduler(3);
	}
}

