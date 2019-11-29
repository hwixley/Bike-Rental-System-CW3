package uk.ac.ed.bikerental;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;


import org.junit.jupiter.api.*;

public class ValuationPolicyTests {
    // You can add attributes here
	 Location loc3 = new Location("EH2 6H3", "6 Tustle Close, Edinburgh");
	 BigDecimal depositR = new BigDecimal(0.1);
	 BigDecimal depreciationR = new BigDecimal(0.1);
	 Provider provider1 = new Provider("testProvider1", loc3, "0123456789" , "9 - 5", depositR, false);
	 Provider provider2 = new Provider("testProvider1", loc3, "0123456789" , "9 - 5", depositR, true);
	 BigDecimal value1 = new BigDecimal(900.0);
	 BigDecimal dRental = new BigDecimal(30);
	 BikeType hybrid = new BikeType("Hybrid", value1, depreciationR);
	 
	 
	 LocalDate bikeCreated1 = LocalDate.of(2010, 01, 01);
	 LocalDate currentDate = LocalDate.of(2013, 01, 01);
	 Bike testBike1 = new Bike(provider1, hybrid, dRental, bikeCreated1);
	 Bike testBike2 = new Bike(provider2, hybrid, dRental, bikeCreated1);
	 
	 BigDecimal result1 = new BigDecimal(630);
	 BigDecimal result2 = new BigDecimal(460.8);
	 
	 BikeValue bVal = new BikeValue();
	 
	 /*
	  * Comparing the two test values depending on which depreciation rate the providers are implementing
	  * 
	  */
	 
	 void test1() {
		 
		 System.out.println(bVal.calculateValue(testBike1, currentDate));
		 System.out.println(bVal.calculateValue(testBike1, currentDate));
		 
		 assertEquals(bVal.calculateValue(testBike1,currentDate), result1);
		 assertEquals(bVal.calculateValue(testBike2,currentDate), result2);
	 }


}
