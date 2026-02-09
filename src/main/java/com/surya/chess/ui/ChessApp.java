package com.surya.chess.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ChessApp extends Application {

    @Override
    public void start(Stage stage) {
        ChessController controller = new ChessController();
        Scene scene = new Scene(controller.getView(), 640, 640);

        stage.setTitle("Chess Engine ♟");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
