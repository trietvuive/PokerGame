import java.util.List;

public class Table {
	int NumOfPlayers;
	Player button = null;
	Deck deck;
	boolean holdem = true;
	private Community community;

	Table(int NumOfPlayers, boolean holdem) {
		deck = new Deck();
		this.holdem = holdem;
		this.NumOfPlayers = NumOfPlayers;
		button = new Player();
		Player first = button;
		for(int i = 1;i<NumOfPlayers;i++)
		{
			Player a = new Player();
			button.next = a;
			button = a;
		}
		button.next = first;
	}
	void runitback()
	{
		deck = new Deck();
		dealHands();
		dealCommunity();
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
	void dealCommunity()
	{
		community = new Community(deck);
	}
	List<Card> communityCard()
	{
		return community.getList();
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
}
