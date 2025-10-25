import java.util.*;

public class SJF {

    public void schedule(List<Process> processList) {
        int currentTime = 0;    //initialize currentTime
        int completed = 0;  //initialize completed process count
        int n = processList.size();

        while (completed < n) { //while not all processes are completed
            List<Process> readyQueue = new ArrayList<>();   //create ready queue of Processes
            for (Process p : processList) { //iterate through process list
                if (p.arrival_Time <= currentTime && p.isCompleted == false) {  //if process has arrived and is not completed
                    readyQueue.add(p);  //add to ready queue
                }
            }

            if (readyQueue.isEmpty()) { //if no processes are ready
                System.out.println("Time " + currentTime + "ms: CPU is idle");  //print idle message
                currentTime++;
                continue; //I only just learned "continue"" for this project, only ever used return. Shoutout to StackOverflow
            }

            Process shortest = Collections.min(readyQueue, Comparator.comparingInt(p -> p.burst_Time)); //find process with min burst time

            shortest.startTime = currentTime;   //set start time for process
            shortest.responseTime = currentTime - shortest.arrival_Time;    //calculate response time
            shortest.waitingTime = currentTime - shortest.arrival_Time;  //calculate waiting time

            for (int i = 0; i < shortest.burst_Time; i++) {   //run process for its burst time
                System.out.println("Time " + currentTime + "ms: Running process " + shortest.pid);
                currentTime++;  //increment currentTime for each ms of burst time
            }

            shortest.finishTime = currentTime;  //set finish time
            shortest.turnaroundTime = shortest.finishTime - shortest.arrival_Time;  //calculate turnaround time
            shortest.isCompleted = true;    //mark process as completed
            completed++;    //increment completed process count
        }

        CPUSim.computeData(processList, currentTime);   //call computeData method from CPUSim to print final data
    }

    
}
