import java.util.HashSet;   //import necessary libraries
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class RR {
    public void schedule(List<Process> processList, int timeQuantum) {
       Queue<Process> readyQueue = new LinkedList<>();  //create ready queue. Easier than arrayList for this
        int currentTime = 0; //initialize vars
        int completed = 0;
        int n = processList.size();
        int quantum = timeQuantum;


Set<Process> seen = new HashSet<>();    //Hashset to track seen processes and prevent dupes

while (completed < n) { //while processes are left
    for (Process p : processList) { //check for new arrivals
        if (p.arrival_Time <= currentTime && !seen.contains(p)) { //if its arrived but not seen,
            readyQueue.add(p);  //add to ready queue
            seen.add(p);    //add to seen
        }
    }

    if (readyQueue.isEmpty()) { //if nothing in ready queue, idle CPU
        System.out.println("Time " + currentTime + "ms: CPU is idle");
        currentTime++;
        continue;
    }

    Process current = readyQueue.poll();    //get next process

    if (current.startTime == -1) {  //if first time running
        current.startTime = currentTime;    //set start time and response time
        current.responseTime = currentTime - current.arrival_Time;
    }

    int timer = Math.min(quantum, current.remainingTime);   //determine if process will end before quantum
    for (int i = 0; i < timer; i++) {   //simulate each ms of execution
        System.out.println("Time " + currentTime + "ms: Running process " + current.pid);
        currentTime++;


        for (Process p : processList) { //during execution, check for new arrivals again
            if (p.arrival_Time == currentTime && !seen.contains(p)) {
                readyQueue.add(p);
                seen.add(p);
            }
        }
    }

    current.remainingTime -= timer; //update remaining time

    if (current.remainingTime == 0) {   //if process is complete, update vars
        current.finishTime = currentTime;
        current.turnaroundTime = current.finishTime - current.arrival_Time;
        current.waitingTime = current.turnaroundTime - current.burst_Time;
        current.isCompleted = true;
        completed++;
    } else {
        readyQueue.add(current); //if process ongoing, add back to queue
    }
}
    CPUSim.computeData(processList, currentTime);   //call computeData method from CPUSim to print final data
}
}