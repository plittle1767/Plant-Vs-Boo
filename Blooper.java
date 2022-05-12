package a9;

/**
 * A very basic blooper actor. Variants on this class can be created by varying
 * the constructor parameters or by subclassing this.
 */
public class Blooper extends Boo {

	/**
	 * Creates a blooper. For parameter descriptions, see Actor.
	 */
	public Blooper(int xPosition, int yPosition, int size, String imgPath, int health, int coolDown, int speed,
			int attackDamage) {
		super(xPosition, yPosition, size, imgPath, health, coolDown, speed, attackDamage);
	}

	/**
	 * An attack only happens when two hitboxes are overlapping and the Blooper is
	 * ready to attack again (based on its cooldown).
	 * 
	 * Bloopers only attack Plants and Bob-Ombs.
	 * 
	 * This sprite deals damage differently than the Boo class. It deals very low
	 * damage but has the special ability to decrease the damage a Plant or BobOmb
	 * deals. It also has lower health than a Boo but is faster.
	 */
	@Override
	public void actOn(Plant plant) {
		if (isColliding(plant)) {
			if (isReadyForAction()) {
				plant.changeAttackDamage(2);
				resetCoolDown();
			}
		}
	}

}
