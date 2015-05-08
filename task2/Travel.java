package demidov.hh.tasks.task2;
import java.util.Random;


public class Travel {

	private ICar car;
	 
    /*public Travel(Car car) {
    	this.car =car;
    }*/
 
	
	public void setCar(ICar car)
	{
		this.car = car;
	}
   
	
    public void doTravel() {
      if (car != null) {
        System.out.println((car.amortization() * 5 + car.repair()) * someInnerLogic());
      }
    }
 
 
    private float someInnerLogic() {
      return new Random().nextFloat() + 1;
    }
 
	
    
    public static void main(String[] args) {
		
    	Ford ford = new Ford();
    	Opel opel = new Opel();
    	
    	Travel travel = new Travel();
    	
    	travel.setCar(opel);
    	travel.doTravel();
    	
    	travel.setCar(ford);
    	travel.doTravel();
    	
    	
	}
}
