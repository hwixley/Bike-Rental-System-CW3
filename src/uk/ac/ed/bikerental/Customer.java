package uk.ac.ed.bikerental;

public class Customer{

  private String fullName;
  private Location address;
  private String phoneNo;

  public Customer(String name, Location addr, String phone) {
    fullName = name;
    address = addr;
    phoneNo = phone;
    Main.customers.add(this);
  }

  public String toString(){
      String res ="Name: " + fullName + "\nPhone number: " + phoneNo + "\n" + address.toString();
      return res;
  }
}
