import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.Timer;
import javax.swing.*;

public class GamePanel extends JPanel{

	JPanel contextPane;
	Frame frame;
	private double firstTime;
	final int DELAY = 30; 
	public Timer t;
	boolean firstStart = true;
	public int preySpeedFast = 8;
	public int preySpeedSlow = 4;
	public int predSpeed = 5;
	public int gameScore = 0;
	public int minutes; 
	public ArrayList<Creature> creatures;
	public ArrayList<SlowPrey> slowPreyCreaturesArray;
	public ArrayList<FastPrey> fastPreyCreaturesArray;
	public ArrayList<Integer> xValues;
	public ArrayList<Integer> yValues;
	public Predator predatorCreature;
	public SlowPrey slowPreyCreature;
	public FastPrey fastPreyCreature;

	/**
	 * Instantiates a new game panel.
	 */
	public GamePanel() {

		contextPane = new JPanel();
		creatures = new ArrayList<Creature>();
		slowPreyCreaturesArray = new ArrayList<SlowPrey>();
		fastPreyCreaturesArray = new ArrayList<FastPrey>();

		xValues = new ArrayList<Integer>();
		yValues = new ArrayList<Integer>();


		//Create Array with Random Numbers for X and Y
		for (int x = 0; x < 540; x += 40) {
			xValues.add(x);
		}
		Collections.shuffle(xValues);

		for (int y = 0; y < 540; y += 40) {
			yValues.add(y);
		}
		Collections.shuffle(yValues);

		// ActionListener and MouseListener
		ActionListener timeListener = new TimeListener();
		MouseListener mouseListener = new MyListener();
		this.addMouseListener(mouseListener);


		predatorCreature = new Predator(frame.FRAME_WIDTH/2,frame.FRAME_HEIGHT/2,predSpeed);
		creatures.add(predatorCreature);

		for (int i = 0; i < 5; i++) {			
			SlowPrey slowPreyCreature = new SlowPrey(xValues.get(i),yValues.get(i),preySpeedSlow);
			creatures.add(slowPreyCreature);
			slowPreyCreaturesArray.add(slowPreyCreature);
		}

		for (int i = 6; i < 11; i++) {
			FastPrey fastPreyCreature = new FastPrey(xValues.get(i),yValues.get(i),preySpeedFast);
			creatures.add(fastPreyCreature);
			fastPreyCreaturesArray.add(fastPreyCreature);
		}

		this.setOpaque(true);
		this.setBackground(Color.BLACK);
		t = new Timer(DELAY, timeListener); //Timer
	}

	/**
	 * The listener interface for receiving time events.
	 *
	 * @see TimeEvent
	 */
	class TimeListener implements ActionListener {
		
