package uk.ac.ed.bikerental;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import java.math.BigDecimal;

public class Quote{
  private Customer customer;
  private HashMap<BikeType,Integer> bikes = new HashMap<BikeType,Integer>();
  private DateRange dateR;
  private Location pickupAdd;
  private Location returnAdd;

  //public Quote(){}

  public Quote(Customer cust,HashMap<BikeType,Integer> bikeMap, DateRange date,Location pickupA, Location returnA) {
    customer = cust;
    bikes = bikeMap;
    dateR = date;
    pickupAdd = pickupA;
    returnAdd = returnA;
  }

  public Boolean addressCheck(){ //returns True if pickup and return are same, False if not
    return pickupAdd.isNearTo(returnAdd);
  }

  public Location getPickA(){
    return pickupAdd;
  }

  public Location getRetA(){
    return returnAdd;
  }

  public HashMap<BikeType,Integer> getBikes(){
    return bikes;
  }

  public DateRange getDateRange() {
    return dateR;
  }

  public void setDateRange(DateRange dr) {
    dateR = dr;
  }

  public Customer getCustomer() {
    return customer;
  }

  public String toString() {
    String bikes = "\nQuantity:  Bike type: \n";
    for (Map.Entry<BikeType, Integer> qBikes : getBikes().entrySet()) {
      bikes = bikes + qBikes.getValue() + "       " + qBikes.getKey() + "\n";
    }


    String result = "Booking Information" + "\n\nCUSTOMER:\n" + getCustomer().toString()
                    + "\n\nBIKES:\n" + bikes + "\n\nDATE RANGE:\n" + getDateRange().toString()
                    + "\n\nPICKUP ADDRESS:\n" + pickupAdd.toString() + "\n\nRETURN ADDRESS:\n"
                    + returnAdd.toString();
    return result;
  }

}

class Booking extends Quote{
  private Provider originalProv;
  private Provider returnProv;
  private ArrayList<Bike> bikes = new ArrayList<Bike>();

  private Boolean pickupMethod; //true = collection or false = delivery
  private int depStatus; //DEPOSIT STATUS: 0=initial 1=paid 2=returned
  private int locStatus; //LOCATIONAL STATUS: check CW2 for signal values
  private int orderID;
  private BigDecimal deposit;

  public Booking(Quote quote, Provider originalProv, Provider returnProv, ArrayList<Bike> bikes,Boolean pickupMethod) {
    super(quote.getCustomer(), quote.getBikes(), quote.getDateRange(), quote.getPickA(), quote.getRetA());
    this.bikes = bikes;
    this.originalProv = originalProv;
    this.returnProv = returnProv;
    this.pickupMethod = pickupMethod;
    deposit = calcDeposit();
    depStatus = 1;
    locStatus = 0;
    Main.bookings.add(this);
    orderID = Main.bookings.size();
  }

  public String updateLocationalStatus(){ //updates the locational status of bikes in a given booking
	  String stage = "";
	  int bikeStatus = 0;
	  if (locStatus == 4) return "Booking process is over, unable to update locational status of the bikes any further.";
	  else {
		  if (locStatus == 0) {
			  if (pickupMethod) {
				  locStatus = 2;
				  bikeStatus = locStatus;
				  stage = "with customer";
			  } else {
				  locStatus = 1;
				  bikeStatus = locStatus;
				  stage = "in transit to customer (with delivery driver)";
			  }
		  } else if (locStatus == 1) {
			  locStatus = 2;
			  bikeStatus = locStatus;
			  stage = "with customer";
		  } else if (returnProv == null) {
			  if (locStatus == 2) {
				  locStatus = 5;
				  bikeStatus = locStatus;
				  depStatus = 2;
				  stage = "returned to original provider";
			  }
		  } else if (locStatus == 2) {
			  locStatus = 3;
			  depStatus = 2;
			  bikeStatus = locStatus;
			  stage = "returned to partner";
		  } else if (locStatus == 3) {
			  locStatus = 4;
			  bikeStatus = locStatus;
			  stage = "in transit to original provider (with delivery driver)";
		  } else if (locStatus == 4) {
			  locStatus = 5;
			  bikeStatus = 0;	//if the end of the booking is reached the locational status of each bike is set to "with provider"
			  stage = "returned to original provider";
		  }


		  for (int i = 0; i < bikes.size(); i++) {
			  bikes.get(i).updateLocationalStatus(bikeStatus);
		  }


		  return "Locational status of the bikes: " + stage;

	  }

  }
  
  public int getLocationalStatus() {
	  return locStatus;
  }

  //calculates the total deposit of all bikes in the booking
  public BigDecimal calcDeposit(){
      BigDecimal deposit = new BigDecimal(0);
      for(Bike currentBike: bikes){
          deposit.add(currentBike.getType().getRepValue());
      }
      deposit.multiply(originalProv.getDepositRate());
      return deposit;

  }

  public String toString() {
    String bikes = "\nQuantity:  Bike type: \n";
    for (Map.Entry<BikeType, Integer> qBikes : getBikes().entrySet()) {
      bikes = bikes + qBikes.getValue() + "       " + qBikes.getKey() + "\n";
    }
    String pickupM;
    if (pickupMethod) {
      pickupM = "Collection";
    } else pickupM = "Delivery";
    String pickup = "PICKUP & RETURN ";
    String secondProv = "";
    if (returnProv != null) {
      pickup = "PICKUP ";
      secondProv = "\n\nRETURN PROVIDER:\n" + returnProv.toString();
    }

    String result = "Booking Information\nOrder ID: " + orderID + "\n\nCUSTOMER:\n" + getCustomer().toString()
                    + "\n\nBIKES:\n" + bikes + "\n\nDATE RANGE:\n" + getDateRange().toString()
                    + "\n\n" + pickup + "PROVIDER:\n" + originalProv.toString() +"\n\nPICKUP METHOD: " + pickupM
                    + secondProv;

    return result;
  }
}
