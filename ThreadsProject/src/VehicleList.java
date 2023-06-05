import java.util.ArrayList;

public class VehicleList {

	private ArrayList<Vehicle> vehicleList;

	
	public VehicleList() {
		vehicleList = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
	    	Motorcycle motorcycle = new Motorcycle();
	    	Taxi taxi = new Taxi();
	    	vehicleList.add(motorcycle);
	    	vehicleList.add(taxi);
	    }
	}
	
	public synchronized void addToVehicleList(Vehicle vehicle) {
		this.vehicleList.add(vehicle);
		this.notifyAll();
	}
	
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
	
	public ArrayList<Vehicle> getVehicleList() {
		return this.vehicleList;
	}
	
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
