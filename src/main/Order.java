package main;

import interfaces.Map;

/**
 * Represents an order for car parts.
 */
public class Order {
	private int id;
	private String customerName;
	private Map<Integer, Integer> requestedParts;
	private boolean fulfilled;
	
	/**
     * Constructs a new Order.
     *
     * @param id              the unique identifier for the order
     * @param customerName    the name of the customer placing the order
     * @param requestedParts  a map representing the parts requested and their quantities
     * @param fulfilled       indicates whether the order has been fulfilled
     */
    public Order(int id, String customerName, Map<Integer, Integer> requestedParts, boolean fulfilled) {
        this.id = id;
        this.customerName = customerName;
        this.requestedParts = requestedParts;
        this.fulfilled = fulfilled;
    }
    
    /**
     * Gets the unique identifier for the order.
     *
     * @return the order ID
     */
    public int getId() {
        return id;
    }
    
    /**
     * Sets the unique identifier for the order.
     *
     * @param id the order ID to set
     */
    public void setId(int id) {
        this.id = id;
    }
    
    /**
     * Checks if the order has been fulfilled.
     *
     * @return true if the order is fulfilled, false otherwise
     */
    public boolean isFulfilled() {
    	return fulfilled;
    }
    
    /**
     * Sets whether the order has been fulfilled.
     *
     * @param fulfilled true if the order is fulfilled, false otherwise
     */
    public void setFulfilled(boolean fulfilled) {
        this.fulfilled = fulfilled;
    }
    
    /**
     * Gets the map of requested parts and their quantities.
     *
     * @return the map of requested parts
     */
    public Map<Integer, Integer> getRequestedParts() {
    	return requestedParts;
    }
    
    /**
     * Sets the map of requested parts and their quantities.
     *
     * @param requestedParts the map of requested parts to set
     */
    public void setRequestedParts(Map<Integer, Integer> requestedParts) {
    	this.requestedParts = requestedParts;
    }
    
    /**
     * Gets the name of the customer placing the order.
     *
     * @return the customer name
     */
    public String getCustomerName() {
    	return customerName;
    }
    
    /**
     * Sets the name of the customer placing the order.
     *
     * @param customerName the customer name to set
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    /**
     * Returns the order's information in a formatted string.
     *
     * @return a string representation of the order
     */
    @Override
    public String toString() {
        return String.format("%d %s %d %s", this.getId(), this.getCustomerName(), this.getRequestedParts().size(), (this.isFulfilled())? "FULFILLED": "PENDING");
    }

    
    
}
