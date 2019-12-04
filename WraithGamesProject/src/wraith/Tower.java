package wraith;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Tower{
	private double x;
	private double y;
	private long lastAttack;
	private int damage;
	private int aoe;
	
	public String type;
	
	protected ImageView node;
	public static int atkSpeed;
	public static int level;
	public static int range;
	
	
	public Tower(double x, double y, int aoe, int dmg) throws FileNotFoundException {
		this.x=x+25;
		this.y=y+25;
		this.aoe=aoe;
		this.damage= dmg;
		this.lastAttack = 0;
		node = new ImageView(new Image(new FileInputStream("C:\\Images\\tower.png")));
		node.setX(x);
		node.setY(y);
	}
	public void attack(Enemy e) {
		e.hp = e.hp - damage;
	}

	//TODO: better upgrading system
	public static void upgrade() {
		level = level + 1; //set the level to add one
		atkSpeed = atkSpeed * 2; //attack speed should be set to run every X seconds and the upgrade will be 3/4 X
		range = range * 2; //have the range be increased by 50% (this.range *= 1.5)
		//damage = damage * 2; //I would say damage does not need to be increased because the attack speed will increase DPS

	}

//do we want this to be tied to the player
//to allow for the "Sale" of the tower
	public static void destroy() {

	}

	public int getAttackSpeed() {
		return atkSpeed;
	}

	public int getLevel() {
		return level;
	}

	public int getRange() {
		return range;
	}

	public int getDamage() {
		return damage;
	}

	public int getAOE() {
		return aoe;
	}

	public String getType() {
		return type;
	}
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public ImageView getNode() {
		return this.node;
	}
	public long getLastAttack() {
		return lastAttack;
	}
	public void setLastAttack(long lastAttack) {
		this.lastAttack = lastAttack;
	}
	

}
