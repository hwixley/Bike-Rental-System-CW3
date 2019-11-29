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
 
  public ArrayList<ProviderQuotes> getQuotes(Quote quote,Boolean recursedCall){
      ArrayList<ProviderQuotes> pQuotes = new ArrayList<ProviderQuotes>();
      

      if (quote.addressCheck()){     //loop for quotes with same pickup and return address
          for(int i = 0; i < providers.size(); i++) {	//loops through all providers
              Provider provider = providers.get(i);
              ArrayList<Bike> bikes = new ArrayList<Bike>();

              if((quote.getPickA()).isNearTo(provider.getAdd())) {	//check if customer's pickup address is in the same area as provider
                  bikes = validBikes(quote, provider);
              }
              if ((bikes.size() > 0)) { //checks if the provider will have available bikes given the criteria
                  ProviderQuotes pq  = new ProviderQuotes(quote,provider,null,bikes); //initializes provider quotes with second provider as NULL
                  pQuotes.add(pq);
              }
          }
      } else {		//loop for quotes with different pickup and return address
          for(int i = 0; i < partnerships.size(); i++) { //loops through all partnerships
              Provider prov1 = partnerships.get(i).getProv1();
              Provider prov2 = partnerships.get(i).getProv2();
              ArrayList<Bike> bikes = new ArrayList<Bike>();

              if ((quote.getPickA().isNearTo(prov1.getAdd()))
            		  && (quote.getRetA().isNearTo(prov2.getAdd()))) { //Checks if the provider addresses match pickup and return locations

                  bikes = validBikes(quote, prov1);
                  if ((bikes.size() > 0)) {	//checks if the provider will have available bikes given the criteria
                      ProviderQuotes pq  = new ProviderQuotes(quote,prov1,prov2,bikes);
                      pQuotes.add(pq);
                  }
              }
              else if ((quote.getPickA().isNearTo(prov2.getAdd()))
            		  && (quote.getRetA().isNearTo(prov1.getAdd()))) { //Checks if the provider addresses match pickup and return locations
                  
            	  bikes = validBikes(quote, prov2);
                  if ((bikes.size() > 0)) {	//checks if the provider will have available bikes given the criteria
                      ProviderQuotes pq  = new ProviderQuotes(quote,prov2,prov1,bikes);
                      pQuotes.add(pq);
                      System.out.println("added/n");
                  }
              }

          }
      }
      if ((pQuotes.size() == 0) && (recursedCall == false)){ //checks if no provider quotes were found and if the function has not already recursively been called
        System.out.println("Sorry there were no available quotes for your given search criteria.\n");

        LocalDate startDate = quote.getDateRange().getStart();
        LocalDate endDate = quote.getDateRange().getEnd();
        DateRange dateRange;
        long days = ChronoUnit.DAYS.between(startDate, endDate.plusDays(1));
        for (int a = 1; a < 4; a++) {	//loops through to find provider quotes for same duration within 3 days before the start date or 3 days after the end date
          dateRange = new DateRange(startDate.plusDays(-a), endDate.plusDays(-a+days));
          quote.setDateRange(dateRange);
          pQuotes.addAll(getQuotes(quote, true));

          dateRange = new DateRange(endDate.plusDays(a-days), startDate.plusDays(a));
          quote.setDateRange(dateRange);
          pQuotes.addAll(getQuotes(quote, true));
        }
        if (pQuotes.size() > 0) {
          System.out.println("Here are available quotes within 3 days before the start or after the end of the date range:.\n");
        }
      }
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


  }
}
