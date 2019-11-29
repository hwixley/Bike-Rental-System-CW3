package uk.ac.ed.bikerental;

public class Location {
    private String postcode;
    private String address;

    public Location(String postcode, String address) {
        assert postcode.length() >= 6;
        this.postcode = postcode;
        this.address = address;
    }

    public boolean isNearTo(Location other) {
        String postA = postcode.substring(0,2);
        String locationB = other.getPostcode();
        String postB = locationB.substring(0,2);
        return postA.equals(postB);
    }

    public String getPostcode() {
        return postcode;
    }

    public String getAddress() {
        return address;
    }

    // You can add your own methods here

    public String toString() {
      return "Address: " + address + ", " + postcode;
    }
}
