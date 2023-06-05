import java.util.ArrayList;

public class ReadyRideLine {
    private ArrayList<ReadyRide> buffer;
    private int maxSize;

    public ReadyRideLine() {
        buffer = new ArrayList<>();
        this.maxSize = 14;
    }

    public synchronized void insert(ReadyRide ride){
        if (buffer.size() == maxSize) {
            try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        }
        buffer.add(ride);
        notifyAll();
    }

    public synchronized ReadyRide extractFirst() {
        while (buffer.isEmpty()) {
            try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        }
        if (buffer.size() == maxSize) {
        	ReadyRide temp = buffer.remove(0);
            notifyAll();
            return temp;
        }
        else {
        	return buffer.remove(0);
        }
        
    }

    public synchronized boolean isEmpty() {
        return buffer.isEmpty();
    }

    public synchronized ReadyRide getFirst() {
    	while (buffer.isEmpty()) {
    		try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
    	}
    	return buffer.get(0);
    }
}