package sample.TextLists;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class Chat extends TextFlowList {


    public Chat(VBox box, ScrollPane scrollPane) {
        super(box, scrollPane);
        scrollToBottom();
    }

    public void addMessage(String name, String message) {
//        Label label = createLabel(String.format("[%s]: %s",name,message));
        box.getChildren().add(createMessage(String.format("*%s:* %s",name,message)));
        isEven = !isEven;
        scrollToBottom();
    }

    public void scrollToBottom() {
        scrollPane.applyCss();
        scrollPane.layout();
        scrollPane.setVvalue(1);
    }
}
