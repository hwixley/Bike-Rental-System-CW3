package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.util.Objects;

public class BikeType {

  private String bikeT;
  private BigDecimal replVal;
  private BigDecimal depreciationRate;

  public BikeType(String bikeT, BigDecimal replVal) {
      this.bikeT = bikeT;
      this.replVal = replVal;
  }

  public BikeType(String bikeT, BigDecimal replVal, BigDecimal deprRate) {
    this.bikeT = bikeT;
    this.replVal = replVal;
    depreciationRate = deprRate;
  }

    public BigDecimal getReplacementValue() {
        // TODO: Implement Bike.getReplacementValue
        assert false;
        return null;
    }

    public BigDecimal getDeprRate() {
      return depreciationRate;
    }

    public BigDecimal getRepValue() {
    	return replVal;
    }
}
