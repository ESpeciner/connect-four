import javax.swing.JPanel;
import java.util.List;
import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;

public class ConnectFourPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	public static final int BOX_SIZE = 100;
	public static final int CIRCLE_DIAMETER = 90;
	public static final int OFFSET = 5;
	public static final int BOARD_ROWS = 6;
	public static final int BOARD_COLUMNS = 7;
	
	private List <Circle> circles = new LinkedList<Circle>();
	
	public void addCircle(Circle circle) {
		circles.add(circle);
		this.repaint();
	}
	
	@Override
	public void paint(Graphics g) {
		g.setColor(Color.BLUE);
		g.fillRect(0, 0, BOX_SIZE * BOARD_COLUMNS, BOX_SIZE * BOARD_ROWS);
		g.setColor(Color.BLACK);
		g.fillRect(BOX_SIZE * BOARD_COLUMNS, 0, BOX_SIZE, BOX_SIZE * (BOARD_ROWS + 1));
		g.fillRect(0, BOX_SIZE * BOARD_ROWS, BOX_SIZE * (BOARD_COLUMNS + 1), BOX_SIZE);
		for (int i = 0; i < BOARD_COLUMNS; i++) {
			for (int j = 0; j < BOARD_ROWS; j++) {
				g.fillOval(i * BOX_SIZE + OFFSET, j * BOX_SIZE + OFFSET, CIRCLE_DIAMETER, CIRCLE_DIAMETER);
			}
		}
		
		for (Circle c : circles) {
			c.drawCircle(g);
		}
	}
	
}
