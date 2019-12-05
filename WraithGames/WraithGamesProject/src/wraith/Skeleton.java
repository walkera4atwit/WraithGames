package wraith;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Skeleton extends Enemy{
		
	
	public Skeleton() throws FileNotFoundException {
		super();
		node = new ImageView(new Image(new FileInputStream("C:\\Images\\skelearms.png")));
	}
	public Skeleton(int damage, int rewardOnDeath, int level, double hp, double speed, String type) throws FileNotFoundException {
		super(damage, rewardOnDeath, level, hp, speed, type);
		node = new ImageView(new Image(new FileInputStream("C:\\Images\\skelearms.png")));
		node.setX(25);
		node.setY(25);
	}
}
