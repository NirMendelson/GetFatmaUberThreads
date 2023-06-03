import java.util.Vector;

public class Request implements Runnable {
	private ClerkLine clerkLine;
    private int ID;
    private String type;
    private String serviceArea;
    private double distance;
    private long time;
    private int arrival;
    private boolean isRunning;
    private boolean isClosed;
    

    public Request(int ID, String type, String serviceArea, double distance, long time, int arrival, ClerkLine clerkLine) {
        this.ID = ID;
        this.type = type;
        this.serviceArea = serviceArea;
        this.distance = distance;
        this.time = time;
        this.arrival = arrival;
        this.clerkLine = clerkLine;
        this.isRunning = true;
        this.isClosed = false;
    }

    @Override
    public void run() {
//        System.out.println("Request " + ID + " run");
        try {
            Thread.sleep(arrival); // Simulate waiting time before getting into line

            clerkLine.insert(this);
            // notify the clerk class
            
//            System.out.println("started to insert request " + this.ID + " to clerkLine");
            
            synchronized (this) {
                while (clerkLine.contains(this) && isRunning) {
                    wait();
                }
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public void closeRequest() {
    	this.isClosed = true;
    	this.isRunning = false;
    }

    public long getTime() {
        return this.time;
    }
    
    public double getDistance() {
        return this.distance;
    }
    
    public int getID() {
        return this.ID;
    }
    
    public String getType() {
    	return this.type;
    }
    
    public String getArea() {
    	return this.serviceArea;
    }
}
