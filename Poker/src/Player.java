import java.util.List;

public class Player {
	private Hand pocket;
	Player next;
	boolean button = false;
	private int money;
	private String name;

	Player() {

	}
	void assignHand(Hand h)
	{
		pocket = h;
	}
	void setButtonOn() {
		button = true;
	}

	void setButtonOff() {
		button = false;
	}

	String getHand() {
		return pocket.getHand();
	}
	List<Card> getList()
	{
		return pocket.getList();
	}
	public int getMoney() {
		return money;
	}
	public void setMoney(int money) {
		this.money = money;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
