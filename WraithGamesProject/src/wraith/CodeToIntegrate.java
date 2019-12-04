import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.input.MouseEvent;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class CodeToIntegrate extends Application {

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		// Timeline variables
		Integer STARTTIME = 0;
		Timeline timeline;
		Label timerLabel = new Label();
		IntegerProperty timeSeconds = new SimpleIntegerProperty(STARTTIME);

		// Timerlabel
		timerLabel.textProperty().bind(timeSeconds.asString());
		timerLabel.setText(timeSeconds.toString());

		// Gold label
		int gold = 0;
		int hp = 100;

		// Will update timer and gold
		if (timeline != null) {
			timeline.stop();
		}
		timeSeconds.set(STARTTIME);
		timeline = new Timeline();
		timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(STARTTIME + 1), new KeyValue(timeSeconds, 0)));

		// gold = Player.getGold();
		// hp = Player.getHP();
		timeline.setCycleCount(Timeline.INDEFINITE);

		timeline.playFromStart();

		// sets the image
		Image image = new Image("Car-patting-meme.jpg");
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
		HBox hbox = new HBox();
		hbox.getChildren().addAll(timerLabel, gold, hp);

		// The box at the bottom
		Pane pane = new Pane();
		pane.getChildren().add(imageView);

		// Start program
		Scene scene = new Scene(pane);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
