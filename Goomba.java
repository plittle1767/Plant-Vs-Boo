package a9;

/**
 * A very basic Goomba actor. Variants on this class can be created by varying
 * the constructor parameters or by subclassing this.
 */
public class Goomba extends Boo {

	/**
	 * Creates a goomba. For parameter descriptions, see Actor.
	 */
	public Goomba(int xPosition, int yPosition, int size, String imgPath, int health, int coolDown, int speed,
			int attackDamage) {
		super(xPosition, yPosition, size, imgPath, health, coolDown, speed, attackDamage);
	}

	@Override
	public void actOn(Plant other) {
		if (isColliding(other)) {
			if (isReadyForAction()) {
				other.changeHealth(-attackDamage);
				resetCoolDown();
			}
		}
	}

}
