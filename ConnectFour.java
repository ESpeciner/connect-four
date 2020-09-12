import java.awt.Color;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ConnectFour {
	public static final int BOX_SIZE = 100;
	public static final int CIRCLE_DIAMETER = 90;
	public static final int OFFSET = 5;
	public static final int BOARD_ROWS = 6;
	public static final int BOARD_COLUMNS = 7;
	public static final int MIN_TURNS_FOR_WIN = 7; // It's impossible to win Connect 4 before the seventh turn
	public static final int MAX_TURNS = BOARD_ROWS * BOARD_COLUMNS; // The number of turns before the board becomes full
	static JFrame frame = new JFrame("Connect Four");
	static ConnectFourPanel panel = new ConnectFourPanel();

	public static void main(String args[]) {
		frame.setContentPane(panel);
		frame.setSize(BOX_SIZE * (BOARD_COLUMNS + 1), BOX_SIZE * (BOARD_ROWS + 1));
		frame.setResizable(false);
		frame.setBackground(Color.BLACK);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		int turn = 1;
		int turnPlayer = 0, selectedColumn = -1;
		Random cpuColumn = new Random();
		boolean moveValidated; // Maintains while loop until the player makes a valid move
		boolean gameOver = false; // Maintains while loop until a player wins
		int[][] gameBoard = new int[BOARD_ROWS][BOARD_COLUMNS];

		// Initialize 2D array that represents game board
		for (int i = 0; i < BOARD_ROWS; i++) {
			for (int j = 0; j < BOARD_COLUMNS; j++) {
				gameBoard[i][j] = 0;
			}
		}
		// Initialize array that tracks number of empty spaces in each column
		int[] columnHeight = new int[BOARD_COLUMNS];
		for (int i = 0; i < columnHeight.length; i++) {
			columnHeight[i] = BOARD_ROWS;
		}

		JOptionPane.showMessageDialog(frame, "Welcome to Evan Speciner's Connect Four in Java!");
		do {
			try {
				String firstOrSecond = JOptionPane.showInputDialog(frame,
						"Would you like to go first (1) or second (2)?");
				turnPlayer = Integer.parseInt(firstOrSecond);
				if (turnPlayer != 1 && turnPlayer != 2) {
					turnPlayer = 0;
				}
			} catch (Exception e) {
				turnPlayer = 0;
			}
		} while (turnPlayer == 0);

		// Main loop that prompts the turn player for their input until the game ends
		while (gameOver == false) {
			moveValidated = false;
			if (turn > MAX_TURNS) {
				JOptionPane.showMessageDialog(frame, "The board is filled up. The game ends in a tie.");
				return;
			}
			if (turnPlayer == 1) { // Prompt user for input until a valid move is selected
				while (moveValidated == false && gameOver == false) {
					do {
						try {
							String playerInput = JOptionPane.showInputDialog(frame, "Enter your selected column, 1 to 7");
							selectedColumn = Integer.parseInt(playerInput) - 1;
							if ((validMove(selectedColumn, columnHeight)) == true) {
								updateBoard(gameBoard, selectedColumn, columnHeight[selectedColumn], turnPlayer);
								columnHeight[selectedColumn]--;
								moveValidated = true;
							}
						} catch (Exception e) {
							selectedColumn = -1;
						}
					} while (selectedColumn == -1);
				}
			} else { // Randomly generate CPU move until a valid move is selected
				while (moveValidated == false && gameOver == false) {
					if (gameBoard[5][3] == 0) {
						selectedColumn = 3;
					}
					else {
						selectedColumn = decideMove(gameBoard, cpuColumn);
					}
					if ((validMove(selectedColumn, columnHeight)) == true) {
						updateBoard(gameBoard, selectedColumn, columnHeight[selectedColumn], turnPlayer);
						columnHeight[selectedColumn]--;
						moveValidated = true;
						JOptionPane.showMessageDialog(frame, "Player 2 selected Column " + (selectedColumn + 1) + ".");
					}
				}
			}
			// Checks if the move just performed won the game for the turn player
			if (turn >= MIN_TURNS_FOR_WIN) {
				if (playerWins(gameBoard, turnPlayer) == true) {
					System.out.println();
					JOptionPane.showMessageDialog(frame, "Player " + turnPlayer + " wins!");
					return;
				}
			}
			switch (turnPlayer) {
				case 1:	turnPlayer = 2;	break;
				case 2:	turnPlayer = 1;	break;
			}
			turn++;
		}
	}

	// Updates the board to reflect the turn player's move
	public static int[][] updateBoard(int[][] gameBoard, int selectedColumn, int columnHeight, int turnPlayer) {
		gameBoard[columnHeight - 1][selectedColumn] = turnPlayer;
		switch (turnPlayer) {
		case 1:
			panel.addCircle(new Circle((selectedColumn * BOX_SIZE + OFFSET), (columnHeight - 1) * BOX_SIZE + OFFSET,
					CIRCLE_DIAMETER, Color.RED));
			break;
		case 2:
			panel.addCircle(new Circle((selectedColumn * BOX_SIZE + OFFSET), (columnHeight - 1) * BOX_SIZE + OFFSET,
					CIRCLE_DIAMETER, Color.YELLOW));
			break;
		}
		return gameBoard;
	}

	// Checks if the turn player is trying to add a piece to a column that's already
	// full
	public static boolean validMove(int selectedColumn, int[] columnHeight) {
		if (selectedColumn > BOARD_COLUMNS || selectedColumn < 0) {
			return false;
		}
		if (columnHeight[selectedColumn] == 0) {
			return false;
		} else {
			return true;
		}
	}
	
	// Decides AI move
	public static int decideMove(int[][] gameBoard, Random cpuColumn) {
			for (int i = 0; i < BOARD_ROWS; i++) {
				for (int j = 0; j < BOARD_COLUMNS - 3; j++) {
					if (gameBoard[i][j] == 1 && gameBoard[i][j + 1] == 1
							&& gameBoard[i][j + 2] == 1 && gameBoard[i][j + 3] == 0) {
						return (j + 3);
					}
					else if ((gameBoard[i][j] == 2 && gameBoard[i][j + 1] == 2
							&& gameBoard[i][j + 2] == 2 && gameBoard[i][j + 3] == 0)) {
						return (j + 3);
					}
				}
			}
			// Check for vertical win
			for (int i = BOARD_ROWS - 1; i > 2; i--) {
				for (int j = 0; j < BOARD_COLUMNS; j++) {
					if (gameBoard[i][j] == 1 && gameBoard[i - 1][j] == 1
							&& gameBoard[i - 2][j] == 1 && gameBoard[i - 3][j] == 0) {
						return j;
					}
					else if ((gameBoard[i][j] == 2 && gameBoard[i - 1][j] == 2
							&& gameBoard[i - 2][j] == 2 && gameBoard[i - 3][j] == 0)) {
						return j;
					}
				}
			}
			// Check for diagonal win
			// Diagonals with negative slope
			for (int i = 0; i < BOARD_ROWS - 3; i++) {
				for (int j = 0; j < BOARD_COLUMNS - 3; j++) {
					if (gameBoard[i][j] == 1 && gameBoard[i + 1][j + 1] == 1
							&& gameBoard[i + 2][j + 2] == 1 && gameBoard[i + 3][j + 3] == 0) {
						return (j + 3);
					}
					else if ((gameBoard[i][j] == 2 && gameBoard[i + 1][j + 1] == 2
							&& gameBoard[i + 2][j + 2] == 2 && gameBoard[i + 3][j + 3] == 0)) {
						return (j + 3);
					}
				}
			}
			// Diagonals with positive slope
			for (int i = BOARD_ROWS - 1; i > 2; i--) {
				for (int j = 0; j < BOARD_COLUMNS - 3; j++) {
					if (gameBoard[i][j] == 1 && gameBoard[i - 1][j + 1] == 1
							&& gameBoard[i - 2][j + 2] == 1 && gameBoard[i - 3][j + 3]  == 0) {
						return (j + 3);
					}
					else if (gameBoard[i][j] == 2 && gameBoard[i - 1][j + 1] == 2
							&& gameBoard[i - 2][j + 2] == 2 && gameBoard[i - 3][j + 3]  == 0) {
						return (j + 3);
					}
				}
			}
		return cpuColumn.nextInt(7);
	}

	// Checks if the turn player just Connected Four
	public static boolean playerWins(int[][] gameBoard, int turnPlayer) {
		// Check for horizontal win
		for (int i = 0; i < BOARD_ROWS; i++) {
			for (int j = 0; j < BOARD_COLUMNS - 3; j++) {
				if (gameBoard[i][j] == turnPlayer && gameBoard[i][j + 1] == turnPlayer
						&& gameBoard[i][j + 2] == turnPlayer && gameBoard[i][j + 3] == turnPlayer) {
					return true;
				}
			}
		}
		// Check for vertical win
		for (int i = 0; i < BOARD_ROWS - 3; i++) {
			for (int j = 0; j < BOARD_COLUMNS; j++) {
				if (gameBoard[i][j] == turnPlayer && gameBoard[i + 1][j] == turnPlayer
						&& gameBoard[i + 2][j] == turnPlayer && gameBoard[i + 3][j] == turnPlayer) {
					return true;
				}
			}
		}
		// Check for diagonal win
		// Diagonals with negative slope
		for (int i = 0; i < BOARD_ROWS - 3; i++) {
			for (int j = 0; j < BOARD_COLUMNS - 3; j++) {
				if (gameBoard[i][j] == turnPlayer && gameBoard[i + 1][j + 1] == turnPlayer
						&& gameBoard[i + 2][j + 2] == turnPlayer && gameBoard[i + 3][j + 3] == turnPlayer) {
					return true;
				}
			}
		}
		// Diagonals with positive slope
		for (int i = BOARD_ROWS - 1; i > 2; i--) {
			for (int j = 0; j < BOARD_COLUMNS - 3; j++) {
				if (gameBoard[i][j] == turnPlayer && gameBoard[i - 1][j + 1] == turnPlayer
						&& gameBoard[i - 2][j + 2] == turnPlayer && gameBoard[i - 3][j + 3] == turnPlayer) {
					return true;
				}
			}
		}
		return false;
	}
}