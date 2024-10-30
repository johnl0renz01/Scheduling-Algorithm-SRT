import java.io.IOException;
import java.lang.Thread;
import java.lang.Integer;
import java.util.*;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class Main {

	public static final String RED_FG = "\u001B[31m";
	public static final String BLUE_FG = "\u001B[34m";
	public static final String GREEN_FG = "\u001B[32m";
	public static final String COLOR_RESET = "\u001B[0m";

	private static final DecimalFormat df = new DecimalFormat("0.00");

	public static void header() throws IOException, InterruptedException {
		new ProcessBuilder("clear").inheritIO().start().waitFor();
		System.out.println("FL-M5: ACT3 - SRT");
		System.out.println("DELA CRUZ, JOHN LORENZ N.\n");
	}

	public static void pressEnterToContinue() {
		System.out.print("\nPress \'Enter\' key to continue...");
		try {
			Scanner scanner = new Scanner(System.in);
			scanner.nextLine();
		} catch (Exception e) {
		}
	}

	public static void processList(int processQuantity, String processVariables[]) {
		System.out.print("Process List: ");
		for (int i = 0; i < processQuantity; i++) {
			if (i == (processQuantity - 1)) {
				System.out.println(processVariables[i]);
			} else {
				System.out.print(processVariables[i] + ", ");
			}
		}
	}

	public static void table(int processQuantity, int processArrivalTime[], String burstTimeText[],
			int sortedTimelineValues[], int timelineLimit, int timelineCounter, String timelineVariables[],
			String processVariables[]) {

		String sortedCompletionTime[] = new String[processQuantity];
		for (int i = 0; i < processQuantity; i++) {
			if (sortedTimelineValues[i] == 0) {
				sortedCompletionTime[i] = " ";
			} else {
				sortedCompletionTime[i] = String.valueOf(sortedTimelineValues[i]);
			}
		}

		Formatter table = new Formatter();
		table.format("\n%1s%2s%1s%2s%1s%2s%1s", BLUE_FG + "Process" + COLOR_RESET, "|",
				BLUE_FG + "Arrival Time" + COLOR_RESET, "|", GREEN_FG + "   Burst Time   " + COLOR_RESET, "|",
				BLUE_FG + "Completion Time" + COLOR_RESET + "|");

		for (int i = 0; i < processQuantity; i++) {
			table.format("\n%13s", COLOR_RESET + "P" + (i + 1) + COLOR_RESET);
			table.format("%4s", "|");

			table.format("%15s", COLOR_RESET + processArrivalTime[i] + COLOR_RESET);
			table.format("%7s", "|");
			table.format("%18s", COLOR_RESET + burstTimeText[i] + COLOR_RESET);
			table.format("%8s", "|");

			table.format("%17s", RED_FG + sortedCompletionTime[i] + COLOR_RESET);
			table.format("%8s", "|");
		}
		System.out.println(table);
	}

	public static void timeline(int timelineCounter, int timelineValues[], String timelineVariables[]) {
		System.out.println("\nTimeline:");
		Formatter timeline = new Formatter();

		timeline.format("\n%1s", GREEN_FG + "");
		for (int i = 0; i < timelineCounter; i++) {
			timeline.format("%2s%5s", timelineValues[i], "");
		}

		timeline.format(COLOR_RESET + "\n%1s", "| ");
		for (int i = 0; i < timelineCounter; i++) {
			if (timelineVariables[i].equals("idle")) {
				timeline.format("%1s", timelineVariables[i] + " | ");
			} else {
				timeline.format("%1s", " " + timelineVariables[i] + "  | ");
			}

		}
		System.out.println(timeline);
	}

	public static void main(String[] args) throws IOException, InterruptedException {

		header();

		int processQuantity = 0;
		int processCounter = 0;
		boolean flag = false;

		boolean validProcessesQuantity = false;
		System.out.print("Enter no. of Processes: ");

		do {
			try {
				do {
					Scanner input = new Scanner(System.in);
					processQuantity = input.nextInt();
					if (processQuantity <= 0) {
						System.out.println("Invalid input. Please enter a positive number.");
						Thread.sleep(1250);
						header();
						System.out.print("Enter no. of Processes: ");
					}
				} while (processQuantity <= 0);
				validProcessesQuantity = true;
			} catch (InputMismatchException ex) {
				validProcessesQuantity = false;
				System.out.println("Invalid input. Please enter a valid number.");
				Thread.sleep(1250);
				header();
				System.out.print("Enter no. of Processes: ");
			}
		} while (!validProcessesQuantity);

		String processVariables[] = new String[processQuantity];
		for (int i = 0; i < processQuantity; i++) {
			processVariables[i] = "P" + String.valueOf(i + 1);
		}

		String processListVariables[] = new String[processQuantity];
		for (int i = 0; i < processQuantity; i++) {
			processListVariables[i] = "P" + String.valueOf(i + 1);
		}
		int processArrivalTime[] = new int[processQuantity];
		int processBurstTime[] = new int[processQuantity];

		do {
			int arrivalInput = 0;
			int burstInput = 0;
			boolean validArrival = false;
			boolean validBurst = false;
			header();
			System.out.println("Enter no. of Processes: " + processQuantity);
			System.out.println("\nProcess " + (processCounter + 1) + ": ");
			System.out.print("Arrival Time: ");
			do {
				try {
					Scanner input = new Scanner(System.in);
					arrivalInput = input.nextInt();
					if (arrivalInput >= 0) {
						validArrival = true;
					} else {
						System.out.println("Invalid input. Please enter a positive number.");
						Thread.sleep(1250);
						header();
						System.out.println("Enter no. of Processes: " + processQuantity);
						System.out.println("\nProcess " + (processCounter + 1) + ": ");
						System.out.print("Arrival Time: ");
					}
				} catch (InputMismatchException ex) {
					System.out.println("Invalid input. Please enter a valid number.");
					Thread.sleep(1250);
					header();
					System.out.println("Enter no. of Processes: " + processQuantity);
					System.out.println("\nProcess " + (processCounter + 1) + ": ");
					System.out.print("Arrival Time: ");
				}
			} while (!validArrival);

			System.out.print("Burst Time: ");

			do {
				try {
					Scanner input = new Scanner(System.in);
					burstInput = input.nextInt();
					if (burstInput > 0) {
						validBurst = true;
					} else {
						System.out.println("Invalid input. Please enter a positive number.");
						Thread.sleep(1250);
						header();
						System.out.println("Enter no. of Processes: " + processQuantity);
						System.out.println("\nProcess " + (processCounter + 1) + ": ");
						System.out.println("Arrival Time: " + arrivalInput);
						System.out.print("Burst Time: ");
					}
				} catch (InputMismatchException ex) {
					System.out.println("Invalid input. Please enter a valid number.");
					Thread.sleep(1250);
					header();
					System.out.println("Enter no. of Processes: " + processQuantity);
					System.out.println("\nProcess " + (processCounter + 1) + ": ");
					System.out.println("Arrival Time: " + arrivalInput);
					System.out.print("Burst Time: ");
				}
			} while (!validBurst);

			processArrivalTime[processCounter] = arrivalInput;
			processBurstTime[processCounter] = burstInput;
			processCounter++;

		} while (processCounter < processQuantity);

		int timelineValues[] = new int[processQuantity * 2];
		int burstTimeComparison[] = new int[processQuantity];
		String timelineVariables[] = new String[processQuantity * 2];

		int originalBurstTime[] = new int[processQuantity];
		int sortedTimelineValues[] = new int[processQuantity];
		String burstTimeText[] = new String[processQuantity];

		for (int i = 0; i < processQuantity; i++) {
			burstTimeText[i] = String.valueOf(processBurstTime[i]);
			originalBurstTime[i] = processBurstTime[i];
			sortedTimelineValues[i] = 0;
		}

		header();
		System.out.println("Enter no. of Processes: " + processQuantity);
		processList(processQuantity, processVariables);
		pressEnterToContinue();

		header();
		System.out.println("Enter no. of Processes: " + processQuantity);
		processList(processQuantity, processVariables);
		table(processQuantity, processArrivalTime, burstTimeText, sortedTimelineValues, 0, 0, timelineVariables,
				processVariables);

		for (int i = 0; i < processQuantity; i++) {
			burstTimeComparison[i] = 0;
		}

		for (int i = 0; i < processQuantity * 2; i++) {
			timelineValues[i] = 0;
			timelineVariables[i] = "";
		}

		int timelineCounter = 0;
		int processIndex = 0;
		int currentArrival = 0;
		int totalArrivalTime = 0;
		int shortestTime = 0;

		// first variable
		for (int i = 0; i < processQuantity; i++) {
			if (i == 0) {
				currentArrival = processArrivalTime[i];
				processIndex = i;
			}

			if (processArrivalTime[i] <= currentArrival) {
				if (processArrivalTime[i] < currentArrival) {
					processIndex = i;
				}
				currentArrival = processArrivalTime[i];

				if (currentArrival == 0) {
					flag = true;
				}

				if (shortestTime == 0) {
					shortestTime = processBurstTime[i];
					processIndex = i;
				} else if (processBurstTime[i] < shortestTime) {
					shortestTime = processBurstTime[i];
					processIndex = i;
				}
			}
		}

		if (!flag) {
			timelineValues[timelineCounter] = processArrivalTime[processIndex];
			timelineVariables[timelineCounter] = "idle";
			totalArrivalTime = processArrivalTime[processIndex];
			timelineCounter++;
		}

		int timeCounter = 0;
		int timelineLimit = processQuantity;

		boolean processChanged = false;

		for (int counter = 0; counter < processQuantity; counter++) {
			boolean repeat = true;
			flag = false;

			while (repeat) {
				for (int i = 0; i < processQuantity; i++) {
					if (processArrivalTime[i] != 0 && totalArrivalTime >= processArrivalTime[i]) {
						if (processBurstTime[i] != 0 && processBurstTime[i] < shortestTime) {

							if (timelineCounter == 0) {
								timelineValues[timelineCounter] = timeCounter;
							} else {
								timelineValues[timelineCounter] = timelineValues[timelineCounter - 1] + timeCounter;
							}

							timelineVariables[timelineCounter] = processVariables[processIndex];
							burstTimeText[processIndex] = burstTimeText[processIndex] + "\\"
									+ String.valueOf(shortestTime);
							processBurstTime[processIndex] = shortestTime;
							shortestTime = processBurstTime[i];
							processIndex = i;
							processChanged = true;

						}
					}
				}

				if (shortestTime == 0) {
					if (timelineCounter == 0) {
						timelineValues[timelineCounter] = timeCounter;
					} else {
						timelineValues[timelineCounter] = timelineValues[timelineCounter - 1] + timeCounter;
					}

					timelineVariables[timelineCounter] = processVariables[processIndex];
					burstTimeText[processIndex] = burstTimeText[processIndex] + "\\" + String.valueOf(shortestTime);
					processBurstTime[processIndex] = shortestTime;
					processChanged = true;
					repeat = false;
				}

				if (processChanged) {
					pressEnterToContinue();
					// print timeline
					header();
					System.out.println("Enter no. of Processes: " + processQuantity);
					processList(processQuantity, processVariables);
					table(processQuantity, processArrivalTime, burstTimeText, sortedTimelineValues, timelineLimit,
							timelineCounter, timelineVariables, processVariables);
					timeline(timelineCounter, timelineValues, timelineVariables);

					timelineCounter++;
					timeCounter = 0;
					processChanged = false;

					if (!repeat) {
						break;
					}
				}

				timeCounter++;
				totalArrivalTime++;
				shortestTime--;

			}

			boolean isTaken = false;
			int availableBurstTime[] = new int[processQuantity];
			if (shortestTime == 0) {
				for (int i = 0; i < processQuantity; i++) {
					if (totalArrivalTime >= processArrivalTime[i]) {
						if (processBurstTime[i] != 0 && !isTaken) {
							shortestTime = processBurstTime[i];
							processIndex = i;
							isTaken = true;
						}

						if (processBurstTime[i] != 0 && processBurstTime[i] < shortestTime && isTaken) {//
							shortestTime = processBurstTime[i];
							processIndex = i;
							flag = true;
						}
					}
				}

				boolean arrivalTimeCheck = true;
				if (!flag) {
					for (int i = 0; i < processQuantity; i++) {
						availableBurstTime[i] = processBurstTime[i];
					}

					Arrays.sort(availableBurstTime);
					for (int i = 0; i < processQuantity; i++) {
						if (availableBurstTime[i] != 0) {
							for (int j = 0; j < processQuantity; j++) {
								if (availableBurstTime[i] == processBurstTime[j]) {
									if (totalArrivalTime < processArrivalTime[j]) {
										arrivalTimeCheck = false;
										break;
									}
								}
							}

							if (arrivalTimeCheck) {
								shortestTime = availableBurstTime[i];
								break;
							}
						}

					}
				}
			}
		}

		pressEnterToContinue();
		// OUTSIDE
		for (int i = 0; i < processQuantity; i++) {
			for (int j = ((processQuantity * 2) - 1); j > 0; j--) {
				if (processVariables[i].equals(timelineVariables[j])) {
					sortedTimelineValues[i] = timelineValues[j];
					break;
				}
			}
		}

		int valuesTAT[] = new int[processQuantity];
		int valuesWT[] = new int[processQuantity];

		for (int i = 0; i < processQuantity; i++) {
			valuesTAT[i] = sortedTimelineValues[i] - processArrivalTime[i];
			valuesWT[i] = valuesTAT[i] - originalBurstTime[i];
		}

		// computing average TAT & WT
		float averageTAT = 0;
		float averageWT = 0;

		for (int i = 0; i < processQuantity; i++) {
			averageTAT += valuesTAT[i];
			averageWT += valuesWT[i];
		}

		averageTAT /= processQuantity;
		averageWT /= processQuantity;

		header();
		System.out.println("Enter no. of Processes: " + processQuantity);
		processList(processQuantity, processVariables);
		table(processQuantity, processArrivalTime, burstTimeText, sortedTimelineValues, timelineLimit, timelineCounter,
				timelineVariables, processVariables);
		timeline(timelineCounter, timelineValues, timelineVariables);

		System.out.println("\nAverage Turnaround Time: " + RED_FG + df.format(averageTAT) + COLOR_RESET);
		System.out.println("Average Waiting Time: " + RED_FG + df.format(averageWT) + COLOR_RESET);
	}
}