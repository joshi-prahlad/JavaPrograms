/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author pkamaljo
 */

import java.awt.*;
import java.net.URL;
import javax.swing.*;



public class RouletteWheel extends JComponent {
	//Variables
	private int score;
	private Color ballColor = Color.yellow;
	private final static int[] scoreX = {94, 119, 84, 156, 47, 170, 32, 157, 46, 120, 84, 149,
		55, 130, 73, 162, 41, 172, 32, 41, 163, 33, 170, 56,
		150, 74, 129, 95, 106, 63, 140, 36, 167, 37, 168, 65, 141, 106};
	private final static int[] scoreY = {31, 168, 33, 142, 57, 100, 99, 56, 142, 32, 167, 47,
		151, 163, 36, 132, 65, 87, 111, 132, 65, 88, 110, 46,
		149, 162, 34, 168, 30, 157, 40, 122, 76, 76, 122, 40, 157, 169};
	private final static int[] scores = {0, 2, 14, 35, 23, 4, 16, 33, 21, 6, 18, 31, 19,
		8, 12, 29, 25, 10, 27, 37, 1, 13, 36, 24, 3, 15, 34, 22, 5, 17, 32, 20, 7, 11, 30, 26, 9, 28};
	boolean ballDrawn = false;
	static Image wheelImage;

	/**
	* This default constructor creates a new roulette wheel.
	*/
	public RouletteWheel(){
		setToolTipText("Roulette Wheel");
                ImageIcon icon=null;
                try
                {
                    icon = new ImageIcon(new URL("http://sites.google.com/site/roulletebiz/sdf/RouletteWheel.png"));
                }catch(Exception e)
                {
                    e.printStackTrace();
                }
		wheelImage = icon.getImage();
	}

	/**
	* This method paints the roulette wheel by drawing the image
	* and drawing the ball in the appropriate slot.
	* @param g the graphics context
	*/
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(wheelImage, 0, 0, this);
		if (ballDrawn){
			g.setColor(ballColor);
			g.fillOval(scoreX[score], scoreY[score], 10, 10);
		}
	}

	/**
	* This method sets the current score of the roulette wheel (the number of the slot containing the ball).
	* @param s the score
	*/
	public void setScore(int s){
		if (s < 0) s = 0;
		else if (s > 37) s = 37;
		score = s;
                
                
	}

	/**
	* This method "spins" the wheel by selecting the score at ranom.
	* @return the random score
	*/
	public int spin(){
		setScore((int)(38 * Math.random()));
		return score;
	}

	/**
	* This method returns the current score.
	* @return the number of the slot containing the ball
	*/
	public int getScore(){
		return score;
	}

	

	/**
	* This method returns the label of the current score. The label is the
	* same as the score, except that score 37 corresponds to 00.
	* @return the label of the score
	*/
	public String getLabel(){
		if (score == 37) return "00";
		else return String.valueOf(score);
	}

	
	/**
	* This method returns the color of a given score.
	* @param s the score
	* @return the color
	*/
	public Color getScoreColor(int s){
		if (s < 0) s = 0; else if (s > 37) s = 37;
		if (s == 0 | s == 37) return Color.green;
		else{
			int i = getIndex(s);
			if (i == 2 * (i / 2)) return Color.red;
			else return Color.black;
		}
	}

	/**
	* This method returns the color of the current score.
	* @return the color
	*/
	public Color getScoreColor(){
		return getScoreColor(score);
	}

	/**
	* This method sets the boolean state of the ball.
	* @param b true if the ball is drawn
	*/
	public void setBallDrawn(boolean b){
		ballDrawn = b;
		repaint();
	}

	


	

	/**
	* This method gets the index corresponding to a given score. The index starts at 0 and progresses
	* counterclockwise.
	* @param s the score
	*/
	public int getIndex(int s){
		int i = 0;
		if (s < 0) s = 0; else if (s > 37) s = 37;
		for (int j = 0; j < 38; j++) if (scores[j] == s) i = j;
		return i;
	}

	/**
	* This method returns the index of the current score.
	* @return the index of the current score
	*/
	public int getScoreIndex(){
		return getIndex(score);
	}


	/**
	* This method specifies the minimum size.
	* @return the 220 by 220 dimension
	*/
	public Dimension getMinimumSize(){
		return new Dimension(220, 220);
	}

	/**
	* This method returns the preferred size.
	* @return the 220 by 220 dimension
	*/
	public Dimension getPreferredSize(){
		return getMinimumSize();
	}

	/**
	* This method returns the preferred size.
	* @return the 220 by 220 dimension
	*/
	public Dimension MaximumSize(){
		return getMinimumSize();
	}
        
       

}
