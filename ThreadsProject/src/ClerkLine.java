import java.util.Vector;

public class ClerkLine {

    private Vector<Request> buffer;
    private InformationSystem informationSystem;

    public ClerkLine(InformationSystem informationSystem) {
        buffer = new Vector<Request>();
        this.informationSystem = informationSystem;
    }

    public synchronized void insert(Request item) {
//    	if (informationSystem.getIsDayOver()) {
//			Thread.interrupted();
//		}
        buffer.add(item);
        this.notifyAll();
    }
    
    public synchronized boolean contains(Request item) {
    	if (buffer.contains(item)) {
    		return true;
    	}
    	else {
    		return false;
    	}
    }

    public synchronized Request extractFirst() {
    	while (buffer.isEmpty()) {
//    		if (informationSystem.getIsDayOver()) {
//    			Thread.interrupted();
//    		}
    		try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
    	}
        return buffer.remove(0);
    }
    
    public synchronized boolean isEmpty() {
        return buffer.isEmpty();
    }
    
    public synchronized Request getFirst() {
    	while (buffer.isEmpty()) {
//    		if (informationSystem.getIsDayOver()) {
//    			Thread.interrupted();
//    		}
    		try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
    	}
    	return buffer.get(0);
    }
    
}
