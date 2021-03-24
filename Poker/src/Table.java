import java.util.ArrayList;
import java.util.List;

public class Table {
	int NumOfPlayers;
	Player button = null;
	Deck deck;
	boolean holdem = true;
	private Community community;
	Player first;

	Table(int NumOfPlayers, boolean holdem, int startingmoney) {
		deck = new Deck();
		this.holdem = holdem;
		this.NumOfPlayers = NumOfPlayers;
		button = new Player();
		button.setMoney(startingmoney);
		button.setName("Player 1");
		first = button;
		for(int i = 1;i<NumOfPlayers;i++)
		{
			Player a = new Player();
			button.next = a;
			button = a;
			a.setMoney(startingmoney);
			a.setName("Player" +Integer.toString(i+1));
		}
		button.next = first;
	}
	void runitall()
	{
		if(!finishDealing())
		{
			dealAll();
		}
		else
		{
			button = button.next;
			deck = new Deck();
			dealHands();
			initiateCommunity();
			dealAll();
		}
	}
	void runitback()
	{
		button = button.next;
		deck = new Deck();
		dealHands();
		initiateCommunity();
	}
	void dealHands()
	{
		Player first = button;
		button = button.next;
		while(!button.equals(first))
		{
			button.assignHand(new Hand(deck,holdem));
			button = button.next;
		}
		button.assignHand(new Hand(deck,holdem));
	}
	void initiateCommunity()
	{
		community = new Community(deck);
	}
	boolean dealFlop()
	{
		return community.dealFlop();
	}
	boolean dealTurn()
	{
		return community.dealTurn();
	}
	boolean dealRiver()
	{
		return community.dealRiver();
	}
	void dealAll()
	{
		dealFlop();
		dealTurn();
		dealRiver();
	}
	List<Card> communityCard()
	{
		return community.getList();
	}
	List<Card> communityCardFilter()
	{
		List<Card> comm = new ArrayList<Card>();
		for(Card c:community.getList())
		{
			if(c!=null)
				comm.add(c);
		}
		return comm;
	}
	String getHands()
	{
		StringBuilder allhands = new StringBuilder();
		Player first = button;
		Player pointer = first;
		pointer = pointer.next;
		while(!pointer.equals(first))
		{
			allhands.append(pointer.getHand());
			pointer = pointer.next;
		}
		allhands.append(pointer.getHand());
		return allhands.toString();
	}
	String getCommunityString()
	{
		return community.getCommunity();
	}
	boolean finishDealing()
	{
		return community.finishDealing();
	}
}
