package demidov.hh.tasks.task1;
import java.math.BigDecimal;


public class NumberCalculation {

	
	private NumberCalculation() {}

		
	public static BigDecimal fromHexToDem(int val1, int val2)
	{
		 BigDecimal aBigDem = new BigDecimal(Integer.valueOf(val1));
		 BigDecimal bBigDem = new BigDecimal(Integer.valueOf(val2));
		    		 
		 BigDecimal sum =aBigDem.add(bBigDem);
		 System.out.println(sum);
		 
		 return sum;
	}
	
	
	public static void main(String[] args) {
		
		
		int a = 0x7ffffffc;
		int b = 0x7ffffffe;
		
		fromHexToDem(a, b);
	
	}

}
