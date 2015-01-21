import java.awt.*;
import java.util.Random;

public class Creature implements MoveableShape{

	private int speedRate;
	private Rectangle sideBounds;
	private int yDirection;
	private int xDirection;
	private Random rand;
	
	/**
	 * Instantiates a new creature.
	 */
	public Creature() {
		
	}
	
	/**
	 * Instantiates a new creature.
	 *
	 * @param int, posX, initialize positiotnX
	 * @param int, posY, initialize positionY
	 * @param int, myspeedRate, initialize speed of the creature
	 */
	public Creature(int posX, int posY, int myspeedRate) {
		sideBounds = new Rectangle(posX, posY, 60, 60); 
		speedRate = myspeedRate;
		initializeDirection();
	}
	
	/**
	 * Initialize direction.
	 * Decides which way creature goes
	 */
	public void initializeDirection() {
		int randomNum = rand.nextInt(4);
		switch (randomNum) {
		case 0: setDirection(speedRate,0); break; //right 
		case 1: setDirection(0,-speedRate); break;//up
		case 2: setDirection(-speedRate,0); break;//left
		case 3: setDirection(0,speedRate); break;//down
		default: setDirection(speedRate,0);
		}
	}
	
	/**
	 * Gets the x value
	 *
	 * @return int, the x value of the bounds
	 */
	public int getX() {
		return (int)sideBounds.getX();
	}
	
	/**
	 * Gets the y coordinate
	 * @return int, the y value of the bounds
	 */
	public int getY() {
		return (int)sideBounds.getY();
	}
	
	/**
	 * Gets the x direction
	 * @return int, the x direction of the creature the creature is traveling on
	 */
	public int getXDir() {
		return xDirection;
	}
	
	/**
	 * Gets the y direction
	 * @return int, the y direction of the creature the creature is traveling on
	 */
	public int getYDir() {
		return yDirection;
	}
	
	/**
	 * Gets the rectangle.
	 * @return Rectangle, the rectangle side bounds
	 */
	public Rectangle getRectangle() {
		return sideBounds;
	}
	
	/**
	 * Sets the direction for the creature
	 * @param int, xDirection, the x direction of the creature
	 * @param int, yDirection, the y direction of the creature
	 */
	public void setDirection(int xDirection, int yDirection) {
		this.xDirection = xDirection;
		this.yDirection = yDirection;
	}
	
	/**
	 * Add's direction to the x and y coordinates of the shape therefore allowing it to move
	 */
	public void move() {
		sideBounds.x += xDirection;
		sideBounds.y += yDirection;
	}
	
	/**
	 * Checks to see if the shapes collide
	 * @param MoveableShape, other, checks with other to see if it collides
	 * @return boolean, return's true if it collides, else false 
	 */
	public boolean collide(MoveableShape other) {
		Predator x = (Predator)other;
		if (this.getX() - x.getX() < 60) {
			if (this.getY() - x.getY() <60) {
				return true;
			}
			return false;
		}
		return false;
	}
	
	/**=
	 * Draw various shapes
	 * @param Graphics2D, g2, is the graphics required to draw
	 */
	public void draw(Graphics2D g2) {
		g2.setColor(Color.RED);
		g2.draw(sideBounds);
		g2.fill(sideBounds);
		
	}
}
