package fr.umlv.hanabi.model;

/**
 - Model of a card, composed of a color and a value
 */
public class Card {
	private final int value;
	private final CardColor color;
	private boolean colorKnown;
	private boolean valueKnown;

	public Card(int val, CardColor color){
		this.value = val;
		this.color = color;
		if(val < 1 || val > 5)
			throw new IllegalArgumentException();
		this.colorKnown = false;
		this.valueKnown = false;
	}

	public int getValue(){
		return value;
	}

	public CardColor getColor(){
		return color;
	}

	public void colorRevealed(){
		colorKnown = true;
	}

	public void valueRevealed(){
		valueKnown = true;
	}

	public boolean isColorKnown(){
		return colorKnown;
	}

	public boolean isValueKnown(){
		return valueKnown;
	}

	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append(color.toString()).append(" ").append(Integer.toString(value));
		return builder.toString();
	}
}