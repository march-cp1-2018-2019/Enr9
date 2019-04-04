package murach.io;

import java.util.*;
import java.io.*;
import java.nio.file.*;
import murach.business.Product;

// Noah 4/4/2019

public class ProductIO{
  private static final Path productsPath = Paths.get("products.txt");
  private static final File productsFile = productsPath.toFile();
  private static final String FIELD_SEP = "\t";
  private static List<Product> products;

  // prevent instantiation of class
  private ProductIO(){
  }

  public static List<Product> getAll() throws IOException{
//    if(true){
//      throw new IOException("Test");
//    }
    
    // if the products file has already been read, don't read it again
    if(products != null){
      return products;
    }

    products = new ArrayList<>();
    if(Files.exists(productsPath)){
      BufferedReader in = new BufferedReader(new FileReader(productsFile));
      // read all products stored in the file into the array list
      String line = in.readLine();
      while(line != null){
        String[] columns = line.split(FIELD_SEP);
        String code = columns[0];
        String description = columns[1];
        String price = columns[2];

        Product p = new Product();
        p.setCode(code);
        p.setDescription(description);
        p.setPrice(Double.parseDouble(price));
        products.add(p);

        line = in.readLine();
      }
      
      in.close();
    }
    return products;
  }

  public static Product get(String code) throws IOException{
    products = getAll();
    for(Product p : products){
      if(p.getCode().equals(code))
        return p;
    }
    return null;
  }

  private static boolean saveAll() throws IOException{
    products = getAll();
    
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(productsFile)));
    // write all products in the array list to the file
    for(Product p : products){
      out.print(p.getCode() + FIELD_SEP);
      out.print(p.getDescription() + FIELD_SEP);
      out.println(p.getPrice());
    }
    
    out.close();

    return true;
  }

  public static boolean add(Product p) throws IOException{
    products = getAll();
    products.add(p);
    return saveAll();
  }

  public static boolean delete(Product p) throws IOException{
    products = getAll();
    products.remove(p);
    return saveAll();
  }

  public static boolean update(Product newProduct) throws IOException{
    products = getAll();

    // get the old product and remove it
    Product oldProduct = get(newProduct.getCode());
    int i = products.indexOf(oldProduct);
    products.remove(i);

    // add the updated product
    products.add(i, newProduct);

    return saveAll();
  }
}