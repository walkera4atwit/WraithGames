package wraith;

import javafx.geometry.Insets;
import java.awt.TextField;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;

import javafx.animation.*; 
import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.shape.*;


public class Field extends Application{
public int score = 0;
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
		
		
		
		for(int i = 0; i<5; i++) {
			for(int j = 0; j < 5; j++) {
				if(tileArrayInts[i][j] == 0) {
					tileArray[i][j] = new PathTile(i,j);
				}
				else {
					tileArray[i][j] = new TowerTile(i*100,j*100);
					grid.getChildren().add(tileArray[i][j].getNode());
					
				}
			}
		}
		Player player = new Player(1, "Player");
		Group g = new Group();
		FileInputStream file = new FileInputStream("C:\\Images\\map.png");
		Image mapImage = new Image(file);
		//ImageView mapView = new ImageView(mapImage);
		BackgroundImage backMap = new BackgroundImage(mapImage, null, null, null, null);
		Background map = new Background(backMap);
		grid.setBackground(map);
		ArrayList<Enemy> field = new ArrayList<Enemy>();
		ArrayList<Tower> towers = new ArrayList<Tower>();
		
		
		arg0.show();
		EventHandler<MouseEvent> placeTower = new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if(inTowerTile(event.getX(), event.getY())) {
					try {
						
						if(player.afford(Tower.COST)) {
							player.buy(Tower.COST);
							towers.add(new Tower(event.getX()-25, event.getY()-25, 300, 30));
							grid.getChildren().add(towers.get(towers.size()-1).getNode());
						}
						else {
							System.out.println("Can't afford tower");
						}
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				
			}
			
		};
		
		
		System.out.printf("hp: %d%n", player.getHp());
		
		
		
		for(Tower t : towers) {
			grid.getChildren().add(t.getNode());
		}
		//Game ends at 10 minutes
		
		//MouseEvent mouse = new MouseEvent();
		
		
		
	
		
		/**
		 * User Interface
		 */
		
		// Timeline variables
				long STARTTIME = System.currentTimeMillis();
				
				Label timerLabel = new Label();
				int timeSeconds = (int) STARTTIME/1000;

				// Timerlabel
				//timerLabel.textProperty().bind(timeSeconds.asString());
				timerLabel.setText(Integer.toString(timeSeconds));

				

				
				
				// sets the image
				Image image = new Image(new FileInputStream("C:\\Images\\map.png"));
				ImageView imageView = new ImageView();
				imageView.setImage(image);

				// image is dragged
				imageView.setOnDragDetected(new EventHandler<MouseEvent>() {
					public void handle(MouseEvent event) {
						/* drag was detected, start a drag-and-drop gesture */
						/* allow any transfer mode */
						Dragboard db = imageView.startDragAndDrop(TransferMode.ANY);

						/* Put a string on a dragboard */
						ClipboardContent content = new ClipboardContent();
						content.putImage(image);
						db.setContent(content);

						event.consume();
					}
				});

				// image dragged onto rect
				Rectangle rect = new Rectangle(100, 100);
				rect.setOnDragOver(e -> {
					Dragboard db = e.getDragboard();
					if (db.hasImage()) {
						e.acceptTransferModes(TransferMode.COPY_OR_MOVE);
					}
					e.consume();
				});

				// The box at the top
				Label goldLabel = new Label();
				Label hpLabel = new Label();
				HBox hbox = new HBox(10);
				

				// The box at the bottom
				//Pane pane = new Pane();
				//pane.getChildren().add(imageView);
				Button placeTowerbtn = new Button("Place Tower");
				placeTowerbtn.setMaxWidth(100);
				placeTowerbtn.setMaxHeight(100);
				
				placeTowerbtn.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						grid.addEventHandler(MouseEvent.MOUSE_CLICKED, placeTower);
					}
					
				});
				
				Button cancel = new Button("Cancel");
				cancel.setMaxWidth(100);
				cancel.setMaxHeight(100);
				cancel.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						grid.removeEventHandler(MouseEvent.MOUSE_CLICKED, placeTower);
						
					}
					
				});
				
				hbox.getChildren().addAll(goldLabel, hpLabel, placeTowerbtn, cancel);
				VBox gridUI = new VBox(timerLabel,grid, hbox);
				
				/**
				 * Play screen
				 */
				arg0.setTitle("Wraith Games Tower Defense");
				
				Image bg = new Image(new FileInputStream("C:\\Images\\background.png")); 
				ImageView bgView = new ImageView(bg);
				
				StackPane gameOver = new StackPane();
				gameOver.getChildren().add(new ImageView(new Image("gameover.png")));
				
				
				
				/**
				 * gameLoop
				 */

				hpLabel.setFont(new Font("Arial", 30));
				goldLabel.setFont(new Font("Arial", 30));
				timerLabel.setFont(new Font("Arial", 30));
				AnimationTimer gameLoop = new AnimationTimer() {
					
					@Override
					//now = current frame timestamp in nanoseconds
					public void handle(long now) {
						long currentTime = System.currentTimeMillis();
						
						int seconds = (int) (currentTime-STARTTIME)/1000;
					
						hpLabel.setText("HP: "+Integer.toString(player.getHp()));
						goldLabel.setText("Gold: "+Integer.toString(player.getGold()));
						timerLabel.setText(Integer.toString(seconds/60)+":"+Integer.toString(seconds%60));
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
								attackT(t, field, grid, player);
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
									arg0.setScene(new Scene(gameOver,500,500));
									
								}
								i--;
								
							}
						}
						
					}
					
				};
				
				AnimationTimer endLoop = new AnimationTimer() {

					@Override
					public void handle(long now) {
						if(player.getHp() == 0) {
							gameLoop.stop();
						}
						
					}
					
				};
				Button playAgainbtn = new Button("Quit");
				playAgainbtn.setMaxHeight(100);
				playAgainbtn.setMaxWidth(250);
				playAgainbtn.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						System.exit(0);
						
					}
					
				});
				Label end = new Label();
				end.setText(Integer.toString(score));
				end.setFont(new Font("Arial", 30));
				gameOver.getChildren().addAll(new VBox(playAgainbtn, end));
				
				Button playbtn = new Button("Play!");
				playbtn.setMaxHeight(100);
				playbtn.setMaxWidth(250);
				playbtn.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						arg0.setScene(new Scene(gridUI, 500, 700));
						gameLoop.start();
						endLoop.start();
						
					}
					
				});
				
				
				
				
				
				
				
				
				StackPane base = new StackPane();
				
				base.getChildren().addAll(bgView, playbtn);
				
				Scene playScene = new Scene(base, 600, 500);
				arg0.setScene(playScene);
				arg0.show();

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
	public void attackT(Tower t, ArrayList<Enemy> field, Pane grid, Player player) {
		
		for(int i = 0; i<field.size(); i++) {
			if(inRange(t,field.get(i))) {
				Circle c = new Circle(10, Color.LIGHTBLUE);
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
					player.earn(field.get(i).getRewardOnDeath());
					field.remove(i);
					score++;
					
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