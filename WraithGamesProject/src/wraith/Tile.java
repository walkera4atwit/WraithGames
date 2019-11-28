package wraith;

import javafx.scene.shape.Rectangle;

public class Tile {
	public int xPos;
	private int yPos;
	public Rectangle tileRect;
	
	public Tile() {
		tileRect = new Rectangle(xPos, yPos);
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
}
