package uk.ac.ed.bikerental;
import java.util.ArrayList;
import java.math.*;
import java.util.HashMap;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.Map;

public class Main {
  public static ArrayList<Provider> providers = new ArrayList<Provider>();
  public static ArrayList<Partnership> partnerships = new ArrayList<Partnership>();
  public static ArrayList<Customer> customers = new ArrayList<Customer>();
  public static ArrayList<BikeType> bikeTypes = new ArrayList<BikeType>();
  public static ArrayList<Booking> bookings = new ArrayList<Booking>();

  private Boolean getQuotesRecursed = false;


  //method loops through bikes from a given provider and return a list of bikes that fit the quote criteria
  public ArrayList<Bike> validBikes(Quote quote, Provider provider) {
    ArrayList<Bike> bikes = new ArrayList<Bike>();
    ArrayList<Bike> result = new ArrayList<Bike>();
    boolean valid = true;

    for(Map.Entry<BikeType, Integer> qBikes : (quote.getBikes()).entrySet()) {
        result = provider.extractBikes(qBikes.getKey(), qBikes.getValue(), quote.getDateRange());

        if ((result != null) && (result.size() == qBikes.getValue())) {
            bikes.addAll(result);
        } else {
          valid = false;
          break;
        }
    }
    if (valid) return bikes;
    else return null;
  }

  public ArrayList<ProviderQuotes> getQuotes(Quote quote){
      ArrayList<ProviderQuotes> pQuotes = new ArrayList<ProviderQuotes>();
      ArrayList<Bike> bikes = new ArrayList<Bike>();

      if (quote.addressCheck()){     //loop for quotes with same pickup and return address
          for(int i = 0; i < providers.size(); i++) {	//loops through all providers
              Provider provider = providers.get(i);
              bikes.removeAll(bikes);

              if((quote.getPickA()).isNearTo(provider.getAdd())) {	//check if customer's pickup address is in the same area as provider
                  bikes = validBikes(quote, provider);
              }
              if (bikes != null) { //checks if the provider will have available bikes given the criteria
                  ProviderQuotes pq  = new ProviderQuotes(quote,provider,null,bikes); //initializes provider quotes with second provider as NULL
                  pQuotes.add(pq);
              }
          }
      } else {		//loop for quotes with different pickup and return address
          for(int i = 0; i < partnerships.size(); i++) { //loops through all partnerships
              Provider prov1 = partnerships.get(i).getProv1();
              Provider prov2 = partnerships.get(i).getProv2();
              bikes.removeAll(bikes);

              if ((quote.getPickA().isNearTo(prov1.getAdd()))
            		  & (quote.getRetA().isNearTo(prov2.getAdd()))) { //Checks if the provider addresses match pickup and return locations

                  bikes = validBikes(quote, prov1);
                  if (bikes != null) {	//checks if the provider will have available bikes given the criteria
                      ProviderQuotes pq  = new ProviderQuotes(quote,prov1,prov2,bikes);
                      pQuotes.add(pq);
                  }
              }
              else if ((quote.getPickA().isNearTo(prov2.getAdd()))
            		  & (quote.getRetA().isNearTo(prov1.getAdd()))) { //Checks if the provider addresses match pickup and return locations
                  bikes = validBikes(quote, prov2);
                  if (bikes != null) {	//checks if the provider will have available bikes given the criteria
                      ProviderQuotes pq  = new ProviderQuotes(quote,prov2,prov1,bikes);
                      pQuotes.add(pq);
                  }
              }

          }
      }
      if ((pQuotes.size() == 0) && (getQuotesRecursed == false)){ //checks if no provider quotes were found and if the function has not already recursively been called
        getQuotesRecursed = true;
        System.out.println("Sorry there were no available quotes for your given search criteria.\n");


        LocalDate startDate = quote.getDateRange().getStart();
        LocalDate endDate = quote.getDateRange().getEnd();
        DateRange dateRange;
        long days = ChronoUnit.DAYS.between(startDate, endDate.plusDays(1));
        for (int a = 1; a < 4; a++) {	//loops through to find provider quotes for same duration within 3 days before the start date or 3 days after the end date
          dateRange = new DateRange(startDate.plusDays(-a), endDate.plusDays(-a+days));
          quote.setDateRange(dateRange);
          pQuotes.addAll(getQuotes(quote));

          dateRange = new DateRange(endDate.plusDays(a-days), startDate.plusDays(a));
          quote.setDateRange(dateRange);
          pQuotes.addAll(getQuotes(quote));
        }
        if (pQuotes.size() > 0) {
          System.out.println("Here are available quotes within 3 days before the start or after the end of the date range:.\n");
        }
      }
      getQuotesRecursed = false;
      return pQuotes;
  }

