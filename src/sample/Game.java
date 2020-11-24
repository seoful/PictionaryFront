package sample;

import sample.TextLists.Chat;
import sample.TextLists.PlayersList;

import java.util.ArrayList;

public class Game {
    private final String nickname;
    private final Chat chat;
    private final Drawer drawer;
    private final PlayersList playersList;
    private ArrayList<Player> users;


    public Game(String nickname, Chat chat, Drawer drawer, PlayersList playersList) {
        this.nickname = nickname;
        this.chat = chat;
        this.drawer = drawer;
        this.playersList = playersList;
        users = new ArrayList<>();
        users.add(new Player("awd",false));
        users.add(new Player("awawdawdd",false));
        users.add(new Player("aghhhhwd",false));
        users.add(new Player("aghhhhwd",false));
        users.add(new Player("aghhhhwd",false));
        users.add(new Player("dhfg",false));
        users.add(new Player("aghhhhwd",false));
        users.add(new Player("aghhhhwd",false));
        users.add(new Player("aghhhhwd",false));
        users.add(new Player("aghhhhwd",false));
        users.add(new Player("aghhhhwd",false));
        users.add(new Player("aghhhhwd",false));
        users.add(new Player("aghhhhwd",false));
        users.add(new Player("aghhhhwd",false));
        users.add(new Player("aghhhhwd",false));
        playersList.update(users);
    }


}
