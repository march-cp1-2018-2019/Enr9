package murach.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import murach.business.Customer;

// Stephen Russell 4/4/19

public class CustomerTextFile {
	private static final String FIELD_SEP = "\t";
	private static final Path customersPath = Paths.get("customers.txt");
	private static final File customersFile = customersPath.toFile();
	private static List<Customer> customers = null;

	// prevent instantiation of the class
	private CustomerTextFile() {
	}

	public static List<Customer> getCustomers() {
		// if the customers file has already been read, don't read it again
		if (customers != null) {
			return customers;
		}

		if (!Files.exists(customersPath)) {
			return null;
		}

		customers = new ArrayList<>();

		// load the array list with Customer objects created from
		// the data in the file
		try (BufferedReader in = new BufferedReader(new FileReader(customersFile))) {
			String line = in.readLine();
			while (line != null) {
				String[] parts = line.split(FIELD_SEP);

				if (parts.length != 3) {
					System.out.println("Invalid line: " + line);
					line = in.readLine();
					continue;
				}

				customers.add(new Customer(parts[0], parts[1], parts[2]));

				line = in.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		return customers;
	}

	public static Customer getCustomer(String email) {
		for (Customer c : customers) {
			if (c.getEmail().equals(email)) {
				return c;
			}
		}
		return null;
	}

	public static boolean addCustomer(Customer c) {
		customers.add(c);
		return saveCustomers();
	}

	public static boolean deleteCustomer(Customer c) {
		customers.remove(c);
		return saveCustomers();
	}

	public static boolean updateCustomer(Customer newCustomer) {
		// get the old customer and remove it
		Customer oldCustomer = getCustomer(newCustomer.getEmail());
		int i = customers.indexOf(oldCustomer);
		customers.remove(i);

		// add the updated customer
		customers.add(i, newCustomer);

		return saveCustomers();
	}

	private static boolean saveCustomers() {
		// save the Customer objects in the array list to the file
		try {
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(customersFile)));

			for (Customer customer : getCustomers()) {
				out.print(customer.getFirstName() + FIELD_SEP);
				out.print(customer.getLastName() + FIELD_SEP);
				out.println(customer.getEmail());
			}

			out.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}
}