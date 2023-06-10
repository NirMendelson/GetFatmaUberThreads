import java.util.ArrayList;

public class SchedulerLine {

	private ArrayList<ServiceCall> buffer;
	private InformationSystem informationSystem;


	// a constructor for the class
	public SchedulerLine(InformationSystem informationSystem) {
		buffer = new ArrayList<>();
		this.informationSystem = informationSystem;
	}

	// synchornized method for inserting items to the buffer, when a item get inserted it notify the waiting threads
	public synchronized void insert(ServiceCall item) {
		buffer.add(item);
		this.notifyAll();
	}

	// synchornized method for extracting the first item from the buffer, if the buffer is empty the threads wait
	public synchronized ServiceCall extractFirst() {
		while (buffer.isEmpty()) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return buffer.remove(0);
	}

	// synchornized method for checking if the buffer is empty
	public synchronized boolean isEmpty() {
		return buffer.isEmpty();
	}

	// synchornized method for getting the first item from the buffer, if the buffer is empty the threads wait
	public synchronized ServiceCall getFirst() {
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