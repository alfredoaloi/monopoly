package boxes;

import logic.Player;

public class Prison extends Box {

	@Override
	public void onAction(Player player) {
		System.out.println("Prigione");
	}

}
