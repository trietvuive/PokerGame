import java.util.List;

public class Player {
	private Hand pocket;
	Player next;
	boolean button = false;

	Player(Hand h) {
		pocket = h;
	}

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
}
