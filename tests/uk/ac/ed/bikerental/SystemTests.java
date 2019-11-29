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
    Bike bk4p2 = new Bike(prov2, road, rentP3);

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
    void myFirstTest() {
    	Main.providers.add(prov1);
        Main.providers.add(prov2);
        Main.providers.add(prov3);
        
        Main.customers.add(c1);
        
        Main.bikeTypes.add(hybrid);
        Main.bikeTypes.add(road);
        Main.bikeTypes.add(mountain);
        
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
        
        Main.partnerships.add(ps1);
        
        bikes.put(hybrid, 1);
        bikes.put(road, 1);
        
        Quote quote1 = new Quote(c1, bikes, d1, loc1, loc1);
        ArrayList<ProviderQuotes> getQs = mainSystem.getQuotes(quote1);

        for (ProviderQuotes retQs: getQs) {
           System.out.printf("Quote 1 - %s\n", retQs.toString());
        }
    }
}
