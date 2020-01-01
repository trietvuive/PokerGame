
public class Runner {
	static boolean holdem = true;
	static boolean omaha = false;
	static int NumOfPlayers = 10;
public static void main(String[]args)
{
	Table table = new Table(NumOfPlayers,holdem);
	table.dealHands();
	System.out.println(table.getHands());
	table.dealCommunity();
	System.out.println(table.getCommunityString());
	Graphic g = new Graphic(table);
}
}
