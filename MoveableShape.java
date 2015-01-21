import java.awt.*;

public interface MoveableShape {
	
	/**
	 * Move.
	 * Moves the method
	 */
	void move();
	

	/**
	 * Determines if the objects have collided with each other
	 * @param MoveableShape, other, the other MoveableShape object
	 * @return boolean, true, if the objects collide, else false
	 */
	boolean collide(MoveableShape other); 
	
	/**
	 * Draw various shapes
	 * @param Graphics2D, g2, is the graphics required to draw
	 */
	void draw(Graphics2D g2); 
	
}
