import java.util.Scanner;

/**
 * @author Nasif Ahmed
 */

public class AirlineCheckinSim {
    // Data Fields
    /** Queue of frequent flyers. */
    private PassengerQueue frequentFlyerQueue =
    new PassengerQueue("Frequent Flyer");
    /** Queue of regular passengers. */
    private PassengerQueue regularPassengerQueue =
    new PassengerQueue("Regular Passenger");
    /** Maximum number of frequent flyers to be served
    before a regular passenger gets served. */
    private int frequentFlyerMax;
    /** Maximum time to service a passenger. */
    private int maxProcessingTime;
    /** Total simulated time. */
    private int totalTime;
    /** If set true, print additional output. */
    private boolean showAll;
    /** Simulated clock. */
    private int clock = 0;
    /** Time that the agent will be done with the current passenger.*/
    private int timeDone;
    /** Number of frequent flyers served since the
    last regular passenger was served. */
    private int frequentFlyersSinceRP;
    
    Scanner scan = new Scanner(System.in);
    
    /** Main method.
    @param args Not used
    */
    public static void main(String args[]) {
        AirlineCheckinSim sim = new AirlineCheckinSim();
        sim.enterData();
        sim.runSimulation();
        sim.showStats();
        System.exit(0);
    }
    
    public void enterData(){
        System.out.println("Frequent flyers per hour?");
        frequentFlyerQueue.setArrivalRate(scan.nextDouble());
        
        System.out.println("Regular passengers per hour?");
        regularPassengerQueue.setArrivalRate(scan.nextDouble());
        
        System.out.println("Frequent flyers tended to before a regular passenger is?");
        frequentFlyerMax = scan.nextInt();
        
        System.out.println("Maximum amount of minutes it takes to process someone's ticket?");
        int input = scan.nextInt();
        frequentFlyerQueue.setMaxProcessingTime(input);
        regularPassengerQueue.setMaxProcessingTime(input);
        
        System.out.println("Simulation time?");
        totalTime = scan.nextInt();
        
        String show;
        System.out.println("Display minute-by-minute trace of the simulation? (Y/N)");
        do{
            show = scan.nextLine();
        }while(!(show.equals("Y") || show.equals("N")));
        if(show.equals("Y")){
            showAll = true;
        }
        else{
            showAll = false;
        }
    }
    
    private void runSimulation() {
        for (clock = 0; clock < totalTime; clock++) {
            frequentFlyerQueue.checkNewArrival(clock, showAll);
            regularPassengerQueue.checkNewArrival(clock, showAll);
            if (clock >= timeDone) {
                startServe();
            }
        }
    }
    
    private void startServe() {
        if (!frequentFlyerQueue.isEmpty()
        && ((frequentFlyersSinceRP <= frequentFlyerMax)
        || regularPassengerQueue.isEmpty())) {
            // Serve the next frequent flyer.
            frequentFlyersSinceRP++;
            timeDone = frequentFlyerQueue.update(clock, showAll);
        } else if (!regularPassengerQueue.isEmpty()) {
            // Serve the next regular passenger.
            frequentFlyersSinceRP = 0;
            timeDone = regularPassengerQueue.update(clock, showAll);
        } else if (showAll) {
            System.out.println("Time is " + clock
            + " server is idle");
        }
    }
    
    /** Method to show the statistics. */
    private void showStats() {
        System.out.println
        ("\nThe number of regular passengers served was "
        + regularPassengerQueue.getNumServed());
        double averageRegularPassengerWaitingTime =
        (double) regularPassengerQueue.getTotalWait()
        / (double) regularPassengerQueue.getNumServed();
        System.out.println(" with an average waiting time of "
                            + averageRegularPassengerWaitingTime);
                            
        System.out.println("The number of frequent flyers served was "
                            + frequentFlyerQueue.getNumServed());
        double averageFrequentFlyerWaitingTime = 
        (double) frequentFlyerQueue.getTotalWait()
        / (double) frequentFlyerQueue.getNumServed();
        System.out.println(" with an average waiting time of "
        + averageFrequentFlyerWaitingTime);
        
        System.out.println("Passengers in frequent flyer queue: "
        + frequentFlyerQueue.size());
        System.out.println("Passengers in regular passenger queue: "
        + regularPassengerQueue.size());
    }
}