package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.*;

public class BikeValue implements ValuationPolicy {

    public BigDecimal calculateValue(Bike bike, LocalDate date){
      BigDecimal years = new BigDecimal(ChronoUnit.YEARS.between(bike.getManufactureDate(), date));

      BigDecimal originalVal = bike.getType().getReplacementValue();
      BigDecimal depreciationRate = bike.getType().getDeprRate();

      if (bike.getProvider().getValType()) { //ValType represents the type of valuation a provider uses on their bikes
        BigDecimal linearDep = originalVal.subtract(((years.multiply(depreciationRate)).multiply(originalVal)));
        return linearDep.setScale(2, RoundingMode.CEILING);

      } else {
        BigDecimal one = new BigDecimal(1);
        BigDecimal two = new BigDecimal(2);
        BigDecimal dblDecliningDep = originalVal.multiply((one.subtract(depreciationRate.multiply(two))).pow(years.intValue()));

        return dblDecliningDep.setScale(2, RoundingMode.CEILING);
      }
    }

}
