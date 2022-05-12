package a9;

/**
 * A very basic BobOmb actor. Variants on this class can be created by varying
 * the constructor parameters or by subclassing this.
 */
public class BobOmb extends Plant {

	/**
	 * Creates a bob-omb. For parameter descriptions, see Actor.
	 */
	public BobOmb(int xPosition, int yPosition, int size, String imgPath, int health, int coolDown, int attackDamage) {
		super(xPosition, yPosition, size, imgPath, health, coolDown, attackDamage);
	}

	/**
	 * An attack only happens when two hitboxes are overlapping and the BobOmb is
	 * ready to attack again (based on its cooldown).
	 * 
	 * Plants only attack Boos and Bloopers.
	 * 
	 * This sprite deals damage different than the Plant class. The Bob-Omb sprite
	 * deals heavy damage to a boo or blooper when they come in contact but has
	 * very low health so that it'll disappear once it comes in contact with an
	 * enemy.
	 */
	@Override
	public void actOn(Boo boo) {
		if (isColliding(boo)) {
			if (isReadyForAction()) {
				boo.changeHealth(-attackDamage);
				resetCoolDown();
			}
		}
	}

	public void actOn(Blooper blooper) {
		if (isColliding(blooper)) {
			if (isReadyForAction()) {
				blooper.changeHealth(-attackDamage);
				resetCoolDown();
			}
		}
	}

}
