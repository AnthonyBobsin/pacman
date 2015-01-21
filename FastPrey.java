import java.awt.*;

public class FastPrey extends Creature {
	
	public int xPosition;
	public int yPosition;
	public int directionY;
	public int directionX;
	public int speed;
	public int radius = 40;
	int randomNum;
	public boolean living;
		
	/**
	 * Instantiates a new fast prey.
	 *
	 * @param int, xPosition, the x position
	 * @param int, yPosition, the y position
	 * @param int, speed, the speed
	 */
	public FastPrey(int xPosition, int yPosition, int speed) {
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		this.speed = speed;
		initializeDirection();
		living = true; 
	}
	
	/**
	 * Kills the creature
	 */
	public void kill() {
		living = false;
	}
	
	/**
	 * Gets the status of the creature. checks if its living or not
	 *
	 * @return boolean, the living, true if the creature is living, else false
	 */
	public boolean getLiving() {
		return living;
	}
	
	/**
	 * Initialize's the Direction of the creature. Decides where it should go
	 */
	public void initializeDirection() {
		randomNum = (int)(Math.random()*((4-0)+1));
		switch (randomNum) {
		case 0: setDir(speed,0); break; //right 
		case 1: setDir(0,-speed); break;//up
		case 2: setDir(-speed,0); break;//left
		case 3: setDir(0,speed); break;//down
		default: setDir(speed,0);
		}
	}
	
	/**
	 * Bounce collide. Checks if it collides with its own creature
	 *
	 * @param Creature, other, the other creature
	 * @return boolean, true, if they collide, else false
	 */
	public boolean bounceCollide(Creature other) {
		FastPrey x = null;
		x = (FastPrey)other;

		int distanceX = this.getX() - x.getX();
		int distanceY = this.getY() - x.getY();
		int sumOfDistanceSqrt = (int)Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2));
		if (sumOfDistanceSqrt < radius+10) {
			return true;
		}
		return false;
	}
	
	/**
	 * Bounce collide with different creature.
	 *
	 * @param Creature, other the other creature
	 * @return boolean, true, if they collide, else false
	 */
	public boolean bounceCollideDifCreature(Creature other) {
		SlowPrey x = null;
		x = (SlowPrey)other;

		int distanceX = this.getX() - x.getX();
		int distanceY = this.getY() - x.getY();
		int sumOfDistanceSqrt = (int)Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2));
		if (sumOfDistanceSqrt < radius+10) {
			return true;
		}
		return false;
	}
	
	/**
	 * Sets the travel direction of the  creature
	 *
	 * @param int, directionX, the direction x
	 * @param int, directionY, the direction y
	 */
	public void setDir(int directionX, int directionY) {
		this.directionX = directionX;
		this.directionY = directionY;
	}
	
	/**
	 * Gets the x Position
	 * 
	 * @return int, xPosition, the x position
	 */
	public int getX() {
		return xPosition;
	}
	
	/**
	 * Gets the y Position
	 * 
	 * @return int, yPosition, the y position
	 */
	public int getY() {
		return yPosition;
	}
	
	/**
	 * Gets the x Direction
	 * 
	 * @return int, directionX, the direction of x its traveling
	 */
	public int getXDir() {
		return directionX;
	}
	
	/**
	 * Gets the y Direction
	 * 
	 * @return int, directionY, the direction of y its traveling
	 */
	public int getYDir() {
		return directionY;
	}
	
	/**
	 * Add's direction to the x and y coordinates of the shape therefore allowing it to move
	 */
	public void move() {
		xPosition+= directionX;
		yPosition+= directionY;
	}
	
	/**
	 * Checks to see if the shapes collide
	 * @param boolean, other, checks with other to see if it collides
	 * @return boolean, return's, true if it collides, else false
	 */
	public boolean collide(MoveableShape other) {
		Predator x = null;
		x = (Predator)other;
		int distanceX = this.getX() - x.getX();
		int distanceY = this.getY() - x.getY();
		int sumOfDistanceSqrt = (int)Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2));
		if (sumOfDistanceSqrt < radius+10) {
			return true;
		}
		return false;
	}
	
	/**
	 * Draw various shapes
	 * @param Graphics2D, g2, is the graphics required to draw
	 */
	public void draw(Graphics2D g2) {
		// ghost body
		g2.setColor(Color.GREEN);
		g2.fillArc (xPosition, yPosition, radius, radius, 0, 180); 

		g2.setColor(Color.GREEN);
		g2.drawRect(xPosition, yPosition + radius / 2, radius, radius / 2);
		g2.fillRect(xPosition, yPosition + radius / 2, radius, radius / 2);

		// ghost eyes
		g2.setColor(Color.black);
		g2.fillOval(xPosition + radius / 4 ,yPosition + radius / 4 , radius / 5, radius / 5); // drawing eye;
		g2.setColor(Color.black);
		g2.fillOval(xPosition + radius / 4 + (radius / 4) ,yPosition + radius / 4 , radius / 5, radius / 5); // drawing eye;
	}
}
