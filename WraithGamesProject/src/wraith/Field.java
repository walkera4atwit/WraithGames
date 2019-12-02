package wraith;

import javafx.geometry.Insets;
import java.awt.TextField;
import java.io.FileInputStream;



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
		grid.setVgap(5);
		grid.setHgap(5);
		
		
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
		//mapView.setX(500);
		//mapView.setY(500);
		
		//Group root = new Group(mapView);
		
		Circle circle = new Circle();
		circle.setRadius(25);
		circle.setFill(Color.BLACK);
		grid.add(circle, 0, 0);
		Circle c2 = new Circle(25, Color.RED);
		grid.add(c2, 0, 0);
		//Group root = new Group(circle);
		
		arg0.setScene(new Scene(grid, 500, 500));
		arg0.show();
		
		enemyPath(circle, 500);
		enemyPath(c2, 600);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	public static void enemyPath(Circle enemy, int speed) {
		
		TranslateTransition t1 = new TranslateTransition(Duration.millis(speed*4), enemy);
		t1.setByX(400);
		TranslateTransition t2 = new TranslateTransition(Duration.millis(speed*2), enemy);
		t2.setByY(200);
		TranslateTransition t3 = new TranslateTransition(Duration.millis(speed*4), enemy);
		t3.setByX(-400);
		TranslateTransition t4 = new TranslateTransition(Duration.millis(speed*2), enemy);
		t4.setByY(200);
		TranslateTransition t5 = new TranslateTransition(Duration.millis(speed*4), enemy);
		t5.setByX(500);
		
		SequentialTransition seqT = new SequentialTransition(t1,t2,t3,t4,t5);
		seqT.setDelay(Duration.millis(0));
		seqT.play();
		
	}

}