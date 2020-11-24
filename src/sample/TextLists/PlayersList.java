package sample.TextLists;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextFlow;
import sample.Player;

import java.util.ArrayList;
import java.util.Collections;

public class PlayersList extends TextFlowList {

    public PlayersList(VBox box, ScrollPane scrollPane) {
        super(box, scrollPane);
    }

    public void update(ArrayList<Player> players) {
        box.getChildren().clear();
        Collections.sort(players);
        ArrayList<TextFlow> textFlows = new ArrayList<>();
        for (Player p : players) {
            TextFlow textFlow = createMessage(
                    String.format("%s - %d", p.getNickname(),p.getScore()));
            if (p.isDrawing())
                textFlow.setBackground(new Background(new BackgroundFill(Color.LIGHTYELLOW, null, null)));
            textFlows.add(textFlow);
            isEven = !isEven;
        }
        box.getChildren().addAll(textFlows);
    }

}
