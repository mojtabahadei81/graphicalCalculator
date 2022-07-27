package ir.ac.kntu;

import ir.ac.kntu.gui.CalculatorGui;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


/**
 * @author mojtaba hadei
 */
public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        CalculatorGui calculatorGui = new CalculatorGui();
        calculatorGui.setEventsHandlers();
        Pane pane = new Pane();
        calculatorGui.addNodesToPane(pane);
        Scene scene = new Scene(pane);
        primaryStage.setTitle("Simple Calculator");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}