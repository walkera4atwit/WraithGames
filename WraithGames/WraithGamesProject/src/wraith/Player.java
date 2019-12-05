package wraith;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Player{
	int hp=1;
	boolean hellMode = false;
	boolean pause = false;
	String name;
	int bank;
	int waveNumber;
	int gameSpeed;
	
	public Player(int hp, String name) {
		this.hp=hp;
		this.name=name;
		bank = 200;
	}
	public boolean afford(int n) {
		if(n<=this.bank) {
			return true;
		}
		return false;
	}
	public int getHp() {
		return hp;
	}
	public void earn(int reward) {
		this.bank+= reward;
	}
	public void setHp(int hp) {
		this.hp = hp;
	}

	
	public boolean pause(boolean pause) { 
		
		
		return false;
	}
	
	public int addmoney(int money) {
		
		
		return money;
	}
	
	public void buy(int cost) {
		
		
		bank-=cost;
	}
	
	
	
	public void damage(int dmg) {
		this.hp-=dmg;
	}
	public int getGold() {
		return this.bank;
	}
	
		
}

	
	


