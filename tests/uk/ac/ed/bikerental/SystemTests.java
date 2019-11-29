package uk.ac.ed.bikerental;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

public class SystemTests {
	Main mainSystem = new Main();

    //Locations
    Location loc1 = new Location("EH3 9JD", "1 Glen Street, Tollcross, Edinburgh");
    Location loc2 = new Location("YW1 9AD", "5 Yendo Avenue, Inverness");
    Location loc3 = new Location("EH2 6H3", "6 Tustle Close, Edinburgh");
    Location loc4 = new Location("YW2 6H3", "10 Sesame Street, Inverness");

    //Bike Values
    BigDecimal value1 = new BigDecimal(500.0);
    BigDecimal value2 = new BigDecimal(400.0);
    BigDecimal value3 = new BigDecimal(700.0);


    //Deposit Rates
    BigDecimal depositRate1 = new BigDecimal(0.2);
    BigDecimal depositRate2 = new BigDecimal(0.1);

    //Daily Rental Price
    BigDecimal rentP1 = new BigDecimal(30.0);
    BigDecimal rentP2 = new BigDecimal(40.0);
    BigDecimal rentP3 = new BigDecimal(50.0);

    //Providers
    Provider prov1 = new Provider("Soul Cycles", loc3, "3298472394", "8:00 - 20:00", depositRate1);
    Provider prov2 = new Provider("Bill's Bikes", loc2, "12345678", "8:00 - 20:00", depositRate2);
    Provider prov3 = new Provider("Andy's Bikes", loc1, "3298472394", "8:00 - 20:00", depositRate1);


    //Customers
    Customer c1 = new Customer("John Smith",loc1, "074126787");

    //Bike Types
    BikeType hybrid = new BikeType("Hybrid", value1);
    BikeType road = new BikeType("Road", value2);
    BikeType mountain = new BikeType("Mountain", value3);

    //Initialising Bikes For Provider 1
    Bike bk1p1 = new Bike(prov1, hybrid, rentP1);
    Bike bk2p1 = new Bike(prov1, hybrid, rentP1);
    Bike bk3p1 = new Bike(prov1, hybrid, rentP1);
    Bike bk4p1 = new Bike(prov1, hybrid, rentP1);
    Bike bk5p1 = new Bike(prov1, road, rentP2);
    Bike bk6p1 = new Bike(prov1, road, rentP2);
    Bike bk7p1 = new Bike(prov1, mountain, rentP3);
    Bike bk8p1 = new Bike(prov1, mountain, rentP3);

    //Initialising Bikes For Provider 2
    Bike bk1p2 = new Bike(prov2, hybrid, rentP2);
    Bike bk2p2 = new Bike(prov2, hybrid, rentP2);
    Bike bk3p2 = new Bike(prov2, road, rentP3);
    Bike bk4p2 = new Bike(prov2, mountain, rentP3);

    //Initialising Bikes For Provider 3
    Bike bk1p3 = new Bike(prov3, road, rentP1);
    Bike bk2p3 = new Bike(prov3, road, rentP1);
    Bike bk3p3 = new Bike(prov3, road, rentP1);
    Bike bk4p3 = new Bike(prov3, road, rentP1);
    Bike bk5p3 = new Bike(prov3, mountain, rentP3);
    Bike bk6p3 = new Bike(prov3, mountain, rentP3);
    Bike bk7p3 = new Bike(prov3, mountain, rentP3);
    Bike bk8p3 = new Bike(prov3, mountain, rentP3);

    //Partnerships
    Partnership ps1 = new Partnership(prov1, prov2);

    ///////////Query Information//////////

    //Bikes In Query
    HashMap<BikeType,Integer> bikes = new HashMap<BikeType,Integer>();


    //Dates for Query
    LocalDate startDate = LocalDate.of(2019, 11, 23);
    LocalDate endDate = LocalDate.of(2019, 11, 26);
    DateRange d1 = new DateRange(startDate, endDate);



    @BeforeEach
   // void setUp() throws Exception {
        // Setup mock delivery service before each tests
   //     DeliveryServiceFactory.setupMockDeliveryService();

        // Put your test setup here
   //}

    // TODO: Write system tests covering the three main use cases