  public Booking bookQuote(ProviderQuotes providerQuote, Boolean pickupMethod){ //assumes deposit payment has been made
      Booking booking = new Booking(providerQuote.getQuote(), providerQuote.getProvPickup(), //initializes new booking using the chosen provider quote
                            providerQuote.getProvReturn(), providerQuote.getBikes(), pickupMethod);
      bookings.add(booking);
      providerQuote.getProvPickup().addBooking(booking);

      for (int i = 0; i < providerQuote.getBikes().size(); i++) {
        Bike bike = providerQuote.getBikes().get(i);
        bike.updateBookings(booking.getDateRange());
      }

      return booking;
  }


  public String toString(){
      return null;
  }

  public static void main(String[] args) {
/*
     Location loc1 = new Location("EH3 9JD", "1 Glen Street, Tollcross, Edinburgh");
     Location loc2 = new Location("YW1 9AD", "5 Yendo Avenue, Inverness");
     Location loc3 = new Location("EH2 6H3", "6 Tustle Close, Edinburgh");
     Location loc4 = new Location("YW2 6H3", "10 Sesame Street, Inverness");

     BigDecimal value1 = new BigDecimal(500.0);
     BigDecimal value2 = new BigDecimal(400.0);
     BigDecimal value3 = new BigDecimal(700.0);

     BigDecimal depositRate1 = new BigDecimal(0.2);
     BigDecimal depositRate2 = new BigDecimal(0.1);

     Provider p1 = new Provider("Soul Cycles", loc3, "3298472394", "8:00 - 20:00", depositRate1);
     Provider p2 = new Provider("Bill's Bikes", loc2, "12345678", "8:00 - 20:00", depositRate2);
     Provider p3 = new Provider("Andy's Bikes", loc1, "3298472394", "8:00 - 20:00", depositRate1);
     Provider p4 = new Provider("Harry's Bikes", loc4, "5678678999", "8:00 - 20:00", depositRate2);

     Customer c1 = new Customer("John Smith",loc1, "074126787");

     BikeType bt1 = new BikeType("Hybrid", value1);
     BikeType bt2 = new BikeType("Road", value2);
     BikeType bt3 = new BikeType("Mountain", value3);

     Partnership ps1 = new Partnership(p1, p2);
     Partnership ps2 = new Partnership(p2, p4);


     HashMap<BikeType,Integer> bikes = new HashMap<BikeType,Integer>();
     bikes.put(bt1, 3);
     bikes.put(bt2, 1);


     LocalDate startDate = LocalDate.of(2019, 11, 23);
     LocalDate endDate = LocalDate.of(2019, 11, 26);
     DateRange d1 = new DateRange(startDate, endDate);

     Main.providers.add(p1);
     Main.providers.add(p2);
     Main.partnerships.add(ps1);
     Main.customers.add(c1);
     Main.bikeTypes.add(bt1);
     Main.bikeTypes.add(bt2);



     Quote quote1 = new Quote(c1, bikes, d1, loc1, loc2);
     //Main var = new Main();
     ArrayList<ProviderQuotes> getQs = getQuotes(quote1);
     System.out.println("getQuotes:\n");
     for (int i = 0; i < getQs.size(); i++) {
       System.out.println(getQs.get(i).getQuote());
     }*/

  }
}
