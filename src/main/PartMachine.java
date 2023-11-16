package main;

import java.util.Random;

import data_structures.ListQueue;
import interfaces.Queue;

/**
 * Represents a machine that produces car parts.
 */
public class PartMachine {
	private int id;
	private CarPart p1;
	private int period;
	private double weightError;
	private int chanceOfDefective;
	private Queue<Integer> timer;
	private Queue<CarPart> conveyorBelt;
	private int totalPartsProduced;
	
	/**
     * Constructor for PartMachine.
     * 
     * @param id the machine ID
     * @param p1 the initial car part template
     * @param period the production period
     * @param weightError the allowed weight error for produced parts
     * @param chanceOfDefective the chance of a part being defective
     */
    public PartMachine(int id, CarPart p1, int period, double weightError, int chanceOfDefective) {
        this.id = id;
        this.p1 = p1;
        this.period = period;
        this.weightError = weightError;
        this.chanceOfDefective = chanceOfDefective;
        this.timer = new ListQueue<Integer>();
        for (int i = period - 1; i >= 0; i--) {
        	timer.enqueue(i);
        }
        this.conveyorBelt = new ListQueue<CarPart>();
        for (int i = 0; i < 10; i++) {
        	conveyorBelt.enqueue(null);
        }
        this.totalPartsProduced = 0;
    }
    
    /**
     * Gets the machine ID.
     * 
     * @return the machine ID
     */
    public int getId() {
    	return id;
    }
    
    /**
     * Sets the machine ID.
     * 
     * @param id the machine ID to set
     */
    public void setId(int id) {
        this.id = id;
    }
    
    /**
     * Gets the timer queue.
     * 
     * @return the timer queue
     */
    public Queue<Integer> getTimer() {
    	return timer;
    }
    
    /**
     * Sets the timer queue.
     * 
     * @param timer the timer queue to set
     */
    public void setTimer(Queue<Integer> timer) {
        this.timer = timer;
    }
    
    /**
     * Gets the current car part template.
     * 
     * @return the current car part template
     */
    public CarPart getPart() {
    	return p1;
    }
    
    /**
     * Sets the current car part template.
     * 
     * @param part1 the car part template to set
     */
    public void setPart(CarPart part1) {
        this.p1 = part1;
    }
    
    /**
     * Gets the conveyor belt queue.
     * 
     * @return the conveyor belt queue
     */
    public Queue<CarPart> getConveyorBelt() {
        return conveyorBelt;
    }
    
    /**
     * Sets the conveyor belt queue.
     * 
     * @param conveyorBelt the conveyor belt queue to set
     */
    public void setConveyorBelt(Queue<CarPart> conveyorBelt) {
    	this.conveyorBelt = conveyorBelt;
    }
    
    /**
     * Gets the total number of parts produced.
     * 
     * @return the total number of parts produced
     */
    public int getTotalPartsProduced() {
         return totalPartsProduced;
    }
    
    /**
     * Sets the total number of parts produced.
     * 
     * @param count the total number of parts produced to set
     */
    public void setTotalPartsProduced(int count) {
    	this.totalPartsProduced = count;
    }
    
    /**
     * Gets the allowed weight error for produced parts.
     * 
     * @return the allowed weight error
     */
    public double getPartWeightError() {
        return weightError;
    }
    
    /**
     * Sets the allowed weight error for produced parts.
     * 
     * @param partWeightError the allowed weight error to set
     */
    public void setPartWeightError(double partWeightError) {
        this.weightError = partWeightError;
    }
    
    /**
     * Gets the chance of a part being defective.
     * 
     * @return the chance of a part being defective
     */
    public int getChanceOfDefective() {
        return chanceOfDefective;
    }
    
    /**
     * Sets the chance of a part being defective.
     * 
     * @param chanceOfDefective the chance of a part being defective to set
     */
    public void setChanceOfDefective(int chanceOfDefective) {
        this.chanceOfDefective = chanceOfDefective;
    }
    
    /**
     * Resets the conveyor belt by filling it with null values.
     */
    public void resetConveyorBelt() {
        for(int i = 0; i < this.conveyorBelt.size(); i++) {
        	conveyorBelt.dequeue();
        	conveyorBelt.enqueue(null);
        }
    }
    
    /**
     * Advances the timer by one tick and returns the current tick value.
     * 
     * @return the current tick value
     */
    public int tickTimer() {
    	int val = timer.front();
    	timer.enqueue(timer.dequeue());
    	return val;
    }
    
    /**
     * Produces a car part based on the machine's parameters and timer.
     * 
     * @return the produced car part
     */
    public CarPart produceCarPart() {
       if (this.tickTimer() != 0) {
//    	   conveyorBelt.dequeue();
    	   conveyorBelt.enqueue(null);
       } else {
    	   int newId = p1.getId();
    	   String newName = p1.getName();
    	   
    	   Random rnd = new Random();
     	   double rndErrorDouble = rnd.nextDouble();
     	   double min = p1.getWeight() - weightError;
     	   double max = p1.getWeight() + weightError;
     	   double newWeight = min + (max - min) * rndErrorDouble;
     	   
     	   boolean newIsDefective = totalPartsProduced % chanceOfDefective == 0;
     	   this.totalPartsProduced += 1;
     	   
     	   CarPart newPart = new CarPart(newId, newName, newWeight, newIsDefective);
//    	   conveyorBelt.dequeue();
     	   conveyorBelt.enqueue(newPart);
       }
       return conveyorBelt.dequeue();
    }

    /**
     * Returns string representation of a Part Machine in the following format:
     * Machine {id} Produced: {part name} {total parts produced}
     */
    @Override
    public String toString() {
        return "Machine " + this.getId() + " Produced: " + this.getPart().getName() + " " + this.getTotalPartsProduced();
    }
    /**
     * Prints the content of the conveyor belt. 
     * The machine is shown as |Machine {id}|.
     * If the is a part it is presented as |P| and an empty space as _.
     */
    public void printConveyorBelt() {
        // String we will print
        String str = "";
        // Iterate through the conveyor belt
        for(int i = 0; i < this.getConveyorBelt().size(); i++){
            // When the current position is empty
            if (this.getConveyorBelt().front() == null) {
                str = "_" + str;
            }
            // When there is a CarPart
            else {
                str = "|P|" + str;
            }
            // Rotate the values
            this.getConveyorBelt().enqueue(this.getConveyorBelt().dequeue());
        }
        System.out.println("|Machine " + this.getId() + "|" + str);
    }
}
