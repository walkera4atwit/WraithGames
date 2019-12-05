package wraith;

import javafx.scene.shape.Rectangle;

public class Tile {
	public int xPos;
	private int yPos;
	protected Rectangle node;
	
	public Tile() {
		node = new Rectangle(xPos, yPos);
	}
	public Tile(int xPos, int yPos) {
		this.xPos = xPos;
		this.yPos = yPos;	
	}

	public void setXPos(int newXPos) {
		xPos = newXPos;
	}
	public int getXPos() {
		return xPos;
	}
	
	public int getYPos() {
		return yPos;
	}
	
	public void setYPos(int newYPos) {
		yPos= newYPos;
	}
	public Rectangle getNode() {
		return node;
	}
	public void setNode(Rectangle node) {
		this.node = node;
	}
	
}
