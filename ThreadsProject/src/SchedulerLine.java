import java.util.ArrayList;

public class SchedulerLine {

    private ArrayList<ServiceCall> buffer;
    private InformationSystem informationSystem;


    public SchedulerLine(InformationSystem informationSystem) {
        buffer = new ArrayList<>();
        this.informationSystem = informationSystem;
    }

    public synchronized void insert(ServiceCall item) {
//    	if (informationSystem.getIsDayOver()) {
//			Thread.interrupted();
//		}
        buffer.add(item);
        this.notifyAll();
    }

    public synchronized ServiceCall extractFirst() {
        while (buffer.isEmpty()) {
//        	if (informationSystem.getIsDayOver()) {
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

    public synchronized ServiceCall getFirst() {
        while (buffer.isEmpty()) {
//        	if (informationSystem.getIsDayOver()) {
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