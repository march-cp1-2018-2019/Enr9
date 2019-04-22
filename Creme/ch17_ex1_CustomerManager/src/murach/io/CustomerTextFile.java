/** @JeremiahCreme */

package murach.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.Scanner;

import murach.business.Customer;

public class CustomerTextFile {
	private static final String FIELD_SEP = "\t";
	private static final Path customersPath = Paths.get("customers.txt");
	private static final File customersFile = new File("customers.txt");
	private static List<Customer> customers = new ArrayList<Customer>();

	private static void createFile(Path path) {
		final Formatter x;
		try {
			x = new Formatter("" + path);
		} catch (FileNotFoundException e) {
		}
	}

	// prevent instantiation of the class
	private CustomerTextFile() {
	}

	public static List<Customer> getCustomers() {
		customers.clear();
		String text;
		String[] parts;
		try {
			Scanner s = new Scanner(customersFile);
			while (s.hasNextLine()) {
				Customer dude = new Customer();
				text = s.nextLine();
				parts = text.split(" ");
				dude.setFirstName(parts[0]);
				dude.setLastName(parts[1]);
				dude.setEmail(parts[2]);
				customers.add(dude);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return customers;
	}

	public static Customer getCustomer(String email) {
		Customer dude = new Customer();
		String text;
		String[] parts;
		try {
			Scanner s = new Scanner(customersFile);
			while (s.hasNextLine()) {
				text = s.nextLine();
				parts = text.split(" ");
				dude.setFirstName(parts[0]);
				dude.setLastName(parts[1]);
				dude.setEmail(parts[2]);
				if (dude.getEmail().equals(email)) {
					return dude;
				}
			}
		} catch (FileNotFoundException e) {
			createFile(customersPath);
		}
		return null;
	}

	public static boolean addCustomer(Customer c) {
		customers.clear();
		boolean keepGoing = true;
		String text;
		String[] parts;
		try {
			Scanner s = new Scanner(customersFile);
			while (s.hasNextLine()) {
				Customer dude = new Customer();
				text = s.nextLine();
				parts = text.split(" ");
				dude.setFirstName(parts[0]);
				dude.setLastName(parts[1]);
				dude.setEmail(parts[2]);
				if (dude.getEmail().equals(c.getEmail())) {
					customers.add(c);
					keepGoing = false;
				} else {
					customers.add(dude);
				}
			}
			if(keepGoing) {
				customers.add(c);
			}
		} catch (FileNotFoundException e) {
			createFile(customersPath);
			return false;
		}
		saveCustomers();
		return true;

	}

	public static boolean deleteCustomer(Customer c) {
		customers.clear();
		String text;
		String[] parts;
		try {
			Scanner s = new Scanner(customersFile);
			while (s.hasNextLine()) {
				Customer dude = new Customer();
				text = s.nextLine();
				parts = text.split(" ");
				dude.setFirstName(parts[0]);
				dude.setLastName(parts[1]);
				dude.setEmail(parts[2]);
				if (!(c.getName().equals(dude.getName()) && c.getEmail().equals(dude.getEmail()))) {
					customers.add(dude);
				}
			}
		} catch (FileNotFoundException e) {
			createFile(customersPath);
			return false;
		}
		return saveCustomers();
	}

	public static boolean updateCustomer(Customer newCustomer) { // ???
		return true;// p.s. i didn't know how to use this
	}

	private static boolean saveCustomers() {
		try {
			String text = "";
			Formatter j = new Formatter(customersPath + "");
			for (int x = 0; x < customers.size(); x++) {
				text += customers.get(x).getName() + " " + customers.get(x).getEmail() + "\n";
			}
			j.format(text);
			j.close();
			customers.clear();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}