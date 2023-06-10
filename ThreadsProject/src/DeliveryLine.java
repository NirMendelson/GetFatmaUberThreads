import java.util.Vector;

public class DeliveryLine {

    private Vector<UpgradedServiceCall> buffer;

    public DeliveryLine() {
        buffer = new Vector<UpgradedServiceCall>();
    }

    public synchronized void insert(UpgradedServiceCall item) {
        buffer.add(item);
        this.notifyAll();
    }
   

    public synchronized UpgradedServiceCall extractFirst() {
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
    
    public synchronized UpgradedServiceCall getFirst() {
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