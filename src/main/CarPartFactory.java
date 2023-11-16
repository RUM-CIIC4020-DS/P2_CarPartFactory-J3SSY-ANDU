package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import data_structures.ArrayList;
import data_structures.BasicHashFunction;
import data_structures.HashTableSC;
import data_structures.LinkedStack;
import interfaces.HashFunction;
import interfaces.List;
import interfaces.Map;
import interfaces.Stack;
/**
 * @author jessy
 * @version 11/15/2023
 */
public class CarPartFactory {

	private String orderPath;
	private String partsPath;
	private List<PartMachine> machines;
	private List<Order> orders;
	private Stack<CarPart> productionBin;
	private Map<Integer, CarPart> partCatalog;
	private Map<Integer, Order> ordersMap;
	private Map<Integer, List<CarPart>> inventory;
	private Map<Integer, Integer> defectives;
	
	/**
	 * Constructor
	 * @param orderPath is the path of the order csv file
	 * @param partsPath is the path of the parts csv file
	 * @throws IOException file related errors
	 */
	public CarPartFactory(String orderPath, String partsPath) throws IOException {
		this.orderPath = orderPath;
		this.partsPath = partsPath;
		
		setupOrders(orderPath);
		setupMachines(partsPath);
		
		this.productionBin = new LinkedStack<CarPart>();
		
		this.setupInventory();
				
		this.defectives = new HashTableSC<Integer, Integer>(1, new BasicHashFunction());
	}
	
	/**
	 * machines getter
	 * @return value passed as setupMachines in the constructor
	 * @throws IOException file related errors
	 */
	public List<PartMachine> getMachines() throws IOException {
		return machines;
	}
	/**
	 * machines setter
	 * @return machines is set as the parameter in the method
	 */
	public void setMachines(List<PartMachine> machines) {
		this.machines = machines;
	}
	/**
     * Sets the list of machines.
     * 
     * @param machines value to set as the list of machines
     */
	public Stack<CarPart> getProductionBin() {
		return productionBin;
	}
	/**
	 * machines getter
	 * @return value passed as setupMachines in the constructor
	 * @throws IOException file related errors
	 */
	public void setProductionBin(Stack<CarPart> production) {
		this.productionBin = production;
	}
	
	/**
	 * Gets the part catalog, a mapping of part IDs to corresponding CarPart objects.
	 * 
	 * @return the part catalog
	 */
	public Map<Integer, CarPart> getPartCatalog() {
		return partCatalog;
	}
	
	/**
	 * Sets the part catalog to the specified mapping of part IDs to CarPart objects.
	 * 
	 * @param partCatalog the part catalog to set
	 */
	public void setPartCatalog(Map<Integer, CarPart> partCatalog) {
		this.partCatalog = partCatalog;
	}
	
	/**
	 * Gets the inventory, a mapping of part IDs to lists of CarPart objects.
	 * 
	 * @return the inventory
	 */
	public Map<Integer, List<CarPart>> getInventory() {
		return inventory;
	}
	
	/**
	 * Sets the inventory to the specified mapping of part IDs to lists of CarPart objects.
	 * 
	 * @param inventory the inventory to set
	 */
	public void setInventory(Map<Integer, List<CarPart>> inventory) {
		this.inventory = inventory;
	}
	
	/**
	 * Gets the list of orders representing customer orders for car parts.
	 * 
	 * @return the list of orders
	 */
	public List<Order> getOrders() {
		return orders;
	}

	/**
	 * Sets the list of orders to the specified list of Order objects.
	 * 
	 * @param orders the list of orders to set
	 */
	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	/**
	 * Gets the defective counts, a mapping of part IDs to the count of defective parts.
	 * 
	 * @return the defective counts
	 */
	public Map<Integer, Integer> getDefectives() {
		return defectives;
	}
	
	/**
	 * Sets the defective counts to the specified mapping of part IDs to the count of defective parts.
	 * 
	 * @param defectives the defective counts to set
	 */
	public void setDefectives(Map<Integer, Integer> defectives) {
		this.defectives = defectives;
	}

	/**
     * Reads order information from the specified file and sets up the list of orders.
     * 
     * @param path is the path of the order csv file
     * @throws IOException for file-related errors
     */
	public void setupOrders(String path) throws IOException {
		this.orders = new ArrayList<Order>();
		FileReader file = new FileReader(path);
		BufferedReader input = new BufferedReader(file);
		String header = input.readLine();
		String orderLine = input.readLine();
		while (orderLine != null) {
			String[] orderLineArray = orderLine.split(",");
			HashFunction<Integer> hashFunction = new BasicHashFunction();
			Map<Integer, Integer> requestedParts = new HashTableSC<Integer, Integer>(1, hashFunction);
			String[] tuples = orderLineArray[2].split("-");
			for(String str: tuples) {
				String[] key_value = str.split(" ");
				requestedParts.put(Integer.parseInt(key_value[0].substring(1)), Integer.parseInt(key_value[1].substring(0, key_value[1].length()-1)));
			}
			Order order = new Order(Integer.parseInt(orderLineArray[0]), orderLineArray[1], requestedParts, false);
			orders.add(order);
			orderLine = input.readLine();
		}
		input.close();
		
		this.ordersMap = new HashTableSC<Integer, Order>(1, new BasicHashFunction());
		for (Order order : orders) {
			ordersMap.put(order.getId(), order);
		}
		
	}

