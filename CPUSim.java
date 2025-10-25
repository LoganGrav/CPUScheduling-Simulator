import java.io.File; //import libraries
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CPUSim {
    public static List<Process> processList = new ArrayList<>(); //list to hold processes
    private static int choice; //var for algorithm choice, used in switch statement (main) and final data printout (computeData)

    @SuppressWarnings("ConvertToTryWithResources") //try-with-resources kept causing issues
    public static void main(String[] args) {


        System.out.println("CPU Scheduling Simulator\nEnter input file name:"); //prompt user for file name
        Scanner scanner = new Scanner(System.in); //scanner for input
        String filename = scanner.nextLine();   //read file name
        while (!new File(filename).exists()) {  //make sure file exists, prompting again if it doesn't
            System.out.println("File not found. Please enter a valid file name:");
            filename = scanner.nextLine();
        }
        readFile(filename); //call method for reading file and making processList




        System.out.println("Select Scheduling Algorithm:\n1. FCFS\n2. SJF\n3. Priority Scheduling\n4. RR"); //prompt user for algorithm
            choice = scanner.nextInt(); 
            switch (choice) {
                case 1 -> { //case for FCFS
                    FCFS fcfs = new FCFS(); //initialize FCFS class
                    fcfs.schedule(processList); //call main scheduling method in FCFS class
                } 
                case 2 -> { //case for SJF
                    SJF sjf = new SJF();    //initialize SJF class
                    sjf.schedule(processList);  //call main scheduling method in SJF class
                }

                case 3 -> { //case for Priority Scheduling
                    PriorityScheduler ps = new PriorityScheduler(); //initialize PriorityScheduler class
                    ps.schedule(processList);   //call main scheduling method in PriorityScheduler class
                }
                case 4 -> { //case for RR
                    System.out.println("Enter Time Quantum:");  //prompt user for time quantum
                    int timeQuantum = scanner.nextInt();
                    RR rr = new RR();   //initialize RR class
                    rr.schedule(processList, timeQuantum);  //call main scheduling method in RR class with time quantum
                }

                default -> System.out.println("Invalid choice.");   //error handling for invalid choice. Doesn't loop like file reading does
            }

            scanner.close();    //close scanner
    }



//Method for reading input file and creating process list. String for arg
    public static void readFile(String filename) {
            try (Scanner fileScanner = new Scanner(new File(filename))) {   //try-with-resources worked here

                while (fileScanner.hasNextLine()) {     //iterate through file
                String line = fileScanner.nextLine();   
                String[] parts = line.split("\\s+");    //split line into each Int
                int Pid = Integer.parseInt(parts[0]);   //Process ID
                int Arrival_Time = Integer.parseInt(parts[1]);  //Arrival Time
                int Burst_Time = Integer.parseInt(parts[2]);    //Burst Time
                int Priority = Integer.parseInt(parts[3]);  //Priority
                processList.add(new Process(Pid, Arrival_Time, Burst_Time, Priority));  //create new process and add to list
                }
            } catch (Exception e) {
            System.out.println(e);  //error handling
        }
    }




//Method for computing and printing final data. ProcessList and currentTime for args. Called by algorithm classes, not CPUSim
    public static void computeData(List<Process> processList, int currentTime) {
        float totalWaitingTime = 0; //variables for computing averages and CPU utilization
        float totalTurnaroundTime = 0;
        float totalResponseTime = 0;
        float totalUseTime = 0;
        float averageWaitingTime;
        float averageTurnaroundTime;
        float averageResponseTime;
        float cpuUtilization;
        int n = processList.size(); //number of processes

        for (Process p : processList) { //iterate through process list
            totalWaitingTime += p.waitingTime;  //find total waiting time
            totalTurnaroundTime += p.turnaroundTime;    //find total turnaround time
            totalResponseTime += p.responseTime;    //find total response time
            totalUseTime += p.burst_Time;   //find total CPU use time (since every process will finish using proper inputs)
        }
        averageWaitingTime = totalWaitingTime / n;  //calculate averages
        averageTurnaroundTime = totalTurnaroundTime / n;
        averageResponseTime = totalResponseTime / n;
        cpuUtilization = (totalUseTime / currentTime) * 100;    //calculate CPU utilization

        
        switch (choice) {   //print algorithm name based on choice
            case 1 -> {
                System.out.println("\nFCFS:");
            }
            case 2 -> {
                System.out.println("\nSJF:");
            }
            case 3 -> {
                System.out.println("\nPriority Scheduling:");
            }
            case 4 -> {
                System.out.println("\nRR:");
            }
        }   //print data
        System.out.println("Average Waiting Time: " + averageWaitingTime + "ms");
        System.out.println("Average Turnaround Time: " + averageTurnaroundTime + "ms");
        System.out.println("Average Response Time: " + averageResponseTime + "ms");
        System.out.println("CPU Utilization: " + cpuUtilization + "%");


    }
}
