public class Clerk implements Runnable {
	private ClerkLine clerkLine;
	private SchedulerLine schedulerLine;
	private ManagerLine managerLine;
	private InformationSystem informationSystem;
	private int ID;
	private int salary;

	// a constructor for the class
	public Clerk(int ID, ClerkLine clerkLine, InformationSystem informationSystem, SchedulerLine schedulerLine, ManagerLine managerLine) {
		this.ID = ID;
		this.clerkLine = clerkLine;
		this.informationSystem = informationSystem;
		this.schedulerLine = schedulerLine;
		this.managerLine = managerLine;
		this.salary = 0;
	}

	@Override
	// implementing Runnable 
	public void run() {
		// don't run if the day is over
		while (!informationSystem.getIsDayOver()) {
			try {
				// take the first request from request line
				Request currentRequest = clerkLine.extractFirst();
				// if the day is over then break
				if (informationSystem.getIsDayOver()) {
					break;
				}
				// sleep according to the time of the request
				Thread.sleep(currentRequest.getTime());


				// Check if customer exists in the informationSystem, if not, add them
				if (!informationSystem.containsCustomer(currentRequest.getID())) {
					informationSystem.addCustomer(currentRequest.getID());
				}
				// if the request has distance of under 100 then create a service call and insert it to the scheduler line
				if (currentRequest.getDistance() < 100) {
					ServiceCall serviceCall = new ServiceCall(currentRequest.getID(), currentRequest.getType(), currentRequest.getArea(), currentRequest.getDistance());
					schedulerLine.insert(serviceCall);
					if (informationSystem.getIsDayOver()) {
						break;
					}
					currentRequest.closeRequest();
				} 

				// if the request has distance of over 100 then create a service call and insert it to the manager line
				else {
					Thread.sleep(500);
					if (informationSystem.getIsDayOver()) {
						break;
					}
					managerLine.insert(currentRequest);
				}
				// pay the clerk
				this.payClerk();

			}

			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	// a method that pays the clerk and add the payment to the information system variable
	public void payClerk() {
		this.salary = this.salary + 4;
		informationSystem.addSalaryToClerk(4);
	}

	// a getter for the clerk's salary
	public int getSalary() {
		return this.salary;
	}
}