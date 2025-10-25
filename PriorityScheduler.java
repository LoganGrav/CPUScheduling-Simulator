import java.util.*;

public class PriorityScheduler {


    public void schedule(List<Process> processList) {
        int currentTime = 0;    //initialize currentTime
        int completed = 0;
        int n = processList.size();
        Process currentProcess = null;  //object to track the currently running process

        while (completed < n) { //while not all processes are completed
            List<Process> readyQueue = new ArrayList<>();   //create ready queue of Processes
            for (Process p : processList) { //iterate through process list
                if (p.arrival_Time <= currentTime && !p.isCompleted) {  //if process has arrived and is not completed
                    readyQueue.add(p);  //add to ready queue
                }
            }

            if (readyQueue.isEmpty()) { //if no processes are ready, idle
                System.out.println("Time " + currentTime + "ms: CPU is idle");
                currentTime++;  
                continue;
            }

            Process highestPriority = Collections.min(readyQueue, Comparator.comparingInt(p -> p.priority));    //find process with highest priority

            if (currentProcess == null || currentProcess != highestPriority) {  //if no process is currently running or a higher priority process is found
                currentProcess = highestPriority;   //set current process to highest priority process
                if (currentProcess.startTime == -1) {   //if process is starting for the first time (hence why startTime is -1 by default in Process)
                    currentProcess.startTime = currentTime; //set start time
                    currentProcess.responseTime = currentTime - currentProcess.arrival_Time;    //calculate response time
                }
            } 

            System.out.println("Time " + currentTime + "ms: Running process " + currentProcess.pid);    //run current process
            currentProcess.remainingTime--; //decrement remaining time
            currentTime++;  //increment current time

            if (currentProcess.remainingTime == 0) {    //if process is completed, set variables
                currentProcess.finishTime = currentTime;
                currentProcess.turnaroundTime = currentProcess.finishTime - currentProcess.arrival_Time;
                currentProcess.waitingTime = currentProcess.turnaroundTime - currentProcess.burst_Time;
                currentProcess.isCompleted = true;
                completed++;
                currentProcess = null;
            }

        }
        CPUSim.computeData(processList, currentTime);   //call computeData method from CPUSim to print final data
}
}