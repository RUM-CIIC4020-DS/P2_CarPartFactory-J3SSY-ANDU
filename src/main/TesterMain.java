package main;

import java.io.IOException;
import java.util.Random;

/**
 * The main class for testing the CarPartFactory application.
 */
public class TesterMain { 
	
	/**
     * The main entry point for the CarPartFactory testing.
     *
     * @param args the command line arguments (not used)
     */
    public static void main(String[] args) {
    	
    	
    	
        try {
            CarPartFactory cpf = new CarPartFactory("input/orders.csv", "input/parts.csv");
            System.out.println(cpf.getMachines().get(1));
            System.out.println(cpf.getMachines().get(6));
            System.out.println(cpf.getMachines().get(2));
            System.out.println(cpf.getPartCatalog().get(1).getName());
            cpf.runFactory(1, 30);
            cpf.generateReport();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
