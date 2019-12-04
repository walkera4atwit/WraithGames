package wraith;

import javafx.geometry.Insets;
import java.awt.TextField;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javafx.animation.*; 
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
		{0,0,0,1,0}
		
};
public final Tile[][] tileArray = new Tile[5][5];
	@Override
	public void start(Stage arg0) throws Exception {
		Pane grid = new Pane();
		
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
					tileArray[i][j] = new TowerTile(i*100,j*100);
					grid.getChildren().add(tileArray[i][j].getNode());
					
				}
			}
		}
		Player player = new Player(100, "Test");
		Group g = new Group();
		FileInputStream file = new FileInputStream("C:\\Images\\map.png");
		Image mapImage = new Image(file);
		//ImageView mapView = new ImageView(mapImage);
		BackgroundImage backMap = new BackgroundImage(mapImage, null, null, null, null);
		Background map = new Background(backMap);
		grid.setBackground(map);
		ArrayList<Enemy> field = new ArrayList<Enemy>();
		ArrayList<Tower> towers = new ArrayList<Tower>();
		
		arg0.setScene(new Scene(grid, 500, 500));
		arg0.show();
		EventHandler<MouseEvent> placeTower = new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if(inTowerTile(event.getX(), event.getY())) {
					try {
						towers.add(new Tower(event.getX()-25, event.getY()-25, 300, 30));
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					grid.getChildren().add(towers.get(towers.size()-1).getNode());
				}
				
			}
			
		};
		grid.addEventHandler(MouseEvent.MOUSE_CLICKED, placeTower);
		
		System.out.printf("hp: %d%n", player.getHp());
		
		
		
		for(Tower t : towers) {
			grid.getChildren().add(t.getNode());
		}
		//Game ends at 10 minutes
		
		//MouseEvent mouse = new MouseEvent();
		
		AnimationTimer gameLoop = new AnimationTimer() {
			
			@Override
			//now = current frame timestamp in nanoseconds
			public void handle(long now) {
				
				
				int rand = (int) (Math.random() *240)+1;
				if(rand == 1) {
					try {
						sendEnemies(true, field, grid);
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
				}
				for(Tower t : towers) {
					
					if(now-t.getLastAttack()>1000000000) {
						attackT(t, field, grid);
						t.setLastAttack(now);
					}
				}
				for(int i = 0; i < field.size(); i++) {
					if(field.get(i).attackIfFinished(player)) {
						System.out.printf("hp: %d%n", player.getHp());
						grid.getChildren().remove(field.get(i).getNode());
						field.remove(i);
						if(player.getHp()==0) {
							System.out.printf("Game Over");
							
						}
						i--;
						
					}
				}
				
			}
			
		};
		
		
		
		gameLoop.start();
		

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
		t1.setToX(400);
		TranslateTransition t2 = new TranslateTransition(Duration.millis(speed*2), enemy.getNode());
		t2.setToY(200);
		TranslateTransition t3 = new TranslateTransition(Duration.millis(speed*4), enemy.getNode());
		t3.setToX(0);
		TranslateTransition t4 = new TranslateTransition(Duration.millis(speed*2), enemy.getNode());
		t4.setToY(400);
		TranslateTransition t5 = new TranslateTransition(Duration.millis(speed*4), enemy.getNode());
		t5.setToX(500);
		
		RotateTransition r1 = new RotateTransition(Duration.millis(1), enemy.getNode());
		r1.setByAngle(90);
		RotateTransition r2 = new RotateTransition(Duration.millis(1), enemy.getNode());
		r2.setByAngle(90);
		RotateTransition r3 = new RotateTransition(Duration.millis(1), enemy.getNode());
		r3.setByAngle(-90);
		RotateTransition r4 = new RotateTransition(Duration.millis(1), enemy.getNode());
		r4.setByAngle(-90);
		SequentialTransition seqT = new SequentialTransition(t1,r1,t2,r2,t3,r3,t4,r4,t5);
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
	
	public void sendEnemies(boolean wave, ArrayList<Enemy> field, Pane grid) throws FileNotFoundException {
		
		
			Skeleton sent = new Skeleton(1,10,10,100,1000,"Enemy");
			grid.getChildren().add(sent.getNode());
			enemyPath(sent, sent.getSpeed());
			field.add(sent);
			
			
		
	}
	public boolean inTowerTile(double x, double y) {
		if((x>0&&x<400&&y>100&y<200)  || (x>100&&x<500&&y>300&&y<400)) {
			return true;
		}
		return false;
	}
	public void attackT(Tower t, ArrayList<Enemy> field, Pane grid) {
		
		for(int i = 0; i<field.size(); i++) {
			if(inRange(t,field.get(i))) {
				Circle c = new Circle(10, Color.BLUE);
				c.setCenterX(t.getX());
				c.setCenterY(t.getY());
				grid.getChildren().add(c);
				TranslateTransition tt = new TranslateTransition(Duration.millis(250), c);
				tt.setByY(field.get(i).getNode().getTranslateY()-t.getY());
				tt.setByX(field.get(i).getNode().getTranslateX()-t.getX());
				FadeTransition ft = new FadeTransition(Duration.millis(1), c);
				ft.setFromValue(1);
				ft.setToValue(0);
				SequentialTransition seqT = new SequentialTransition(c,tt,ft);
			
				//grid.getChildren().remove(c);
				seqT.play();
				t.attack(field.get(i));
				if(field.get(i).checkHealth(field)) {
					grid.getChildren().remove(field.get(i).getNode());
					field.remove(i);
				}
				return;
			}
		}
	}
	public boolean inRange(Tower t, Enemy e) {
		double distance = Math.sqrt(Math.abs((t.getX()-e.getNode().getTranslateX())*(t.getX()-e.getNode().getTranslateX()) + (t.getY() - e.getNode().getTranslateY())*(t.getY() - e.getNode().getTranslateY())));
		if(distance<=t.getAOE()) {
			return true;
		}
		return false;
	}
	public void enemyKilled() {
		
	}

}