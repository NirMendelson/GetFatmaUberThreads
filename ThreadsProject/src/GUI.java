import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class GUI extends JFrame {

	private JLabel titleLabel;
	private JLabel workingTimeLabel;
	private JLabel numDriversLabel;
	private JTextField workingTimeField;
	private JTextField numDriversField;
	private JButton startButton;
	private JButton exitButton;

	private double workingTime;
	private int numDrivers;

	public GUI() {
		// Set up the GUI components
		titleLabel = new JLabel("Welcome to GetFatmaUber");
		workingTimeLabel = new JLabel("Car officers working time");
		numDriversLabel = new JLabel("Number of drivers");
		workingTimeField = new JTextField("1");
		numDriversField = new JTextField("4");
		startButton = new JButton("Start");
		exitButton = new JButton("Exit");

		// Set the layout manager
		setLayout(new GridLayout(4, 2));

		// Add components to the frame
		add(titleLabel);
		add(new JLabel()); // Empty label for spacing
		add(workingTimeLabel);
		add(workingTimeField);
		add(numDriversLabel);
		add(numDriversField);
		add(startButton);
		add(exitButton);

		// Set action listener for the start button
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Get the input values
				try {
					workingTime = Double.parseDouble(workingTimeField.getText());
					numDrivers = Integer.parseInt(numDriversField.getText());

					// Run the program with the new values
					runProgram();
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "Invalid input! Please enter numeric values.");
				}
			}
		});

		// Set action listener for the exit button
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Exit the program
				System.exit(0);
			}
		});

		// Set frame properties
		setTitle("GetFatmaUber");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setLocationRelativeTo(null); // Center the frame on the screen
		setVisible(true);
	}

	private void runProgram() {
		InformationSystem informationSystem = new InformationSystem();
		ClerkLine clerkLine = new ClerkLine(informationSystem);

		SchedulerLine schedulerLine = new SchedulerLine(informationSystem);
		ManagerLine managerLine = new ManagerLine();
		ReadyRideLine readyRideLine = new ReadyRideLine();

		// Read requests from the file
		String filePath = "C:\\Users\\nirme\\eclipse-workspace\\ThreadsProject\\src\\Data.txt";
		ArrayList<Request> requests = requestTxtReader.readRequests(filePath, clerkLine);

		// Start clerk threads
		for (int i = 0; i < 3; i++) {
			Clerk clerk = new Clerk(i, clerkLine, informationSystem, schedulerLine, managerLine);
			Thread clerkThread = new Thread(clerk);
			clerkThread.start();
		}

		// Start scheduler threads
		Scheduler scheduler1 = new Scheduler(1, "Tel Aviv", schedulerLine, informationSystem);
		Scheduler scheduler2 = new Scheduler(2, "Jerusalem", schedulerLine, informationSystem);
		Thread schedulerThread1 = new Thread(scheduler1);
		Thread schedulerThread2 = new Thread(scheduler2);
		schedulerThread1.start();
		schedulerThread2.start();
		

		// Start car officer threads
		CarOfficer carOfficer1 = new CarOfficer(1, informationSystem, readyRideLine);
		CarOfficer carOfficer2 = new CarOfficer(2, informationSystem, readyRideLine);
		CarOfficer carOfficer3 = new CarOfficer(3, informationSystem, readyRideLine);
		Thread carOfficerThread1 = new Thread(carOfficer1);
		Thread carOfficerThread2 = new Thread(carOfficer2);
		Thread carOfficerThread3 = new Thread(carOfficer3);
		carOfficerThread1.start();
		carOfficerThread2.start();
		carOfficerThread3.start();
		


		// create and start manager
		Manager manager = new Manager(managerLine, 8, informationSystem);
		Thread managerThread = new Thread(manager);
		managerThread.start();

		// Start request threads from the ArrayList
		for (Request request : requests) {
			Thread requestThread = new Thread(request);
			requestThread.start();
		}

		// Start driver threads
		//		Driver driver = new Driver(1, "A", readyRideLine, informationSystem, manager);
		//		Thread driverThread = new Thread(driver);
		//		driverThread.start();

		if (numDrivers % 2 == 0) {
			for (int i = 0; i < numDrivers/2; i++) {
				Driver driver = new Driver(i, 'A', readyRideLine, informationSystem, manager);
				Thread driverThread = new Thread(driver);
				driverThread.start();
			}
			for (int i = numDrivers/2; i < numDrivers; i++) {
				Driver driver = new Driver(i, 'B', readyRideLine, informationSystem, manager);
				Thread driverThread = new Thread(driver);
				driverThread.start();
			}
		}
		else {
			for (int i = 0; i < (numDrivers-1)/2; i++) {
				Driver driver = new Driver(i, 'A', readyRideLine, informationSystem, manager);
				Thread driverThread = new Thread(driver);
				driverThread.start();
			}
			for (int i = (numDrivers-1)/2; i < (numDrivers-1); i++) {
				Driver driver = new Driver(i, 'B', readyRideLine, informationSystem, manager);
				Thread driverThread = new Thread(driver);
				driverThread.start();
			}
			Random random = new Random();
			char license;

			if (random.nextDouble() < 0.5) {
				license = 'A';
			} else {
				license = 'B';
			}
			Driver driver = new Driver(numDrivers, license, readyRideLine, informationSystem, manager);
			Thread driverThread = new Thread(driver);
			driverThread.start();

		}

		informationSystem.setNumOfDrivers(numDrivers);
		informationSystem.setCarOfficersWorkingTime(workingTime);
	}


	public static void main(String[] args) {
		// Create an instance of the GetFatmaUberGUI
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new GUI();
			}
		});
	}
}
