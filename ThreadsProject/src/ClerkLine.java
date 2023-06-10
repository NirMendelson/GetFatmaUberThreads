import java.util.Vector;

public class ClerkLine {

	private Vector<Request> buffer;

	// a constructor for the class
	public ClerkLine() {
		buffer = new Vector<Request>();
	}

	// synchornized method for inserting items to the buffer, when a item get inserted it notify the waiting threads
	public synchronized void insert(Request item) {
		buffer.add(item);
		this.notifyAll();
	}

	// synchornized method that checks if the buffer contains an item
	public synchronized boolean contains(Request item) {
		while (buffer.isEmpty()) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if (buffer.contains(item)) {
			return true;
		}
		else {
			return false;
		}
	}

	// synchornized method for extracting the first item from the buffer, if the buffer is empty the clerks wait
	public synchronized Request extractFirst() {
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
	public synchronized Request getFirst() {
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