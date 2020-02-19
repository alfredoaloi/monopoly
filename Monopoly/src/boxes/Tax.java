package boxes;

import logic.Player;

public class Tax extends Box {

	public Tax(String name) {
		super(name);
	}
	
	@Override
	public void onAction(Player player) {
		System.out.println("Tassa di:" + super.getName());
	}

}
