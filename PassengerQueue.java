import java.util.Queue;
import java.util.LinkedList;

/** Class to simulate a queue of passengers. */
public class PassengerQueue {
    // Data Fields
    /** The queue of passengers. */
    private Queue<Passenger> theQueue;
    /** The number of passengers that have arrived. */
    private int numArrived;
    /** The number of passengers served. */
    private int numServed;
    /** The total time passengers were waiting. */
    private int totalWait;
    /** The name of this queue. */
    private String queueName;
    /** The average arrival rate. */
    private double arrivalRate;
    
    private int maxProcessingTime;
    
    // Constructor
    /** Construct a PassengerQueue with the given name.
    @param queueName The name of this queue
    */
    public PassengerQueue(String queueName) {
        numServed = 0;
        totalWait = 0;
        this.queueName = queueName;
        theQueue = new LinkedList<Passenger>();
    }
    
    /** Check if a new arrival has occurred.
    @param clock The current simulated time
    @param showAll Flag to indicate that detailed
    data should be output
    */
    public void checkNewArrival(int clock, boolean showAll) {
        if (Math.random() < arrivalRate) {
            theQueue.add(new Passenger(clock, maxProcessingTime));
            numArrived++;
            if (showAll) {
                System.out.println("Time is "
                + clock + ": "
                + queueName
                + " arrival, new queue size is "
                + theQueue.size());
            }
        }
    }
    
    /** Update statistics.
    pre: The queue is not empty.
    @param clock The current simulated time
    @param showAll Flag to indicate whether to show detail
    @return Time passenger is done being served
    */
    public int update(int clock, boolean showAll) {
        Passenger nextPassenger = theQueue.remove();
        int timeStamp = nextPassenger.getArrivalTime();
        int wait = clock - timeStamp;
        totalWait += wait;
        numServed++;
        if (showAll) {
            System.out.println("Time is " + clock
            + ": Serving "
            + queueName
            + " with time stamp "
            + timeStamp);
        }
        return clock + nextPassenger.getProcessingTime();
    }
    
    public void setArrivalRate(double rate){
        arrivalRate = rate / 60.0;
    }
    
    public void setMaxProcessingTime(int max){
        maxProcessingTime = max;
    }
    
    public boolean isEmpty(){
        if(theQueue.peek() == null){
            return true;
        }
        else{
            return false;
        }
    }
    
    public int getNumServed(){
        return numServed;
    }
    
    public int getNumArrived(){
        return numArrived;
    }
    
    public int size(){
        return numArrived - numServed;
    }
    
    public int getTotalWait(){
        return totalWait;
    }
}