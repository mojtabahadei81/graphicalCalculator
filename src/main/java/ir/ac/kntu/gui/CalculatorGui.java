package ir.ac.kntu.gui;

import ir.ac.kntu.logic.Calculator;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

/**
 * @author mojtaba hadei
 */
public class CalculatorGui {
    private String path = "file:src/main/resources/buttons/";

    private TilePane tilePane;

    private TextField textField;

    private TextField lastOpField;

    private StringBuilder firstNum = new StringBuilder();

    private StringBuilder secondNum = new StringBuilder();
    
    private StringBuilder operator = new StringBuilder();

    private String[][] buttons = {
            {"7", "8", "9", "plus"},
            {"4", "5", "6", "minus"},
            {"1", "2", "3", "divide"},
            {"clear", "0", "equal", "multiply"}
    };

    private Button[][] buttonsArray = new Button[buttons.length][buttons[0].length];

    public CalculatorGui() {
        tilePane = new TilePane();
        tilePane.setVgap(-5);
        tilePane.setHgap(-9);
        tilePane.setPrefColumns(4);
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                buttonsArray[i][j] = new Button();
                ImageView image = new ImageView(new Image(path + buttons[i][j] + ".jpg"));
                image.setFitWidth(60);
                image.setPreserveRatio(true);
                buttonsArray[i][j].setGraphic(image);
                buttonsArray[i][j].setStyle("-fx-background-color: rgba(219,28,28,0)");
                tilePane.getChildren().add(buttonsArray[i][j]);
            }
        }
        textField = new TextField();
        textField.setStyle("-fx-background-color: transparent");
        textField.setFocusTraversable(false);
        textField.setEditable(false);
        lastOpField = new TextField();
        lastOpField.setStyle("-fx-background-color: transparent; -fx-font-size: 10px; -fx-text-fill: #999999");
        lastOpField.setFocusTraversable(false);
        lastOpField.setEditable(false);
        lastOpField.setText("");
    }

    public void setEventsHandlers() {
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                if (buttons[i][j].matches("[0-9]")) {
                    String num = buttons[i][j];
                    buttonsArray[i][j].setOnMouseClicked(event -> addNum(num));
                } else if (buttons[i][j].equals("clear")) {
                    buttonsArray[i][j].setOnMouseClicked(event -> clear());
                } else if (buttons[i][j].equals("equal")) {
                    buttonsArray[i][j].setOnMouseClicked(event -> equal());
                } else {
                    String op = buttons[i][j];
                    buttonsArray[i][j].setOnMouseClicked(event -> operator(op));
                }
            }
        }
    }

    private void addNum(String num) {
        if (operator.isEmpty()) {
            firstNum.append(num);
        } else {
            secondNum.append(num);
        }
        textField.setText(textField.getText() + num);
    }

    private void clear() {
        firstNum = new StringBuilder();
        secondNum = new StringBuilder();
        operator = new StringBuilder();
        lastOpField.setText("");
        textField.setText("");
    }

    private void equal() {
        if (operator.isEmpty() || firstNum.isEmpty() || secondNum.isEmpty()) {
            return;
        }
        firstNum = new StringBuilder(String.valueOf(Calculator.calculate(firstNum, secondNum, operator)));
        secondNum = new StringBuilder();
        operator = new StringBuilder();
        lastOpField.setText("");
        textField.setText(firstNum.toString());
        operator = new StringBuilder();
        firstNum = new StringBuilder();
        secondNum = new StringBuilder();
    }

    private void operator(String op) {
        if (!firstNum.isEmpty()) {
            operator = new StringBuilder(op.toUpperCase());
            lastOpField.setText(firstNum + nameToSign(op));
            secondNum = new StringBuilder();
            textField.setText("");
        }
    }

    private String nameToSign(String name) {
        switch (name) {
            case "plus":
                return "+";
            case "minus":
                return "-";
            case "multiply":
                return "*";
            case "divide":
                return "/";
            default:
                return "";
        }
    }

    public void addNodesToPane(Pane pane) {
        VBox vBox = new VBox(textField, tilePane);
        pane.getChildren().add(vBox);
    }
}
