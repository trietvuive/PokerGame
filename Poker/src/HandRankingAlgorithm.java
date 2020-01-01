import java.util.ArrayList;

enum HandRank 
{RoyalFlush(1), StraightFlush(2), FourOfAKind(3), FullHouse(4),Flush(5),Straight(6),ThreeOfAKind(7),TwoPairs(8),Pair(9),HighCard(10)
	int value;
	HandRank(int value)
	{
		this.value = value;
	}
	int compare(HandRank another)
	{
		return this.value - another.value;
	}
}
public class HandRankingAlgorithm {
	ArrayList<Card> dorito = new ArrayList<Card>();
	Hand hand;
	Community community;
	boolean notomaha = true;
	HandRankingAlgorithm(Hand h, Community c, boolean holdem)
	{
		hand = h;
		community = c;
		notomaha = holdem;
		dorito.addAll(hand.getList());
		dorito.addAll(community.getList());
	}
	boolean isPair()
	{
		return false;
	}
}
