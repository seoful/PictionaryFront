package sample.TextLists;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public abstract class TextFlowList {
    protected final String BOLD = "-fx-font-weight: bold";;

    protected final VBox box;
    protected boolean isEven;
    protected final ScrollPane scrollPane;


    public TextFlowList(VBox box, ScrollPane scrollPane) {
        this.box = box;
        this.scrollPane = scrollPane;
        isEven = true;
        box.setPrefHeight(scrollPane.getPrefHeight());
    }

    protected TextFlow createMessage(String text){
        TextFlow textFlow = new TextFlow();
        String[] parts = text.split("\\*");
        for(int i = 0;i<parts.length;i++){
            if(!parts[i].isEmpty()) {
                Text part = new Text(parts[i]);
                if (i % 2 != 0)
                    part.setStyle(BOLD);
                textFlow.getChildren().add(part);
            }
        }
        if (isEven)
            textFlow.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
        textFlow.setMaxWidth(box.getPrefWidth());
        textFlow.getStyleClass().add("message");
        return textFlow;
    }
}
