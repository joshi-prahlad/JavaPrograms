/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author pkamaljo
 */
import java.awt.*;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.*;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.*;
import javax.swing.*;


public class GUI extends javax.swing.JFrame implements WindowListener {
    RouletteWheel wheel;
    Button spin,bet,reset;
    Label lBalance,lNumberBet,lBetType,lBetAmount,lBettingReport;
   TextField tBalance,tNumberBet,tBetAmount;
   JCheckBox red,black,high,low;
   //HashMap<JCheckBox,Integer> checkBoxCount=new HashMap<JCheckBox,Integer>();
   Set<String> selectedCheckBoxes=new TreeSet<String>();//need to be reset
   int iNumberBet;
   TextArea tBettingReport;
   JPanel wheelPanel;
   FlowLayout layout;
   
     /**
     * Creates a new instance of Java2DFrame
     */
    public GUI()
    {
        Container container=getContentPane();
        layout=new FlowLayout();
        container.setLayout(layout);
        
        layout.setVgap(50);
        //layout.setAlignment(layout.LEFT);
       // layout.layoutContainer(container);
        addWindowListener(this);

      
        
        wheel=new RouletteWheel();
        wheel.setBallDrawn(true);
        wheel.setScore(0);
       
        container.add(wheel);
       
        
        
         lBalance=new Label("Current Balance");
        container.add(lBalance);
        
        tBalance=new TextField();
        tBalance.setEditable(false);
        tBalance.setText(""+Roulette.initialBalance);
        container.add(tBalance);
        
        lNumberBet=new Label("Number Bet(1-36)?");
        container.add(lNumberBet);
        tNumberBet=new TextField();
        container.add(tNumberBet);
        
        red=new JCheckBox("RED");
        red.setName("RED");
        container.add(red);
        red.addItemListener(new ItemListener(){
            
                public void itemStateChanged(ItemEvent evt)
                {
                    checkBoxHandler(evt);
                }
            });
    
        black=new JCheckBox("BLACK");
        black.setName("BLACK");
        container.add(black);
        black.addItemListener(new ItemListener(){
            
                public void itemStateChanged(ItemEvent evt)
                {
                    checkBoxHandler(evt);
                }
            });
        
         high=new JCheckBox("HIGH");
         high.setName("HIGH");
        container.add(high);
        high.addItemListener(new ItemListener(){
            
                public void itemStateChanged(ItemEvent evt)
                {
                    checkBoxHandler(evt);
                }
            });
        
          low=new JCheckBox("LOW");
          low.setName("LOW");
        container.add(low);
        low.addItemListener(new ItemListener(){
            
                public void itemStateChanged(ItemEvent evt)
                {
                    checkBoxHandler(evt);
                }
            });
        
        lBetAmount=new Label("Betting Amount");
        container.add(lBetAmount);
        tBetAmount=new TextField();
        tBetAmount.setSize(25, 100);
        container.add(tBetAmount);
        
        
        //add a bet button
        
        bet=new Button("BET & SPIN");
        container.add(bet);
        bet.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
               betActionPerformed( e); 
            }});
            
        lBettingReport=new Label("Betting Report");
        container.add(lBettingReport);
        tBettingReport=new TextArea();
        tBettingReport.setEditable(false);
        container.add(tBettingReport);
        
        reset=new Button("RESET");
        reset.addActionListener(new ActionListener(){
           
            public void actionPerformed(ActionEvent evt)
            {
                resetActionPerformed(evt);
            }
        
        });
        container.add(reset);
        
        setSize(555,670);
        
        setVisible(true);
    }
    
    public void resetActionPerformed(ActionEvent evt)
    {
       red.setSelected(false);
       black.setSelected(false);
       high.setSelected(false);
       low.setSelected(false);
       tBalance.setText(""+Roulette.initialBalance);
       tBettingReport.setText("");
       tNumberBet.setText("");
       tBetAmount.setText("");
       wheel.setScore(0);
       repaint();
       
       selectedCheckBoxes=new TreeSet<String>(); //reset selected check boxes
       
       iNumberBet=0;
    }
    public void checkBoxHandler(ItemEvent evt)
    {
        Integer temp;
        JCheckBox jTemp=null;
        if(evt.getStateChange()==ItemEvent.SELECTED)
            {
                jTemp=(JCheckBox)evt.getSource();
                selectedCheckBoxes.add(jTemp.getName());
                System.out.println(""+jTemp.getName());
            
            }
            else
                if(evt.getStateChange()==ItemEvent.DESELECTED)
                {
                    jTemp=(JCheckBox)evt.getSource();
                    System.out.println(""+selectedCheckBoxes);
                    System.out.println("current one"+jTemp.getName());
                    selectedCheckBoxes.remove(jTemp.getName());
                }
        
    }
    public void betActionPerformed(ActionEvent e)
    {
       /**
        * Betting amount should be greater than 0 and
        * less than currently available balance
        */
        int bettingAmount=0;
        int reqAmount=0;
        iNumberBet=0;
            try{
                bettingAmount=Integer.parseInt(tBetAmount.getText());
               
            }catch(NumberFormatException nfe)
            {
                JOptionPane.showMessageDialog(null,"Enter a Valid Number");
                return;
            }
            
        
        int balance=Integer.parseInt(tBalance.getText());
        if(bettingAmount<=0||bettingAmount>balance)
        {
            if(bettingAmount<=0)
                JOptionPane.showMessageDialog(null,"Betting amount should be greater than zero");
            else
                JOptionPane.showMessageDialog(null,"Betting amount should be less than "+balance);
            return;
        }
        
        
        /**
         * At leat one of the bet types must have been selected by user before
         * clicking on bet.
         */
        if(tNumberBet.getText().trim().equals(""))
        {
            if(selectedCheckBoxes.size()==0)
            {
                JOptionPane.showMessageDialog(null,"Select at Least one bet type");
                return;
            }
        }
        else
            /**
             * ensure that the number bet is within the range of 1 to 36
             */
        {
            try{
                iNumberBet=Integer.parseInt(tNumberBet.getText().trim());
                if(iNumberBet<1||iNumberBet>36)
                    throw new NumberFormatException();
                
            }catch(NumberFormatException nfe)
            {
                JOptionPane.showMessageDialog(null, "Enter a valid Number Bet");
                return;
            }
            
        }
        /**
         * check whether there is enough balance for
         * playing all the bets.
         */
        if(iNumberBet==0)        
             reqAmount=bettingAmount*selectedCheckBoxes.size();
        else
            reqAmount=bettingAmount*(selectedCheckBoxes.size()+1);
        if(reqAmount>balance)
        {
            JOptionPane.showMessageDialog(null,"You don't have enough money Either decrease bet amount or bet types");
            return;
        }
        
        spintheWheel();
        
    }
    public void spintheWheel()
    {
        
        int currentBalance=Integer.parseInt(tBalance.getText());
        int finalBalance=currentBalance;
        int score=wheel.spin();
        repaint();//to reflect the change.
        int betAmount=Integer.parseInt(tBetAmount.getText().trim());
        tBettingReport.append("The lucky number is "+wheel.getLabel()+"\n");
        if(score==0||score==37)
        {int lostAmount;
            if(iNumberBet==0)
            {
                lostAmount=betAmount*selectedCheckBoxes.size();
                finalBalance=currentBalance-lostAmount;
            }
            else
            {
                lostAmount=betAmount*(selectedCheckBoxes.size()+1);
                finalBalance=currentBalance-lostAmount;;
            }
            tBalance.setText(""+finalBalance);
            tBettingReport.append("You have lost "+lostAmount+"$\n");
            
        }
        else
        {
           if(iNumberBet!=0&&iNumberBet==score)
           {
               finalBalance+=betAmount*Roulette.NUMBER;
               tBettingReport.append("You win "+betAmount*Roulette.NUMBER+ "$ in Number Bet\n");
           }
           if(iNumberBet!=0&&iNumberBet!=score)
           {
               finalBalance-=betAmount;
               tBettingReport.append("You loose "+betAmount+"$ in Number Bet\n");
           }
           if((selectedCheckBoxes.contains("RED"))&&wheel.getScoreColor(score).equals(Color.red))
           {
               finalBalance+=betAmount*Roulette.COLOR;
               tBettingReport.append("You win "+betAmount*Roulette.COLOR+"$ in Red Color Bet\n");
           }
           if((selectedCheckBoxes.contains("RED"))&&!wheel.getScoreColor(score).equals(Color.red))
           {
               finalBalance-=betAmount;
               tBettingReport.append("You loose "+betAmount+"$ in Red Color Bet\n");
           }
           if((selectedCheckBoxes.contains("BLACK"))&&wheel.getScoreColor(score).equals(Color.black))
           {
               finalBalance+=betAmount*Roulette.COLOR;
               tBettingReport.append("You win "+betAmount*Roulette.COLOR+"$ in Black Color Bet\n");
           }
           if((selectedCheckBoxes.contains("BLACK"))&&!wheel.getScoreColor(score).equals(Color.black))
           {
               finalBalance-=betAmount;
               tBettingReport.append("You loose "+betAmount+"$ in Black Color Bet\n");
           }
           if(selectedCheckBoxes.contains("EVEN"))
           {
               if(score%2==0)
               {
                   finalBalance+=betAmount*Roulette.EVEN;
                   tBettingReport.append("You win "+betAmount*Roulette.EVEN+"$ in Even Bet\n");
               }
               else
               {
                   finalBalance-=betAmount;
                   tBettingReport.append("You loose "+betAmount+"$ in Even Bet\n");
               }
           }
           if(selectedCheckBoxes.contains("ODD"))
           {
               if(score%2!=0)
               {
                   finalBalance+=betAmount*Roulette.ODD;
                   tBettingReport.append("You win "+betAmount*Roulette.ODD+"$ in Odd Bet\n");
               }
               else
               {
                   finalBalance-=betAmount;
                   tBettingReport.append("You loose "+betAmount+"$ in Odd Bet\n");
               }
           }
           if(selectedCheckBoxes.contains("HIGH"))
           {
               if(score>=19&&score<=36)
               {
                   finalBalance+=betAmount*Roulette.HIGH;
                   tBettingReport.append("You win "+betAmount*Roulette.HIGH+"$ in High Bet\n");
               }
               else
               {
                   finalBalance-=betAmount;
                   tBettingReport.append("You loose "+betAmount+"$ in High Bet\n");
               }
        }
           if(selectedCheckBoxes.contains("LOW"))
           {
               if(score>=1&&score<=18)
               {
                   finalBalance+=betAmount*Roulette.LOW;
                   tBettingReport.append("You win "+betAmount*Roulette.LOW+"$ in Low Bet\n");
               }
               else
               {
                   finalBalance-=betAmount;
                   tBettingReport.append("You loose "+betAmount+"$ in Low Bet\n");
               } 
           }
           int overallWin=finalBalance-Integer.parseInt(tBalance.getText().trim());
           if(overallWin>=0)
           {
               tBettingReport.append("Your overall win is "+overallWin+"$\n");
           }
           else
               tBettingReport.append("Your overall loss is "+overallWin*-1+"$\n");
           tBalance.setText(""+finalBalance);
           if(finalBalance<=0)
           {
               JOptionPane.showMessageDialog(null, "Game Over");
               resetActionPerformed(null);
           }
        
    }
  }
  
  
   
    	public void windowOpened(WindowEvent e) {}
	public void windowActivated(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowDeactivated(WindowEvent e) {}
	public void windowClosed(WindowEvent e) {}
        public void windowClosing(WindowEvent e) {}

    
    
    
    /**
     * Starts the program
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        
                GUI gui=new GUI();
                gui.setDefaultCloseOperation(GUI.EXIT_ON_CLOSE);
                
               
          
    }
}

class Roulette
{
    public static final int initialBalance=1000;
    public final static int NUMBER=35;
     
    public final static int HIGH=1;
    
    public final static int LOW=1;
    
    public final static int EVEN=1;
    
    public final static int ODD=1;
    
    public final static int COLOR=1;
}
