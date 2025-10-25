import java.util.Comparator;    //import necessary libraries
import java.util.List;

public class FCFS {
    

    public void schedule(List<Process> processList) {
        int currentTime = 0;    //initialize currentTime

        processList.sort(Comparator.comparingInt(p -> p.arrival_Time));
        for (Process p : processList) { //iterate through process list
            if (currentTime < p.arrival_Time) { //if CPU is idle
                while (currentTime < p.arrival_Time) {
                    System.out.println("Time " + currentTime + "ms: CPU is idle");  //print idle message
                    currentTime++;  //increment currentTime
                }
            }

            p.startTime = currentTime;  //set start time for process when it arrives
            p.responseTime = currentTime - p.arrival_Time;  //calculate response time
            p.waitingTime = currentTime - p.arrival_Time;   //calculate waiting time

            for (int i = 0; i < p.burst_Time; i++) {    //run process for its burst time, no quantum or preemption
                System.out.println("Time " + currentTime + "ms: Running process " + p.pid);
                currentTime++;  //increment currentTime for each ms of burst time
            }

            p.finishTime = currentTime; //set finish time
            p.turnaroundTime = p.finishTime - p.arrival_Time;   //calculate turnaround time

            
        }
        CPUSim.computeData(processList, currentTime);   //call computeData method from CPUSim to print final data
    }


    
}
