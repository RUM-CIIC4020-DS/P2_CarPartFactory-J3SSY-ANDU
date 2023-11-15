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
	
	public CarPartFactory(String orderPath, String partsPath) throws IOException {
		this.orderPath = orderPath;
		this.partsPath = partsPath;
		
		setupOrders(orderPath);
		setupMachines(partsPath);
		
		this.productionBin = new LinkedStack<CarPart>();
		
		this.inventory = new HashTableSC<Integer, List<CarPart>>(1, new BasicHashFunction());
		
		this.defectives = new HashTableSC<Integer, Integer>(1, new BasicHashFunction());
	}

	public List<PartMachine> getMachines() throws IOException {
		return machines;
	}

	public void setMachines(List<PartMachine> machines) {
		this.machines = machines;
	}

	public Stack<CarPart> getProductionBin() {
		return productionBin;
	}

	public void setProductionBin(Stack<CarPart> production) {
		this.productionBin = production;
	}

	public Map<Integer, CarPart> getPartCatalog() {
		return partCatalog;
	}

	public void setPartCatalog(Map<Integer, CarPart> partCatalog) {
		this.partCatalog = partCatalog;
	}

	public Map<Integer, List<CarPart>> getInventory() {
		return inventory;
	}

	public void setInventory(Map<Integer, List<CarPart>> inventory) {
		this.inventory = inventory;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public Map<Integer, Integer> getDefectives() {
		return defectives;
	}

	public void setDefectives(Map<Integer, Integer> defectives) {
		this.defectives = defectives;
	}

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

	public void setupInventory() {
		List<CarPart> list = new ArrayList<CarPart>();
		for (Integer id : partCatalog.getKeys()) {
			inventory.put(id, list);
		}
	}

	public void storeInInventory() {

	}

	public void runFactory(int days, int minutes) {

	}

	public void processOrders() {

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
