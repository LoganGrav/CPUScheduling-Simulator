public class Process {
    int pid;    //process ID
    int arrival_Time;   //arrival time
    int burst_Time;   //burst time
    int priority;   //priority, only used for Priority Scheduling
    int startTime;  //start time
    int finishTime; //finish time
    int waitingTime;    //waiting time
    int turnaroundTime; //turnaround time
    int responseTime;   //response time
    int remainingTime;  //remaining time, only used forpreemptive algorithms
    boolean isCompleted = false;    //completion status, not used for FCFS

    public Process(int pid, int arrival_Time, int burst_Time, int priority) {
        this.pid = pid; //initialize process attributes
        this.arrival_Time = arrival_Time;
        this.burst_Time = burst_Time;
        this.priority = priority;
        this.remainingTime = burst_Time; //used for preemptive algorithms
        this.startTime = -1; 
    }

    
}
