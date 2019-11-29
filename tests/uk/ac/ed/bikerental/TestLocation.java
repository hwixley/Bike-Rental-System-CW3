package uk.ac.ed.bikerental;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

class TestLocation {

    Location loc1 = new Location("EH3 9JD", "1 Glen Street, Tollcross, Edinburgh");
    Location loc2 = new Location("YW1 9AD", "5 Yendo Avenue, Inverness");
    Location loc3 = new Location("EH2 6H3", "6 Tustle Close, Edinburgh");
    Location loc4 = new Location("YW2 6H3", "10 Sesame Street, Inverness");
    Location loc5 = new Location("YK2 6H3", "13 Cockburn Street, Edinburgh");
    @BeforeEach
    void setUp() throws Exception {
        
    	assert(loc1.isNearTo(loc3));
    	assert(loc1.isNearTo(loc3));
    	assert(loc2.isNearTo(loc4));
    	assert(!loc4.isNearTo(loc5));    	
    }
    

}
