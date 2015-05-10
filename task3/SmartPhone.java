package demidov.hh.tasks.task3;
import java.util.Date;
import java.util.Random;


class SmartPhone { 

	private String model; //Encapsulaton violation need use private 
	private Date date; //Encapsulaton violation need use private 
	private String revision; //Encapsulaton violation need use private 
 
    
    /*A constructor is basically for this case of class def.
     * should have same access modifier cuz anyway you CAN NOT instantiate it outside of this package
    */
    public SmartPhone(String model, Date date, String revision) {
      //This preferred for more clear code
      this.model = model; 
      this.date = date;
      this.revision = revision;
    }
 
    
    // ------------------- HASHCODE CHANGE ---------------------------
    /* Old hashCode() 
     * Method is NOT overrided correctly
   	 * The method can violate the contract of hashCode that:
   	 * "If two objects are equal that MUST return same hasCode int representation"
   	   
   	   public int hashCode() {
         return new Random().nextInt(); | What a hack is that??? 
         								| Can return different random num for same equal object
         								| Case of wrong behavior with hash based data structures HashSet, HashMaps etc.
       }
    */
    
    
    /*New hashCode method can be:
     * 1. Most likely that you have calc of hash for all values components that used in equals() to follow the contract 
     * 2. Start with some nonzero val as constant for calc 
     * 3. Simplest impl: we can take hashes calc of date, string fields which has already overrided and impl
     * 4. Calculate hashCode on each field of the class and combine results to final return val  
     * */ 
    public int hashCode() {
    	
    	int hashResult = 13;
    	
    	hashResult = 33 * hashResult + this.model.hashCode();
    	hashResult = 33 * hashResult + this.date.hashCode();
    	hashResult = 33 * hashResult + this.revision.hashCode();
    	
    	return hashResult;
    	
    };
 
    // ------------------- HASHCODE CHANGE ---------------------------    
    
    
    
    // ------------------- EQUALS CHANGE ---------------------------
    
    /* OLD version of equals:   
     @Override
     public boolean equals(Object o) {
      
    	if (this == o)	//This part is fine  
    	  return true; //This part is fine
    	  
     -----------------  
      1)
        * Comparison on null is unnecessary
        * I believe  getClass() != o.getClass() is same as this == o 
        * More preferable check on instanceof 
        * if(o instanceof(SmartPhone)) .....
       
    	 
        if (o == null || getClass() != o.getClass()) return false;  //I would not do like this
      -----------------
      
      2)	
        Not good chosen name
        SmartPhone that passedObject = (SmartPhone) o;
      ----------------
         
 	  
      3)
       * With "passedObject" name and this key word and some formating  
      	  if (this.date.equals(passedObject.date) && 
      	  	  this.model.equals(passedObject.model) &&
      	  	  this.revision.equals(passedObject.revision)) 
      	  	  return true;
            	  
      	  return false
      ----------------
            
      if (!date.equals(that.date)) return false;
      if (!model.equals(that.model)) return false;
      if (!revision.equals(that.revision)) return false;
 
      return true;
      
    }
    */
      
    /*New version on equal*/
    @Override
    public boolean equals(Object o) {
    
    	if (this == o)	  
      	  return true; 
    	    	    	
    	if(o instanceof SmartPhone) {
    		
    		SmartPhone passedObject = (SmartPhone) o;
    		
    		 if (this.date.equals(passedObject.date) && 
    	      	 this.model.equals(passedObject.model) &&
    	      	 this.revision.equals(passedObject.revision)) 
    	      	  	  return true;
    	}
    	
    	return false;
    }
    
    // ------------------- EQUALS CHANGE ---------------------------
    
    
    /**
     * setters, getters and
     * some important methods
     *
     *
     */
  	
}
