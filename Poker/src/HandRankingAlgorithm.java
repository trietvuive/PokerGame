import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

enum HandRank 
{RoyalFlush(10), StraightFlush(9), FourOfAKind(8), FullHouse(7),Flush(6),Straight(5),ThreeOfAKind(4),TwoPairs(3),Pair(2),HighCard(1);
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
		Collections.sort(dorito,new SortByRank());
	}
	class SortByRank implements Comparator<Card>
	{

		@Override
		public int compare(Card o1, Card o2) {
			return o1.compare(o2);
		}
		
	}
	boolean isHighCard()
	{
		for(int i = 0;i<dorito.size()-1;i++)
		{
			if(dorito.get(i).compare(dorito.get(i+1)) == 0)
				return false;
		}
		return true;
	}
	boolean isPair()
	{
		for(int i = 0;i<dorito.size()-1;i++)
		{
			if(dorito.get(i).compare(dorito.get(i+1)) == 0)
				return true;
		}
		return false;
	}
	boolean isTwoPair()
	{
		boolean firstPair = false, secondPair = false;
		for(int i = 0;i<dorito.size()-1;i++)
		{
			if(dorito.get(i).compare(dorito.get(i+1)) == 0)
			{
				if(firstPair)secondPair = true;
				else firstPair = true;
			}
		}
		return firstPair && secondPair;
	}
	boolean isTrip()
	{
		for(int i = 0;i<dorito.size()-2;i++)
		{
			if(dorito.get(i).compare(dorito.get(i+1)) == 0 && dorito.get(i+1).compare(dorito.get(i+2)) == 0)
				return true;
		}
		return false;
	}
	boolean isStraight() 
	{
		int consecutive = 1;
		for(int i = 0;i<dorito.size()-1;i++)
		{
			if(dorito.get(i+1).compare(dorito.get(i)) == 1)
				consecutive++;
			else
				consecutive = 1;
		}
		return consecutive >= 5;
	}
	boolean isFlush()
	{
		int heart = 0, spade = 0, diamond = 0, club = 0;
		for(int i = 0;i<dorito.size();i++)
		{
			switch (dorito.get(i).suit)
			{
			case C:
				club++;
			case D:
				diamond++;
			case H:
				heart++;
			case S:
				spade++;
			default:
				break;
			}
		}
		return heart >= 5 || spade >= 5 || diamond >= 5 || club >= 5;
	}
	boolean isQuad()
	{
		int pair = 1;
		for(int i = 0;i<dorito.size();i++)
		{
			if(dorito.get(i).compare(dorito.get(i+1)) == 0)
				pair++;
			else
				pair = 1;
		}
		return pair == 4;
	}
}
