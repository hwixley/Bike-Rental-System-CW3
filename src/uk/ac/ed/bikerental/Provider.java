package uk.ac.ed.bikerental;
import java.math.BigDecimal;
import java.util.ArrayList;

public class Provider{
  private ArrayList<Bike> inventory = new ArrayList<Bike>();
  private ArrayList<Booking> cBookings = new ArrayList<Booking>();
  private ArrayList<Partnership> partners = new ArrayList<Partnership>();

  private String name;
  private Location address;
  private String phoneNo;
  private String openingHours;
  private BigDecimal depositRate;
  private Boolean valuationDeprType; //True for linearDep   False for dblDecliningDep

  public Provider(String nom, Location addr, String phone, String hours, BigDecimal depR) {
    name = nom;
    address = addr;
    phoneNo = phone;
    openingHours = hours;
    depositRate = depR;
    Main.providers.add(this);
  }

  public Provider(String nom, Location addr, String phone, String hours, BigDecimal depR, Boolean valDeprType) {
    name = nom;
    address = addr;
    phoneNo = phone;
    openingHours = hours;
    depositRate = depR;
    valuationDeprType = valDeprType;
  }

  //extracts a given quantity of bikes of a given bikeType from provider's inventory
  public ArrayList<Bike> extractBikes(BikeType bikeType, int quantity, DateRange dateRange){
      ArrayList<Bike> extrBikes = new ArrayList();
      ArrayList<Bike> validBikes = new ArrayList();

      for (int i = 0; i < inventory.size(); i++) {	//loops through provider's inventory
        if ((inventory.get(i)).getType() == bikeType) { //only retrieves bikes of the given bikeType
            extrBikes.add(inventory.get(i));
        }
      }
      if (extrBikes.size() < quantity) { //checks if there is a sufficient quantity of extracted bikes
        return null;
      } else {
        for (int a = 0; a < extrBikes.size(); a++) {	//loops through extracted bikes
            if((extrBikes.get(a)).checkAvailability(dateRange)) {  //checks if the bike is available for the given date range
                validBikes.add(extrBikes.get(a));
                if (validBikes.size() == quantity) { //once enough valid bikes are found the loop terminates
                  return validBikes;
                }
            }
        }
        return null; //NULL is returned if a sufficient amount of valid bikes are not found
      }
  }

  public void addBike(Bike bike) {
	  inventory.add(bike);
  }

  public String toString(){
      return "Name: " + name + "\nAddress: " + address.toString() + "\nPhone number: "
              + phoneNo + "\nOpening hours: " + openingHours + "\n";
  }

  public BigDecimal getDepositRate(){
	  return depositRate;
  }

  public Location getAdd(){
    return address;
  }
  
  public void addPartner(Partnership partnership) {
	  partners.add(partnership);
  }

  public Boolean getValType() {
    return valuationDeprType;
  }

  public void addBooking(Booking booking) {
    cBookings.add(booking);
  }

  public String getName() {
    return name;
  }
}
