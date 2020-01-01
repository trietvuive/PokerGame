enum Suit {S,C,H,D};
enum Rank {
	Ace("A",13),Two("2",1),Three("3",2),Four("4",3),Five("5",4),Six("6",5),Seven("7",6),Eight("8",7),Nine("9",8),Ten("10",9),Jack("J",10),Queen("Q",11),King("K",12);
	String value;
	int worth;
	Rank(String val, int worth)
	{
		value = val;
		this.worth = worth;
	}
	String getValue()
	{
		return value;
	}
	int getWorth()
	{
		return worth;
	}
	int compare(Rank other) {
		return this.worth - other.worth;
	}
	}

public class Card {
	Suit suit;
	Rank rank;
	
	Card(Suit suit,Rank rank)
	{
		this.suit = suit;
		this.rank = rank;
	}
	public String toString()
	{
		return rank.getValue()+suit.name();
	}
	int compare (Card c)
	{
		return rank.compare(c.rank);
	}
}