    @Test
    void getQuotesTest1() { //same pickup and return area

        prov1.addBike(bk1p1);
        prov1.addBike(bk2p1);
        prov1.addBike(bk3p1);
        prov1.addBike(bk4p1);
        prov1.addBike(bk5p1);
        prov1.addBike(bk6p1);
        prov1.addBike(bk7p1);
        prov1.addBike(bk8p1);

        prov2.addBike(bk1p2);
        prov2.addBike(bk2p2);
        prov2.addBike(bk3p2);
        prov2.addBike(bk4p2);

        prov3.addBike(bk1p3);
        prov3.addBike(bk2p3);
        prov3.addBike(bk3p3);
        prov3.addBike(bk4p3);
        prov3.addBike(bk5p3);
        prov3.addBike(bk6p3);
        prov3.addBike(bk7p3);
        prov3.addBike(bk8p3);
        
        bikes.put(mountain, 1);
        bikes.put(road, 1);

        //getQuotes function output for same pickup and return area
        Quote quote1 = new Quote(c1, bikes, d1, loc1, loc1);
        ArrayList<ProviderQuotes> getQs = mainSystem.getQuotes(quote1, false);

        for (ProviderQuotes retQs: getQs) { //getQuotes printing
        	System.out.printf("Quote 1 - %s\n", retQs.toString());
        }

        //Expected getQuotes function output for same pickup and return area, with 1 road and 1 mountain bike
        ArrayList<Bike> bikes1 = new ArrayList<Bike>();
        bikes1.add(bk7p1);	//mountain bike
        bikes1.add(bk5p1); //road bike
        ProviderQuotes pq1 = new ProviderQuotes(quote1, prov1, null, bikes1); //Soul cycles provider quote

        ArrayList<Bike> bikes2 = new ArrayList<Bike>();
        bikes2.add(bk5p3);	//mountain bike
        bikes2.add(bk1p3); //road bike
        ProviderQuotes pq2 = new ProviderQuotes(quote1, prov3, null, bikes2); //Soul cycles provider quote

        ArrayList<ProviderQuotes> getQuotesResult1 = new ArrayList<ProviderQuotes>();
        getQuotesResult1.add(pq1);
        getQuotesResult1.add(pq2);

                
        //ASSERTION on getQuotes result for same pickup and return area
        assertEquals(getQuotesResult1.size(), getQs.size());
        
        for (int i = 0; i < getQs.size(); i++) {
     
        	assertEquals(getQs.get(i).getProvPickup(), getQuotesResult1.get(i).getProvPickup());
        	assertEquals(getQs.get(i).getProvReturn(), getQuotesResult1.get(i).getProvReturn());
        	assertEquals(getQs.get(i).getQuote(), getQuotesResult1.get(i).getQuote());
        	assertEquals(getQs.get(i).getBikes().size(), getQuotesResult1.get(i).getBikes().size());
        	
        	for (int a = 0; a < getQs.get(i).getBikes().size(); a++) {
        		assertEquals(getQs.get(i).getBikes().get(a).getBikeType(),getQuotesResult1.get(i).getBikes().get(a).getBikeType());
        	}
        	
        }
    }
    
    @Test
    void getQuotesTest2() {


        //getQuotes function output for different pickup and return locations, with 1 road and 1 mountain bike
        Quote quote2 = new Quote(c1, bikes, d1, loc2, loc3); 

        ArrayList<ProviderQuotes> getQs2 = mainSystem.getQuotes(quote2, false);

        for (ProviderQuotes retQs: getQs2) { //getQuotes printing
           System.out.printf("Quote 2 - %s\n", retQs.toString());
        }
        
        //Expected getQuotes function output for same pickup and return area, with 1 road and 1 mountain bike
        ArrayList<Bike> bikes3 = new ArrayList<Bike>();
        bikes3.add(bk4p2);	//mountain bike
        bikes3.add(bk3p2); //road bike
        ProviderQuotes pq3 = new ProviderQuotes(quote2, prov2, prov1, bikes3); //Soul cycles provider quote

        ArrayList<ProviderQuotes> getQuotesResult2 = new ArrayList<ProviderQuotes>();
        getQuotesResult2.add(pq3);
        
      //ASSERTION on getQuotes result for different pickup and return area
        assertEquals(getQuotesResult2.size(), getQs2.size());
        
        for (int i = 0; i < getQs2.size(); i++) {
     
        	assertEquals(getQs2.get(i).getProvPickup(), getQuotesResult2.get(i).getProvPickup());
        	assertEquals(getQs2.get(i).getProvReturn(), getQuotesResult2.get(i).getProvReturn());
        	assertEquals(getQs2.get(i).getQuote(), getQuotesResult2.get(i).getQuote());
        	assertEquals(getQs2.get(i).getBikes().size(), getQuotesResult2.get(i).getBikes().size());
        	
        	for (int a = 0; a < getQs2.get(i).getBikes().size(); a++) {
        		assertEquals(getQs2.get(i).getBikes().get(a).getBikeType(),getQuotesResult2.get(i).getBikes().get(a).getBikeType());
        	}
        	
        }
    }
    
