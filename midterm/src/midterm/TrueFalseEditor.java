package midterm;

import java.beans.PropertyEditorSupport;

public class TrueFalseEditor extends PropertyEditorSupport {
    private boolean propertyValue=false;
   
    public TrueFalseEditor () {
      
    }
   
    public String[] getTags() {
       return null;
    }
   
    public void setAsText(String value) {    
    	boolean val;
    	try
    	{
    		 
    	    val= new Boolean(value).booleanValue();
    	}
    	catch(Exception ex)
    	{
    		throw new IllegalArgumentException(value);
    	}
    	propertyValue=val;
       
    }
   
    public boolean getAsBoolean() {
       return propertyValue;
    }
}

