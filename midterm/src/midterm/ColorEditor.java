package midterm;

import java.beans.PropertyEditorSupport;

public class ColorEditor extends PropertyEditorSupport {
    private String colorString;
    private String[] tags = { "Red","Green","Yellow","Blue"
           , "Black", "Cyan", "Orange", "Gray", "Magenta"};   
    private String[] validColors = {"Red","Green","Yellow","Blue"};
   
   
    public ColorEditor() {
       colorString="";
    }
   
    public String[] getTags() {
       return (String[]) tags.clone();
    }
   
    public void setAsText(String value) {
    	System.out.println("Value " + value);
       boolean validString=false;
       for(int i=0;i<validColors.length;i++) {
           if(value.equalsIgnoreCase(validColors[i])) {             
               colorString=value;
               validString=true;
               break;
           }         
       }
       if(!validString) {       
           throw new IllegalArgumentException(value);
       }
 
    }
   
    public String getAsText() {
       return colorString;
    }
}