		/**
		 * Does Various Functions. Collide, Kill
		 */
		public void actionPerformed(ActionEvent event) {

			for (Creature c : creatures) { 
				c.move();

				//Borders
				for (SlowPrey b : slowPreyCreaturesArray) { 
					if (b.getX() < 0) b.setDir(-b.getXDir(), 0);
					if (b.getX() > frame.FRAME_WIDTH-60) b.setDir(-b.getXDir(), 0);
					if (b.getY() < 0) b.setDir(0,-b.getYDir());
					if (b.getY() > frame.FRAME_HEIGHT-60) b.setDir(0,-b.getYDir());
				}
				for (FastPrey b: fastPreyCreaturesArray) {
					if (b.getX() < 0) b.setDir(-b.getXDir(), 0);
					if (b.getX() > frame.FRAME_WIDTH-40) b.setDir(-b.getXDir(), 0);
					if (b.getY() < 0) b.setDir(0,-b.getYDir());
					if (b.getY() > frame.FRAME_HEIGHT-60) b.setDir(0,-b.getYDir());
				}

				//Predator Border
				if (predatorCreature.getX() < 0) predatorCreature.setDir(-predatorCreature.getXDir(), 0);
				if (predatorCreature.getX() > frame.FRAME_WIDTH-60) predatorCreature.setDir(-predatorCreature.getXDir(), 0);
				if (predatorCreature.getY() < 0) predatorCreature.setDir(0,-predatorCreature.getYDir());
				if (predatorCreature.getY() > frame.FRAME_HEIGHT-60) predatorCreature.setDir(0,-predatorCreature.getYDir());
				//Predator Reverse Border
				if (predatorCreature.getX() < 0) predatorCreature.reverseIndicator();
				if (predatorCreature.getX() > frame.FRAME_WIDTH-60) predatorCreature.reverseIndicator();
				if (predatorCreature.getY() < 0) predatorCreature.reverseIndicator();
				if (predatorCreature.getY() > frame.FRAME_HEIGHT-60) predatorCreature.reverseIndicator();

				// Remove Creatures if collide
				for (SlowPrey b : slowPreyCreaturesArray) {
					if (b.collide(predatorCreature) && b.getLiving()) {
						b.setTypeCreatureToTrue();
						b.kill();
						gameScore+= 1;
					}
				}
				// Remove Creatures if collide
				for (FastPrey b: fastPreyCreaturesArray) {
					if (b.collide(predatorCreature) && b.getLiving()) {
						b.kill();
						gameScore+= 2;
					}
				}

				//Stopping Self Collision
				for (SlowPrey b : slowPreyCreaturesArray) {
					for (SlowPrey b2 : slowPreyCreaturesArray) {
						if (b != b2 && b.bounceCollide(b2) && b.getLiving() && b2.getLiving()) {
							b.setDir(-b.getXDir(),-b.getYDir());
						}
					}
				}
				//Stopping Self Collision
				for (FastPrey b : fastPreyCreaturesArray) {
					for (FastPrey b2 : fastPreyCreaturesArray) {
						if (b != b2 && b.bounceCollide(b2) && b.getLiving() && b2.getLiving()) {
							b.setDir(-b.getXDir(),-b.getYDir());
						}
					}
				}
				//Stopping Collision
				for (FastPrey b : fastPreyCreaturesArray) {
					for (SlowPrey b2 : slowPreyCreaturesArray) {
						if (b.bounceCollideDifCreature(b2) && b.getLiving() && b2.getLiving()) {
							b.setDir(-b.getXDir(),-b.getYDir());
							b2.setDir(-b2.getXDir(), -b2.getYDir());
						}
					}
				}				
			}
			repaint();
		}
	}

	/**
	 * The listener interface for receiving my events.
	 *
	 * @see MyEvent
	 */
	class MyListener extends MouseAdapter {
		
		/**
		 * Sets the functions for the left and right mouse click
		 */
		public void mousePressed(MouseEvent e) {

			if (firstStart) {
				firstTime = System.currentTimeMillis()/1000;
				t.start();
				firstStart = false;
			} else {
				if (e.getButton() == MouseEvent.BUTTON1) { //left click
					predatorCreature.left();
				} else if (e.getButton() == MouseEvent.BUTTON3) { //right click
					predatorCreature.right();
				}
			}
		}

	}

	/**
	 * Paints the different creatures
	 * 
	 * @param Graphic, g, draw components
	 */
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		g2.setColor(Color.RED); 
		if (firstStart) {
			g2.drawString("Time: 0:00" , 10, 20);
			g2.drawString("Score: 0", 10, 35);
			g2.drawString("Kill all the prey by using the mouse.", 10,50);
			g2.drawString("Fast Prey(Green) = 2 Points", 10, 70);
			g2.drawString("Slow Prey(Red) = 1 Points", 10, 85);
		} else {
			minutes = (int)(System.currentTimeMillis()/1000 - firstTime)/60;
			g2.drawString("Time:  " + + minutes + " minutes & " + (int)(System.currentTimeMillis()/1000 - firstTime)%60 + " seconds", 10, 20);
			g2.drawString("Score: " + gameScore, 10, 35);
		}

		if (gameScore == 15) {
			t.stop();
			g2.drawString("Game Over", 10, 60);
		}

		predatorCreature.draw(g2);

		for (SlowPrey b : slowPreyCreaturesArray) {
			if (b.getLiving()) {
				b.draw(g2);
			}
		}

		for (FastPrey b : fastPreyCreaturesArray) {
			if (b.getLiving()) {
				b.draw(g2);
			}
		}
		
	}
}


