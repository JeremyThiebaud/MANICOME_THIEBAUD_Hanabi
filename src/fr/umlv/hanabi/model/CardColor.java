package fr.umlv.hanabi.model;

/**
 * Enum type for the different colors of the cards.
 */
public enum CardColor {
	Red, Green, Blue, White, Yellow;

	@Override
	public String toString(){
		switch(this){
			case Red:
				return "Red";
			case Green:
				return "Green";
			case Blue:
				return "Blue";
			case White:
				return "White";
			case Yellow:
				return "Yellow";
			default:return "Unknown cardColor";
		}
	}
}
