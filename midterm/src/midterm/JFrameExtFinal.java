package midterm;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.awt.ItemSelectable;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.*;
import java.beans.BeanInfo;
import java.beans.Beans;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.awt.event.ActionEvent;

public class JFrameExtFinal extends JFrame implements ActionListener{
	private String tempFill = "";
	private String tempValueBackColor = "";
	private String tempValueForColor = "";
	private String tempHeight = "";
	private String tempWidth = "";
	private String tempX = "";
	private String tempY = "";
	private JPanel contentPane;
	private JLabel[] jlbPropNames = new JLabel[10];
	private JTextField[] jtfPropValues = new JTextField[10];
	private JComboBox[] jcbPropValues = new JComboBox[10];
	private PropertyDescriptor[] pd = null;
	private JPanel targetBeanObject = null;
	PropertyEditor[] pe = new PropertyEditor[10];
	JPanel jpPropValues = new JPanel();
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JFrameExtFinal frame = new JFrameExtFinal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public JFrameExtFinal() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 2, 0, 0));
		
		JPanel jpLeft = new JPanel();
		jpLeft.setBackground(Color.YELLOW);
		contentPane.add(jpLeft);
		
		JPanel jpRight = new JPanel();
		contentPane.add(jpRight);
		jpRight.setLayout(new BorderLayout(0, 0));
		
		JPanel jpController = new JPanel();
		jpController.setBackground(Color.MAGENTA);
		jpRight.add(jpController, BorderLayout.NORTH);
		
		JComboBox jcboClassName = new JComboBox();	
		jcboClassName.setMaximumRowCount(4);
			
		//add customized handler for JCombox
		jcboClassName.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {

				String className = (String)jcboClassName.getSelectedItem();
				//System.out.println(className); //test
				
				if (className.equals(""))
					return;
													
				//create target bean object
				try {
					targetBeanObject = (JPanel)Beans.instantiate(null,className);
				} catch (Exception e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
					
				//if targetBeanObject is a JPanel, 
				//replace existing jpLeft with targetBeanObject
				if (targetBeanObject instanceof JPanel){
					contentPane.remove(0);
					contentPane.add(targetBeanObject,0);
					contentPane.validate();
				}
				
				//create Class object
				Class classObject = null;
				//create BeanInfo object
				BeanInfo bi = null;
								
				try {
					classObject = Class.forName(className);
					//getBeanInfo to get properties from classObject 
					//and stop at JPanel class
					bi = Introspector.getBeanInfo(classObject, JPanel.class);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
								
				String propName;
				//get an array of PropertyDecriptor objects from BeanInfo
				pd = bi.getPropertyDescriptors();
				
				//get property name from PropertyDescriptor array element
				for (int i = 0; i < pd.length; i++){
										
					propName = pd[i].getName();
					//set the corresponding JLable text
					jlbPropNames[i].setText(propName);
					
					//call customEditorClass
					String propertyName = pd[i].getName();
					Class customEditorClass = pd[i].getPropertyEditorClass();

					PropertyEditor customEditor = null;
					
					//get the initial value for property value
					Method mget = pd[i].getReadMethod();
					Object robj = null;
					try {
						robj = mget.invoke(targetBeanObject, null);
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					String sobj = robj.toString();
					
					//determine whether to use text field or comboBox to display property values	
					//if no customEditorClass, display using JTextField
					if(i==1)
					{
						tempFill = sobj;
					}
					else if(i==3)
					{
						tempHeight = sobj;
					}
					else if(i==4)
					{
						tempWidth = sobj;
					}
					else if(i==5)
					{
						tempX = sobj;
					}
					else if(i==6)
					{
						tempY = sobj;
					}
					if (customEditorClass == null)
					{
						jtfPropValues[i].setText(sobj);
						jpPropValues.add(jtfPropValues[i]);						
					}
					else
					{
						try {
								customEditor = (PropertyEditor)customEditorClass.newInstance();
								pe[i] = customEditor;
								
								//call the getTags method to determine whether a comboBox is needed
								if (pe[i].getTags() == null)  //if getTags()== null,use JTextField		
									{
										jtfPropValues[i].setText(sobj);
										jpPropValues.add(jtfPropValues[i]);	//add a JTextField into the panel
										//set the initial value in the corresponding PropertyEditor
										pe[i].setAsText(sobj);	
									}
								//if getTags()!= null, then use JComboBox
								else   
									{										
										//retrieve list members through getTags()
										String[] tagList = pe[i].getTags();
								
										//If it's the first time, then adding tagList.
										//otherwise, do nothing to avoid duplication of 
										//comboBox items.
										if (jcbPropValues[i].getSelectedIndex() == -1){
										for (int j = 0; j < tagList.length; j++)
											jcbPropValues[i].addItem(tagList[j]); 
										}
										//set the initial value in the corresponding PropertyEditor
										pe[i].setAsText(sobj);	
										//retrieve the default selection from getAsText()
										jcbPropValues[i].setSelectedItem(pe[i].getAsText());
										
										//for this specific propertyValue, add JComboBox to display the tag array						
										jpPropValues.add(jcbPropValues[i]);
									}
						} catch (InstantiationException | IllegalAccessException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
					}						
				}
			}			
		});
		
		jcboClassName.setEditable(true);
		jcboClassName.addItem("");
		jcboClassName.addItem("midterm.Rect");
		jcboClassName.addItem("midterm.Circ");
		jcboClassName.addItem("midterm.Ticker");
		jpController.add(jcboClassName);
	
		JPanel jpInspector = new JPanel();
		jpRight.add(jpInspector, BorderLayout.CENTER);
		jpInspector.setLayout(new GridLayout(1, 2, 0, 0));
		
		JPanel jpPropNames = new JPanel();
		jpPropNames.setForeground(new Color(255, 255, 255));
		jpPropNames.setBackground(Color.PINK);
		jpInspector.add(jpPropNames);
		jpPropNames.setLayout(new GridLayout(10, 1, 0, 0));
		
		for (int i = 0; i < 10; i++){
			jlbPropNames[i] = new JLabel("");
			jpPropNames.add(jlbPropNames[i]);
		}
		
		jpPropValues.setBackground(Color.LIGHT_GRAY);
		jpInspector.add(jpPropValues);
		jpPropValues.setLayout(new GridLayout(10, 1, 0, 0));
		
		for (int i = 0; i < 10; i++){
			jtfPropValues[i]= new JTextField(10);
			jcbPropValues[i] = new JComboBox();
		}
		
		//define customized handler for JTextField
		for (int i = 0; i < 10; i++){
			if (jtfPropValues[i]!= null)
			{
				jtfPropValues[i].addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						int i;
						String propName = "", propValue = "";
						
						//determine whether there is a change in the property
						for (
								i = 0; i < jtfPropValues.length;i++){
							if (e.getSource() == jtfPropValues[i])
								break;
						}
						
						//if there is a change, get the index of the JLabel and JTextField
						propName = jlbPropNames[i].getName();
						propValue = jtfPropValues[i].getText();
						//System.out.println(propValue);   //test
						
						//access the pd array for its property type
						Class propType = pd[i].getPropertyType();
						String propTypeName = propType.getName();
						
						//create object array to store parameters
						//in the right data type
						Object[] params = new Object[1];
						
						if (propTypeName.equals("int")){
							params[0] = new Integer(Integer.parseInt(propValue));
						}
						else if (propTypeName.equals("double")){
							params[0] = new Double(Double.parseDouble(propValue));
						}
						else if (propTypeName.equals("boolean")){
							params[0] = new Boolean(propValue);
						}
						else if (propTypeName.equals("java.lang.String")){
							params[0] = propValue;
						}
						
						//get the set method object
						Method mset = pd[i].getWriteMethod();
						
						//invoke set method and pass the right parameters to the target bean
						try {
							
							if(i==0)
							{
								Boolean val = validateBackColor(propValue);
								if(!val)
								{
									jtfPropValues[i].setText(tempValueBackColor);
									return;
								}
								else
								{
									System.out.println("tempValueBackColor : " + tempValueBackColor);
									tempValueBackColor = propValue;
								}
							}
							else if(i==1)
							{
								System.out.println("sdfsdfasdfasdf");
								Boolean val = validateMyFill(propValue);
								if(!val)
								{
									jtfPropValues[i].setText(tempFill);
									return;
								}
								else
								{
									System.out.println("tempValue : " + tempFill);
									tempFill = propValue;
								}
							}
							else if(i==2)
							{
								Boolean val = validateForColor(propValue);
								if(!val)
								{
									jtfPropValues[i].setText(tempValueForColor);
									return;
								}
								else
								{
									System.out.println("tempValueForColor : " + tempValueForColor);
									tempValueForColor = propValue;
								}
							} 
							
							else if(i == 3)
							{
								Boolean val = validateHeight(propValue);
								if(!val)
								{
									jtfPropValues[i].setText(tempHeight);
									return;
								}
								else
								{
									System.out.println("tempHeight : " + tempHeight);
									tempHeight = propValue;
								}
							} 
							else if(i == 4)
							{
								Boolean val = validateHeight(propValue);
								if(!val)
								{
									jtfPropValues[i].setText(tempWidth);
									return;
								}
								else
								{
									System.out.println("tempHeight : " + tempWidth);
									tempWidth = propValue;
								}
							} 
							else if(i == 5)
							{
								Boolean val = validateXY(propValue);
								if(!val)
								{
									jtfPropValues[i].setText(tempX);
									return;
								}
								else
								{
									System.out.println("tempX : " + tempX);
									tempX = propValue;
								}
							} 
							else if(i == 6)
							{
								System.out.println("tempY : " + tempY);
								Boolean val = validateXY(propValue);
								if(!val)
								{
									
									jtfPropValues[i].setText(tempY);
									return;
								}
								else
								{
									System.out.println("tempY : " + tempY);
									tempY = propValue;
								}
							}
							
								mset.invoke(targetBeanObject, params);
								
								//if there is a custom pe, pass the value through setAsText()
								if (pe[i] != null)
								{
									pe[i].setAsText(propValue);
								}
									
						} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
							
							//if an exception is thrown, get the old value through getAsText()
							String oldValue = pe[i].getAsText();
							jtfPropValues[i].setText(oldValue);
							 
						}						
					}
				});
			}
			
		for (int j = 0; j < 10; j++){
			if (jcbPropValues[j]!= null)
			{				
				jcbPropValues[j].addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						int i;
						String propName = "", propValue = "";
								
						//determine whether there is a change in the property
						for (i = 0; i < jcbPropValues.length;i++){
							if (e.getSource()== jcbPropValues[i])
							{
								break;
							}
						}
						
						//if there is a change, get the index of the JLabel and JTextField
						propName = jlbPropNames[i].getName();
						propValue = (String)jcbPropValues[i].getSelectedItem();
											
						//access the pd array for its property type
						Class propType = pd[i].getPropertyType();
						String propTypeName = propType.getName();
						
						//create object array to store parameters
						//in the right data type
						Object[] params = new Object[1];
						
						if (propTypeName.equals("int")){
							params[0] = new Integer(Integer.parseInt(propValue));
						}
						else if (propTypeName.equals("double")){
							params[0] = new Double(Double.parseDouble(propValue));
						}
						else if (propTypeName.equals("boolean")){
							params[0] = new Boolean(propValue);
						}
						else if (propTypeName.equals("java.lang.String")){
							params[0] = propValue;
						}
						
						//get the set method object
						Method mset = pd[i].getWriteMethod();
						
						//invoke set method and pass the right parameters to the target bean
						try {
							mset.invoke(targetBeanObject, params);
							
							//if there is a custom pe, pass the value through setAsText()
							if (pe[i] != null)
							{
								//System.out.println("propValue : " + propValue);
								pe[i].setAsText(propValue);
								 
							}
						} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {
							// TODO Auto-generated catch block
							 
							e1.printStackTrace();
							
							//if an exception is thrown, get the old value through getAsText()
							String oldValue = pe[i].getAsText();
							jcbPropValues[i].setSelectedItem(oldValue);
						}						
					}
				});
			}
		}
	}
		
		
}
	private boolean validateMyFill(String value) {  
		if(value.equalsIgnoreCase("yes") || value.equalsIgnoreCase("no"))
   	{
			return true;
   	}
		else
		{
			JOptionPane.showConfirmDialog(null,
	                 "Invalid Fill Value " + value + ". Please enter only yes or no.",
	                 "Validation failed!!!", 
	                 JOptionPane.CLOSED_OPTION,
	                 JOptionPane.WARNING_MESSAGE); 	
			return false;
		}
	}
	 
	private boolean validateBackColor(String value) {  
 	   if(value.equalsIgnoreCase("Green") || value.equalsIgnoreCase("Red") 
 			   || value.equalsIgnoreCase("Yellow") || value.equalsIgnoreCase("Blue"))
   	{
			 
			return true;
   	}
		else
		{
			JOptionPane.showConfirmDialog(null,
	                 "Invalid Back color Value " + value + ". Please enter valid color",
	                 "Validation failed!!!", 
	                 JOptionPane.CLOSED_OPTION,
	                 JOptionPane.WARNING_MESSAGE); 
			return false;
		}
	}
	private boolean validateForColor(String value) {  
	  	   if(value.equalsIgnoreCase("Green") || value.equalsIgnoreCase("Red") 
	  			   || value.equalsIgnoreCase("Yellow") || value.equalsIgnoreCase("Blue"))
	    	{
				  
				return true;
	    	}
			else
			{
				JOptionPane.showConfirmDialog(null,
		                 "Invalid For color Value " + value + ". Please enter valid color",
		                 "Validation failed!!!", 
		                 JOptionPane.CLOSED_OPTION,
		                 JOptionPane.WARNING_MESSAGE); 
				return false;
			}
		}
	private boolean validateHeight(String str) {  
		 
		Integer result = null;
		    if (null == str || 0 == str.length()) {
		    	JOptionPane.showConfirmDialog(null,
		                 "Invalid height Value " + str + ". Please enter valid number",
		                 "Validation failed!!!", 
		                 JOptionPane.CLOSED_OPTION,
		                 JOptionPane.WARNING_MESSAGE); 
		    	return false;
		    }
		    try {
		        result = Integer.parseInt(str);
		    } 
		    catch (NumberFormatException e) {
		        String negativeMode = "";
		        if(str.indexOf('-') != -1)
		            negativeMode = "-";
		        str = str.replaceAll("-", "" );
		        if (str.indexOf('.') != -1) {
		            str = str.substring(0, str.indexOf('.'));
		            if (str.length() == 0) {
		                return (Integer)0 != null;
		            }
		        }
		        String strNum = str.replaceAll("[^\\d]", "" );
		        if (0 == strNum.length()) {
		        	JOptionPane.showConfirmDialog(null,
			                 "Invalid height Value " + str + ". Please enter valid number.",
			                 "Validation failed!!!", 
			                 JOptionPane.CLOSED_OPTION,
			                 JOptionPane.WARNING_MESSAGE); 
		        	return false;
		        }
		        JOptionPane.showConfirmDialog(null,
		                 "Invalid height Value " + str + ". Please enter valid number.",
		                 "Validation failed!!!", 
		                 JOptionPane.CLOSED_OPTION,
		                 JOptionPane.WARNING_MESSAGE); 
		    }
		    if(result < 0 || result > 100)
		    {
		    	JOptionPane.showConfirmDialog(null,
		                 "Invalid height Value " + str + ". Please enter valid number.",
		                 "Validation failed!!!", 
		                 JOptionPane.CLOSED_OPTION,
		                 JOptionPane.WARNING_MESSAGE); 
		    	return false;
		    }
		    else
		    {
		    	return true;
		    }
		}
