
package midterm;

import javax.swing.*;

import java.awt.*;

public class Ticker extends JPanel implements Runnable

{

  private int myXStart=20;

  private int myYStart=50;

  private Thread thread;

  private String myForeColor = "Blue";

  private String myBackColor = "Yellow";

  private boolean myMoving=false;

  private String myMessage="Hello";

  public Ticker()

  {

    thread = new Thread(this);

    thread.start();

  }

  public void paintComponent(Graphics g)

  {

    super.paintComponent(g);

    if (myBackColor.equalsIgnoreCase("Green"))

    {

      this.setBackground(Color.green);

    }

    else if (myBackColor.equalsIgnoreCase("Blue"))

    {

      this.setBackground(Color.blue);

    }

    else if (myBackColor.equalsIgnoreCase("Yellow"))

    {

      this.setBackground(Color.yellow);

    }

    if (myForeColor.equalsIgnoreCase("Green"))

    {

      g.setColor(Color.green);

    }

    else if (myForeColor.equalsIgnoreCase("Blue"))

    {

      g.setColor(Color.blue);

    }

    else if (myForeColor.equalsIgnoreCase("Yellow"))

    {

      g.setColor(Color.yellow);

    }

    g.drawString(myMessage,myXStart, myYStart);

  }

  public void run()

  {

    while (true)

    {

 

      if (myMoving)

      {

        myXStart = (myXStart + 10)%this.getWidth();

        repaint();

      }

      try

      {

        Thread.sleep(100);

      }

      catch (InterruptedException ex)

      {

      }

    }

  }

  public void setMyXStart(int myXStart)

  {

    this.myXStart = myXStart;

    repaint();

  }

  public int getMyXStart()

  {

    return myXStart;

  }

  public void setMyYStart(int myYStart)

  {

    this.myYStart = myYStart;

    repaint();

  }

  public int getMyYStart()

  {

    return myYStart;

  }

  public void setMyForeColor(String myForeColor)

  {

    this.myForeColor = myForeColor;

    repaint ( );

  }

  public String getMyForeColor()

  {

    return myForeColor;

  }

  public void setMyBackColor(String myBackColor)

  {

    this.myBackColor = myBackColor;

    repaint ( );

  }

  public String getMyBackColor()

  {

    return myBackColor;

  }

  public void setMyMoving(boolean myMoving)

  {

    this.myMoving = myMoving;

    repaint( );

  }

  public boolean isMyMoving()

  {

    return myMoving;

  }

  public void setMyMessage(String myMessage)

  {

    this.myMessage = myMessage;

    repaint( );

  }

  public String getMyMessage()

  {

    return myMessage;

}
  
}