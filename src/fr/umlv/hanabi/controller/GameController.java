package fr.umlv.hanabi.controller;

import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

import fr.umlv.hanabi.model.*;
import fr.umlv.hanabi.view.*;
import static fr.umlv.hanabi.model.CardColor.*;

public class GameController {
	private final Reserve reserve;
	private final Board board;
	private final ArrayList<Player> players;
	private Card lastDiscard;
	private final View view;
	private boolean lastTurn;

	public GameController(View view){
		this.reserve = new Reserve();
		this.board = new Board();
		this.players = new ArrayList<Player>();
		this.lastDiscard = null;
		this.lastTurn = false;
		this.view = view;
	}

	private int selection(Scanner in, int min, int max) {
		return selection(in, min, max, List.of());
	}

	private int selection(Scanner in, int min, int max, List<Integer> excepts){
		boolean correctInput = false;
		int value = -1;

		do{
			try{
				value = in.nextInt();
				correctInput = !excepts.contains(value) && value >= min && value <= max;

				if (!correctInput) {
					view.displayIndexError(min, max, excepts);
				}
			}catch(Exception e){
				view.displayText("Wrong input !\nEnter a number :");
				in.next(); /* discard the rejected input */
			}
		} while(!correctInput);

		return value;
	}

	/**
	 * Lets the user choose which tip he want to give, he is allowed to be dumb
	 * @param playerIndex Current turn's player index
	 * @param in Scanner on standard input
	 */
	private void tipsChoice(int playerIndex, Scanner in){
		int index;
		Player targetPlayer;

		reserve.subBlue();
		if(players.size() > 2){
			view.displayText("Select a player :");
			targetPlayer = players.get(selection(in, 0, players.size() - 1, List.of(playerIndex)));
		} else{
			targetPlayer = players.get((0 == playerIndex ? 1 : 0));
		}
		
		view.displayValueOrColor();
		switch (selection(in, 1, 2)) {
			case 1:
				view.displayOptions(targetPlayer.getValues());
				view.displayText("What will be the value of the tipped cards ?");
				view.displayTips(targetPlayer.getValues().get(selection(in, 1, targetPlayer.getValues().size()) -1), targetPlayer);
				break;
			case 2:
				view.displayOptions(targetPlayer.getColors());
				view.displayText("What will be the color of the tipped cards ?");
				view.displayTips(targetPlayer.getColors().get(selection(in, 1, CardColor.values().length) - 1), targetPlayer);
		}
	}

	/**
	 * @return True if there are no more cards in the deck
	 */
	private boolean discard(Player p, Scanner in){
		view.displayText("Enter the index of the card you want to discard");
		lastDiscard = p.useCard(selection(in, 0, p.getHandSize() -1));
		reserve.addBlue();
		try{
			p.addCard(reserve.draw());
			return false;
		}catch(Exception e) {
			view.displayText("No more cards to draw !\nOne last turn !");
			return true;
		}
	}

	/**
	 * @return True if there are no more cards in the deck
	 */
	private boolean play(Player p, Scanner in){
		view.displayText("Enter the index of your played card");
		Card played = p.useCard(selection(in, 0, p.getHandSize() - 1));
		view.displayText("Here is the card you selected : "+played.toString());

		if(!board.putCard(played, played.getColor())){
			reserve.addRed();
			view.displayText("Card rejected !\nBe careful !");
			view.displayText("You only have "+(Integer.toString(3-reserve.getRedTokens()))+" chances left !");
		} else {
			view.displayText("Card accepted !");
		}

		view.displayBoard(board);
		try {
			p.addCard(reserve.draw());
			return false;
		} catch(Exception e) {
			view.displayText("No more cards to draw !\nOne last turn !");
			return true;
		}

	}

	public Card getDiscarded() {
		return lastDiscard;
	}

	private int playerSelection(Scanner in){
		switch(reserve.getBlueTokens()){
			case 8:
				return selection(in, 1, 2);
			case 0:
				return selection(in, 1, 3, List.of(2));
			default:
				return selection(in, 1, 3);
		}
	}

	private void playerChoice(Player p, Scanner in){
		int input;
		boolean correctInput;
		do{
			view.displayChoice(reserve);
			input = playerSelection(in);
			correctInput = true;
			switch(input){ /* mettre input = in.nextLine() directement ici ?*/
				case 1:
					lastTurn = play(p, in);
					break;
				case 2:
					tipsChoice(players.indexOf(p), in);
					break;
				case 3:
					lastTurn = discard(p, in);
					break;
				default:
					correctInput = false;
					view.displayText("Wrong input");
			}
		}while(!correctInput);
	}

	public int menu(){
		Scanner in = new Scanner(System.in);
		view.displayText("How many players ? (2 to 5)");

		//int nb_player = selection(in, 2, 5);
		int nb_player = 2;
		for(int i = 0; i < nb_player; i++){
			players.add(
				new Player(reserve.draw((nb_player < 4)?5:4))
			);
		}
		return gameLoop(in);
	}

	/**
	 * @return the score when the game ends
	 */
	public int gameLoop(Scanner in){
		int remaining = players.size();

		while(!board.isFilled()){
			for(Player p : players){
				if(lastDiscard != null){
					view.displayText("The last discarded card was :");
					view.displayText(getDiscarded().toString());
				}
				view.displayText("It's Player "+players.indexOf(p)+"'s turn !");
				view.displayReserve(reserve);
				view.displayText("Here is the hand of the other players :");
				view.displayPlayers(players, players.indexOf(p));
				view.displayText("And here is your hand :");
				view.displayHand(p);
				view.displayText("What do you want to do ?");
				playerChoice(p, in);

				if (lastTurn) remaining--;
				if (reserve.getRedTokens() == 3)
					return endGame(0);
				else if(remaining == 0)
					return endGame();
			}
		}
		return endGame();
	}
	private int endGame(int score) {
		view.displayText("You lost !\nScore: " + score);
		return score;
	}

	private int endGame() {
		view.displayText("Score: " + board.getScore());
		return board.getScore();
	}
}