private boolean validateXY(String str) {  
		 
		Integer result = null;
		    if (null == str || 0 == str.length()) {
		    	JOptionPane.showConfirmDialog(null,
		                 "Invalid X Value " + str + ". Please enter valid number.",
		                 "Validation failed!!!", 
		                 JOptionPane.CLOSED_OPTION,
		                 JOptionPane.WARNING_MESSAGE); 
		    	return false;
		    }
		    try {
		        result = Integer.parseInt(str);
		    } 
		    catch (NumberFormatException e) {
		        String negativeMode = "";
		        if(str.indexOf('-') != -1)
		            negativeMode = "-";
		        str = str.replaceAll("-", "" );
		        if (str.indexOf('.') != -1) {
		            str = str.substring(0, str.indexOf('.'));
		            if (str.length() == 0) {
		                return (Integer)0 != null;
		            }
		        }
		        String strNum = str.replaceAll("[^\\d]", "" );
		        if (0 == strNum.length()) {
		        	JOptionPane.showConfirmDialog(null,
			                 "Invalid X Value " + str + ". Please enter valid number.",
			                 "Validation failed!!!", 
			                 JOptionPane.CLOSED_OPTION,
			                 JOptionPane.WARNING_MESSAGE); 
		        	return false;
		        }
		        JOptionPane.showConfirmDialog(null,
		                 "Invalid X Value " + str + ". Please enter valid number.",
		                 "Validation failed!!!", 
		                 JOptionPane.CLOSED_OPTION,
		                 JOptionPane.WARNING_MESSAGE); 
		        return false;
		    }
		    if(result < 50 || result > 300)
		    {
		    	JOptionPane.showConfirmDialog(null,
		                 "Invalid X Value " + str + ". Please enter valid number.",
		                 "Validation failed!!!", 
		                 JOptionPane.CLOSED_OPTION,
		                 JOptionPane.WARNING_MESSAGE); 
		    	return false;
		    }
		    else
		    {
		    	return true;
		    }
		}	

	@Override
	public void actionPerformed(ActionEvent e) {
			
	}

}