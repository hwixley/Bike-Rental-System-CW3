package uk.ac.ed.bikerental;
import java.math.BigDecimal;
import java.util.ArrayList;

public class ProviderQuotes{
      private Quote quote;
      private Provider providerPickup;
      private Provider providerReturn;
      private ArrayList<Bike> bikes = new ArrayList<Bike>();

      public ProviderQuotes(Quote quote, Provider providerPickup, Provider providerReturn, ArrayList<Bike> bikes){
          this.quote = quote;
          this.providerPickup = providerPickup;
          this.providerReturn = providerReturn;  //null for quotes with same pickup and return locations
          this.bikes = bikes;
      }

      //calcDeposit loops through all Bikes in the current quotes, gets the total replacement value of all bikes and then calculates the deposit
      public BigDecimal calcDeposit(){
          BigDecimal deposit = new BigDecimal(0);
          for(Bike currentBike: bikes){
              deposit.add(currentBike.getType().getRepValue());
          }
          deposit.multiply(providerPickup.getDepositRate());
          return deposit;

      }

      public String toString() {
    	  String provRetString = "";
    	  if (providerReturn != null) {
    		  provRetString = providerReturn.toString();
    	  }
          return "Pickup Provider " + providerPickup.toString() + "DropOff Provider " + provRetString;

      }

      public Quote getQuote() {
        return quote;
      }

      public Provider getProvPickup() {
        return providerPickup;
      }

      public Provider getProvReturn() {
        return providerReturn;
      }

      public ArrayList<Bike> getBikes() {
        return bikes;
      }

}
