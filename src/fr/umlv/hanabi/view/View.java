package fr.umlv.hanabi.view;

import java.util.List;
import java.util.Objects;
import fr.umlv.hanabi.model.*;
import fr.umlv.hanabi.controller.*;

public class View {
    public static void displayChoice(Reserve r) {
        Objects.requireNonNull(r);

        System.out.println("1) Engage a card");

        if (r.getBlueTokens() > 0) {
            System.out.println("2) Give a tips");
        }
        if (r.getBlueTokens() < 8) {
            System.out.println("3) Discard");
        }
    }

    public static void displayBoard(Board b) {
        View.displayText("Current board state :");
        System.out.println(Objects.requireNonNull(b).toString());
    }

    public static void displayHand(Player p) {
        StringBuilder builder = new StringBuilder();
        Card currentCard;

        for (int i = 0; i < p.getHandSize(); i++) {
            currentCard = p.getCard(i);
            builder.append(i);
            builder.append(") ");
            if(currentCard.isColorKnown())
                builder.append(currentCard.getColor());
            else
                builder.append("color? ");                
            if(currentCard.isValueKnown())
                builder.append(currentCard.getValue());
            else
                builder.append("value?");
            builder.append("\n");
        }

        System.out.println(builder);
    }

    public static void displayReserve(Reserve r) {
        Objects.requireNonNull(r);
        StringBuilder builder = new StringBuilder();

        builder.append("Blue tokens: ");
        builder.append(r.getBlueTokens());
        builder.append("\nRed Tokens: ");
        builder.append(r.getRedTokens());
        builder.append("\nDeck: ");
        builder.append(r.getRemainingCards());

        System.out.println(builder.toString());
    }

    private static void displayTips(List<Integer> indexes, StringBuilder builder) {
        builder.append(" : ");

        for (int i : indexes) {
            builder.append(i);
            builder.append(" ");
        }

        System.out.println(builder.toString());
    }

    public static void displayTips(CardColor cardColor, Player p) {
        Objects.requireNonNull(cardColor);
        Objects.requireNonNull(p);

        StringBuilder builder = new StringBuilder();

        builder.append("Cards with color ");
        builder.append(cardColor.toString());

        displayTips(p.getIndexOfCardsByCardColor(cardColor), builder);
    }

    public static void displayValueOrColor() {
        System.out.println("Choose to indicate either value or color : ");
        System.out.println("1) Value");
        System.out.println("2) Color");
    }

    public static void displayTips(int value, Player p) {
        if (value < 1 || value > 5) {
            throw new IllegalArgumentException("Value must be between 1 and 5 included");
        }
        Objects.requireNonNull(p);

        StringBuilder builder = new StringBuilder();

        builder.append("Index of cards with value ");
        builder.append(value);

        displayTips(p.getIndexOfCardsByValue(value), builder);
    }

    public static void displayText(String text) {
        System.out.println(text);
    }

    public static void displayDiscarded(GameController g) {
        Card c = Objects.requireNonNull(g).getDiscarded();
        if (c == null) {
            System.out.println("No discarded card");
        } else {
            System.out.println(
                "Last discarded card : "
                + c.toString()
            );
        }
    }

    public static void displayCardColors() {
        StringBuilder builder = new StringBuilder();
        CardColor[] cardColors = CardColor.values();

        for (int i = 0; i < cardColors.length; i++) {
            builder.append(i + 1);
            builder.append(") ");
            builder.append(cardColors[i].toString());
            builder.append('\n');
        }
        System.out.println(builder.toString());
    }

    public static void displayPlayers(List<Player> players, int playerIndex) {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < players.size(); i++) {
            if(i == playerIndex) continue;

            builder.append(i);
            builder.append(") ");
            builder.append(players.get(i).toString());
            builder.append('\n');
        }

        System.out.println(builder.toString());
    }

    public static void displayIndexError(int min, int max, List<Integer> exceptions) {
        StringBuilder builder = new StringBuilder();

        builder.append("Min: ");
        builder.append(min);
        builder.append("\nMax: ");
        builder.append(max);
        builder.append("\nexceptions: ");

        for (Integer expt : exceptions) {
            builder.append(expt);
            builder.append(" ");
        }

        System.out.println(builder.toString());
    }

    public static void displayOptions(List values) {
        System.out.println("Your chosen partner has the following card values: ");
        for (int i = 0; i < values.size(); i++) {
            System.out.print(i + 1);
            System.out.print(") ");
            System.out.println(values.get(i).toString());
        }
    }
}