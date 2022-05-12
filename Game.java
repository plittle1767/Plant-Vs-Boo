package a9;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.swing.*;

/**
 * The theme chosen for this game was based on Super Mario. The plants are
 * piranha plants and Bob-ombs. The Boos are the characters Boo and Blooper.
 * 
 * A top-level panel for playing a game similar to Plants Vs Zombies.
 * 
 * This panel is primarily responsible for coordinating the various aspects of
 * the game, including: - Running the game step-by-step using a timer - Creating
 * and displaying other components that make up the game - Creating new plants
 * and/or boos, when necessary
 * 
 * @author Travis Martin and David Johnson
 */
@SuppressWarnings("serial")
public class Game extends JPanel implements ActionListener, MouseListener {
	private static final int NUM_ROWS = 5;
	private static final int NUM_COLS = 7;
	private static final int GRID_BUFFER_PIXELS = 20;
	private static final int CELL_SIZE = 75;
	private static final int STEP_TIME = 20;

	private Random generator = new Random();

	private double numOfStars = 0;
	private JLabel starDisplay = new JLabel();
	private Timer starTimer;

	private JRadioButton plantButton = new JRadioButton("Piranha Plant cost: 5 stars");
	private JRadioButton bobOmbButton = new JRadioButton("Bob-omb cost: 10 stars");

	/**
	 * This panel is responsible for displaying plants and boos, and for managing
	 * their interactions. As well as displaying the resources available.
	 */
	private ActorDisplay actorDisplay = new ActorDisplay(NUM_COLS * CELL_SIZE + GRID_BUFFER_PIXELS * 2,
			NUM_ROWS * CELL_SIZE + GRID_BUFFER_PIXELS * 2);

	private Game() {
		// This adds the MouseListener to the panel
		actorDisplay.addMouseListener(this);

		// This adds the star resource label
		add(starDisplay);

		// This timer calls the actionPerformed method every STEP_TIME milliseconds
		starTimer = new Timer(STEP_TIME, null);
		starTimer.start();

		// Adds the radio buttons to the ActionListener
		plantButton.addActionListener(this);
		bobOmbButton.addActionListener(this);

		// Adds the radio buttons to the panel
		add(plantButton);
		add(bobOmbButton);

		ButtonGroup group = new ButtonGroup();
		plantButton.setSelected(true);
		group.add(plantButton);
		group.add(bobOmbButton);

		add(actorDisplay);

		// This layout causes all elements to be stacked vertically
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		// The timer calls the actionPerformed method every STEP_TIME milliseconds
		Timer timer = new Timer(STEP_TIME, this);
		timer.start();

		// This adds a plant to every row
		for (int i = 0; i < NUM_ROWS; i++) {
			addPlant(0, i);
		}
	}

	/**
	 * Executes game logic every time the timer ticks.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		actorDisplay.step();

		numOfStars = numOfStars + 0.05;
		starDisplay.setText("Number of Gold Stars: " + (int) numOfStars);

		// randomly place boos
		if (generator.nextInt(300) == 0) {
			int rightCol = NUM_COLS;
			int randomRow = generator.nextInt(NUM_ROWS);
			addBoo(rightCol, randomRow);
		}

		// randomly place bloopers
		if (generator.nextInt(300) == 0) {
			int rightCol = NUM_COLS;
			int randomRow = generator.nextInt(NUM_ROWS);
			addBlooper(rightCol, randomRow);
		}
	}

	/**
	 * Adds a plant to the official game grid & display panel.
	 */
	private void addPlant(int col, int row) {
		// The magic numbers below define various hardcoded plant properties
		actorDisplay.addActor(new Plant(gridToPixel(col), gridToPixel(row), CELL_SIZE * 4 / 5,
				"src/a9/sprite-icons/piranha-plant-icon.png", 100, 5, 8));
	}

	/**
	 * Adds a bob-omb to the official game grid & display panel.
	 */
	private void addBobOmb(int col, int row) {
		// The magic numbers below define various hardcoded plant properties
		actorDisplay.addActor(new BobOmb(gridToPixel(col), gridToPixel(row), CELL_SIZE * 4 / 5,
				"src/a9/sprite-icons/bob-omb-icon.png", 1, 3, 75));
	}

	/**
	 * Adds a boo to the official game grid & display panel.
	 */
	private void addBoo(int col, int row) {
		// The magic numbers below define various hardcoded boo properties
		actorDisplay.addActor(new Boo(gridToPixel(col), gridToPixel(row), CELL_SIZE * 4 / 5,
				"src/a9/sprite-icons/boo-icon.png", 100, 20, -1, 8));
	}

	/**
	 * Adds a blooper to the official game grid & display panel.
	 */
	private void addBlooper(int col, int row) {
		// The magic numbers below define various hardcoded boo properties
		actorDisplay.addActor(new Blooper(gridToPixel(col), gridToPixel(row), CELL_SIZE * 4 / 5,
				"src/a9/sprite-icons/blooper-icon.png", 80, 20, -2, 4));
	}

	/**
	 * Converts a row or column to its exact pixel location in the grid.
	 */
	private int gridToPixel(int rowOrCol) {
		return rowOrCol * CELL_SIZE + GRID_BUFFER_PIXELS;
	}

	/**
	 * The inverse of gridToPixel
	 */
	private int pixelToGrid(int xOrY) {
		return (xOrY - GRID_BUFFER_PIXELS) / CELL_SIZE;
	}

	/**
	 * This method allows us to click somewhere on the panel and it will add the
	 * sprite that was selected from the radio buttons.
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		// Find the x and y values
		int xPos = pixelToGrid(e.getX());
		int yPos = pixelToGrid(e.getY());

		// If the plant radio button is selected, and we have enough resources, a plant
		// will be placed where we clicked on the panel.
		if (plantButton.isSelected() && numOfStars >= 5) {
			addPlant(xPos, yPos);
			numOfStars -= 5;
		}

		// If the bob-omb radio button is selected, and we have enough resources, a
		// bob-omb will be placed where we clicked on the panel.
		if (bobOmbButton.isSelected() && numOfStars >= 10) {
			addBobOmb(xPos, yPos);
			numOfStars -= 10;
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// Do nothing

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// Do nothing
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// Do nothing
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// Do nothing
	}

	/**
	 * Create, start, and run the game.
	 */
	public static void main(String[] args) {
		JFrame app = new JFrame("Bob-omb & Piranha vs Boo & Blooper");
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		app.add(new Game());
		app.pack();
		app.setVisible(true);
	}
}
