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
	boolean dealFlop()
	{
		if(!flop)
		{
			for(int i = 0;i<3;i++)
			{
				community.add(deck.pop());
			}
			flop = true;
			return true;
		}
		return false;
	}
	boolean dealTurn()
	{
		if(!turn)
		{
			community.add(deck.pop());
			turn = true;
			return true;
		}
		return false;
	}
	boolean dealRiver()
	{
		if(!river)
		{
			community.add(deck.pop());
			river = true;
			return true;
		}
		return false;
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
