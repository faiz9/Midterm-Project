package midterm;

import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;

public class CircBeanInfo extends SimpleBeanInfo{

    Class beanClass = Circ.class;

 

    public CircBeanInfo() {

 

    }

 

    public PropertyDescriptor[] getPropertyDescriptors() {

        try {

            PropertyDescriptor _myXStart = new PropertyDescriptor(

                    "myXStart", beanClass, "getMyXStart", "setMyXStart");

            _myXStart.setPropertyEditorClass(ZeroToHundredEditor.class);

            PropertyDescriptor _myYStart = new PropertyDescriptor(

                    "myYStart", beanClass, "getMyYStart", "setMyYStart");

            _myYStart.setPropertyEditorClass(ZeroToHundredEditor.class);

            PropertyDescriptor _myWidth = new PropertyDescriptor(

                    "myWidth", beanClass, "getMyWidth", "setMyWidth");

            _myWidth.setPropertyEditorClass(FiftyToThreeHundredEditor.class);

            PropertyDescriptor _myHeight = new PropertyDescriptor(

                    "myHeight", beanClass, "getMyHeight", "setMyHeight");

            _myHeight.setPropertyEditorClass(FiftyToThreeHundredEditor.class);

            PropertyDescriptor _myForeColor = new PropertyDescriptor(

                    "myForeColor", beanClass, "getMyForeColor", "setMyForeColor");           

            _myForeColor.setPropertyEditorClass(ColorEditor.class);

            PropertyDescriptor _myBackColor = new PropertyDescriptor(

                    "myBackColor", beanClass, "getMyBackColor", "setMyBackColor");

            _myBackColor.setPropertyEditorClass(ColorEditor.class);

            PropertyDescriptor _myFill = new PropertyDescriptor(

                    "myFill", beanClass, "getMyFill", "setMyFill");

            _myFill.setPropertyEditorClass(TrueFalseEditor.class);

            PropertyDescriptor[] pds = new PropertyDescriptor[]

                                       {

                                       _myXStart,

                                       _myYStart,

                                       _myWidth,

                                       _myHeight,

                                       _myForeColor,

                                       _myBackColor,

                                       _myFill

            };

            return pds;

        } catch (Exception exception) {

            exception.printStackTrace();

            return null;

        }

    }

}