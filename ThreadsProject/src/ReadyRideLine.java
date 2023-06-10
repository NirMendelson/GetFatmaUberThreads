import java.util.ArrayList;

public class ReadyRideLine {
	private ArrayList<ReadyRide> buffer;
	private int maxSize;

	// a constructor for the class
	public ReadyRideLine() {
		buffer = new ArrayList<>();
		this.maxSize = 14;
	}

	// synchornized method for inserting items to the buffer, when a item get inserted it notify the waiting threads
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

	// synchornized method for extracting the first item from the buffer, if the buffer is empty the clerks wait
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

	// synchornized method for checking if the buffer is empty
	public synchronized boolean isEmpty() {
		return buffer.isEmpty();
	}

	// synchornized method for getting the first item from the buffer, if the buffer is empty the threads wait
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