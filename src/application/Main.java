package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("List.fxml"));
		AnchorPane root = (AnchorPane) loader.load();

		Controllers listController = loader.getController();
		listController.start();

		Scene scene = new Scene(root, 427, 245);
		primaryStage.setTitle("Ben and Karls SongList");
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}
}
