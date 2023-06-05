import java.util.ArrayList;

public class ManagerLine {

    private ArrayList<Request> buffer;

    public ManagerLine() {
        buffer = new ArrayList<>();
    }

    public synchronized void insert(Request item) {
        buffer.add(item);
        this.notifyAll();
    }
    
    public synchronized void insertToStart(Request item) {
    	buffer.add(0, item);
    }
    
    public synchronized boolean contains(Request item) {
        return buffer.contains(item);
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