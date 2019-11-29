package uk.ac.ed.bikerental;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

public class ValuationPolicyTests {
    // You can add attributes here

    //Providers
    Provider prov1 = new Provider("Soul Cycles", loc3, "3298472394", "8:00 - 20:00", depositRate1);
    Provider prov2 = new Provider("Bill's Bikes", loc2, "12345678", "8:00 - 20:00", depositRate2);
    Provider prov3 = new Provider("Andy's Bikes", loc1, "3298472394", "8:00 - 20:00", depositRate1);

    @BeforeEach
    void setUp() throws Exception {
        // Put setup here
    	
    }
    
    // TODO: Write tests for valuation policies
}
