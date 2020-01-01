import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class Community {
	private Card[] community = new Card[5];

	Community(Deck d) {
		Stack<Card> deck = d.getDeck();
		for (int i = 0; i < 5; i++)
			community[i] = deck.pop();

	}
	String getCommunity()
	{
		return Arrays.toString(community);
	}
	List<Card> getList()
	{
		return (List<Card>)Arrays.asList(community);
	}
	

}
