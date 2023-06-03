public class Clerk implements Runnable {
	private ClerkLine clerkLine;
	private SchedulerLine schedulerLine;
	private ManagerLine managerLine;
	private InformationSystem informationSystem;
	private int ID;
	private int salary;

	public Clerk(int ID, ClerkLine clerkLine, InformationSystem informationSystem, SchedulerLine schedulerLine, ManagerLine managerLine) {
		this.ID = ID;
		this.clerkLine = clerkLine;
		this.informationSystem = informationSystem;
		this.schedulerLine = schedulerLine;
		this.managerLine = managerLine;
		this.salary = 0;
	}

	@Override
	public void run() {
		while (!informationSystem.getIsDayOver()) {
			try {
				synchronized (clerkLine) {
					
					Request currentRequest = clerkLine.getFirst();
					if (informationSystem.getIsDayOver()) {
						break;
					}
					System.out.println("clerk " + this.ID + " is handling request: " + currentRequest.getID());
					Thread.sleep(currentRequest.getTime());
					
					
					// Check if customer exists in the informationSystem, if not, add them
					if (!informationSystem.containsCustomer(currentRequest.getID())) {
						informationSystem.addCustomer(currentRequest.getID());
					}

					if (currentRequest.getDistance() < 100) {
						ServiceCall serviceCall = new ServiceCall(currentRequest.getID(), currentRequest.getType(), currentRequest.getArea(), currentRequest.getDistance());
						schedulerLine.insert(serviceCall);
						if (informationSystem.getIsDayOver()) {
							break;
						}
						System.out.println("Added service call " + serviceCall.getCustomerID() + " to schedulerLine");
						currentRequest.closeRequest();
					} 
					
					else {
						Thread.sleep(500);
						if (informationSystem.getIsDayOver()) {
							break;
						}
						managerLine.insert(currentRequest);
						System.out.println("Added request " + currentRequest.getID() + " to managerLine");
					}
					clerkLine.extractFirst();
					this.payClerk();
					//				System.out.println("clerk salary is " + this.getSalary());
					//				System.out.println("Clerk " + this.ID + " is free");
				}

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void payClerk() {
		this.salary = this.salary + 5;
		informationSystem.addSalaryToClerk(5);
	}

	public int getSalary() {
		return this.salary;
	}
}
