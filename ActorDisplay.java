package a9;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ActorDisplay extends JPanel {
	/** Contains all plants and boos in this game. */
	private ArrayList<Actor> actors = new ArrayList<>();

	private boolean gameOver;

	/**
	 * Creates a canvas upon which all actors will live.
	 * 
	 * @param colPixels the number of pixels that this panel is wide
	 * @param rowPixels the number of pixels that this panel is high
	 */
	public ActorDisplay(int colPixels, int rowPixels) {
		setPreferredSize(new Dimension(colPixels, rowPixels));
	}

	/**
	 * Adds an actor to the master list of actors ONLY IF the provided actor is not
	 * colliding with any of the existing actors.
	 * 
	 * @param actor the object to add
	 * @return false if something prevents the actor from being added, true
	 *         otherwise
	 */
	public boolean addActor(Actor actor) {
		if (gameOver == false) {
			if (actor.isCollidingAny(actors)) {
				return false;
			}
			actors.add(actor);
			return true;
		}
		return gameOver;
	}

	/**
	 * This overrided method draws the details of this particular panel, including
	 * all actors that are contained within. It also will display our game over
	 * picture.
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (Actor actor : actors) {
			actor.draw(g);
		}

		if (gameOver == true) {
			actors.clear();

			try {
				BufferedImage gameOverPic = ImageIO.read(new File("src/a9/sprite-icons/game-over.png"));
				g.drawImage((gameOverPic), 0, 0, 580, 400, null);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Executes all of the actor logic that happens in one turn, including moving
	 * actors, checking for collisions, managing attacks, determining if the user
	 * has lost, and more.
	 */
	public void step() {
		// Increment actor cooldowns.
		for (Actor actor : actors) {
			actor.update();
		}

		// Allow all actors to interact with all other actors.
		// This is where attacks, healing, etc happen.
		for (Actor actor : actors) {
			for (Actor other : actors) {
				actor.actOn(other);
			}
		}

		// Remove plants and boos with low health
		ArrayList<Actor> nextTurnActors = new ArrayList<>();
		for (Actor actor : actors) {
			if (actor.isAlive())
				nextTurnActors.add(actor);
			else
				actor.removeAction(actors); // Execute any special effects for dead actors
		}
		actors = nextTurnActors;

		// Move the (alive) actors that are not colliding. If the moving actor reaches
		// the far left side of the panel, the game is over.
		for (Actor actor : actors) {
			if (!actor.isCollidingAny(actors)) {
				actor.move();
				if (actor.getXPosition() <= 0) {
					gameOver = true;
				}
			}
		}

		// Redraw the scene.
		repaint();
	}
}
