package fr.umlv.hanabi.model;

import java.util.ArrayList;
import java.util.Collections;
import fr.umlv.hanabi.view.*;

/**
 * 
 */
public class Reserve {
	private final ArrayList<Card> deck;
	private int redTokens, blueTokens;

	public Reserve(){
		int i;
		this.redTokens = 0;
		this.blueTokens = 8;
		this.deck = new ArrayList<Card>();
		for(CardColor color : CardColor.values()){
			for(i = 0; i < 3; i++)
				this.deck.add(new Card(1, color));
			for(i = 0; i < 2; i++){
				this.deck.add(new Card(2, color));
				this.deck.add(new Card(3, color));
				this.deck.add(new Card(4, color));
			}
			this.deck.add(new Card(5, color));
		}
		Collections.shuffle(deck);
	}

	/**
	 * @return True if there are at least 3 red tokens in reserve.
	 */
	public boolean addRed(){
		redTokens += 1;
		return redTokens >= 3;
	}

	/**
	 * @return number of blue tokens in reserve.
	 */
	public int addBlue(){
		if(blueTokens < 8)
			blueTokens += 1;
		return blueTokens;
	}

	/**
	 * @return number of blue tokens in reserve.
	 */
	public int subBlue(){
		if(blueTokens > 0)
			blueTokens -= 1;
		return blueTokens;
	}

	/**
	 * @return Card extracted from the deck.
	 * @throws IllegalStateException if deck is emptied
	 */
	public Card draw(){
		try {
			return deck.remove(0);
		} catch(Exception e){
			View.displayText("No more cards to draw !\nOne last turn !");
			throw new IllegalStateException();
		}
	}

	/**
	 * @param n number of cards to draw
	 * @return ArrayList<Card> of the n cards extracted from the deck.
	 * @throws IllegalStateException if deck is emptied
	 */
	public ArrayList<Card> draw(int n){
		ArrayList<Card> cards = new ArrayList<>();
		for(int i = 0; i < n; i++)
				cards.add(draw());
		return cards;
	}

	public int getBlueTokens() {
		return blueTokens;
	}

	public int getRedTokens() {
		return redTokens;
	}

	public int getRemainingCards() {
		return deck.size();
	}
}