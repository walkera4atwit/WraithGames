package wraith;
import java.util.ArrayList;

import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Enemy {

	//Added damage

	public int damage;
	public int rewardOnDeath;
	public PathTile currTile;   //may be redundant if we have enemies in PathTile
	public int level;
	public double hp; 
	public double speed;
	public String type;
	
	protected ImageView node;
	
	public Enemy() {
		
		this.damage = 0;
		this.rewardOnDeath = 0;
		this.currTile = new PathTile(0, 0);
		this.level = 0;
		this.hp = 0;
		this.speed = 0;
		node = new ImageView();
		this.type = "super";
	}

	public Enemy(int damage, int rewardOnDeath, int level, double hp, double speed, String type) {

		this.damage = damage;
		this.rewardOnDeath = rewardOnDeath;
		this.level = level;
		this.hp = hp;
		this.speed = speed;
		this.type = type;
		
	}

	/**
	 * Getters & Setters
	 */

	public int getRewardOnDeath() {
		return rewardOnDeath;
	}

	public void  setRewardOnDeath(int newRewardOnDeath) {
		rewardOnDeath= newRewardOnDeath;
	}

	/*public Tile getCurrentTile() {
		return currTile;
	}
	public void setCurrentTile( Tile newCurrTile){
		currTile= newCurrTile;
	}

	 */
	public void setNode(ImageView node) {
		this.node = node;
	}
	public ImageView getNode() {
		return this.node;
	}
	public int getLevel() {
		return level;
	}

	public void setLevel(int newLevel) {
		level= newLevel;
	}

	public double getHP() {
		return hp;
	}

	public void setHP(int newHP) {
		hp = newHP;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double newSpeed) {
		speed=newSpeed;
	}

	public String getType() {
		return type;
	}

	public void setType(String newType) {
		type = newType;
	}
	
	/**
	 * @param p will be called with 'this' in player class
	 * called "endReached()" in class chart
	 */
	public boolean attackIfFinished(Player p) {
		if(this.getNode().getTranslateX()>400) {
			p.damage(this.damage);
			return true;
		}
		return false;
	}

	public void move(ArrayList<Tile> pathPositions) {
		//need to know positions of the path tiles
		//currTile= currTile + speed;

	}

	public boolean checkHealth(ArrayList<Enemy> field) {
		if(this.hp<=0) {
			return true;
		}
		return false;
	}
	public void damage(int dmg) {
		hp-=dmg;

	}

}