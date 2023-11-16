package main;

/**
 * Represents a car part with an ID, name, weight, and defect status.
 */
public class CarPart {
    private int id;
    private String name;
    private double weight;
    private boolean isDefective;
    
    /**
     * Constructs a new CarPart.
     *
     * @param id          the unique identifier for the car part
     * @param name        the name of the car part
     * @param weight      the weight of the car part
     * @param isDefective indicates whether the car part is defective
     */
    public CarPart(int id, String name, double weight, boolean isDefective) {
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.isDefective = isDefective;
    }
    
    /**
     * Gets the unique identifier for the car part.
     *
     * @return the car part ID
     */
    public int getId() {
        return id;
    }
    
    /**
     * Sets the unique identifier for the car part.
     *
     * @param id the car part ID to set
     */
    public void setId(int id) {
        this.id = id;
    }
    
    /**
     * Gets the name of the car part.
     *
     * @return the name of the car part
     */
    public String getName() {
        return name;
    }
    
    /**
     * Sets the name of the car part.
     *
     * @param name the name of the car part to set
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Gets the weight of the car part.
     *
     * @return the weight of the car part
     */
    public double getWeight() {
      return weight;
    }
    
    /**
     * Sets the weight of the car part.
     *
     * @param weight the weight of the car part to set
     */
    public void setWeight(double weight) {
      this.weight = weight;
    }
    
    /**
     * Checks if the car part is defective.
     *
     * @return true if the car part is defective, false otherwise
     */
    public boolean isDefective() {
      return isDefective;
    }
    
    /**
     * Sets whether the car part is defective.
     *
     * @param isDefective true if the car part is defective, false otherwise
     */
    public void setDefective(boolean isDefective) {
        this.isDefective = isDefective;
    }
    /**
     * Returns the parts name as its string representation
     * @return (String) The part name
     */
    public String toString() {
        return this.getName();
    }
}