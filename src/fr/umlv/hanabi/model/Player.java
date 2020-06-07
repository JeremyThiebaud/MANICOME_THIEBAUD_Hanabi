package fr.umlv.hanabi.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Model of a player, basically a list of Card
 */
public class Player {
    private final ArrayList<Card> cards;

    /**
     * @param cards Initial hand (Must not be null)
     */
    public Player(ArrayList<Card> cards) {
        this.cards = Objects.requireNonNull(cards);
    }

    /**
     * @param i index of the card int player's hand
     * @return card of given index in hand
     * throws IllegalArgumentException if i exceeds boundaries
     */
    public Card useCard(int i) {
        if (i < 0 || i > cards.size() - 1) {
            throw new IllegalArgumentException("Index out of bound");
        }

        return cards.remove(i);
    }

    public void addCard(Card c){
        cards.add(c);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("Player { ");
        for(Card card : cards) {
            builder.append(card.toString());
            builder.append(' ');
        }
        builder.append("}");

        return builder.toString();
    }

    public Card getCard(int i){
        return cards.get(i);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(cards, player.cards);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cards);
    }

    public int getHandSize() {
        return cards.size();
    }

    public List<Integer> getIndexOfCardsByValue(int value) {
        ArrayList<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i).getValue() == value){
                cards.get(i).valueRevealed();
                indexes.add(i);
            }
        }

        return indexes;
    }

    public List<Integer> getIndexOfCardsByCardColor(CardColor cardColor) {
        ArrayList<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i).getColor().equals(cardColor)){
                cards.get(i).colorRevealed();
                indexes.add(i);
            }
        }

        return indexes;
    }

    public List<CardColor> getColors() {
        ArrayList<CardColor> cardColors = new ArrayList<>();

        for (Card card : cards) {
            if (!cardColors.contains(card.getColor()))
                cardColors.add(card.getColor());
        }

        return cardColors;
    }

    public List<Integer> getValues() {
        ArrayList<Integer> values = new ArrayList<>();

        for (Card card : cards) {
            if (!values.contains(card.getValue()))
                values.add(card.getValue());
        }

        values.sort((a, b) -> a < b ? 1 : 0);
        return values;
    }
}