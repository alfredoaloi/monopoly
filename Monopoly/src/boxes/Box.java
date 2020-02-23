package boxes;

import logic.Player;

public abstract class Box {

	private String name = "";

	public Box() {
	}

	public Box(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public abstract void onAction(Player player);

}
