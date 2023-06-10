import java.util.ArrayList;

public class VehicleList {

	private ArrayList<Vehicle> vehicleList;

	// a constructor for the class
	public VehicleList() {
		vehicleList = new ArrayList<>();
		for (int i = 0; i < 2; i++) {
			Motorcycle motorcycle = new Motorcycle();
			Taxi taxi = new Taxi();
			vehicleList.add(motorcycle);
			vehicleList.add(taxi);
		}
	}

	// a method that gets a vehicle input and adds it to the list, the method notify the waiting threads
	public synchronized void addToVehicleList(Vehicle vehicle) {
		this.vehicleList.add(vehicle);
		this.notifyAll();
	}

	// synchornized method for extracting the first item from the buffer, if the buffer is empty the threads wait
	public synchronized Vehicle extractVehicle(int i) {
		while (vehicleList.isEmpty()) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return this.vehicleList.remove(i);
	}

	// a getter for vehicle list
	public ArrayList<Vehicle> getVehicleList() {
		return this.vehicleList;
	}

	// a method that makes the thread to wait until a vehicle gets add to the list
	public synchronized void waitUntilListNotEmpty() {
		while (vehicleList.isEmpty()) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}