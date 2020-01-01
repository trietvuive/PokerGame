import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class Hand {
	private Card[] holdem = new Card[2];
	private Card[] omaha = new Card[4];
	boolean hold = true;

	Hand(Deck d, boolean hold) {
		this.hold = hold;
		Stack<Card> deck = d.getDeck();
		if (hold) {
			for (int i = 0; i < 2; i++) {
				holdem[i] = deck.pop();
			}
		} else {
			for (int i = 0; i < 3; i++) {
				omaha[i] = deck.pop();
			}
		}

	}

	String getHand() {
		if (hold)
			return Arrays.toString(holdem);
		else
			return Arrays.toString(omaha);
	}

	List<Card> getList() {
		if (hold)
			return (List<Card>) Arrays.asList(holdem);
		else
			return (List<Card>) Arrays.asList(omaha);
	}

}
