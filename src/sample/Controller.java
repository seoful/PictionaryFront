package sample;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import sample.TextLists.Chat;
import sample.TextLists.PlayersList;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Controller {
    @FXML
    private AnchorPane mainBox;
    @FXML
    private ScrollPane scoreContainer;
    @FXML
    private ScrollPane chatContainer;
    @FXML
    private VBox chatBox, scoreBox;
    @FXML
    private AnchorPane previewBox, canvasBox;
    @FXML
    private TextField textField;

    @FXML
    private RadioButton eraserButton, brushButton;
    @FXML
    private Button clearButton,sendButton;
    @FXML
    private Canvas downCanvas, upperCanvas, previewCanvas;
    @FXML
    private ColorPicker colorPicker;
    @FXML
    private Slider sizeSlider;
    private Drawer drawer;
    private Chat chat;
    private PlayersList playersList;
    private Game game;

    private String nickname = "awdawd";


    @FXML
    void initialize() {
        initDrawing();
        initChat();
        initColorPicker();
        initSizeSlider();
        initButtons();
        initPlayersList();
        game = new Game("awdawd",chat,drawer, playersList);
    }

    private void initPlayersList() {
        playersList = new PlayersList(scoreBox,scoreContainer);
    }

    private void initChat() {
        chat = new Chat(chatBox, chatContainer);
        chat.addMessage("sasha", "Hi");
        chat.scrollToBottom();

        mainBox.setOnMouseClicked(mouseEvent -> mainBox.requestFocus());
        textField.setOnAction(actionEvent -> {
           sendMessage();
        });

        textField.textProperty().addListener((observableValue, s, t1) -> checkText(t1));
    }

    private void checkText(String text) {

//        System.out.println(text);
        Pattern pattern = Pattern.compile("^\\s+",Pattern.CASE_INSENSITIVE);
        if(text == null)
            text = "";
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            textField.setText(text.substring(matcher.end()));
        }
    }

    void initDrawing() {
        drawer = new Drawer(downCanvas, upperCanvas, previewCanvas, Color.BLACK, 5);

        upperCanvas.setOnMousePressed(e -> {
            drawer.startLine(e.getX(), e.getY());
            upperCanvas.requestFocus();
        });

        upperCanvas.setOnMouseDragged(e -> drawer.continueLine(e.getX(), e.getY()));

        upperCanvas.setOnMouseReleased(e -> drawer.endLine(e.getX(), e.getY()));

        upperCanvas.setOnMouseExited(e -> drawer.clearUpperLayer());
        upperCanvas.setOnMouseMoved(e -> drawer.drawMouse(e.getX(), e.getY()));
    }

    void initColorPicker() {
        colorPicker.setValue(Color.BLACK);
        colorPicker.setOnAction(e -> drawer.setColor(colorPicker.getValue()));
    }

    void initSizeSlider() {
        sizeSlider.valueProperty().addListener((observableValue, old_size, new_size) -> drawer.setSize(new_size.doubleValue()));
    }

    void initButtons() {
        brushButton.setOnAction(actionEvent -> drawer.setToBrush());
        eraserButton.setOnAction(actionEvent -> {
            drawer.setToEraser();
        });
        clearButton.setOnAction(actionEvent -> {
            drawer.clearDownLayer();
        });

        sendButton.setOnAction(actionEvent -> sendMessage());
    }

    void disableDrawing(boolean disable) {
        brushButton.setDisable(disable);
        eraserButton.setDisable(disable);
        clearButton.setDisable(disable);

        colorPicker.setDisable(disable);
        sizeSlider.setDisable(disable);
        previewBox.setDisable(disable);
        canvasBox.setDisable(disable);
    }

    void sendMessage(){
        Pattern pattern = Pattern.compile("^\\w+",Pattern.CASE_INSENSITIVE);
        String text = textField.getText();
        if(text == null)
            text = "";
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            chat.addMessage(nickname, text);
            textField.setText(null);
        }

    }
}
