public class Runner {
	static boolean holdem = true;
	static boolean omaha = false;
	static int NumOfPlayers = 9;
	static int startingmoney = 1500;
	public static void main(String[]args)
	{
		Table table = new Table(NumOfPlayers,holdem, startingmoney);
		table.dealHands();
		System.out.println(table.getHands());
		table.initiateCommunity();
		System.out.println(table.getCommunityString());
		Graphic g = new Graphic(table);
	}
}
