package wraith;

import javafx.geometry.Insets;
import java.awt.TextField;
import java.io.FileInputStream;
import java.util.ArrayList;

import javafx.animation.*; 
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.shape.*;

public class Field extends Application{
public final int[][] tileArrayInts = {
		{0,1,0,0,0},
		{0,1,0,1,0},
		{0,1,0,1,0},
		{0,1,0,1,0},
		{1,0,0,1,1}
		
};
public final Tile[][] tileArray = new Tile[5][5];
	@Override
	public void start(Stage arg0) throws Exception {
		GridPane grid = new GridPane();
		
		arg0.setTitle("Field test");
		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setMinSize(500, 500);
		
		
		
		//BackgroundFill fill = new BackgroundFill(new Color(.66, .6, .45, 1), new CornerRadii(0), new Insets(10,10,10,10));
		//grid.setBackground(new Background(fill));
		
		
		
		for(int i = 0; i<5; i++) {
			for(int j = 0; j < 5; j++) {
				if(tileArrayInts[i][j] == 0) {
					tileArray[i][j] = new PathTile(i,j);
					//grid.add(tileArray[i][j].tileRect, i, j);
					//root.getChildren().add(tileArray[i][j].tileRect);
				}
				else {
					tileArray[i][j] = new TowerTile(i,j);
					//grid.add(tileArray[i][j].tileRect, i, j);
					//root.getChildren().add(tileArray[i][j].tileRect);
				}
			}
		}
		Group g = new Group();
		FileInputStream file = new FileInputStream("C:\\Images\\map.png");
		Image mapImage = new Image(file);
		//ImageView mapView = new ImageView(mapImage);
		BackgroundImage backMap = new BackgroundImage(mapImage, null, null, null, null);
		Background map = new Background(backMap);
		grid.setBackground(map);
		
		
		arg0.setScene(new Scene(grid, 500, 500));
		arg0.show();
		ArrayList<Enemy> field = new ArrayList<Enemy>();
		
		AnimationTimer gameLoop = new AnimationTimer() {

			@Override
			//now = current frame timestamp in nanoseconds
			public void handle(long now) {
				if(now%20000 == 0) {
					sendEnemies(true, field, grid);
				}
				
			}
			
		};
		gameLoop.start();
//		for(int i = 0; i<3; i++) {
//			sendEnemies(true, field, grid);
//			synchronized(this) {
//				this.wait(10000);
//			}
//		}
		//enemyPath(circle, 500);
		//enemyPath(c2, 600);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	/**
	 * 
	 * @param enemy type of enemy sent
	 * @param speed time to cross one tile (100px)
	 */
	public static void enemyPath(Enemy enemy, double speed) {
		
		TranslateTransition t1 = new TranslateTransition(Duration.millis(speed*4), enemy.getNode());
		t1.setByX(400);
		TranslateTransition t2 = new TranslateTransition(Duration.millis(speed*2), enemy.getNode());
		t2.setByY(200);
		TranslateTransition t3 = new TranslateTransition(Duration.millis(speed*4), enemy.getNode());
		t3.setByX(-400);
		TranslateTransition t4 = new TranslateTransition(Duration.millis(speed*2), enemy.getNode());
		t4.setByY(200);
		TranslateTransition t5 = new TranslateTransition(Duration.millis(speed*4), enemy.getNode());
		t5.setByX(500);
		
		SequentialTransition seqT = new SequentialTransition(t1,t2,t3,t4,t5);
		seqT.setDelay(Duration.millis(0));
		seqT.play();
		
	}
	
	/*Idea:
	*store path tiles in linked list, so that they are ordered by position on map 
	*the enemyPath method will then translate the enemies one by one to each tile
	*at each tile the position of the enemy is saved so the towers can retrieve it to attack
	*/
	/*
	 *Idea 2:
	 *get (x,y) position of enemy nodes (circles) and use that to attack
	 *towers attack by check if (x,y) of each enemy fits into its range, then attacks
	 *the field contains all enemies, enemies are removed from the field when they reach the end of the board or when their health reaches 0 
	 */
	
	public void sendEnemies(boolean wave, ArrayList<Enemy> field, GridPane grid) {
		
		
			Enemy sent = new Enemy(10,10,10,100,500,"Enemy");
			grid.add(sent.getNode(),0,0);
			enemyPath(sent, sent.getSpeed());
			field.add(sent);
			
			
		
	}
	public void attackT(Tower t, ArrayList<Enemy> field) {
		for(int i = field.size()-1; i>= 0; i--) {
			if(inRange(t,field.get(i))) {
				t.attack(field.get(i));
				return;
			}
		}
	}
	public boolean inRange(Tower t, Enemy e) {
		double distance = Math.sqrt(Math.abs((t.getX()-e.getNode().getCenterX()) + (t.getY() - e.getNode().getCenterY())));
		if(distance<=t.getAOE()) {
			return true;
		}
		return false;
	}

}