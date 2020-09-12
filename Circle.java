import java.awt.Color;
import java.awt.Graphics;

public class Circle {
	private int x;
	private int y;
	private int diameter;
	private int offset;
	private Color color;
	
	public Circle(int x, int y, int diameter, Color color) {
		super();
		this.x = x;
		this.y = y;
		this.diameter = diameter;
		this.color = color;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getDiameter() {
		return diameter;
	}

	public Color getColor() {
		return color;
	}
	
	public void drawCircle(Graphics g) {
		g.setColor(color);
		g.fillOval(x + offset, y + offset, diameter, diameter);
	}
}