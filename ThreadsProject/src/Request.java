import java.util.Vector;

public class Request implements Runnable {
	private ClerkLine clerkLine;
	private int ID;
	private String type;
	private String serviceArea;
	private double distance;
	private long time;
	private int arrival;
	private boolean isClosed;

	// a constructor for the class
	public Request(int ID, String type, String serviceArea, double distance, long time, int arrival, ClerkLine clerkLine) {
		this.ID = ID;
		this.type = type;
		this.serviceArea = serviceArea;
		this.distance = distance;
		this.time = time;
		this.arrival = arrival;
		this.clerkLine = clerkLine;
		this.isClosed = false;
	}

	@Override
	// implementing Runnable 
	public void run() {
		try {
			// sleep according to the request arrival
			Thread.sleep(arrival); 
			clerkLine.insert(this);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	// a method that close the request
	public void closeRequest() {
		this.isClosed = true;
	}

	// a getter for time
	public long getTime() {
		return this.time;
	}

	// a getter for distance
	public double getDistance() {
		return this.distance;
	}

	// a getter for ID
	public int getID() {
		return this.ID;
	}

	// a getter for type
	public String getType() {
		return this.type;
	}

	// a getter for area
	public String getArea() {
		return this.serviceArea;
	}
}