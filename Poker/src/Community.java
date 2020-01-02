import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class Community {
	private List<Card> community = new ArrayList<Card>();
	Stack<Card> deck;
	boolean flop = false,river = false,turn = false;
	Community(Deck d) {
		deck = d.getDeck();
	}
	void dealFlop()
	{
		for(int i = 0;i<3;i++)
		{
			community.add(deck.pop());
		}
		flop = true;
	}
	void dealTurn()
	{
		if(flop)
		community.add(deck.pop());
		turn = true;
	}
	void dealRiver()
	{
		if(turn)
		community.add(deck.pop());
		river = true;
	}
	String getCommunity()
	{
		StringBuffer string = new StringBuffer();
		for(Card c:community)
			string.append(c.toString());
		return string.toString();
	}
	List<Card> getList()
	{
		return community;
	}
	boolean finishDealing()
	{
		return river;
	}
	

}
