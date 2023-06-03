import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class requestTxtReader {
    public static ArrayList<Request> readRequests(String filePath, ClerkLine clerkLine) {
        ArrayList<Request> requests = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isFirstLine = true; // Track the first line

            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // Skip the first line
                }

                String[] data = line.split("\t");
                if (data.length == 6) {
                    int id = Integer.parseInt(data[0]);
                    String serviceType = data[1];
                    String serviceArea = data[2];
                    double distance = Double.parseDouble(data[3]);
                    double tempTime = Double.parseDouble(data[4]);
                    long time = (long) tempTime;
                    int arrival = Integer.parseInt(data[5]);

                    Request request = new Request(id, serviceType, serviceArea, distance, time, arrival, clerkLine);
                    requests.add(request);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return requests;
    }
}