	/**
     * Reads machine information from the specified file and sets up the list of
     * machines.
     * 
     * @param path is the path of the parts csv file
     * @throws IOException for file-related errors
     */
	public void setupMachines(String path) throws IOException {
		this.machines = new ArrayList<PartMachine>();
		FileReader parts = new FileReader(path);
		BufferedReader in = new BufferedReader(parts);
		String header = in.readLine();
		String machineDoc = in.readLine();
		while (machineDoc != null) {
			String[] machineDocArray = machineDoc.split(",");
			PartMachine machine = new PartMachine(Integer.parseInt(machineDocArray[0]),
					new CarPart(Integer.parseInt(machineDocArray[0]), machineDocArray[1],
							Double.parseDouble(machineDocArray[2]), false),
					Integer.parseInt(machineDocArray[4]), Double.parseDouble(machineDocArray[3]),
					Integer.parseInt(machineDocArray[5]));
			machines.add(machine);
			machineDoc = in.readLine();
		}
		in.close();
		
		this.partCatalog = new HashTableSC<Integer, CarPart>(1, new BasicHashFunction());
		for (PartMachine partMachine : machines) {
			partCatalog.put(partMachine.getPart().getId(), partMachine.getPart());
		}
	}

	/**
     * Sets up the initial inventory based on the part catalog.
     */
	public void setupInventory() {
		this.inventory = new HashTableSC<Integer, List<CarPart>>(1, new BasicHashFunction());
		List<CarPart> list = new ArrayList<CarPart>();
		for (Integer id : partCatalog.getKeys()) {
			inventory.put(id, list);
		}
	}

	/**
     * Processes the production bin and updates the inventory and defective counts.
     */
	public void storeInInventory() {
		while(!productionBin.isEmpty()) {
			CarPart partInBin = this.productionBin.pop();
			int partId = partInBin.getId();
			Integer count = this.defectives.get(partId);
			if (!partInBin.isDefective()) {
				if (inventory.get(partId).isEmpty()) {
					List<CarPart> newList = new ArrayList<CarPart>();
					newList.add(partInBin);
					this.inventory.put(partId, newList);
				} else {
					List<CarPart> newList = inventory.get(partId);
					newList.add(partInBin);
					this.inventory.put(partId, newList);
				}
			} else {
				if (count == null) {
					this.defectives.put(partId, 1);
				}
				else {
					this.defectives.put(partId, count+1);
				}
			}
		}		
	}

	/**
     * Simulates the operation of the factory for a specified number of days and
     * minutes.
     * 
     * @param days    number of days to run the factory
     * @param minutes number of minutes per day
     */
	public void runFactory(int days, int minutes) {
		for (int i = 0; i < days; i++) {
			for (PartMachine partMachine : this.machines) {
				for (int j = 0; j < minutes; j++) {
					CarPart part = partMachine.produceCarPart();
					if (part != null) {
						this.productionBin.push(part);
					}
				}
				partMachine.resetConveyorBelt();
			}
			this.storeInInventory();
		}
		this.processOrders();
	}

	/**
     * Processes the orders and updates their fulfillment status.
     */
	public void processOrders() {
		for (Order order : this.getOrders()) {
			boolean fulfilled = true;
			Map<Integer, Integer> requestedParts = order.getRequestedParts();
			for (Integer requestedId : requestedParts.getKeys()) {
				int requestedPartsCount = requestedParts.get(requestedId);
				int inventoryPartCount = inventory.get(requestedId).size();
				if (!inventory.containsKey(requestedId) || requestedPartsCount > inventoryPartCount) {
					fulfilled = false;
					break;
				}
			}
			if (fulfilled) {
				for (Integer requestedId : requestedParts.getKeys()) {
	                int requestedPartsCount = requestedParts.get(requestedId);
	                List<CarPart> part = inventory.get(requestedId);
	                for (int i = 0; i < requestedPartsCount; i++) {
	                    part.remove(0);
	                }
	                inventory.put(requestedId, part);
	            }
	            order.setFulfilled(true);
			}
		}
	}

	/**
	 * Generates a report indicating how many parts were produced per machine, how
	 * many of those were defective and are still in inventory. Additionally, it
	 * also shows how many orders were successfully fulfilled.
	 * @throws IOException 
	 */
	public void generateReport() throws IOException {
		String report = "\t\t\tREPORT\n\n";
		report += "Parts Produced per Machine\n";
		for (PartMachine machine : this.getMachines()) {
			report += machine + "\t(" + this.getDefectives().get(machine.getPart().getId()) + " defective)\t("
					+ this.getInventory().get(machine.getPart().getId()).size() + " in inventory)\n";
		}

		report += "\nORDERS\n\n";
		for (Order transaction : this.getOrders()) {
			report += transaction + "\n";
		}
		System.out.println(report);
	}

}
