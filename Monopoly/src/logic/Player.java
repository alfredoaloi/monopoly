package logic;

public class Player {

	private String name;
	private int cash;
	private int position;
	private int properties;

	public Player() {
		name = "DEFAULT_NAME";
		cash = 0;
		position = 0;
		properties = 0;
	}

	public Player(String name, int cash) {
		super();
		this.name = name;
		this.cash = cash;
		position = 0;
		properties = 0;
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