    @Test
    void getQuotesTest3() { //tests that getQuotes only retrieves bikes available for a given date range
    	
    	//Books bikes bk7p1 and bk5p1 (the only road and mountain bikes for prov2) for daterange d1 (23.11.2019 - 26.11.2019)
    	ArrayList<Bike> bikes1 = new ArrayList<Bike>();
   	 	bikes1.add(bk3p2);	//mountain bike
        bikes1.add(bk4p2); //road bike
        
        Quote quote1 = new Quote(c1, bikes, d1, loc1, loc1);
    	ProviderQuotes pq1 = new ProviderQuotes(quote1, prov2, null, bikes1);
    	
    	Booking bookingOut = mainSystem.bookQuote(pq1,true); 
    	
    	
    	//getQuotes function output, should not return any quotes from provider 2
        LocalDate start = LocalDate.of(2019, 11, 25);
        LocalDate end = LocalDate.of(2019, 11, 29);
        DateRange newDR = new DateRange(start, end);
        
        Quote quote2 = new Quote(c1, bikes, newDR, loc1, loc1);
        
        ArrayList<ProviderQuotes> getQuotesResult3 = mainSystem.getQuotes(quote2, false);
        
        //testing expected and given values
        for (int i = 0; i < getQuotesResult3.size(); i++) {
        	if (getQuotesResult3.get(i).getProvPickup() == prov2) {
        		assert(false);
        	}
        }
    }
    
    @Test
    void bookQuotesTest1() {
    	
    	
    	//Expected book quote function output
    	ArrayList<Bike> bikes1 = new ArrayList<Bike>();
    	 bikes1.add(bk7p1);	//mountain bike
         bikes1.add(bk5p1); //road bike
         
    	Quote quote1 = new Quote(c1, bikes, d1, loc1, loc1);
    	Booking booking = new Booking(quote1, prov1, null, bikes1,true);
    	
    	
    	//bookQuote function output
    	ProviderQuotes pq1 = new ProviderQuotes(quote1, prov1, null, bikes1);
    	
    	Booking bookingOut = mainSystem.bookQuote(pq1,true);
    	
    	//testing equality between expected and given bookQuote bookings
        assertEquals(booking.calcDeposit(),bookingOut.calcDeposit());
        	
    	
    }
    
    
    @Test
    void updateLocationalStatusTest1() { //for bookings where bikes are collected
    	//variable initialisations
    	ArrayList<Bike> bikes1 = new ArrayList<Bike>();
   	 	bikes1.add(bk7p1);	//mountain bike
        bikes1.add(bk5p1); //road bike
        
        Quote quote1 = new Quote(c1, bikes, d1, loc1, loc1);
    	ProviderQuotes pq1 = new ProviderQuotes(quote1, prov1, null, bikes1);
    	
    	Booking booking = mainSystem.bookQuote(pq1,true); 
    	
    	//test function output values
    	assertEquals(booking.getLocationalStatus(), 0);
    	String s =  booking.updateLocationalStatus();
    	
    	for (int i = 2; i < 5; i++) {
    		assertEquals(booking.getLocationalStatus(),i);
    		s = booking.updateLocationalStatus();
    	}
    	assertEquals(booking.getLocationalStatus(), 5);
    }
    
    @Test
    void updateLocationalStatusTest2() { //for bookings where bikes are delivered
    	//variable initialisations
    	ArrayList<Bike> bikes1 = new ArrayList<Bike>();
   	 	bikes1.add(bk7p1);	//mountain bike
        bikes1.add(bk5p1); //road bike
        
        Quote quote1 = new Quote(c1, bikes, d1, loc1, loc1);
    	ProviderQuotes pq1 = new ProviderQuotes(quote1, prov1, null, bikes1);
    	
    	Booking bookingOut = mainSystem.bookQuote(pq1,true);
    	
    	//test function output values
    }
}

