import java.util.Collections;
import java.util.Stack;

public class Deck {
	Stack<Card> deck = new Stack<Card>();

	Deck() {
		for (Suit s : Suit.values())
			for (Rank r : Rank.values())
				deck.push(new Card(s, r));

		Collections.shuffle(deck);
	}

	String displayDeck() {
		StringBuilder a = new StringBuilder();
		for (Card c : deck)
			a.append(c.toString()+"\n");
		return a.toString();
	}
	Stack<Card> getDeck()
	{
		return deck;
	}
}
