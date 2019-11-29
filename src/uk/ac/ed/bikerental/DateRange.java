package uk.ac.ed.bikerental;

import java.time.LocalDate;

import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.function.BooleanSupplier;

public class DateRange {
    private LocalDate start, end;

    public DateRange(LocalDate start, LocalDate end) {
        this.start = start;
        this.end = end;
    }

    public LocalDate getStart() {
        return this.start;
    }

    public LocalDate getEnd() {
        return this.end;
    }

    public long toYears() {
        return ChronoUnit.YEARS.between(this.getStart(), this.getEnd());
    }

    public long toDays() {
        return ChronoUnit.DAYS.between(this.getStart(), this.getEnd());
    }

    public String toString() {
      return "Start: " + start.toString() + "\nEnd: " + end.toString();
    }
    
    /*
     * Method compares whether 2 bookings overlap, this method will be inside another loop
     * If a daterange returns true on every iteration of overlaps the given bikes are considered available for that daterange
     */
    public Boolean overlaps(DateRange other) {

        LocalDate compareStart = other.getStart();
        LocalDate compareEnd = other.getEnd();

        LocalDate queryStart = start;
        LocalDate queryEnd = end;

        if (queryStart.isAfter(compareEnd)){
          return false;
        }else if(queryEnd.isBefore(compareStart)){
          return false;
        }else{
          return true;
        }
    }

    @Override
    public int hashCode() {
        // hashCode method allowing use in collections
        return Objects.hash(end, start);
    }

    @Override
    public boolean equals(Object obj) {
        // equals method for testing equality in tests
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DateRange other = (DateRange) obj;
        return Objects.equals(end, other.end) && Objects.equals(start, other.start);
    }

    // You can add your own methods here
}
