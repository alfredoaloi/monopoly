package logic;

public class Player {

	private String name;
	private int cash;
	private int position;
	private int properties;
	private boolean human;
	private int daysInPrison;

	public Player(String name, int cash, boolean human) {
		super();
		this.name = name;
		this.cash = cash;
		this.human = human;
		position = 0;
		properties = 0;
		daysInPrison = 0;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCash() {
		return cash;
	}

	public void setCash(int cash) {
		this.cash = cash;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public int getProperties() {
		return properties;
	}

	public void setProperties(int properties) {
		this.properties = properties;
	}

	public boolean isHuman() {
		return human;
	}

	public void setHuman(boolean human) {
		this.human = human;
	}

	public int getDaysInPrison() {
		return daysInPrison;
	}

	public void setDaysInPrison(int daysInPrison) {
		this.daysInPrison = daysInPrison;
	}

	public void payToBank(int amount) {
		this.cash -= amount;
	}

	public void pickFromBank(int amount) {
		this.cash += amount;
	}

	public void payToPlayer(Player player, int amount) {
		this.cash -= amount;
		player.cash += amount;
	}

}
