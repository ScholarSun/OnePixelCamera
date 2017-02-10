package XiaoPix;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class AllenGUI extends JFrame
{
	 protected JPanel mainwindow = new JPanel();
	 protected JPanel sidewindow = new JPanel();
	 protected ImagePanel evan= new ImagePanel();
	 protected HistoPanel allen= new HistoPanel();
	 	protected int drawcolo[][] = new int[300][300];
	 	protected static TextArea instructions;
	 	protected int drawx = 0;
	    protected int drawy = 0;
	    protected static int phase = 0;
	    
	    public AllenGUI()
	    {
       mainwindow.setLayout(new GridLayout(2,2));
       
       setContentPane(mainwindow);
       instructions = new TextArea("---Pleaz Klik Hear To Start Collecting.---");
       //mainwindow.add(instructions,BorderLayout.LINE_START);
       //mainwindow.add(sidewindow,BorderLayout.LINE_END);
       //mainwindow.add(allen,BorderLayout.LINE_START);
       
       mainwindow.add(instructions);
     
       instructions.addMouseListener(new MouseAdapter()        
           {
               public void mouseClicked(MouseEvent e)  
               {  
            	   if(AllenGUI.phase==0)
                  {
            		   TwoWaySerialComm.send = 1;
            		   AllenGUI.phase = 1;
            		   TwoWaySerialComm.collect = true;
            		   AllenGUI.instructions.setText("Now collecting, please wait...");
                  }
                  
               } 
           }
       );
       mainwindow.add(evan);
       mainwindow.add(allen);
       
       setTitle("Heyyyyyyyyy ;)");
       setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
       setLocationRelativeTo (null);           // Center window.
       //pack();
       setVisible(true);
       setSize(1200,800);
       //allen.setSize(550,420);
       //evan.setSize(600,600);
       int bufferh = TwoWaySerialComm.h;
	   int bufferw = TwoWaySerialComm.w;
	   //Wait Until Thing Clicked to start.
	   while(TwoWaySerialComm.send==0)
	   {
		   System.out.print("");
	   }
	   
       while(true)
       {
    	   if(TwoWaySerialComm.w>=300)
    	   {
    		   TwoWaySerialComm.collect=false;
    		   System.out.println("Collection is finished.");
    		   break;
    	   }
    	   if(TwoWaySerialComm.h!=bufferh||TwoWaySerialComm.w!=bufferw)
    	   {
    		   bufferh = TwoWaySerialComm.h;
        	   bufferw = TwoWaySerialComm.w;
    		   //System.out.println("happy times.");
    		
        	   System.out.print("");
        	   evan.repaint();
        	   allen.repaint();
    	   }
    	   System.out.print("");
       }
       
       
       
     
       
       	    }
	   public class ImagePanel extends JPanel
	    {
	        public void paintComponent(Graphics g) 
		    {
	            super.paintComponent(g);

	            for(int a = 0;a<TwoWaySerialComm.w*300;a++)
	            {
	            	g.setColor (new Color (TwoWaySerialComm.pixels[a%300][a/300],TwoWaySerialComm.pixels[a%300][a/300],TwoWaySerialComm.pixels[a%300][a/300])); 
	            	//g.setColor (new Color(144,144,144));
	            	// randomness!!! g.setColor (new Color((a)%256,(a)%212,(a)%208));
	            	g.fillRect (a%300*2+5,a/300*2+5,2,2);
	            }
	            if(TwoWaySerialComm.w%2==0)
	            for(int a = 1;a<TwoWaySerialComm.h;a++)
	            {
	            	g.setColor (new Color (TwoWaySerialComm.pixels[a][TwoWaySerialComm.w],TwoWaySerialComm.pixels[a][TwoWaySerialComm.w],TwoWaySerialComm.pixels[a][TwoWaySerialComm.w]));
	            	//g.setColor (new Color (TwoWaySerialComm.pixels[TwoWaySerialComm.h][TwoWaySerialComm.w],TwoWaySerialComm.pixels[TwoWaySerialComm.h][TwoWaySerialComm.w],TwoWaySerialComm.pixels[TwoWaySerialComm.h][TwoWaySerialComm.w])); 
	            	//g.setColor (new Color(144,144,144));
	            	// randomness!!! g.setColor (new Color((a)%256,(a)%212,(a)%208));
	            	g.fillRect (a*2+5,TwoWaySerialComm.w*2+5,2,2);
	            }
	            else
	            	for(int a = 299;a>=TwoWaySerialComm.h;a--)
		            {
	            		g.setColor (new Color (TwoWaySerialComm.pixels[a][TwoWaySerialComm.w],TwoWaySerialComm.pixels[a][TwoWaySerialComm.w],TwoWaySerialComm.pixels[a][TwoWaySerialComm.w]));
		            	//g.setColor (new Color(144,144,144));
		            	// randomness!!! g.setColor (new Color((a)%256,(a)%212,(a)%208));
		            	g.fillRect (a*2+5,TwoWaySerialComm.w*2+5,2,2);
		            }
	        }
	    }
	   
	   public class HistoPanel extends JPanel
	    {
	        public void paintComponent(Graphics g) 
		    {
	            super.paintComponent(g);
	            int sum = 0; 

            			g.setColor (new Color (0, 80, 180));
	            		for (int a = 0; a < 256; a++)
	            		{
	            			if(TwoWaySerialComm.histogram[a]!=0)
	            			{
	            			g.fillRect (a*2+5,5,2,(TwoWaySerialComm.histogram[a]*4000)/(TwoWaySerialComm.numofpixels+1));
	            			//g.fillRect (a*2,80,86,TwoWaySerialComm.histogram[a]);
	            			
	            			//System.out.println("\n"+a+":"+TwoWaySerialComm.histogram[a]);
	            			}
	            		}
	            	
	        }
	    }
	    
}