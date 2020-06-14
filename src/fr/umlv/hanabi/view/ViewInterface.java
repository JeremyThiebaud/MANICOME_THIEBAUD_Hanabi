package fr.umlv.hanabi.view;

import fr.umlv.hanabi.model.Board;
import fr.umlv.hanabi.model.CardColor;
import fr.umlv.hanabi.model.Player;
import fr.umlv.hanabi.model.Reserve;

import java.util.List;

public interface ViewInterface<E> {
    void displayBoard(Board b);
    void displayHand(Player p);
    void displayReserve(Reserve r);
    void displayTips(CardColor cardColor, Player player);
    void displayTips(int value, Player player);
    void displayValueOrColor();
    void displayText(String text);
    void displayPlayers(List<Player> players, int playerIndex);
    Option displayOptions(List<Option> values);
    void displayIndexError(int min, int max, List<Integer> exceptions);
    int selection(int min, int max, List<Integer> exceptions);
    int selection(int min, int max);
}
