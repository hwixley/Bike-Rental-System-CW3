package uk.ac.ed.bikerental;
import java.util.ArrayList;
import java.math.*;
import java.time.temporal.ChronoUnit;
import java.time.LocalDate;

public class Bike {

 // private Provider owner;
  private BikeType bikeT;
  private BigDecimal dailyRental;
  private LocalDate manufactureDate;
  private int locationalStatus;


  private ArrayList<DateRange> cBookings = new ArrayList<DateRange>();

  public Bike(BikeType bt, BigDecimal dr, int locStatus) {
    //owner = prov;
    bikeT = bt;
    dailyRental = dr;
    manufactureDate = null;
    locationalStatus = locStatus;
  }

  public Bike(BikeType bt, BigDecimal dr, LocalDate md, int locStatus) { //constructor for ExtensionModule (manufactureDate included)
    //owner = prov;
    bikeT = bt;
    dailyRental = dr;
    manufactureDate = md;
    locationalStatus = locStatus;
  }

    public BikeType getType() {
        return bikeT;
    }

    //method compares current daterange being queried against all current bookings
    public Boolean checkAvailability(DateRange queryRange){
        for(DateRange bookedDates: cBookings) {
            if(queryRange.overlaps(bookedDates)){
              return false;
            }
        }
        return true;
    }

    public void updateBookings(DateRange bookingDates){
        cBookings.add(bookingDates);
    }

    public String toString(){
        return null;
    }

    //public Provider getProvider(){
    //  return owner;
    //}
    public BikeType getBikeType(){
      return bikeT;
    }
    public BigDecimal getDailyRental(){
      return dailyRental;
    }

    public LocalDate getManufactureDate() {
      return manufactureDate;
    }

    public void updateLocationalStatus(int stage){
    	locationalStatus = stage;
    }
}
