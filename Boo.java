package a9;

/**
 * A very basic Boo actor. Variants on this class can be created by varying
 * the constructor parameters or by subclassing this.
 */
public class Boo extends Actor {

	/**
	 * Creates a boo. For parameter descriptions, see Actor.
	 */
	public Boo(int xPosition, int yPosition, int size, String imgPath, int health, int coolDown, int speed,
			int attackDamage) {
		super(xPosition, yPosition, size, imgPath, health, coolDown, speed, attackDamage);
	}

	/**
	 * An attack only happens when two hitboxes are overlapping and the Boo is
	 * ready to attack again (based on its cooldown).
	 * 
	 * Boos only attack Plants and Bob-Ombs.
	 * 
	 * This sprite only does basic damage and has basic health.
	 */
	@Override
	public void actOn(Plant other) {
		if (isColliding(other)) {
			if (isReadyForAction()) {
				other.changeHealth(-attackDamage);
				resetCoolDown();
			}
		}
	}

	@Override
	public void actOn(Boo other) {
		// Do nothing
	}

	/**
	 * Overrides the sprite logic to allow Boo to overlap with other boos.
	 */
	@Override
	public boolean isColliding(Sprite other) {
		return !(other instanceof Boo) && super.isColliding(other);
	}
}
