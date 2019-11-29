package uk.ac.ed.bikerental;

public class Partnership{

  private Provider prov1;
  private Provider prov2;

  public Partnership(Provider p1, Provider p2){
    prov1 = p1;
    prov2 = p2;
  }

  public Provider getProv1(){
    return prov1;
  }

  public Provider getProv2(){
    return prov2;
  }

  public String toString(){
      return null;
  }
}
