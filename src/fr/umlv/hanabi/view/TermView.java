package fr.umlv.hanabi.view;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import fr.umlv.hanabi.model.*;
import fr.umlv.hanabi.controller.*;

public class TermView extends View implements ViewInterface {

    public int selection(int min, int max) {
        return selection(min, max, List.of());
    }

    public int selection(int min, int max, List<Integer> excepts){
        boolean correctInput = false;
        Scanner in = new Scanner(System.in);
        int value = -1;

        do{
            try{
                value = in.nextInt();
                correctInput = !excepts.contains(value) && value >= min && value <= max;

                if (!correctInput) {
                    displayIndexError(min, max, excepts);
                }
            }catch(Exception e){
                displayText("Wrong input !\nEnter a number :");
                in.next(); /* discard the rejected input */
            }
        } while(!correctInput);

        in.close();
        return value;
    }

    public void displayChoice(Reserve r) {
        Objects.requireNonNull(r);

        System.out.println("1) Engage a card");

        if (r.getBlueTokens() > 0) {
            System.out.println("2) Give a tips");
        }
        if (r.getBlueTokens() < 8) {
            System.out.println("3) Discard");
        }
    }

    public void displayBoard(Board b) {
        System.out.println("Current board state :");
        System.out.println(Objects.requireNonNull(b).toString());
    }

    public void displayHand(Player p) {
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

    public void displayReserve(Reserve r) {
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

    public void displayTips(List<Integer> indexes, StringBuilder builder) {
        builder.append(" : ");

        for (int i : indexes) {
            builder.append(i);
            builder.append(" ");
        }

        System.out.println(builder.toString());
    }

    public void displayTips(CardColor cardColor, Player p) {
        Objects.requireNonNull(cardColor);
        Objects.requireNonNull(p);

        StringBuilder builder = new StringBuilder();

        builder.append("Cards with color ");
        builder.append(cardColor.toString());

        displayTips(p.getIndexOfCardsByCardColor(cardColor), builder);
    }

    public void displayValueOrColor() {
        System.out.println("Choose to indicate either value or color : ");
        System.out.println("1) Value");
        System.out.println("2) Color");
    }

    public void displayTips(int value, Player p) {
        if (value < 1 || value > 5) {
            throw new IllegalArgumentException("Value must be between 1 and 5 included");
        }
        Objects.requireNonNull(p);

        StringBuilder builder = new StringBuilder();

        builder.append("Index of cards with value ");
        builder.append(value);

        displayTips(p.getIndexOfCardsByValue(value), builder);
    }

    public void displayText(String text) {
        System.out.println(text);
    }

    public void displayDiscarded(GameController g) {
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

    public void displayPlayers(List<Player> players, int playerIndex) {
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

    @Override
    public Option displayOptions(List<Option> values) {

    }

    public void displayIndexError(int min, int max, List<Integer> exceptions) {
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

    public void displayAvailableChoices(List values) {
        System.out.println("Your chosen partner has the following card values: ");
        for (int i = 0; i < values.size(); i++) {
            System.out.print(i + 1);
            System.out.print(") ");
            System.out.println(values.get(i).toString());
        }
    }
}