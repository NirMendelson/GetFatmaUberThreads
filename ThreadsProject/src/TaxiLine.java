import java.util.Vector;

public class TaxiLine {

    private Vector<Request> buffer;

    public TaxiLine() {
        buffer = new Vector<Request>();
    }

    public synchronized void insert(Request item) {
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
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return buffer.get(0);
    }

    
}
