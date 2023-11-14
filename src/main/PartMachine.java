package main;

import java.util.Random;

import data_structures.ListQueue;
import interfaces.Queue;

public class PartMachine {
	private int id;
	private CarPart p1;
	private int period;
	private double weightError;
	private int chanceOfDefective;
	private Queue<Integer> timer;
	private Queue<CarPart> conveyorBelt;
	private int totalPartsProduced;
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
    public int getId() {
    	return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Queue<Integer> getTimer() {
    	return timer;
    }
    public void setTimer(Queue<Integer> timer) {
        this.timer = timer;
    }
    public CarPart getPart() {
    	return p1;
    }
    public void setPart(CarPart part1) {
        this.p1 = part1;
    }
    public Queue<CarPart> getConveyorBelt() {
        return conveyorBelt;
    }
    public void setConveyorBelt(Queue<CarPart> conveyorBelt) {
    	this.conveyorBelt = conveyorBelt;
    }
    public int getTotalPartsProduced() {
         return totalPartsProduced;
    }
    public void setTotalPartsProduced(int count) {
    	this.totalPartsProduced = count;
    }
    public double getPartWeightError() {
        return weightError;
    }
    public void setPartWeightError(double partWeightError) {
        this.weightError = weightError;
    }
    public int getChanceOfDefective() {
        return chanceOfDefective;
    }
    public void setChanceOfDefective(int chanceOfDefective) {
        this.chanceOfDefective = chanceOfDefective;
    }
    public void resetConveyorBelt() {
        for(int i = 0; i < 10; i++) {
        	conveyorBelt.dequeue();
        	conveyorBelt.enqueue(null);
        }
    }
    public int tickTimer() {
    	int val = timer.front();
    	timer.enqueue(timer.dequeue());
    	return val;
    }
    public CarPart produceCarPart() {
       if (this.tickTimer() != 0) {
//    	   conveyorBelt.dequeue();
    	   conveyorBelt.enqueue(null);
       } else {
    	   int newId = p1.getId();
    	   String newName = p1.getName();
    	   
    	   Random rnd = new Random();
     	   double rndErrorDouble;
     	   if (rnd.nextInt(2) == 0) {
     		   rndErrorDouble = rnd.nextDouble();
     	   } else {
     		   rndErrorDouble = rnd.nextDouble();
     	   }
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
